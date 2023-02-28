package hello.AllInShop;

import hello.AllInShop.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static hello.AllInShop.domain.Gender.남성;

@Component
@RequiredArgsConstructor
public class InitProduct {

   /* private final InitProductService initProductService;
    @PostConstruct
    public void init() {
        initProductService.init();
    }

    @Component
    static class InitProductService {
        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {

            for (int i=0; i<200; i++ ) {
                Product productA;
                if (i %2 ==0) {
                    productA = new Product("바람막이" + i, 남성, i, i);
                } else {
                    productA = new Product("후리스" + i, 남성, i, i);
                }
                em.persist(productA);

            }
        } }*/
}
