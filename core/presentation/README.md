## core:presentation 모듈
- presentation 레이어에서 공통적으로 사용하는 기능이 정의된 모듈입니다.

### 의존성 그래프
![core_presentation모듈](https://github.com/l5x5l/travel_diary/assets/39579912/79058b62-a151-4800-a4e9-77e2522d8908)
- 본 모듈은 다른 모듈을 참조하지 않습니다.

### core:presentation 모듈 내 패키지 구조
- logger : 화면 기록 및 이벤트 호출을 로깅하기 위한 클래스 및 인터페이스
- model : toast, snack bar같은 앱 전체적으로 표시할 메세지를 호출하는 MessageTrigger 인터페이스 및 구현체 
