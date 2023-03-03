package hello.AllInShop.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import hello.AllInShop.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

import static hello.AllInShop.domain.QBrand.*;
import static hello.AllInShop.domain.QCategory.*;
import static hello.AllInShop.domain.QMember.*;
import static hello.AllInShop.domain.QProduct.product;
import static hello.AllInShop.domain.QReply.*;

@Slf4j
public class SearchProductRepositoryImpl extends QuerydslRepositorySupport implements SearchProductRepository{

    public SearchProductRepositoryImpl() {
        super(Product.class);
    }


    @Override
    public Product search1() {

        log.info("search1..................");

        QProduct product = QProduct.product;
        QReply reply = QReply.reply;
        QMember member = QMember.member;
        QCategory category = QCategory.category;
        QBrand brand = QBrand.brand;

        JPQLQuery<Product> jpqlQuery = from(product);
        jpqlQuery.leftJoin(brand).on(product.brand.eq(brand));
        jpqlQuery.leftJoin(category).on(product.category.eq(category));
        jpqlQuery.leftJoin(member).on(product.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.product.eq(product));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(product, member.nickname, category.cateName, brand.brandName, reply.count());
        tuple.groupBy(product);


        log.info("-------------------------------------------");
        log.info(String.valueOf(tuple));
        log.info("-------------------------------------------");

        List<Tuple> result = tuple.fetch();
        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage..................");

        JPQLQuery<Product> jpqlQuery = from(product);
        jpqlQuery.leftJoin(brand).on(product.brand.eq(brand));
        jpqlQuery.leftJoin(category).on(product.category.eq(category));
        jpqlQuery.leftJoin(member).on(product.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.product.eq(product));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(product, brand, category, member, reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = product.id.gt(0L);

        booleanBuilder.and(expression);

        if (type != null) {
            String[] typeArr = type.split("");
            //검색조건 저장
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "n":
                        conditionBuilder.or(product.name.contains(keyword));
                        break;
                    case "g":
                        conditionBuilder.or(product.gender.stringValue().contains(keyword));
                        break;
                    case "b":
                        conditionBuilder.or(brand.brandName.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(category.cateName.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);

        //Order by
        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Product.class, "product");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        tuple.groupBy(product);

        //page 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info(String.valueOf(result));

        long count = tuple.fetchCount();

        log.info("COUNT: "+count);

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,count);

    }
}
