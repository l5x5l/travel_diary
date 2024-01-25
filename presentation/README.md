## Presentation 레이어/모듈
- presentation 레이어는 ui와 직접적으로 관련이 있는 기능을 수행하는 레이어입니다.
  - 앱 알림, 권한 요청과 같은 android context가 필요한 작업
  - composable ui
  - 앱 테마 속성

### 의존성 그래프
![presentation레이어](https://github.com/l5x5l/travel_diary/assets/39579912/928c5265-aa6e-4e4e-968a-5dd225c9b26d)
![presentation모듈](https://github.com/l5x5l/travel_diary/assets/39579912/e89d3265-d33b-4b76-98f2-aec53f7f7456)
- presentation 레이어는 domain 레이어 내 모듈과, core 레이어 내 core:presentation, core:domain 모듈을 참조할 수 있습니다.

### 세부 패키지 구성
- alarm : 알람을 발생시키고, 알람을 설정/취소할 때 사용되는 클래스
- components : 앱에서 공통적으로 사용되는 composable ui로 규모에 따라 아래 3개로 분류됩니다.
  - atom : 단순 버튼, 텍스트와 같이 하나의 요소로만 구성된 최소 단위의 composable
  - block : 둘 이상의 atom으로 구성된 composable
  - template : dialog처럼 독립적으로 사용되는 요소이거나 화면의 대부분을 차지하는 composable
- models : 
- screens : 앱에서 사용하는 화면이 정의되어 있으며, 하나의 화면은 아래처럼 구성되어 있습니다.(*는 필수)
  - component : 해당 화면에서만 사용되는 composable ui
  - model : 해당 화면에서만 사용되는 데이터 클래스
  - util : 해당 화면에서만 사용되는 유틸 함수
  - ~~Screen* : 화면 composable
  - ~~ViewModel : 해당 화면에서 사용되는 viewModel
  단, nestedNavHost가 존재하는 경우(home, login_home) 해당 패키지 내부에 추가적인 screen 패키지가 존재할 수 있습니다.
- ui.theme : 앱에서 사용되는 테마 속성
- utils : presentation layer에서 사용되는 유틸 함수
