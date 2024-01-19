## data:auth 모듈
- 토큰 데이터에 접근하고 이를 관리하는 모듈입니다.

### 의존성 그래프
![data_auth모듈](https://github.com/l5x5l/travel_diary/assets/39579912/751ce67c-57e5-4839-80a2-6bf5aa86cc9d)
- 본 모듈은 domain:auth, core:domain, core:data 모듈을 참조합니다.

### data:auth 모듈 내 패키지 구조
- api : 토큰 관련 retrofit api 인터페이스
- datastore : 토큰을 저장할 로컬 DataStore
- model : 서버와 통신할 때 사용할 Response 데이터 형식
- repository : domain:auth 모듈에 정의된 repository 인터페이스의 구현 클래스
