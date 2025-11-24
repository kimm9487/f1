// News 엔티티용 JPA 레포지토리

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    // 같은 출처에서 동일 URL 뉴스가 이미 존재하는지 확인 (있다면 Optional 반환)
    Optional<News> findBySourceAndUrl(String source, String url);
    
    // 혹은 제목으로도 검색 (필요에 따라 사용)
    Optional<News> findBySourceAndTitle(String source, String title);
}
