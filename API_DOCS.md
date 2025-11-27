# F1 Korea API 문서

## 🚀 빠른 시작

### Swagger UI 접속
개발 서버 실행 후 다음 URL로 접속하세요:
```
http://localhost:8080/swagger-ui/index.html
```

### 기본 정보
- **Base URL**: `http://localhost:8080`
- **API Version**: v1.0.0
- **Content-Type**: `application/json`

## 📋 API 엔드포인트 목록

### 🌤️ Weather (날씨)
- `GET /api/f1/weather` - 날씨 데이터 목록 조회
- `GET /api/f1/weather/latest` - 최신 날씨 데이터 조회
- `POST /api/f1/weather/refresh` - 날씨 데이터 새로고침

### 🏎️ Car Data (차량 데이터)
- `GET /api/f1/car-data` - 차량 데이터 목록 조회
- `GET /api/f1/car-data/latest` - 최신 차량 데이터 조회
- `POST /api/f1/car-data/refresh` - 차량 데이터 새로고침

### 👨‍💼 Drivers (드라이버)
- `GET /api/f1/drivers` - 드라이버 정보 목록 조회
- `GET /api/f1/drivers/latest` - 최신 드라이버 정보 조회
- `POST /api/f1/drivers/refresh` - 드라이버 정보 새로고침

### ⏱️ Intervals (인터벌)
- `GET /api/f1/intervals` - 인터벌 데이터 목록 조회
- `GET /api/f1/intervals/latest` - 최신 인터벌 데이터 조회
- `POST /api/f1/intervals/refresh` - 인터벌 데이터 새로고침

### 🏁 Laps (랩타임)
- `GET /api/f1/laps` - 랩타임 데이터 목록 조회
- `GET /api/f1/laps/latest` - 최신 랩타임 데이터 조회
- `POST /api/f1/laps/refresh` - 랩타임 데이터 새로고침

### 📍 Location (위치)
- `GET /api/f1/location` - 위치 데이터 목록 조회
- `GET /api/f1/location/latest` - 최신 위치 데이터 조회
- `POST /api/f1/location/refresh` - 위치 데이터 새로고침

### 🏟️ Meetings (미팅)
- `GET /api/f1/meetings` - 미팅 정보 목록 조회
- `GET /api/f1/meetings/latest` - 최신 미팅 정보 조회
- `POST /api/f1/meetings/refresh` - 미팅 정보 새로고침

### 🚗 Overtakes (추월)
- `GET /api/f1/overtakes` - 추월 데이터 목록 조회
- `GET /api/f1/overtakes/latest` - 최신 추월 데이터 조회
- `POST /api/f1/overtakes/refresh` - 추월 데이터 새로고침

### 🔧 Pit (피트스톱)
- `GET /api/f1/pit` - 피트스톱 데이터 목록 조회
- `GET /api/f1/pit/latest` - 최신 피트스톱 데이터 조회
- `POST /api/f1/pit/refresh` - 피트스톱 데이터 새로고침

### 🏆 Position (순위)
- `GET /api/f1/position` - 순위 데이터 목록 조회
- `GET /api/f1/position/latest` - 최신 순위 데이터 조회
- `POST /api/f1/position/refresh` - 순위 데이터 새로고침

### 🚨 Race Control (레이스 컨트롤)
- `GET /api/f1/race-control` - 레이스 컨트롤 메시지 목록 조회
- `GET /api/f1/race-control/latest` - 최신 레이스 컨트롤 메시지 조회
- `POST /api/f1/race-control/refresh` - 레이스 컨트롤 메시지 새로고침

### 🏁 Sessions (세션)
- `GET /api/f1/sessions` - 세션 정보 목록 조회
- `GET /api/f1/sessions/latest` - 최신 세션 정보 조회
- `POST /api/f1/sessions/refresh` - 세션 정보 새로고침

