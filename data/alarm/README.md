## data:alarm 모듈
- 알람 기능과 관련된 데이터에 접근하고 이를 관리하는 모듈입니다.

### 의존성 그래프
![data_alarm모듈](https://github.com/l5x5l/travel_diary/assets/39579912/0d39d92f-c25d-4666-b94b-2ecad6779c3e)
- 본 모듈은 domain:alarm, core:data 모듈을 참조합니다.

### data:alarm 모듈 내 패키지 구조
- datastore : 알림 관련 로컬 DataStore
- repository : domain:alarm 모듈에 정의된 repository의 구현 클래스
