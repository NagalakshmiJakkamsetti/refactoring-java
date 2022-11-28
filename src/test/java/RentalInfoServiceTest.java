import com.etraveli.refactoring.model.Customer;
import com.etraveli.refactoring.model.Movie;
import com.etraveli.refactoring.model.MovieRental;
import com.etraveli.refactoring.service.MovieService;
import com.etraveli.refactoring.service.RentalInfoService;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.etraveli.refactoring.util.MovieCode.CHILDRENS;
import static com.etraveli.refactoring.util.MovieCode.REGULAR;

import static javafx.beans.binding.Bindings.when;
import static org.junit.Assert.assertEquals;

public class RentalInfoServiceTest {

    public MovieService movieService = new MovieService();
    public RentalInfoService rentalInfoService = new RentalInfoService();

    @Test
   public void getStatementOfCustomerForZeroRental() {
        movieService.setMovies();
        List<MovieRental> movieRentals = new ArrayList<>();
        Customer customer = new Customer("C. U. Stomer", movieRentals);

        String expected =
                String.format(
                        "Rental Record for C. U. Stomer\nAmount owed is 0\nYou earned 0 frequent points\n");

        String actual = rentalInfoService.getStatementForCustomer(customer);

        assertEquals(actual,expected);
    }

    @Test
    public void getStatementForCustomerForMultipleRentals() {
        movieService.setMovies();
        Movie movie1 = new Movie("You've Got Mail", REGULAR);
        Movie movie2 = new Movie("Cars", CHILDRENS);
        List<MovieRental> movieRentals =
                Arrays.asList(new MovieRental("F001", 1), new MovieRental("F003", 10));
        Customer customer = new Customer("C. U. Stomer", movieRentals);


        String expected =
                String.format(
                        "Rental Record for C. U. Stomer\n\t%s\t2.0\n\t%s\t12.0\nAmount owed is 14.0\nYou earned 2 frequent points\n",
                        movie1.getTitle(), movie2.getTitle());

        String actual = rentalInfoService.getStatementForCustomer(customer);

        assertEquals(actual,expected);
    }

}
