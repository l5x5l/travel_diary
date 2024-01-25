## Domain 레이어
- domain 레이어의 모듈들은 각 도메인에 대한 로직이 정의되어 있습니다.
- domain 레이어의 모듈들은 순수 kotlin 모듈입니다.

### 의존성 그래프
![domain레이어](https://github.com/l5x5l/travel_diary/assets/39579912/793b44e3-e518-4309-a00e-0068812a8700)
- domain 레이너 내 모듈들은 core:domain만을 참조할 수 있습니다.

### domain 레이어 모듈 내 패키지 구조
domain 레이어는 alarm, auth, calendar, diary, file, login, map 모듈로 세부 분리되어 있습니다. (lock 모듈은 추후 개발예정)  
각 모듈 내 세부구조는 아래와 같습니다. (*는 필수)
- di : 에러 코드를 매핑하는 ErrorCodeMapper를 주입할 때 사용하는 Qualifier 어노테이션 클래스
- model : 해당 도메인과 관련된 클래스
- usecase* : 해당 도메인과 관련된 UseCase
- repository* : 해당 도메인과 관련된 데이터를 접근하도록 하는 repository 인터페이스
- utils : 유틸 함수
