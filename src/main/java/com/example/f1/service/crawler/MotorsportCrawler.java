//사이트별 크롤러 구현

@Component  // Spring Bean 등록
public class MotorsportCrawler implements NewsCrawler {

    private static final String URL = "https://www.motorsport.com/f1/news/";

    @Override
    public String getSourceName() {
        return "Motorsport.com";
    }

    @Override
    public List<News> crawlLatestNews() throws IOException {
        List<News> newsList = new ArrayList<>();
        Document doc = Jsoup.connect(URL).get();                      // ① 페이지 가져오기:contentReference[oaicite:5]{index=5}
        
        Elements articleLinks = doc.select("a.js-article-link");      // ② 뉴스 아이템 요소 선택 (가정: Motorsports의 기사 링크에 공통 클래스 존재)
        for (Element link : articleLinks) {
            // ③ 제목과 URL 추출
            String title = link.text();                              // 링크 텍스트 전체 (제목 포함)
            String articleUrl = link.attr("abs:href");               // 기사 절대 URL
            
            // ④ 요약, 이미지, 시간 등 추가 정보 추출
            String summary = link.parent().select(".teaser").text(); // 가정: link의 부모에 요약 텍스트가 있음
            String imageUrl = link.parent().select("img").attr("abs:src"); // 이미지 URL 추출
            String timeText = link.parent().select(".date").text();  // 게시 시간 (예: "2 h" 또는 "2025-11-20")
            
            // ⑤ 시간 파싱 (상대 시간 "2 h" 등을 실제 timestamp로 변환 또는 현재 시각 기준 보정)
            LocalDateTime publishedAt = parseTimeText(timeText);
            
            // ⑥ News 엔티티 객체 생성
            News news = News.builder()
                    .title(title)
                    .summary(summary)
                    .imageUrl(imageUrl)
                    .publishedAt(publishedAt)
                    .source(getSourceName())
                    .url(articleUrl)
                    .build();
            newsList.add(news);
        }
        return newsList;
    }
    
    /** "2 h"와 같은 상대 시간 문자열을 현재 시각 기준으로 변환하거나, 날짜 포맷일 경우 LocalDateTime 파싱 */
    private LocalDateTime parseTimeText(String timeText) {
        if (timeText == null || timeText.isEmpty()) return LocalDateTime.now();
        // 간단 예시: "42 min", "5 h", "2 d" 등을 파싱하여 현재시각 기준 감소 (정교한 파싱 로직 필요)
        if (timeText.endsWith("min")) {
            long minutes = Long.parseLong(timeText.replace("min", "").trim());
            return LocalDateTime.now().minusMinutes(minutes);
        } else if (timeText.endsWith("h")) {
            long hours = Long.parseLong(timeText.replace("h", "").trim());
            return LocalDateTime.now().minusHours(hours);
        } else if (timeText.endsWith("d")) {
            long days = Long.parseLong(timeText.replace("d", "").trim());
            return LocalDateTime.now().minusDays(days);
        }
        // 기본: 날짜 형식 (예: "2025-11-20") 가정
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(timeText, fmt).atStartOfDay();
        } catch(Exception e) {
            return LocalDateTime.now();
        }
    }
}
