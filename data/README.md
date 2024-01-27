## Data 레이어
- data 레이어는 해당 도메인에 대한 데이터 저장 및 접근 기능들이 정의되어 있습니다.

### 의존성 그래프
![데이터레이어](https://github.com/l5x5l/travel_diary/assets/39579912/53f8a621-40ba-4068-8b3c-d6bf657cb476)
- data 레이너 내 모듈들은 domain 레이어의 모듈과 core 레이어내 core:domain, core:data 모듈을 참조할 수 있습니다.

### data 레이어 모듈 내 패키지 구조
data 레이어는 alarm, auth, calendar, diary, file, login, map 모듈로 세부 분리되어 있습니다. (lock 모듈은 추후 개발예정)  
각 모듈 내 세부구조는 아래와 같습니다. (*는 필수)
- api : 해당 도메인과 관련된 retrofit interface
- data_source : 서버, 로컬 저장소와 같이 달력 데이터를 원천적으로 가지고 있거나 해당 원천에 접근하는 클래스
- data_cache_store : 해당 도메인과 관련된 데이터를 런타임시 저장하는 데이터 저장소 클래스
- model : api의 requestBody, responseBody를 정의한 데이터 클래스
- repository_impl(repository)* : domain 모듈에서 선언한 repository 인터페이스의 구현 클래스
- utils : data 레이어의 데이터를 domain 레이어의 데이터로 매핑하는 매핑 함수
