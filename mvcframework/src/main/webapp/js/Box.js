        /*js의 2015버전인 ES6부터는 클래스가 지원되므로 다이어리를 이루는 셀을 클래스로 정의하여 재사용해본다.*/
        class Box{
            // new Box(document.querySelector(".content"), 250, 300, 100, 100, "red","")
            constructor(container, x, y, width, height, bg, msg){
                this.container=container;
                this.div=document.createElement("div");
                this.x=x;
                this.y=y;
                this.width=width;
                this.height=height;
                this.bg=bg;
                this.msg=msg;
                this.dd; // 박스가 보유할 날짜 (printNum함수로 이중 반복문 돌 때 날짜를 주입 받아야 함)

                // 스타일 적용
                this.div.style.position="absolute";
                this.div.style.left=this.x+"px";
                this.div.style.top=this.y+"px";

                this.div.style.width=this.width+"px";
                this.div.style.height=this.height+"px";

                this.div.style.backgroundColor=this.bg;
                this.div.style.borderRadius="3px";
                this.div.style.border="1px solid #cccccc";
                this.div.style.boxSizing="border-box";


                // 텍스트 반영
                this.div.innerHTML=this.msg;

                // 화면에 부착
                this.container.appendChild(this.div);

                // 마우스 오버 이벤트 연결
                this.div.addEventListener("mouseover", ()=>{
                    // 화살표 함수 내에서의 this는 상위 스코프를 가리키므로 현재 메서드에서의 상위 스코프는 Box라는 객체를 말한다.
                    this.div.style.backgroundColor="";
                });
                this.div.addEventListener("mouseout", ()=>{
                    this.div.style.backgroundColor="yellow";
                });

                // 클릭 이벤트 연결
                this.div.addEventListener(()=>{
                    alert(currentDate.getFullYear()+"년 "+(currentDate.getMonth()+1)+"월" + this.dd+"일 입니다.");
                });
            }

            // 텍스트 넣기
            setMsg(msg){
                this.div.innerText=msg;
            }

            // 날짜 대입받기
            setDate(dd){
                this.dd=dd;
            }

        }
