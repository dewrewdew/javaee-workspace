package com.ch.shop.controller.shop;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ch.shop.dto.GoogleUser;
import com.ch.shop.dto.OAuthClient;
import com.ch.shop.dto.OAuthTokenResponse;
import com.ch.shop.model.topcategory.TopCategoryService;

import lombok.extern.slf4j.Slf4j;

/*일반 유저가 사용하게 될 쇼핑몰쪽의 회원 관련 요청을 처리하는 컨트롤러*/
@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	private TopCategoryService topCategoryService;
	
	@Autowired
	private Map<String, OAuthClient> oauthClients;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// 회원 로그인 폼 요청 처리
	@GetMapping("/member/loginform")
	public String getLoginForm(Model model) {
		
		// 3단계 일 시키기
		List topList = topCategoryService.getList();
		// 4단계 결과 페이지로 가져갈 것이 있다면 저장하기
		model.addAttribute("topList", topList);
		
		
		
		return "shop/member/login";
	}
	
	
	// sns 로그인을 희망하는 유저들의 로그인 인증 요청 url 주소를 알려주는 컨트롤러 메서드
	
	// @PathVariable("provider")는 url의 일부를 파라미터화 시키는 기법, REST API에 사용됨
	@GetMapping("/oauth2/authorize/{provider}") // {} => url 안에 변수가 올 수 있다!
	@ResponseBody // 이 annotation을 설정해야 Dispatcher Servlet이 jsp와의 매핑을 시도하지 않는다. 반환값 그대로 가져가기만을 바라는거니까 기재해줘야함!
	public String getAuthUrl(@PathVariable("provider") String provider) throws Exception { // pathvariable의 의미 : 내가 적은 url안에 변수가 있어! 그게 바로 provider야!
		OAuthClient oAuthClient = oauthClients.get(provider);
		
		log.debug(provider+"의 로그인 요청 url은 {}", oAuthClient.getAuthorizeUrl());
		
		// 이주소를 이용하여 브라우저 사용자는 프로바이더에게 로그인을 요청해야 하는데, 이 때 요청 파라미터를 갖추어야 로그인 절차가 완료될 수 있다.
		// 요청 시 지참할 파라미터 : clientId, callback url, scope 등
		// https://accounts.google.com/o/oauth2/v2/auth?response_type=code&clientId=~~이런식으로 작성
		StringBuffer sb = new StringBuffer();
		sb.append(oAuthClient.getAuthorizeUrl()).append("?")
		.append("response_type=code") // 이 요청에 의해 인가 code를 받을 것임을 알린다.
		.append("&client_id=").append(urlEncode(oAuthClient.getClientId())) // 클라이언트 ID 추가
		.append("&redirect_uri=").append(urlEncode(oAuthClient.getRedirectUri())) // 콜백받을 주소 추가
		.append("&scope=").append(urlEncode(oAuthClient.getScope())); // 사용자 정보의 접근 범위 추가
		
		
		return sb.toString();
	}
	
	
	// 웹을 통해 파라미터 전송 시 문자열이 깨지지 않도록 인코딩 처리를 해주는 메서드
	private String urlEncode(String s) throws Exception{
		return URLEncoder.encode(s, "UTF-8");
	}
	
	/*클라이언트가 동의 화면(최초 사용자) 또는 로그인(기존) 요청이 들어오게되고, Provider가 이를 처리하는 과정에서
	 * 개발자가 등록해놓은 callback주소로 임시코드 (Authorize code)를 발급한다.*/
	@GetMapping("/login/callback/google")
	public String handleCallback(String code) {
		/*------------------------------------------------------------------------------
		 * 구글이 보내온 인증 코드를 이용하여, 나의 Client id, client Secret을 조합하여 Token을 요청하자
		 * 결국 개발자가 원하는 것은 사용자의 정보이므로 이 정보를 얻기 위해서는 토큰이 필요하므로
		 * -----------------------------------------------------------------------------
		 */
		
		log.debug("구글이 발급한 임시 코드는 {}", code);
		
		
		OAuthClient google = oauthClients.get("google");
		// 구글로부터 받은 임시코드와 나의 정보(client id, client secret)을 조합하여 구글에게 보내자!(토큰 받으려고)
		// 이 때, 구글과 같은 프로바이더와 데이터를 주고 받기 위해서는 HTTP 통신 규약을 지켜서 말을 걸 때는 머리, 몸을 구성하여 요청을 시도해야함.
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>(); // 몸체
		param.add("grant_type", "authorization_code"); // 인가코드를 이용하여 토큰을 요청하겠다는 의지를 표현하는 것!
		param.add("code", code); // 구글로부터 발급 받은 임시코드를 그대로 추가
		param.add("client_id", google.getClientId()); // 클라이언트 아이디 추가
		param.add("client_secret", google.getClientSecret()); // 클라이언트 비밀번호 추가
		param.add("redirect_uri", google.getRedirectUri()); // callback uri
		
		HttpHeaders headers = new HttpHeaders(); // 머리
		// 아래와 같이 전송 파라미터에 대한 contentType을 명시하면 key=value&key=value 방식의 데이터쌍으로 자동으로 변환해줌
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		// 머리와 본문(몸)을 합쳐서 하나의 HTTP 요청 엔티티로 결합
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity< >(param, headers); // ()기준 왼쪽이 몸 오른쪽이 머리
		
		// 구글에 요청 시작! 스프링에서는 Http 요청 후 그 응답 정보를 java 객체와 자동으로 매핑해주는 편리한 객체를 지원해주는데
		// 그 객체가 바로 RestTemplate (http 요청 능력 + jack능력)
		// 구글 서버로 http의 머리랑 몸 날리기!()기준 구글의 토큰 발급주소, 머리와 몸을 합친 요청 객체, 결과를 받을 클래스 기재
		ResponseEntity<OAuthTokenResponse> response=restTemplate.postForEntity(google.getTokenUrl(), request, OAuthTokenResponse.class);
		log.debug("구글로부터 받은 응답정보는 {}", response.getBody());


		// 얻어진 토큰으로 구글에 회원정보를 요청해보기
		OAuthTokenResponse responseBody =response.getBody();
		String access_token= responseBody.getAccess_token();
		log.debug("구글로부터 받은 엑세스 토큰은 {}", access_token);
		
		// 회원정보 가져오기
		// 구글에 요청을 시도하려면 역시나 이번에도 Http 프로토콜 형식을 갖춰야 함
		HttpHeaders userInfoHeaders = new HttpHeaders();
		// 내가 바로 토큰을 가진 자임을 알리는 헤더 속성값을 넣으면 됨!
		userInfoHeaders.add("Authorization", "Bearer "+access_token); // bearer 옆에 한칸 일부러 띄워둔 것!! 필수!!(정해져있는 표준 방법임)
		HttpEntity<String> userInfoRequest = new HttpEntity<>("", userInfoHeaders); // 몸은 없다고 표현할 때 ""넣으면 됨!
		ResponseEntity<GoogleUser> userInfoResponse =restTemplate.exchange(google.getUserInfoUrl(), HttpMethod.GET, userInfoRequest, GoogleUser.class); // 서버로부터 데이터를 가져올때는 get받식(exchange) 보낼때는 post방식.
		
		log.debug("사용자 정보는 {}", userInfoResponse);
		
		
		return null;
	}
	
}
