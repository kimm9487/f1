// 크롤러 인터페이스

public interface NewsCrawler {
    /** 
     * 최신 뉴스 항목들을 크롤링하여 News 엔티티 리스트로 반환한다.
     * @return 수집된 최신 뉴스 목록 (없으면 빈 리스트)
     * @throws Exception 크롤링 중 오류 발생 시 예외 전파 
     */
    List<News> crawlLatestNews() throws Exception;
    
    /**
     * 크롤링 대상 사이트의 이름(identifier)을 반환 (예: "Motorsport")
     */
    String getSourceName();
}
