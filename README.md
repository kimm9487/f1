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
- **OpenF1 API 연동**
  - `GET /api/f1/weather`: meeting key와 limit을 받아 저장된 날씨 기록을 반환
  - `GET /api/f1/weather/latest`: 가장 최근 저장된 날씨 상태
  - `POST /api/f1/weather/refresh`: 즉시 OpenF1 API를 호출해 최신 날씨를 DB에 적재
  - 매 5분마다 `@Scheduled` 작업이 `openf1.weather.default-meeting-key` 세션을 기준으로 OpenF1 날씨 데이터를 다시 가져옵니다
  - `GET /api/f1/car-data`: driver/meeting key와 limit을 받아 저장된 카 데이터 시계열을 제공
  - `GET /api/f1/car-data/latest`: 해당 설정의 최신 카 데이터를 반환
  - `POST /api/f1/car-data/refresh`: OpenF1의 car_data 엔드포인트를 호출하여 DB에 저장
  - 매 1분마다 `@Scheduled` 작업이 설정된 driver/meeting 키로 Car Data를 갱신합니다
  - `GET /api/f1/drivers`: meeting/driver key와 limit으로 저장된 드라이버 정보를 반환
  - `GET /api/f1/drivers/latest`: 해당 설정의 최신 드라이버 정보를 반환
  - `POST /api/f1/drivers/refresh`: OpenF1의 drivers 엔드포인트를 호출하여 DB를 갱신
  - 매 5분마다 `@Scheduled` 작업으로 설정된 meeting/driver 키를 기준으로 드라이버 정보를 갱신합니다
  - `GET /api/f1/intervals`: meeting/driver key와 limit을 받아 기록된 인터벌 데이터를 반환
  - `GET /api/f1/intervals/latest`: 해당 설정의 가장 최근 인터벌 정보를 반환
  - `POST /api/f1/intervals/refresh`: OpenF1의 intervals 엔드포인트를 호출하여 DB를 갱신
  - 매 5분마다 `@Scheduled` 작업이 설정된 meeting/driver 키를 기준으로 intervals 데이터를 가져옵니다
  - `GET /api/f1/laps`: meeting/driver key로 저장된 랩 데이터를 조회
  - `GET /api/f1/laps/latest`: 가장 최근 저장된 랩 기록
  - `POST /api/f1/laps/refresh`: OpenF1의 laps 엔드포인트를 호출하여 DB에 저장
  - 매 5분마다 `@Scheduled` 작업이 설정된 driver/meeting 키로 Laps를 갱신합니다
  - `GET /api/f1/location`: meeting/driver key로 저장된 위치 데이터를 조회
  - `GET /api/f1/location/latest`: 가장 최근 저장된 위치 기록
  - `POST /api/f1/location/refresh`: OpenF1의 location 엔드포인트를 호출하여 DB에 저장
  - 매 2분마다 `@Scheduled` 작업이 설정된 driver/meeting 키로 Location을 갱신합니다
  - `GET /api/f1/meetings`: year 또는 meetingKey로 저장된 미팅 정보를 조회
  - `GET /api/f1/meetings/latest`: 가장 최근 저장된 미팅 기록
  - `POST /api/f1/meetings/refresh`: OpenF1의 meetings 엔드포인트를 호출하여 DB에 저장
  - 매 1시간마다 `@Scheduled` 작업이 설정된 연도를 기준으로 Meetings를 갱신합니다
  - `GET /api/f1/overtakes`: meetingKey 또는 driverNumber로 저장된 추월 데이터를 조회
  - `GET /api/f1/overtakes/latest`: 가장 최근 저장된 추월 기록
  - `POST /api/f1/overtakes/refresh`: OpenF1의 overtakes 엔드포인트를 호출하여 DB에 저장
  - 매 3분마다 `@Scheduled` 작업이 설정된 meeting/session 키로 Overtakes를 갱신합니다
  - `GET /api/f1/pit`: meetingKey 또는 driverNumber로 저장된 피트스톱 데이터를 조회
  - `GET /api/f1/pit/latest`: 가장 최근 저장된 피트스톱 기록
  - `POST /api/f1/pit/refresh`: OpenF1의 pit 엔드포인트를 호출하여 DB에 저장
  - 매 4분마다 `@Scheduled` 작업이 설정된 meeting/session 키로 Pit을 갱신합니다
  - `GET /api/f1/position`: meetingKey 또는 driverNumber로 저장된 순위 데이터를 조회
  - `GET /api/f1/position/latest`: 가장 최근 저장된 순위 기록
  - `POST /api/f1/position/refresh`: OpenF1의 position 엔드포인트를 호출하여 DB에 저장
  - 매 1.5분마다 `@Scheduled` 작업이 설정된 meeting/session 키로 Position을 갱신합니다
  - `GET /api/f1/race-control`: meetingKey 또는 category로 저장된 레이스 컨트롤 메시지를 조회
  - `GET /api/f1/race-control/latest`: 가장 최근 저장된 레이스 컨트롤 기록
  - `POST /api/f1/race-control/refresh`: OpenF1의 race_control 엔드포인트를 호출하여 DB에 저장
  - 매 2분마다 `@Scheduled` 작업이 설정된 meeting/session 키로 Race Control을 갱신합니다
  - `GET /api/f1/sessions`: meetingKey, year 또는 sessionType으로 저장된 세션 정보를 조회
  - `GET /api/f1/sessions/latest`: 가장 최근 저장된 세션 기록
  - `POST /api/f1/sessions/refresh`: OpenF1의 sessions 엔드포인트를 호출하여 DB에 저장
  - 매 30분마다 `@Scheduled` 작업이 설정된 연도를 기준으로 Sessions를 갱신합니다
  - `GET /api/f1/session-result`: meetingKey, sessionKey 또는 driverNumber로 저장된 세션 결과를 조회
  - `GET /api/f1/session-result/latest`: 가장 최근 저장된 세션 결과 기록
  - `POST /api/f1/session-result/refresh`: OpenF1의 session_result 엔드포인트를 호출하여 DB에 저장
  - 매 10분마다 `@Scheduled` 작업이 설정된 meeting/session 키로 Session Result를 갱신합니다
  - `GET /api/f1/starting-grid`: meetingKey, sessionKey 또는 driverNumber로 저장된 스타팅 그리드를 조회
  - `GET /api/f1/starting-grid/latest`: 가장 최근 저장된 스타팅 그리드 기록
  - `POST /api/f1/starting-grid/refresh`: OpenF1의 starting_grid 엔드포인트를 호출하여 DB에 저장
  - 매 30분마다 `@Scheduled` 작업이 설정된 meeting/session 키로 Starting Grid를 갱신합니다
  - `GET /api/f1/stints`: meetingKey, sessionKey 또는 driverNumber로 저장된 스틴트를 조회
  - `GET /api/f1/stints/latest`: 가장 최근 저장된 스틴트 기록
  - `POST /api/f1/stints/refresh`: OpenF1의 stints 엔드포인트를 호출하여 DB에 저장
  - 매 10분마다 `@Scheduled` 작업이 설정된 meeting/session 키로 Stints를 갱신합니다
  - `GET /api/f1/team-radio`: meetingKey, sessionKey 또는 driverNumber로 저장된 팀 라디오를 조회
  - `GET /api/f1/team-radio/latest`: 가장 최근 저장된 팀 라디오 기록
  - `POST /api/f1/team-radio/refresh`: OpenF1의 team_radio 엔드포인트를 호출하여 DB에 저장
  - 매 5분마다 `@Scheduled` 작업이 설정된 meeting/session 키로 Team Radio를 갱신합니다

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

- **IDE**: Eclipse
- **JDK**: 17 이상
- **MySQL**: 8.0 이상
- **Gradle**: 7.0 이상

## 📝 라이선스

이 프로젝트는 졸업작품 목적으로 제작되었습니다.

## 👥 기여자

- Backend Developer: [김규호]

