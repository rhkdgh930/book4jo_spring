package com.booksajo.bookPanda.review.service;

import com.booksajo.bookPanda.product.entity.BookSales;
import com.booksajo.bookPanda.product.exception.errorCode.BookSalesErrorCode;
import com.booksajo.bookPanda.product.exception.errorCode.ReviewErrorCode;
import com.booksajo.bookPanda.product.exception.exception.BookSalesException;
import com.booksajo.bookPanda.product.exception.exception.ReviewException;
import com.booksajo.bookPanda.review.dto.ReviewDto;
import com.booksajo.bookPanda.review.entity.Review;
import com.booksajo.bookPanda.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review getReview(Long id)
    {
        return reviewRepository.findById(id).orElseThrow(()->
                new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
    }

    public List<Review> getReviews()
    {
        return reviewRepository.findAll();
    }

    public Review postReview(ReviewDto dto)
    {
        Review newReview = dto.toEntity();
        return reviewRepository.save(newReview);
    }

    public Review patchReview(Long id, ReviewDto dto)
    {
        Review review = reviewRepository.findById(id).orElseThrow(() ->
                new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        review.setBookSales(dto.getBookSales());
        review.setRate(dto.getRate());
        review.setContent(dto.getContent());

        return reviewRepository.save(review);
    }

    public void deleteReview(Long id)
    {
        reviewRepository.findById(id).orElseThrow(()->
                new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        reviewRepository.deleteById(id);
    }
}
