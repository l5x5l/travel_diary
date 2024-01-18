## core:data 모듈
- data 레이어에서 공통적으로 사용하는 기능이 정의된 모듈입니다.

### 의존성 그래프
![core_data모듈](https://github.com/l5x5l/travel_diary/assets/39579912/7817a81a-9c4e-4e6b-8a93-e3ec47aebd5c)
- 본 모듈은 core:domain 모듈을 참조합니다. (core:domain 모듈 내 BaseResponse를 사용)

### core:data 모듈 내 패키지 구조
- demo_data_source : 비 로그인시(둘러보기 기능) 사용할 더미 데이터가 담긴 데이터 저장소
- model 
  - ListResponseData : 사전 정의된 서버에서 List형 데이터를 받을 때 사용할 Response 데이터 형식
  - ResponseData : 데이터 레이어에서 공통적으로 사용되는 서버 Dto 데이터 클래스
- utils : 서버에서 받은 Response를 BaseResponse로 매핑하는 유틸 함수
