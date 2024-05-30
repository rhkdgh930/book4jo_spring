package com.booksajo.bookPanda.product.service;

import com.booksajo.bookPanda.product.dto.BookInfo;
import com.booksajo.bookPanda.product.dto.BookSalesDto;
import com.booksajo.bookPanda.product.dto.BookSalesRequest;
import com.booksajo.bookPanda.product.dto.SalesInfoDto;
import com.booksajo.bookPanda.product.entity.BookSales;
import com.booksajo.bookPanda.product.exception.errorCode.BookSalesErrorCode;
import com.booksajo.bookPanda.product.exception.exception.BookSalesException;
import com.booksajo.bookPanda.product.repository.BookSalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookSalesService {
    private final BookSalesRepository bookSalesRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String BOOKSALES_VISITCOUNT_KEY = "visitcount";



    public BookSales getBookSales(Long id)
    {
        incrementViewCount(id);

        return bookSalesRepository.findById(id).orElseThrow(() ->
                new BookSalesException(BookSalesErrorCode.BOOK_SALES_NOT_FOUND));
    }

    public List<BookSales> getBookSalesList()
    {
        return bookSalesRepository.findAll();
    }

    public BookSales createBookSales(BookSalesRequest bookSalesRequest)
    {
        BookSalesDto bookSalesDto = new BookSalesDto();

        System.out.println(bookSalesRequest.getTitle());

        BookInfo newBookInfo = getBookInfo(bookSalesRequest);
        bookSalesDto.setBookInfo(newBookInfo);
        bookSalesDto.setSellCount(bookSalesRequest.getSalesInfoDto().getSellCount());
        bookSalesDto.setVisitCount(bookSalesRequest.getSalesInfoDto().getVisitCount());
        bookSalesDto.setStock(bookSalesRequest.getSalesInfoDto().getStock());


        BookSales bookSales = bookSalesDto.toEntity();

        return bookSalesRepository.save(bookSales);
    }

    private BookInfo getBookInfo(BookSalesRequest bookSalesRequest) {
        BookInfo newBookInfo = new BookInfo();

        newBookInfo.setTitle(bookSalesRequest.getTitle());
        newBookInfo.setIsbn(bookSalesRequest.getIsbn());
        newBookInfo.setImage(bookSalesRequest.getImage());
        newBookInfo.setDiscount(bookSalesRequest.getDiscount());
        newBookInfo.setPubdate(bookSalesRequest.getPubdate());
        newBookInfo.setLink(bookSalesRequest.getLink());
        newBookInfo.setPublisher(bookSalesRequest.getPublisher());
        newBookInfo.setDescription(bookSalesRequest.getDescription());
        newBookInfo.setAuthor(bookSalesRequest.getAuthor());
        return newBookInfo;
    }

    public BookSales patchBookSales(Long id, BookSalesRequest bookSalesRequest)
    {
        SalesInfoDto salesInfoDto = bookSalesRequest.getSalesInfoDto();
        BookSales bookSales = bookSalesRepository.findById(id).orElseThrow(() ->
                new BookSalesException(BookSalesErrorCode.BOOK_SALES_NOT_FOUND));


        BookInfo newBookInfo = getBookInfo(bookSalesRequest);
        bookSales.setBookInfo(newBookInfo);
        bookSales.setStock(salesInfoDto.getStock());
        bookSales.setSellCount(salesInfoDto.getSellCount());
        return bookSalesRepository.save(bookSales);
    }

    public void deleteBookSales(Long id)
    {
        BookSales bookSales = bookSalesRepository.findById(id).orElseThrow(() ->
                new BookSalesException(BookSalesErrorCode.BOOK_SALES_NOT_FOUND));
        bookSalesRepository.delete(bookSales);
    }

    public void incrementViewCount(Long postId) {

        String redisKey = BOOKSALES_VISITCOUNT_KEY + postId;
        redisTemplate.opsForValue().increment(redisKey, 1);
    }

    @Scheduled(fixedRate = 5000) // 30초 마다 실행
    @Transactional
    public void syncViewCountToDatabase() {
        List<BookSales> bookSalesList = bookSalesRepository.findAll();
        for (BookSales bookSales : bookSalesList) {
            String redisKey = BOOKSALES_VISITCOUNT_KEY + bookSales.getId();
            if (redisTemplate.opsForValue().get(redisKey) != null ) {
                Integer newCount = Integer.parseInt(redisTemplate.opsForValue().get(redisKey).toString());
                if(newCount > 0)
                {
                    System.out.println("BookSales id :" + bookSales.getId() + " / 기존 방문 수 : " + bookSales.getVisitCount() + " / 새로운 조회수 : " + newCount);
                    bookSales.setVisitCount(bookSales.getVisitCount() + newCount);
                    bookSalesRepository.save(bookSales);
                    redisTemplate.delete(redisKey);
                }
            }
        }
    }
}