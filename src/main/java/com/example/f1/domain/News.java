//뉴스 엔티티 클래스

@Entity
@Table(name = "news",
       uniqueConstraints = {@UniqueConstraint(name="UK_news_source_url", columnNames={"source", "url"})})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class News {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;    // 뉴스 제목
    @Column(length = 1000)
    private String summary;  // 요약/본문 일부
    private String imageUrl; // 썸네일 이미지 URL
    private LocalDateTime publishedAt; // 뉴스 게시 시간
    private String source;   // 출처 (예: "Motorsport", "FIA", ...)
    private String url;      // 원본 기사 URL
}

