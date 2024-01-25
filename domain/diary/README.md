## domain:diary 모듈
- 일지(일기)와 관련된 기능이 정의된 모듈입니다.

### 의존성 그래프
![domain_diary모듈](https://github.com/l5x5l/travel_diary/assets/39579912/06fcbf32-3df3-443d-b890-68cbd3e03a9a)
- 본 모듈은 core:domain 모듈을 참조합니다.

### domain:diary 모듈 내 패키지 구조
- di : app 모듈에서 에러코드 매핑 클래스 주입시 qualifier로 사용할 어노테이션 클래스
- model : 일지 및 날씨, 감정같은 일지와 관련된 데이터
- repository : 일지 관련 repository 인터페이스
- use_case : 일지 CURD, 작성 일지 개수 조회와 같은 일지 관련 usecase
