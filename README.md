# DevelopedCalendar
# 📅 일정 관리 앱(업그레이드 버전)

> **Spring Boot와 JPA를 활용한 일정 관리 시스템**
> 3-Layer Architecture를 준수하여 설계되었으며, 사용자가 일정을 등록, 조회, 수정, 삭제(CRUD)할 수 있는 RESTful API 서버입니다. JPA를 통한 데이터 영속성 관리와 비밀번호 기반의 보안 검증 프로세스를 제공합니다.

---

## 🛠 기술 스택 (Tech Stack)

- **Language:** Java 17
- **Framework & Environment:**
    - **Spring Boot:** RESTful API 환경 구축 및 의존성 주입(DI)을 통한 객체 관리.
    - **Spring Data JPA:** 인터페이스 기반의 데이터 처리 및 영속성 로직 구현.
- **Tools:**
    - **Postman:** API 엔드포인트 호출을 통한 데이터 생성, 조회, 수정, 삭제 기능 검증 및 결과 확인.
- **Concepts:**
    - **3-Layer Architecture:** 계층 분리(Controller, Service, Repository)를 통한 객체지향적 설계 및 유지보수성 향상.
    - **JPA Auditing:** 데이터의 생성 및 수정 시간을 자동으로 기록하여 데이터 무결성 유지.
    - **Data Security:** DTO(Data Transfer Object) 활용을 통해 응답 시 비밀번호 등 민감 정보 노출을 차단하고, 권한 검증 로직 구현.

---

## 🚀 주요 기능 (Key Features)

### 1. 일정 생성, 조회, 수정, 삭제

---

## 🏗 프로젝트 구조 (Architecture)



---

## 🛠 주요 기술 개념 (Technical Concepts)



---

## 🧐 트러블슈팅 (Troubleshooting)



**문제:** 

**원인:** 

**해결:** 

---

# API 명세서

domain: http://localhost:8080/schedules

url: /schedules

method: POST

## 01. 설명

---

새로운 일정 정보를 작성하는 API

## 02. 요청(Request)

---

### a. Parameter & Querystring

```

```

| 이름 | 데이터타입 | 설명 |
| --- | --- | --- |
|  |  |  |

### b. request headers

```sql

```

| 이름           | 데이터타입  | 설명               |
|--------------|--------|------------------|
| Content-Type | String | application/json |

### c. request body

```json
{
  "title": "직관데이",
  "content": "창원에 야구보러 가는날",
  "writerName": "이병우"
}
```

| 이름         | 데이터타입  | 설명    |
|------------|--------|-------|
| title      | String | 할일 제목 |
| content    | String | 할일 내용 |
| writerName | String | 작성자명  |

## 03. 응답(response)

---

### a. response header

```sql

```

| 이름 | 데이터타입  | 설명 |
| --- |--------| --- |
| Content-Type | String | application/json |

### b. response body

**성공응답:**
```json
{
  "id": 1,
  "title": "직관데이",
  "content": "창원에 야구보러 가는날",
  "writerName": "이병우",
  "writeDate": "2026-04-16T19:30:00",
  "editDate": "2026-04-16T19:30:00"
}
```
| 이름             | 데이터 타입 | 설명                 |
|:---------------|:-------|:-------------------|
| **id**         | Long   | 고유 식별자 (PK, 중복 없음) |
| **title**      | String | 할일 제목              |
| **content**    | String | 할일 내용              |
| **writerName** | String | 작성자명               |
| **writeDate**  | String | 작성일                |
| **editDate**   | String | 수정일                |