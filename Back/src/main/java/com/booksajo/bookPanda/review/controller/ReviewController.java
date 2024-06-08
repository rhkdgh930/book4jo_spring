package com.booksajo.bookPanda.review.controller;

import com.booksajo.bookPanda.review.dto.ReviewDto;
import com.booksajo.bookPanda.review.entity.Review;
import com.booksajo.bookPanda.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("review")
    public ResponseEntity<Review> getReview(@RequestParam(name = "id") Long id)
    {
        return ResponseEntity.ok(reviewService.getReview(id));
    }

    @GetMapping("reviews")
    public ResponseEntity<List<Review>> getReviews(@RequestParam(name = "id") Long id)
    {
        return ResponseEntity.ok(reviewService.getReviews(id));
    }

    @PostMapping("review")
    public ResponseEntity<Review> postReview(@RequestBody ReviewDto dto)
    {
        return ResponseEntity.ok(reviewService.postReview(dto));
    }

    @PatchMapping("review")
    public ResponseEntity<Review> patchReview(@RequestParam(name = "id") Long id, @RequestBody ReviewDto dto)
    {
        return ResponseEntity.ok(reviewService.patchReview(id, dto));
    }

    @DeleteMapping("review")
    public void deleteReview(@RequestParam(name = "id") Long id)
    {
        reviewService.deleteReview(id);
    }
}
