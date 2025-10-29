<<<<<<< HEAD
# F1 Korea Backend

F1 Korea 커뮤니티 사이트의 Spring Boot 백엔드 API 서버입니다.

## 🚀 주요 기능

### 🔐 인증 시스템
- JWT 토큰 기반 인증
- 회원가입/로그인
- 비밀번호 암호화 (BCrypt)

### 📝 게시판 시스템
- 게시글 CRUD (생성, 조회, 수정, 삭제)
- 카테고리별 게시글 분류 (일반, 공지, 정보, 잡담, 후기, 질문, 투표)
- 검색 기능 (제목, 내용, 태그, 작성자)
- 조회수 자동 증가
- 좋아요 시스템
- HOT 지수 계산 (조회수 + 좋아요수 + 댓글수)
- 태그 시스템

### 💬 댓글 시스템
- 댓글 작성/삭제
- 게시글별 댓글 조회
- 댓글 수 자동 업데이트

### 🏎️ F1 관련 기능
- 드라이버 정보 관리
- 팀 정보 관리
- 레이스 정보 관리

## 🛠️ 기술 스택

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Security** (JWT 인증)
- **Spring Data JPA**
- **MySQL 8.0**
- **Gradle**

## 📋 API 엔드포인트

### 인증 API
```
POST /api/auth/register              # 회원가입
POST /api/auth/login                # 로그인
GET  /api/user/profile             # 사용자 프로필
```

### 게시글 API
```
GET    /api/posts                    # 전체 게시글 조회
GET    /api/posts/type/{postType}    # 카테고리별 조회
GET    /api/posts/search?keyword=검색어 # 검색
GET    /api/posts/notices           # 공지사항 조회
GET    /api/posts/hot               # 인기 게시글 조회
GET    /api/posts/{id}              # 게시글 상세
POST   /api/posts                   # 게시글 작성 (인증 필요)
PUT    /api/posts/{id}              # 게시글 수정 (인증 필요)
DELETE /api/posts/{id}              # 게시글 삭제 (인증 필요)
POST   /api/posts/{id}/like         # 좋아요 토글 (인증 필요)
```

### 댓글 API
```
GET    /api/comments/post/{postId}   # 게시글별 댓글 조회
POST   /api/comments                # 댓글 작성 (인증 필요)
DELETE /api/comments/{id}           # 댓글 삭제 (인증 필요)
```

## 🗄️ 데이터베이스 설정

### MySQL 데이터베이스 생성
```sql
CREATE DATABASE f1;
```

### application.properties 설정
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/f1?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password
```

## 🚀 실행 방법

1. **의존성 설치**
   ```bash
   ./gradlew build
   ```

2. **애플리케이션 실행**
   ```bash
   ./gradlew bootRun
   ```

3. **서버 접속**
   - URL: `http://localhost:8080`

## 📊 데이터베이스 스키마

### 주요 테이블
- `users`: 사용자 정보
- `posts`: 게시글 정보
- `comments`: 댓글 정보
- `driver`: 드라이버 정보
- `team`: 팀 정보
- `race`: 레이스 정보

### 게시글 타입 (PostType)
- `GENERAL`: 일반
- `NOTICE`: 공지
- `INFORMATION`: 정보
- `CHAT`: 잡담
- `REVIEW`: 후기
- `QUESTION`: 질문
- `VOTE`: 투표

## 🔧 개발 환경

- **IDE**: IntelliJ IDEA 또는 Eclipse
- **JDK**: 17 이상
- **MySQL**: 8.0 이상
- **Gradle**: 7.0 이상

## 📝 라이선스

이 프로젝트는 교육 목적으로 제작되었습니다.

## 👥 기여자

- Backend Developer: [Your Name]

## 📞 문의

프로젝트 관련 문의사항이 있으시면 이슈를 등록해 주세요.

=======
# f1
f1
>>>>>>> 12a60e3c02efb85320d2bc1cb3134041f296c310
