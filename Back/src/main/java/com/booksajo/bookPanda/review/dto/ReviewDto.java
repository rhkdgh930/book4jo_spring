package com.booksajo.bookPanda.review.dto;

import com.booksajo.bookPanda.book.domain.BookSales;
import com.booksajo.bookPanda.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewDto {

    private BookSales bookSales;
    // private User user;
    private Integer rate;
    private String content;

    public Review toEntity()
    {
        return Review.builder()
                .bookSales(this.bookSales)
                //.user(this.user)
                .rate(this.rate)
                .content(this.content)
                .build();
    }
}
