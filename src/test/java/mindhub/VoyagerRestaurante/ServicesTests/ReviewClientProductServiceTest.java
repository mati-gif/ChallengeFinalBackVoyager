//package mindhub.VoyagerRestaurante.ServicesTests;
//
//import mindhub.VoyagerRestaurante.models.ReviewClientProduct;
//import mindhub.VoyagerRestaurante.repositories.ReviewClientProductRepository;
//import mindhub.VoyagerRestaurante.services.implementation.ReviewClientProductServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class ReviewClientProductServiceTest {
//
//    @Mock
//    private ReviewClientProductRepository reviewRepository;
//
//    @InjectMocks
//    private ReviewClientProductServiceImpl reviewService;
//
//    private ReviewClientProduct review;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        review = new ReviewClientProduct("Great product!", "Loved the quality", null, null);
//    }
//
//    @Test
//    void testSaveReview() {
//        when(reviewRepository.save(any(ReviewClientProduct.class))).thenReturn(review);
//
//        ReviewClientProduct savedReview = reviewService.saveReview(review);
//        assertEquals(review.getText(), savedReview.getText());
//        verify(reviewRepository, times(1)).save(review);
//    }
//
//    @Test
//    void testGetReviewById() {
//        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
//
//        Optional<ReviewClientProduct> foundReview = reviewRepository.findById(1L);
//        assertEquals(review.getText(), foundReview.get().getText());
//        verify(reviewRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testDeleteReview() {
//        reviewService.deleteReview(1L);
//        verify(reviewRepository, times(1)).deleteById(1L);
//    }
//}
