## domain:auth 모듈
- accessToken, refreshToken과 같은 사용자 인증 토큰과 관련된 기능이 정의된 모듈입니다.

### 의존성 그래프
![domain_auth모듈](https://github.com/l5x5l/travel_diary/assets/39579912/e53703d7-e51a-4bd3-97e5-df0a4d876de3)
- 본 모듈은 core:domain 모듈을 참조합니다.

### domain:auth 모듈 내 패키지 구조
- repository : 토큰 관련 repository 인터페이스
- usecase : 토큰 재발급, 토큰 초기화, 토큰 저장과 같은 토큰 관련 usecase
