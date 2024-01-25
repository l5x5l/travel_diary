## Core 레이어
- core 레이어의 모듈들은 각 레이어에서 사용되는 공통 기능들이 정의되어 있습니다. (presentation, domain, data)

### 의존성 그래프
![core레이어](https://github.com/l5x5l/travel_diary/assets/39579912/ba5780f9-3cf1-439c-a236-eb4873bd1fef)
- core 레이어 내 모듈들은 다른 레이어의 모듈을 참조할 수 없습니다.
- 일반적으로는 동일한 레이어 내 모듈간 참조는 허용하지 않지만, 예외로 core 레이어 내 core:presentation, core:data 모듈은 core:domain 모듈을 참조할 수 있습니다.