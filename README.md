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

### 📅 API 명세서 목록

#### 1. 일정 관리 (Schedules)
| 기능       | Method | URL                        | request | response | 상태코드 |
|:---------| :---: |:---------------------------| :--- | :--- |:----:|
| 일정 등록    | POST | /schedules                 | 요청 body | 등록 정보 | 201: 정상 등록  |
| 일정 전체 조회 | GET | /schedules                 |  | 다건 응답 정보 | 200: 정상 조회  |
| 일정 단건 조회 | GET | /schedules/{scheduleId}    | 요청 param | 단건 응답 정보 | 200: 정상 조회  |
| 일정 수정    | PUT | /schedules/{scheduleId}    | 요청 body | 수정 정보 | 200: 정상 수정  |
| 일정 삭제    | DELETE | /schedules/{scheduleId}    | 요청 param | - | 204: 정상 삭제  |

#### 2. 유저 관리 (Users)
| 기능       | Method | URL             | request | response | 상태코드 |
|:---------| :---: |:----------------| :--- | :--- |:----:|
| 유저 등록    | POST | /users          | 요청 body | 등록 정보 | 201: 정상 등록  |
| 유저 전체 조회 | GET | /users          |  | 단건 응답 정보 | 200: 정상 조회  |
| 유저 단건 조회 | GET | /users/{userId} | 요청 param | 단건 응답 정보 | 200: 정상 조회  |
| 유저 수정    | PUT | /users/{userId} | 요청 body | 수정 정보 | 200: 정상 수정  |
| 유저 삭제    | DELETE | /users/{userId} | 요청 param | - | 204: 정상 삭제  |

#### 3. 기타 기능 관리 (인증/인가)
| 기능 | Method | URL              | request | response | 상태코드 |
| :--- | :---: |:-----------------| :--- | :--- | :--- |
| 회원가입 | POST | /users/signup    | 요청 body (이메일, 비밀번호, 이름) | 가입 유저 정보 | 201: 정상 등록 |
| 로그인 | POST | /users/login     | 요청 body (이메일, 비밀번호) | 로그인 성공 메시지 | 200: 로그인 성공 |