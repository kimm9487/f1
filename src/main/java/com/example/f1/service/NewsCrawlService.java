// 크롤링 통합 서비스

@Service
public class NewsCrawlService {

    private final NewsRepository newsRepository;
    private final List<NewsCrawler> crawlers;

    // 생성자 주입: 모든 NewsCrawler 빈들이 리스트로 주입됨
    public NewsCrawlService(NewsRepository newsRepository, List<NewsCrawler> crawlers) {
        this.newsRepository = newsRepository;
        this.crawlers = crawlers;
    }

    /** 모든 사이트 크롤러를 실행하여 뉴스를 수집하고 저장 */
    public void crawlAllSites() {
        for (NewsCrawler crawler : crawlers) {
            String source = crawler.getSourceName();
            try {
                List<News> newsList = crawler.crawlLatestNews();
                for (News news : newsList) {
                    // 중복 뉴스는 저장하지 않음 (이미 존재하면 skip)
                    boolean exists = newsRepository.findBySourceAndUrl(news.getSource(), news.getUrl()).isPresent();
                    if (!exists) {
                        newsRepository.save(news);
                    }
                }
                log.info("크롤러 완료 - {}: {}개 뉴스 수집 및 저장", source, newsList.size());
            } catch (Exception e) {
                // 개별 사이트 크롤링 오류 처리: 로그 남기고 다음 크롤러 진행
                log.error("크롤링 실패 - {}: {}", source, e.getMessage(), e);
            }
        }
    }
}
