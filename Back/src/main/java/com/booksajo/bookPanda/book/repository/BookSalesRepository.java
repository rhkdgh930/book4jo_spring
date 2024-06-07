package com.booksajo.bookPanda.book.repository;

import com.booksajo.bookPanda.book.domain.BookSales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookSalesRepository extends JpaRepository<BookSales, Long> {

    // visitCount를 기준으로 내림차순 정렬하여 상위 5개의 BookSales 엔티티를 반환
    List<BookSales> findTop5ByOrderByVisitCountDesc();

    // sellCount를 기준으로 내림차순 정렬하여 상위 5개의 BookSales 엔티티를 반환
    List<BookSales> findTop5ByOrderBySellCountDesc();

    // category의 id와 같은 값을 가진 BookSales 엔티티를 조회하여 List로 반환
    List<BookSales> findByCategoryId(Long categoryId);

}
