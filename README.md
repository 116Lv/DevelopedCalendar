# DevelopedCalendar
# 📅 일정 관리 앱(업그레이드 버전)

> **Spring Boot와 JPA를 활용한 객체 지향 일정 관리 시스템** 
> 본 프로젝트는 단순 CRUD를 넘어 **사용자 인증(Session), 비밀번호 암호화(BCrypt), 전역 예외 처리(Global Exception Handling)** 등 실무적인 백엔드 요소를 포함하여 설계되었습니다. 3-Layer Architecture를 통해 유지보수성을 극대화한 RESTful API 서버입니다.

---

## 🛠 기술 스택 (Tech Stack)

- **Language:** Java 17
- **Framework & Environment:**
  - **Spring Boot 4:** RESTful API 환경 구축 및 의존성 주입(DI).
  - **Spring Data JPA:** 객체-데이터베이스 매핑 및 쿼리 메서드 기반의 데이터 처리.
  - **Bean Validation:** `@Valid` 어노테이션을 활용한 계층 간 데이터 유효성 검증.
- **Database:** MySQL
- **Security:**
  - **BCrypt:** 강력한 해시 알고리즘을 통한 비밀번호 단방향 암호화.
  - **Session-Based Auth:** `HttpSession`과 `Interceptor`를 활용한 로그인 유지 및 인가 처리.
- **Tools:** Postman (API Testing)

---

## 🚀 주요 기능 (Key Features)

### 1. 일정 및 댓글 관리 (Schedules & Comments)
- **연관 관계 매핑:** 유저-일정-댓글 간의 **1:N, N:1 양방향 연관 관계**를 통해 객체 지향적 데이터 관리.
- **영속성 전이(Cascade):** 일정 삭제 시 해당 일정에 달린 모든 댓글이 자동으로 삭제되는 `CascadeType.ALL` 적용.
- **지연 로딩(Lazy Loading):** 불필요한 조인을 방지하여 조회 성능 최적화.

### 2. 사용자 인증 및 보안 (Auth & Security)
- **비밀번호 암호화:** `BCrypt` 라이브러리를 활용해 평문 비밀번호가 아닌 해시값으로 안전하게 저장.
- **로그인 필터링:** `HandlerInterceptor`를 구현하여 로그인되지 않은 사용자의 특정 API 접근 제한.
- **세션 관리:** `SessionUser` DTO를 활용하여 민감 정보(비밀번호 등)를 제외한 유저 정보만 메모리에서 관리.

### 3. 고도화된 시스템 설계
- **전역 예외 처리:** `@RestControllerAdvice`를 통해 애플리케이션 전역의 에러를 공통된 형식(JSON)으로 응답.
- **JPA Auditing:** 데이터의 생성 및 수정 시간을 `BaseEntity` 또는 엔티티 내 설정으로 자동 기록.

---

## 🏗 프로젝트 구조 (Architecture)

```text
src/main/java/com/example/developedcalendar/
├── core/
│   ├── config/          # 설정 (WebMvc, PasswordEncoder, Interceptor)
│   └── exception/       # 전역 예외 처리 (GlobalExceptionHandler, CustomException)
├── controller/          # REST Controller (API 엔드포인트)
├── service/             # Business Logic (Service Interface & Impl)
├── repository/          # Spring Data JPA Repository
├── entity/              # JPA Entities (User, Schedule, Comment)
└── dto/                 # Data Transfer Objects (Request, Response, Session)
```

---

## 🛠 주요 기술 개념 (Technical Concepts)

### **1. DTO와 Entity의 분리**
엔티티가 API 응답에 직접 노출되는 것을 방지하여 DB 구조를 보호하고, 순환 참조 문제를 예방했습니다. 서비스 계층에서 엔티티를 DTO로 변환하여 전달하는 방식을 채택했습니다.

### **2. 객체 지향 쿼리 메서드**
`CommentRepository`에서 `findAllBySchedule_ScheduleId`와 같이 객체 그래프 탐색 방식을 사용하여 JPA의 장점을 극대화하고 데이터 무결성을 높였습니다.

### **3. 커스텀 예외 처리**
`IllegalArgumentException`과 같은 공통 예외 외에도 `UnauthorizedException`, `ResourceNotFoundException` 등을 커스터마이징하여 상황별로 적절한 HTTP 상태 코드를 반환합니다.

---

## 🧐 트러블슈팅 (Troubleshooting)

**문제:** 댓글 조회 시 연결된 일정 ID를 찾지 못하는 `PropertyReferenceException` 발생.

**원인:** `Comment` 엔티티 내부에서 `Schedule`을 객체로 관리하고 있음에도, 리포지토리 메서드 명칭을 단순 필드명인 `findAllByScheduleId`로 작성하여 JPA가 경로를 인식하지 못함.

**해결:** JPA의 예약어와 객체 탐색 규칙에 따라 메서드 명을 **`findAllBySchedule_ScheduleId`**로 변경하여, `Comment -> Schedule -> scheduleId` 순으로 정상 접근하도록 수정함.

---

## 📅 API 명세서 목록

