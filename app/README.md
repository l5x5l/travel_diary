## App 모듈
본 모듈은 앱 내 존재하는 모든 모듈을 참조하여 앱을 구성하는 모듈입니다. 아래 기능을 수행합니다.
- application 클래스, RootActivity, RootNavigation 정의
- hilt를 통한 의존성 주입

### 의존성 그래프
![app모듈](https://github.com/l5x5l/travel_diary/assets/39579912/c845ce09-93ac-407e-87a9-4bf1066f3dc3)
- app 모듈 자체는 본 프로젝트를 구성하는 모든 모듈을 참조하고 있으며 이는 Application 클래스에서 수행하는 초기화작업 및 의존성 주입을 위함입니다.

### 세부 패키지 구성
- di : 의존성 주입 모듈
- network : 앱에서 사용하는 okhttp3 Interceptor
- BaseApplication.kt : 어플리케이션 클래스
- DataStore.kt : 앱에서 공통적으로 사용되는 Preference DataStore
- RootActivity.kt : SAA 구조의 RootActivity
- RootDestination.kt : RootNavHost에서 사용하는 screen route 정보
- RootViewModel.kt : RootActivity의 ViewModel
