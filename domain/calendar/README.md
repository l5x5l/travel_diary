## domain:calendar 모듈
- 달력 데이터와 관련된 기능이 정의된 모듈입니다.

### 의존성 그래프
![domain_calendar모듈](https://github.com/l5x5l/travel_diary/assets/39579912/e67e8ae0-e03e-4637-b06a-7f598c37ccdf)
- 본 모듈은 core:domain 모듈을 참조합니다.

### domain:calendar 모듈 내 패키지 구조
- model : 달력 데이터와 관련된 데이터 클래스
- repository : 달력 관련 repository 인터페이스
- usecase : 달력 데이터 관련 usecase
- utils : 날짜 비교 및 월별 총 날의 개수와 같은 달력 관련 유틸 함수
