## data:lock 모듈
- 앱 화면 잠금 관련 데이터인 화면 잠금 비밀번호 저장소에 접근하는 모듈입니다.

### 의존성 그래프
![data_lock모듈](https://github.com/l5x5l/travel_diary/assets/39579912/4a78006b-ead7-4996-81c6-3150ce1be1dc)
- 본 모듈은 domain:lock, core:domain, core:data 모듈을 참조합니다.

### data:lock 모듈 내 패키지 구조
- data_source : 잠금 관련 데이터를 원천적으로 가지고 있거나 해당 원천에 접근하는 클래스
- repository_impl : domain:lock 모듈에 정의된 repository 인터페이스의 구현 클래스
