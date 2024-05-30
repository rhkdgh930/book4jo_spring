package com.booksajo.bookPanda.review.repository;

import com.booksajo.bookPanda.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
