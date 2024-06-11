package com.booksajo.bookPanda.book.service;

import com.booksajo.bookPanda.book.domain.BookSales;
import com.booksajo.bookPanda.book.dto.*;
import com.booksajo.bookPanda.book.repository.BookSalesRepository;
import com.booksajo.bookPanda.category.domain.Category;
import com.booksajo.bookPanda.category.repository.CategoryRepository;
import com.booksajo.bookPanda.exception.errorCode.BookSalesErrorCode;
import com.booksajo.bookPanda.exception.errorCode.CategoryErrorCode;
import com.booksajo.bookPanda.exception.exception.BookSalesException;
import com.booksajo.bookPanda.exception.exception.CategoryException;
import com.booksajo.bookPanda.review.entity.Review;
import com.booksajo.bookPanda.review.repository.ReviewRepository;
import com.booksajo.bookPanda.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookSalesService {
    private final BookSalesRepository bookSalesRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;

    private static final String BOOKSALES_VISITCOUNT_KEY = "visitcount";



    public ResponseBookSales getBookSales(Long id)
    {
        incrementViewCount(id);

        List<Review> reviewList = reviewRepository.findByBookSalesId(id);

        BookSales bookSales = bookSalesRepository.findById(id).orElseThrow(() ->
                new BookSalesException(BookSalesErrorCode.BOOK_SALES_NOT_FOUND));

        ResponseBookSales res = new ResponseBookSales();
        res.setBookSales(bookSales);
        res.setReviewList(reviewList);

        return res;
    }

    public List<BookSales> getBookSalesList()
    {
        return bookSalesRepository.findAll();
    }

    public BookSales createBookSales(BookSalesRequest bookSalesRequest, User user)
    {
        BookSalesDto bookSalesDto = new BookSalesDto();

       // System.out.println(bookSalesRequest.getTitle());

        BookInfo newBookInfo = getBookInfo(bookSalesRequest);
        bookSalesDto.setBookInfo(newBookInfo);
        bookSalesDto.setSellCount(bookSalesRequest.getSalesInfoDto().getSellCount());
        bookSalesDto.setVisitCount(bookSalesRequest.getSalesInfoDto().getVisitCount());
        bookSalesDto.setStock(bookSalesRequest.getSalesInfoDto().getStock());


        Category category = categoryRepository.findById(bookSalesRequest.getSalesInfoDto().getCategoryId())
                .orElseThrow(()->new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND));

        BookSales bookSales = bookSalesDto.toEntity(category);
        bookSales.setUser(user);

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
        bookSalesRepository.findById(id).orElseThrow(() ->
                new BookSalesException(BookSalesErrorCode.BOOK_SALES_NOT_FOUND));
        bookSalesRepository.deleteById(id);
    }

    public void incrementViewCount(Long postId) {

        String redisKey = BOOKSALES_VISITCOUNT_KEY + postId;
        redisTemplate.opsForValue().increment(redisKey, 1);
    }

    public Page<BookSales> getBookSalesByCategoryId(Long categoryId,int page, int size){
        return bookSalesRepository.findBookSalesByCategoryId(categoryId, PageRequest.of(page,size));
    }

    public Page<BookSales> getBookSalesByCategoryIdOrderByVisitCount(Long categoryId, int page , int size){
        return bookSalesRepository.findBookSalesByCategoryIdOrderByVisitCount(categoryId,PageRequest.of(page,size));
    }

    public Page<BookSales> getBookSalesByCategoryIdOrderBySellCount(Long categoryId, int page , int size){
        return bookSalesRepository.findBookSalesByCategoryIdOrderBySellCount(categoryId,PageRequest.of(page,size));
    }

    public Page<BookSales> getBookSalesByCategoryIdOrderById(Long categoryId, int page , int size){
        return bookSalesRepository.findBookSalesByCategoryIdOrderById(categoryId,PageRequest.of(page,size));
    }

    public Page<BookSales> getBookSalesContainedWord(String keyword, int page,int size){
        return bookSalesRepository.getBookSalesTitleByContainedWord(keyword,PageRequest.of(page,size));
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