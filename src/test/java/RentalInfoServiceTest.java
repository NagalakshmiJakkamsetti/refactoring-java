import com.etraveli.refactoring.model.Customer;
import com.etraveli.refactoring.model.Movie;
import com.etraveli.refactoring.model.MovieRental;
import com.etraveli.refactoring.service.MovieService;
import com.etraveli.refactoring.service.RentalInfoService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.etraveli.refactoring.util.MovieCode.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RentalInfoServiceTest {

    public RentalInfoService rentalInfoService = new RentalInfoService();
    @Mock
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCalculateRentalAmountForMinimumDaysRegular() {
        MovieRental movieRental = new MovieRental("F001", 2);
        BigDecimal expected = new BigDecimal(2).setScale(1);
        Movie movie = new Movie("Titanic", REGULAR);
        BigDecimal actual = rentalInfoService.calculateRentalAmount(movieRental, movie);

        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateRentalAmountForFourDaysRegular() {
        MovieRental movieRental = new MovieRental("F002", 4);
        BigDecimal expected = new BigDecimal(5).setScale(1);
        Movie movie = new Movie("CAST AWAY", REGULAR);
        BigDecimal actual = rentalInfoService.calculateRentalAmount(movieRental, movie);

        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateRentalAmountForSixDaysChildren() {
        MovieRental movieRental = new MovieRental("F003", 6);
        BigDecimal expected = new BigDecimal(6).setScale(1);
        Movie movie = new Movie("Cars", CHILDRENS);
        BigDecimal actual = rentalInfoService.calculateRentalAmount(movieRental, movie);

        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateRentalAmountForNewMovie() {
        MovieRental movieRental = new MovieRental("F004", 1);
        BigDecimal expected = new BigDecimal(3).setScale(1);
        Movie movie = new Movie("AVATAR", NEW);
        BigDecimal actual = rentalInfoService.calculateRentalAmount(movieRental, movie);

        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateRentalAmountForNewMovieForFiveDays() {
        MovieRental movieRental = new MovieRental("F004", 5);
        BigDecimal expected = new BigDecimal(15).setScale(1);
        Movie movie = new Movie("Transporter", NEW);
        BigDecimal actual = rentalInfoService.calculateRentalAmount(movieRental, movie);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatementForCustomerForZeroMoviesRental() {
        List<MovieRental> movieRentals = new ArrayList<>();
        Customer customer = new Customer("C. U. Stomer", movieRentals);
        String expected =
                String.format(
                        "Rental Record for C. U. Stomer\nAmount owed is 0\nYou earned 0 frequent points\n");

        String actual = rentalInfoService.generateStatementForCustomer(customer);

        assertEquals(actual, expected);
    }

    @Test
    public void testGetStatementForCustomerForSingleMoviesRental() {
        Movie movie = new Movie("Fast & Furious X", NEW);
        List<MovieRental> movieRentals = Arrays.asList(new MovieRental("F004", 1));
        Customer customer = new Customer("C. U. Stomer", movieRentals);

        String expected =
                String.format(
                        "Rental Record for C. U. Stomer\n\t%s\t3.0\nAmount owed is 3.0\nYou earned 1 frequent points\n",
                        movie.getTitle());

        String actual = rentalInfoService.generateStatementForCustomer(customer);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatementForCustomerForMultipleRentals() {
        movieService.setMovies();
        Movie movie1 = new Movie("Matrix", REGULAR);
        Movie movie2 = new Movie("Cars", CHILDRENS);
        List<MovieRental> movieRentals =
                Arrays.asList(new MovieRental("F002", 1), new MovieRental("F003", 8));
        Customer customer = new Customer("C. U. Stomer", movieRentals);

        String expected =
                String.format(
                        "Rental Record for C. U. Stomer\n\t%s\t2.0\n\t%s\t9.0\nAmount owed is 11.0\nYou earned 2 frequent points\n",
                        movie1.getTitle(), movie2.getTitle());

        String actual = rentalInfoService.generateStatementForCustomer(customer);

        assertEquals(expected, actual);
    }

    @Test
    public void testAddFrequentBonusForNewMoviesShouldNotIncrementForRegularMovies() {
        MovieRental movieRental = new MovieRental("F001", 6);
        Movie movie = new Movie("Mission Impossible", REGULAR);
        Integer frequentBonus = 0;
        Integer expected = 0;
        Integer actual = rentalInfoService.addFrequentBonusForNewMovies(movieRental, movie, frequentBonus);

        assertEquals(expected, actual);
    }

    @Test
    public void testAddFrequentBonusForNewMoviesShouldNotIncrementForChildrenMovies() {
        MovieRental movieRental = new MovieRental("F003", 10);
        Movie movie = new Movie("Kungfu Panda", CHILDRENS);
        Integer frequentBonus = 5;
        Integer expected = 5;
        Integer actual = rentalInfoService.addFrequentBonusForNewMovies(movieRental, movie, frequentBonus);

        assertEquals(expected, actual);
    }

    @Test
    public void testAddFrequentBonusForNewMoviesShouldIncrementOnce() {
        MovieRental movieRental = new MovieRental("F004", 4);
        Movie movie = new Movie("INCEPTION", NEW);
        Integer frequentBonus = 1;
        Integer expected = 2;
        Integer actual = rentalInfoService.addFrequentBonusForNewMovies(movieRental, movie, frequentBonus);

        assertEquals(expected, actual);
    }

    @Test
    public void testAddFrequentBonusForNewMoviesWithOneDayShouldNotIncrement() {
        MovieRental movieRental = new MovieRental("F004", 1);
        Movie movie = new Movie("Transformer", NEW);
        Integer frequentBonus = 1;
        Integer expected = 1;
        Integer actual = rentalInfoService.addFrequentBonusForNewMovies(movieRental, movie, frequentBonus);

        assertEquals(expected, actual);
    }


}
