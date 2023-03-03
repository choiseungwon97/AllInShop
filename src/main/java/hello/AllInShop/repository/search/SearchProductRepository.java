package hello.AllInShop.repository.search;

import hello.AllInShop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProductRepository {
    Product search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