### 1. 일정 관리 (Schedules)
| 기능       | Method | URL                        | request  | response | 상태코드 |
|:---------| :---: |:---------------------------|:---------| :--- |:----:|
| 일정 등록    | POST | /schedules                 | 요청 body  | 등록 정보 | 201: 정상 등록  |
| 일정 전체 조회 | GET | /schedules                 | -        | 다건 응답 정보 | 200: 정상 조회  |
| 일정 단건 조회 | GET | /schedules/{scheduleId}    | 요청 param | 단건 응답 정보 | 200: 정상 조회  |
| 일정 수정    | PUT | /schedules/{scheduleId}    | 요청 body  | 수정 정보 | 200: 정상 수정  |
| 일정 삭제    | DELETE | /schedules/{scheduleId}    | 요청 param | - | 204: 정상 삭제  |

#### **일정 등록**
- **요청 Body:**
  ```json
  {
    "title": "밥 약속",
    "content": "대학교 친구들과의 식사모임"
  }
  ```
- **응답 (201):** 생성된 일정 정보 반환.

#### **일정 전체 조회**
- **설명:** 등록된 모든 일정을 ID 오름차순으로 조회합니다.
- **응답 (200):** 일정 목록 리스트 반환.

#### **일정 단건 조회**
- **응답 (200):** 특정 일정 상세 정보 반환.

#### **일정 수정**
- **요청 Body:**
  ```json
  {
    "title": "술 약속",
    "content": "모이는 날짜 변경으로 저녁에 술 한잔"
  }
  ```
- **응답 (200):** 수정된 일정 정보 반환.

#### **일정 삭제**
- **응답 (204):** No Content.

---

### 2. 유저 관리 (Users)
| 기능       | Method | URL             | request  | response | 상태코드 |
|:---------| :---: |:----------------|:---------| :--- |:----:|
| 유저 등록    | POST | /users          | 요청 body  | 등록 정보 | 201: 정상 등록  |
| 유저 전체 조회 | GET | /users          | -        | 단건 응답 정보 | 200: 정상 조회  |
| 유저 단건 조회 | GET | /users/{userId} | 요청 param | 단건 응답 정보 | 200: 정상 조회  |
| 유저 수정    | PUT | /users/{userId} | 요청 body  | 수정 정보 | 200: 정상 수정  |
| 유저 삭제    | DELETE | /users/{userId} | 요청 param | - | 204: 정상 삭제  |

#### **유저 등록**
- **설명:** 신규 유저 정보를 등록합니다. 비밀번호는 암호화되어 저장됩니다.
- **요청 Body:**
  ```json
  {
    "userName": "홍길동",
    "email": "hong@example.com",
    "password": "abcd1234"
  }
  ```
- **응답 (201):** 가입된 유저 정보 (ID, 이름, 이메일, 가입일) 반환.

#### **유저 전체 조회**
- **응답 (200):** 가입된 전체 유저 리스트 반환 (최신순).

#### **유저 단건 조회**
- **응답 (200):** 해당 ID를 가진 유저의 상세 정보 반환.

#### **유저 수정**
- **설명:** 유저의 이름 또는 이메일을 수정합니다. (비밀번호 확인 필요 로직 포함 시 해당 필드 추가)
- **요청 Body:**
  ```json
  {
    "userName": "임꺽정",
    "email": "im@example.com"
  }
  ```
- **응답 (200):** 수정된 유저 정보 반환.

#### **유저 삭제**
- **설명:** 유저를 삭제(탈퇴) 처리하며, 관련 데이터(일정, 댓글)가 Cascade 설정에 의해 함께 삭제됩니다.
- **응답 (204):** No Content.

---

#### 3. 기타 기능 관리 (인증/인가)
| 기능 | Method | URL              | request | response | 상태코드 |
| :--- | :---: |:-----------------| :--- | :--- | :--- |
| 로그인 | POST | /users/login     | 요청 body (이메일, 비밀번호) | 로그인 성공 메시지 | 200: 로그인 성공 |

#### **로그인**
- **URL:** `/users/login`
- **설명:** 인증 후 세션을 생성합니다.
- **요청 Body:**
  ```json
  {
    "email": "hong@example.com",
    "password": "abcd1234"
  }
  ```
- **응답 (200):** `"로그인 성공"` 메시지 반환.

---

#### 4. 댓글 관리 (Users)
| 기능        | Method | URL                                          | request  | response | 상태코드 |
|:----------| :---: |:---------------------------------------------|:---------| :--- |:----:|
| 댓글 등록     | POST | /schedules/{scheduleId}/comments             | 요청 body  | 등록 정보 | 201: 정상 등록  |
| 일정별 댓글 조회 | GET | /schedules/{scheduleId}/comments             | -        | 단건 응답 정보 | 200: 정상 조회  |
| 댓글 수정     | PUT | /schedules/{scheduleId}/comments/{commentId} | 요청 body  | 수정 정보 | 200: 정상 수정  |
| 댓글 삭제     | DELETE | /schedules/{scheduleId}/comments/{commentId} | 요청 param | - | 204: 정상 삭제  |

#### **댓글 등록**
- **요청 Body:**
  ```json
  {
    "content": "좋다! 바로 ㄱㄱ"
  }
  ```
- **응답 (201):** 등록된 댓글 정보 반환.

#### **일정별 댓글 조회**
- **설명:** 특정 일정에 달린 모든 댓글을 조회합니다.
- **응답 (200):** 해당 일정의 댓글 리스트 반환.

#### **댓글 수정**
- **요청 Body:**
  ```json
  {
    "content": "ㅇㅋㅇㅋ"
  }
  ```
- **응답 (200):** 수정된 댓글 정보 반환.

#### **댓글 삭제**
- **응답 (204):** No Content.

---