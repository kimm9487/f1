//뉴스 DTO 클래스

@Data @Builder
public class NewsDto {
    private String title;
    private String summary;
    private String imageUrl;
    private LocalDateTime publishedAt;
    private String source;
    private String url;
    
    // Entity -> DTO 변환 편의 메서드
    public static NewsDto fromEntity(News news) {
        return NewsDto.builder()
                .title(news.getTitle())
                .summary(news.getSummary())
                .imageUrl(news.getImageUrl())
                .publishedAt(news.getPublishedAt())
                .source(news.getSource())
                .url(news.getUrl())
                .build();
    }
}
