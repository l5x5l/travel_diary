## domain:file 모듈
- 파일 업로드, 파일 크기조정과 같은 파일 관련 기능이 정의된 모듈입니다.

### 의존성 그래프
![domain_file모듈](https://github.com/l5x5l/travel_diary/assets/39579912/cb5dbbac-5534-42af-ab3e-21f55c9706e6)
- 본 모듈은 core:domain 모듈을 참조합니다.

### domain:file 모듈 내 패키지 구조
- model : 파일 타입, 파일 정보 클래스와 파일 크기조정 관련 인터페이스
- repository : 파일 업로드 관련 repository
- usecase : 파일 업로드 usecase
