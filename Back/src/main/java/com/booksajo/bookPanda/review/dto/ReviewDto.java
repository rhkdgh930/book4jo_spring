package com.booksajo.bookPanda.review.dto;

import com.booksajo.bookPanda.book.domain.BookSales;
import com.booksajo.bookPanda.book.dto.BookSalesDto;
import com.booksajo.bookPanda.review.entity.Review;
import com.booksajo.bookPanda.user.dto.UserResponseDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private BookSales bookSales;
    private Integer rate;
    private String content;

    public Review toEntity()
    {
        return Review.builder()
                .bookSales(this.bookSales)
                .rate(this.rate)
                .content(this.content)
                .build();
    }


}
