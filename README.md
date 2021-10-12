# delivery-service
+ 음식 주문을 하고 배달 서비스를 제공하는 서버입니다.
+ 음식 주문, 음식 메뉴 조회, 음식 배달 기능을 직접 구현함으로써 대규모 트래픽에도 견고한 어플레이션을 구현할 수 있드록 하고 있습니다.

## 기능

### 사용자
+ 회원 가입, 회원 정보 수정, 회원 탈퇴

### 관리자


### 사용 기술
+ Spring Boot, Gradle, Mysql, Redis, Java8, RestApi, QueryDsl, Spring Security, Jenkins, Docker, k8s

### 기술적인 집중 요소
+ 객체지향의 기본 원리와 spring의 IOC/DI , AOP, PSA 활용과 의미 있는 코드 작성
+ 라이브러리 및 기능 추가 시 이유있는 선택과 사용 목적 고려

### 브렌치 관리 전략
Git Flow를 사용하여 branch를 관리   
모든 branch는 pull request 리뷰 완료후 merge   

![git-flow-strategy](https://user-images.githubusercontent.com/29122916/83837107-79166100-a730-11ea-8744-3761ad01ca96.png)

+ master: 개발, 테스트 완료후 검증이 완료된 코드가 있는 branch
+ develop: 개발이 끝난후 issue branch를 merge
+ issue(feature): develop에서 새로운 기능을 개발 진행
+ release: issue에서 develop으로 merge하여 master에 merge전 배포하여 테스트를 진행
+ hot-fix: release, master에서 발생한 버그를 수정

> #### 브렌치 관리 전략 참고 문헌
+ https://riptutorial.com/ko/git/example/4182/gitflow-%EC%9B%8C%ED%81%AC-%ED%94%8C%EB%A1%9C%EC%9A%B0

### ERD
읽기 전용 url(비밀번호 5q5x55)
https://aquerytool.com:443/aquerymain/index/?rurl=ac7647bc-0794-46a1-8752-22f88fc8abd1


### WIKI
해당 프로젝트의 모든 정보는 [WIKI](https://github.com/0hun/delivery-service/wiki)를 통해 참고하실 수 있습니다.

