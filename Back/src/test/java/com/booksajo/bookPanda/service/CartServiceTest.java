package com.booksajo.bookPanda.service;

/*
@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookSalesRepository bookSalesRepository;

    @InjectMocks
    private CartService cartService;

    private User testUser;
    private BookSales testBookSales;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");

        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(1L);
        bookInfo.setTitle("Book Title");
        bookInfo.setPrice("Book Price");
        bookInfo.setAuthor("Book Author");

        testBookSales = new BookSales();
        testBookSales.setId(1L);
        testBookSales.setBookInfo(bookInfo);

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
    }

    @Test
    void testGetCart() {
        Cart cart = new Cart();
        cart.setUser(testUser);

        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.getCartByUserId(testUser.getId());
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getUser().getId());
    }

    @Test
    void testCreateCartForUser() {
        Cart cart = new Cart();
        cart.setUser(testUser);

        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        Cart result = cartService.createCartForUser(testUser.getId());
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getUser().getId());
    }

    @Test
    void testAddNewCartItem() {
        Cart cart = new Cart();
        cart.setUser(testUser);

        when(cartRepository.findByUserId(testUser.getId())).thenReturn(Optional.of(cart));
        when(bookSalesRepository.findById(testBookSales.getId())).thenReturn(Optional.of(testBookSales));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.addCartItem(testUser.getId(), testBookSales.getId());

        assertNotNull(result);
        assertEquals(1, result.getCartItems().size());
        assertEquals(testBookSales.getId(), result.getCartItems().get(0).getBookSales().getId());
        assertEquals(1, result.getCartItems().get(0).getQuantity());

    }

    @Test
    void testAddExistingCartItem() {

    }




}
*/
