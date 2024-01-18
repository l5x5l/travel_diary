## domain:login 모듈
- 로그인 및 회원가입과 관련된 기능이 정의된 모듈입니다.

### 의존성 그래프
![domain_login모듈](https://github.com/l5x5l/travel_diary/assets/39579912/7aac2809-feef-485f-a41f-01a1b3f17da2)
- 본 모듈은 core:domain 모듈을 참조합니다.

### domain:login 모듈 내 패키지 구조
- di : app 모듈에서 에러코드 매핑 클래스 주입시 qualifier로 사용할 어노테이션 클래스
- model : 로그인 및 회원가입 관련 데이터 클래스
- repository : 로그인 및 회원가입 관련 repository 인터페이스
- use_case : 로그인 및 회원가입 관련 usecase
- utils : 이메일 형식 체크, 공백 제거와 같이 로그인 및 회원가입 과정에 사용되는 유틸 함수
