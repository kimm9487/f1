
//예시
/*
Document doc = Jsoup.connect("https://www.formula1.com/en/latest").get();
Elements items = doc.select("ul.listing-items > li.listing-item");
for (Element item : items) {
    String title = item.select("a h2").text();               // 제목 텍스트
    String articleUrl = item.select("a").attr("abs:href");   // 기사 URL
    String imageUrl = item.select("a img").attr("abs:src");  // 썸네일 이미지 URL
    String dateText = item.select("a .date").text();         // 날짜 텍스트 (예: "20 Nov 2025")
    LocalDateTime publishedAt = parseDate(dateText);         // 날짜 문자열 파싱 -> LocalDateTime
    
    News news = News.builder()
            .title(title)
            .summary(null)      // Formula1.com은 요약 없이 제목만 제공되는 구조
            .imageUrl(imageUrl)
            .publishedAt(publishedAt)
            .source("Formula1.com")
            .url(articleUrl)
            .build();
    newsList.add(news);
}
*/
