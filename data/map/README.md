## data:map 모듈
- 지도와 관련된 데이터에 접근하고 이를 관리하는 모듈입니다.

### 의존성 그래프
![data_map모듈](https://github.com/l5x5l/travel_diary/assets/39579912/54a0f4c6-19d7-40cf-b86a-2c28928e9185)
- 본 모듈은 ~를 참조합니다.

### data:map 모듈 내 패키지 구조
- api : 지도 데이터 관련 retrofit api 인터페이스
- data_store : 런타임시 저장되는 지도 관련 데이터로, 타 화면에서 지도 관련 데이터의 변동이 발생했을 때 이를 반영하기 위해 사용됩니다.
- model : 서버와 통신할 때 사용할 Response 데이터 형식
- repository_impl : domain:login 모듈에 정의된 repository 인터페이스의 구현 클래스

