package com.booksajo.bookPanda.repository;

import com.booksajo.bookPanda.domain.book.BookSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSalesRepository extends JpaRepository<BookSales, Long> {
}
