# travel_diary (가명)

### 앱 구조
클린 아키텍쳐를 따라 app, presentation, domain, data 모듈로 구성되어 있습니다.
- app : 의존성 주입, application 클래스 관리
- presentation : 앱을 구성하는 화면
- domain : 앱에서 사용하는 데이터에 대한 비즈니스 로직을 처리하거나, 앱에서 사용하는 useCase를 정의
- data : 서버 및 로컬 저장소로부터 데이터를 가져오거나, 저장, 수정, 삭제하는 기능들을 정의

* * *

## 세부 폴더 구성 (개발 과정에서 변경될 수 있습니다.)
### presentation
- screens : 화면에 대한 Activity, ViewModel코드
- components : 화면을 구성하는 Button과 같은 선정의된 UI요소들
  - atom : 단일버튼, 커스텀된 EditText와 같은 한가지의 요소로만 구성된 UI
  - block : 둘 이상의 atom혹은 block의 조합으로 구성된 UI
  - template : 미리 정의된 모달창과 같이 하나만으로 어느정도의 화면이 구성되는 UI
- models : presentation 모듈에서만 사용하는 클래스/데이터

### domain
domain 모듈은 다루는 기능(로그인, 일기 데이터 작성/조회/수정 등)별로 분리되며, 한 기능에 대한 폴더내부구조는 아래와 같다.
- models : 해당 폴더 내에서 사용하는 클래스/데이터
- repository : data모듈에서 구현할 repository의 인터페이스 (app 모듈에서 DI)
- use_case : viewModel에서 호출하는 useCase
- utils : 해당 폴더 내에서 사용하는 유틸 함수

### data
공통 폴더
- network : 네트워크 통신에 사용할 클래스의 인터페이스를 정의한다. (app 모듈에서 DI)
- local_storage : 로컬스토리지에 접근할 떄 사용할 클래스의 인터페이스를 정의한다. (app 모듈에서 DI)
위 공통폴더를 제외한 나머지 폴터는 data 모듈은 다루는 데이터(사용자 데이터, 일기 데이터 등)에 따라 생성되며, 한 데이터에 대한 폴더내부구조는 아래와 같다.
- repository_impl : domain모듈에서 정의한 인터페이스를 구현한 repository 클래스
- models
  - response_data : 서버/로컬 스토리지에서 전달되는 데이터 형식
  - request_data : 서버/로컬 스토리지에 접근할 떄 사용하는 (예시-request body)의 데이터 형식
* * *
