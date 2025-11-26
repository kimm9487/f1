@Component
public class SkySportsNewsCrawler implements NewsCrawler {
    private static final String URL = "https://www.skysports.com/f1/news";

    @Override
    public List<NewsEntity> crawl() throws Exception {
        List<NewsEntity> result = new ArrayList<>();
        Document doc = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0")
                .get();
        // SkySports F1 뉴스 목록의 헤드라인(<h3> 태그 내 a 요소) 선택
        Elements headlineLinks = doc.select("h3 a");
        for (Element aTag : headlineLinks) {
            String titleText = aTag.text().trim();
            String relativeLink = aTag.attr("href"); // 예: "/f1/news/12345-some-article-title"
            if (relativeLink == null || relativeLink.isEmpty()) continue;
            // SkySports는 내부 링크가 상대경로이므로 전체 URL로 변환
            String articleUrl = relativeLink.startsWith("http") ? relativeLink 
                                    : "https://www.skysports.com" + relativeLink;
            // SkySports 목록에는 날짜/요약 정보 없음 -> 필요하면 기사 페이지 추가 요청 가능
            LocalDateTime published = LocalDateTime.now(); 
            String summaryText = "";
            // (요약과 발행일 얻기 위해 기사 페이지 크롤링 가능. 여기서는 생략하거나 기본값 사용)

            NewsEntity news = new NewsEntity();
            news.setTitle(titleText);
            news.setLink(articleUrl);
            news.setPublishedDate(published);
            news.setSummary(summaryText);
            news.setSource("SkySports");
            result.add(news);
        }
        return result;
    }
}