### 📊 Session Result (세션 결과)
- `GET /api/f1/session-result` - 세션 결과 목록 조회
- `GET /api/f1/session-result/latest` - 최신 세션 결과 조회
- `POST /api/f1/session-result/refresh` - 세션 결과 새로고침

### 🏁 Starting Grid (스타팅 그리드)
- `GET /api/f1/starting-grid` - 스타팅 그리드 목록 조회
- `GET /api/f1/starting-grid/latest` - 최신 스타팅 그리드 조회
- `POST /api/f1/starting-grid/refresh` - 스타팅 그리드 새로고침

### 🔄 Stints (스틴트)
- `GET /api/f1/stints` - 스틴트 데이터 목록 조회
- `GET /api/f1/stints/latest` - 최신 스틴트 데이터 조회
- `POST /api/f1/stints/refresh` - 스틴트 데이터 새로고침

### 📻 Team Radio (팀 라디오)
- `GET /api/f1/team-radio` - 팀 라디오 메시지 목록 조회
- `GET /api/f1/team-radio/latest` - 최신 팀 라디오 메시지 조회
- `POST /api/f1/team-radio/refresh` - 팀 라디오 메시지 새로고침

## 📝 공통 파라미터

### Query Parameters
- `meetingKey` (선택사항): 특정 미팅의 데이터를 조회 (기본값: 1208)
- `sessionKey` (선택사항): 특정 세션의 데이터를 조회
- `driverNumber` (선택사항): 특정 드라이버의 데이터를 조회
- `limit` (선택사항): 조회할 레코드 수 (기본값: 10)

## 📋 응답 형식

### 성공 응답 (200 OK)
```json
{
  "meetingKey": 1208,
  "count": 10,
  "weather": [
    {
      "airTemperature": 25.5,
      "date": "2024-11-27T10:30:00",
      "humidity": 65,
      "meetingKey": 1208,
      "pressure": 1013.2,
      "rainfall": 0,
      "sessionKey": 9158,
      "trackTemperature": 35.2,
      "windDirection": 180,
      "windSpeed": 5.2
    }
  ]
}
```

### 최신 데이터 응답 (200 OK)
```json
{
  "airTemperature": 25.5,
  "date": "2024-11-27T10:30:00",
  "humidity": 65,
  "meetingKey": 1208,
  "pressure": 1013.2,
  "rainfall": 0,
  "sessionKey": 9158,
  "trackTemperature": 35.2,
  "windDirection": 180,
  "windSpeed": 5.2
}
```

### 데이터 없음 (204 No Content)
```
(빈 응답)
```

### 오류 응답 (500 Internal Server Error)
```json
{
  "error": "Failed to fetch data: Connection timeout"
}
```

## 🔄 자동 새로고침

모든 엔드포인트는 다음 간격으로 자동 새로고침됩니다:
- **Weather**: 30초마다
- **Car Data**: 5초마다
- **Drivers**: 1시간마다
- **Intervals**: 5초마다
- **Laps**: 10초마다
- **Location**: 5초마다
- **Meetings**: 1시간마다
- **Overtakes**: 30초마다
- **Pit**: 30초마다
- **Position**: 5초마다
- **Race Control**: 10초마다
- **Sessions**: 1시간마다
- **Session Result**: 30분마다
- **Starting Grid**: 30분마다
- **Stints**: 30초마다
- **Team Radio**: 10초마다

## 🛠️ 개발 도구

### Swagger UI
- URL: `http://localhost:8080/swagger-ui/index.html`
- 모든 API를 브라우저에서 직접 테스트 가능
- 실시간 API 문서 제공

### API 문서 JSON
- URL: `http://localhost:8080/v3/api-docs`
- OpenAPI 3.0 형식의 API 스펙

## 📞 지원

- **프로젝트**: F1 Korea
- **웹사이트**: https://f1korea.vercel.app/
- **이메일**: support@f1korea.com

## 📄 라이센스

MIT License
