# image_search

카카오 이미지검색 Api(REST API)로 명령어 검색후 3xN의 리스트 표현.

통신에 사용된 라이브러리 Retrofit,
비동기 통신 처리를 위하여 Corutines이용
MVVM 패턴 사용,

통신에 사용되는 Retrofit은 싱글톤으로 사용
데이터 바인딩을 통하여, UI 결합
