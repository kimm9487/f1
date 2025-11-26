@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    // 수집된 전체 뉴스 목록 제공 (최신순)
    @GetMapping
    public List<NewsDTO> getAllNews() {
        return newsService.getLatestNews();
    }
    
    // (필요시) 특정 뉴스 조회나 페이지네이션 등의 엔드포인트 추가 가능
}
