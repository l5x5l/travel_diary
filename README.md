# traily
![그래픽 이미지](https://github.com/l5x5l/travel_diary/assets/39579912/71d1da58-5225-422e-ac51-63f5b82b949d)

## 기능
- 회원가입 전 앱의 전반적인 기능을 경험할 수 있는 "둘러보기"
- 일기 작성/수정/삭제
- 작성한 일기를 지도/달력 UI로 모아보는 기능
- 푸시 알림
- 화면 잠금 기능

## 기술 스택

| 분야            | 기술 스택                                           |
|---------------|-----------------------------------------------------|
| **언어**        | `Kotlin`                                           |
| **UI**        | `Compose`                                          |
| **의존성 주입**    | `Dagger Hilt`                                          |
| **구조**        | `Clean Architecture, MVVM, MVI`              |
| **jetpack**   | `Navigation`              |
| **로컬 데이터베이스** | `Room`              |
| **비동기 처리**    | `Flow, Coroutine`              |
| **통신**        | `Retrofit2, Okhttp3`              |


## 시연 영상

일지 작성, 수정 / 비회원 둘러보기 기능 / 푸시 알림
<p>
<img width="24%" src="https://github.com/l5x5l/travel_diary/assets/39579912/bd51c286-91d0-42ff-a2c5-eb9c05dad003"/>
<img width="24%" src="https://github.com/l5x5l/travel_diary/assets/39579912/2a58489d-b8ac-4edd-8ebd-aa280545032a"/>
<img width="24%" src="https://github.com/l5x5l/travel_diary/assets/39579912/5c614c05-1a68-45d5-a10d-31997a3d64cb"/>
</p>

화면 잠금 기능 / 폴더블 기기 화면 지원
<p>
<img width="24%" src="https://github.com/l5x5l/travel_diary/assets/39579912/22579f91-aa93-4fbc-afd0-aeca02856466"/>
<img width="48%" src="https://github.com/l5x5l/travel_diary/assets/39579912/101ca059-8696-473c-91f3-3783b7d82042"/>
</p>

### 앱 구조
클린 아키텍쳐를 따라 app, presentation, domain, data 모듈로 구성되어 있습니다.
- app : 의존성 주입, application 클래스 관리
- presentation : 앱을 구성하는 화면
- domain : 앱에서 사용하는 데이터에 대한 비즈니스 로직을 처리하거나, 앱에서 사용하는 useCase를 정의
- data : 서버 및 로컬 저장소로부터 데이터를 가져오거나, 저장, 수정, 삭제하는 기능들을 정의
- core : 각 레이어 내 모듈에서 공통적으로 사용하는 기능들을 정의

![프로젝트](https://github.com/l5x5l/travel_diary/assets/39579912/07f8f70c-94f3-42fe-a305-1e4d95fada0f)


* * *

## 세부 폴더 구성 (레이어 내 모듈마다 약간의 차이가 있을 수 있습니다.)
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
- network : 네트워크 통신에 사용할 retrofit 인터페이스를 정의한다. (app 모듈에서 DI)
- local_storage : 로컬스토리지에 접근할 떄 사용할 클래스의 인터페이스를 정의한다. (app 모듈에서 DI)
- repository_impl : domain모듈에서 정의한 인터페이스를 구현한 repository 클래스
- models
  - response_data : 서버/로컬 스토리지에서 전달되는 데이터 형식
  - request_data : 서버/로컬 스토리지에 접근할 떄 사용하는 (예시-request body)의 데이터 형식
* * *

