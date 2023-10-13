# 원티드 프리온보딩 백엔드 인턴십 선발과제

## 사용 기술
- Java 17
- SpringBoot 3.1.4
- H2 Database
- Spring Data JPA

## API
- 채용공고 등록
    - endpoint: `POST, /job-postings`
    - 성공응답
        - http status: 201
    - 실패응답
        - 존재하지 않는 회사 아이디로 요청 시
            - http status: 404
            - body

                ```json
                {"code":"E1000","errorMessage":"존재하지 않는 리소스입니다."}
                ```

- 채용공고 수정
    - endpoint: `PATCH, /job-postings/{job-posting-id}`
    - 성공응답
        - http status: 200
    - 실패응답
        - 존재하지 않는 회사 아이디로 요청 시
            - http status: 404
            - body

                ```json
                {"code":"E1000","errorMessage":"존재하지 않는 리소스입니다."}
                ```

- 채용공고 삭제
    - endpoint: `DELETE, /job-postings/{job-posting-id}`
    - 성공응답
        - http status: 204
- 채용공고 목록 가져오기
    - endpoint: `GET, /job-postings` or `GET, /job-postings?search={검색어}`
    - 성공응답
        - http status: 200
        - body

            ```json
            {
              "jobPostings": [
                {
                  "jobPostingId": 1,
                  "companyName": "원티드랩",
                  "nationality": "한국",
                  "region": "서울",
                  "position": "백엔드 개발자",
                  "reward": 10000,
                  "skill": "Python",
                  "detail": "원티드랩에서 백엔드 주니어 개발자를 채용합니다.",
                  "anotherJobPostingIds": [
                    2
                  ]
                },
                {
                  "jobPostingId": 2,
                  "companyName": "원티드랩",
                  "nationality": "한국",
                  "region": "서울",
                  "position": "프론트엔드 개발자",
                  "reward": 20000,
                  "skill": "React",
                  "detail": "원티드랩에서 프론트엔드 주니어 개발자를 채용합니다.",
                  "anotherJobPostingIds": [
                    1
                  ]
                }
              ]
            }
            ```

- 채용공고 지원
    - endpoint: `POST, /applies`
    - 성공응답
        - http status: 201
    - 실패응답
        - 사용자가 이미 지원한 회사에 중복 지원 시
            - http status: 409
            - body

                ```json
                {"code":"E1001","errorMessage":"이미 해당 공고에 지원했습니다. 중복해서 지원할 수 없습니다."}
                ```

## ERD
![erd.png](assets/erd.png)

## Git commit 메시지 컨벤션
- [udacity 스타일](https://udacity.github.io/git-styleguide/)을 따랐습니다.

## 테스트 구조
- Integration Tests
    - controllers/...IntegrationTest.java 파일 이름 컨벤션을 갖습니다.
    - API 모든 엔드포인트를 대상으로 작성되었습니다.
