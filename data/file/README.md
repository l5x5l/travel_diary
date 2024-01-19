## data:file 모듈
- 파일 저장소에 접근하여 파일을 업로드하는 모듈입니다.

### 의존성 그래프
![data_file모듈](https://github.com/l5x5l/travel_diary/assets/39579912/bc83db85-8ee6-42da-8ee8-2235ed6ef60b)
- 본 모듈은 domain:file, core:domain, core:data 모듈을 참조합니다.

### data:file 모듈 내 패키지 구조
- api : 파일 업로드 관련 retrofit api 인터페이스
- model : 서버와 통신할 때 사용할 Response, RequestBody 데이터 형식
- repository : domain:file 모듈에 정의된 repository 인터페이스의 구현 클래스
