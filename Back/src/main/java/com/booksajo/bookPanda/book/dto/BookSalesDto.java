package com.booksajo.bookPanda.book.dto;

import com.booksajo.bookPanda.book.domain.BookSales;
import com.booksajo.bookPanda.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookSalesDto {
    private Integer visitCount;
    private Integer sellCount;
    private Integer stock;
    private Category category;
    private BookInfo bookInfo;

    public BookSales toEntity(Category category)
    {
        return BookSales.builder()
                .visitCount(this.visitCount)
                .sellCount(this.sellCount)
                .stock(this.stock)
                .category(category)
                .bookInfo(this.bookInfo)
                .build();
    }
}
