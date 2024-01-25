## core:domain 모듈
- domain 레이어에서 공통적으로 사용하는 기능이 정의된 모듈입니다.

### 의존성 그래프
![core_domain모듈](https://github.com/l5x5l/travel_diary/assets/39579912/0deffda6-9a62-46d2-be06-214d63b73115)
- 본 모듈은 다른 모듈을 참조하지 않습니다.

### core:domain 모듈 내 패키지 구조
- model
  - BaseResponse : 앱 내 비동기 데이터 통신에 사용되는 기본 응답 형식
  - DiaryDate : domain 레이어에서 공통적으로 사용하는 날짜 형식 (년, 월, 일)
  - ErrorCodeMapper : http 에러 코드를 문자열로 매핑하는 단일 메서드를 가진 인터페이스
