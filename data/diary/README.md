## data:diary 모듈
- 일지와 관련된 데이터에 접근하고 이를 관리하는 모듈입니다.

### 의존성 그래프
![data_diary모듈](https://github.com/l5x5l/travel_diary/assets/39579912/c02ed646-5d79-4f82-9ffe-e37315f4c99e)
- 본 모듈은 domain:diary, domain:map, core:domain, core:data 모듈을 참조합니다.

### data:diary 모듈 내 패키지 구조
- api : 일지 도메인 관련 retrofit api 인터페이스
- data_source : 서버, 로컬 저장소와 같이 달력 데이터를 원천적으로 가지고 있거나 해당 원천에 접근하는 클래스
- model : 서버와 통신할 때 사용할 Response, RequestBody 데이터 형식
- repository_impl : domain:diary 모듈에 정의된 repository 인터페이스의 구현 클래스
- utils : 서버로부터 받은 Dto 데이터를 domain 레이어 내 데이터 형식으로 전환하는 Mapping 함수
