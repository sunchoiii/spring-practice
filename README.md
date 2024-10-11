# 🛠️ SPRING PLUS 개인과제 주요 코드

### JPQL 사용 
- 할 일 검색 시 `weather` 조건으로도 검색
    - `weather` 조건은 있을 수도 있고, 없을 수도 있다.
- 할 일 검색 시 수정일 기준으로 기간 검색이 가능
    - 기간의 시작과 끝 조건은 있을 수도 있고, 없을 수도 있다.
<img width="843" alt="스크린샷 2024-10-11 오전 9 17 34" src="https://github.com/user-attachments/assets/f08af09c-8aee-443d-bd62-7dba1cedb0e9">

<br>
<br>

### N+1문제 해결
- `CommentController` 클래스의 `getComments()` API를 호출할 때 N+1 문제를 해결
<img width="647" alt="스크린샷 2024-10-11 오전 10 05 07" src="https://github.com/user-attachments/assets/d0fe5a56-49f4-466e-abb9-d3e2f9db8fdf">

<br>
<br>

###  Spring Security
- 기존 `Filter`와 `Argument Resolver`를 사용하던 코드들을 Spring Security로 변경
    - 접근 권한 및 유저 권한 기능은 그대로 유지한다.
    - 권한은 Spring Security의 기능을 사용한다.
    - 토큰 기반 인증 방식은 유지하고 JWT는 그대로 사용한다.
<img width="839" alt="스크린샷 2024-10-11 오전 10 08 48" src="https://github.com/user-attachments/assets/e9ccec2d-ba49-46d1-a32e-367260c9fc1a">
<img width="915" alt="스크린샷 2024-10-11 오전 10 10 02" src="https://github.com/user-attachments/assets/4bc3fa5c-188a-4aab-b843-7accd3b818dd">
<img width="721" alt="스크린샷 2024-10-11 오전 10 10 09" src="https://github.com/user-attachments/assets/e4ba7c92-c66f-4880-869e-c26c50596a55">

<br>
<br>

### QueryDSL
- 새로운 API로 생성
- 검색 조건
    - 검색 키워드로 일정의 제목을 검색 가능
        - 제목은 부분적으로 일치해도 검색이 가능하다.
    - 일정의 생성일 범위로 검색 가능
        - 일정을 생성일 최신순으로 정렬한다.
    - 담당자의 닉네임으로도 검색이 가능
        - 닉네임은 부분적으로 일치해도 검색이 가능하다.
- 검색 결과 조건
    - 일정에 대한 모든 정보가 아닌, 제목만 넣는다.
    - 해당 일정의 담당자 수와 총 댓글 개수를 넣는다.
- 검색 결과는 페이징 처리되어 반환
<img width="729" alt="스크린샷 2024-10-11 오전 10 13 23" src="https://github.com/user-attachments/assets/70e90984-f068-495d-9e71-82075de9cf30">
<img width="600" alt="스크린샷 2024-10-11 오전 10 13 13" src="https://github.com/user-attachments/assets/e663779d-7e0f-4465-b3b2-1a182f0d4557">

<br>
<br>

### Transaction 전파범위
- 매니저 등록 요청을 기록하는 로그 테이블 생성
    - DB 테이블명: `log`
- 매니저 등록과는 별개로 로그 테이블에는 항상 요청 로그가 남기기
    - 매니저 등록은 실패할 수 있지만, 로그는 반드시 저장되어야 한다.
    - 로그 생성 시간은 반드시 필요하다.
<img width="639" alt="스크린샷 2024-10-11 오전 10 15 00" src="https://github.com/user-attachments/assets/708643d6-08a9-46d7-9338-150ea9f87d67">
<img width="651" alt="스크린샷 2024-10-11 오전 10 15 29" src="https://github.com/user-attachments/assets/ddb13245-d026-4ae1-94f2-ce4267f3aaeb">

<br>
<br>
<br>


# 💥 SPRING PLUS 개인과제 트러블슈팅 기록
https://velog.io/@csun1047/SPRING-PLUS-%EA%B0%9C%EC%9D%B8%EA%B3%BC%EC%A0%9C-%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85

<br>
<br>
<br>
