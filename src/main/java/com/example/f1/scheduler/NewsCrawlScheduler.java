//스케줄러 클래스, @Scheduled 메서드 포함

@Component
public class NewsCrawlScheduler {

    private final NewsCrawlService newsCrawlService;
    
    public NewsCrawlScheduler(NewsCrawlService newsCrawlService) {
        this.newsCrawlService = newsCrawlService;
    }

    // 새벽 6시부터 자정까지 30분마다 실행 (예시 cron 표현식)
    @Scheduled(cron = "0 0/30 6-23 * * *")
    public void runCrawlerTask() {
        log.info("뉴스 크롤링 작업 시작...");
        newsCrawlService.crawlAllSites();
        log.info("뉴스 크롤링 작업 종료.");
    }
}
