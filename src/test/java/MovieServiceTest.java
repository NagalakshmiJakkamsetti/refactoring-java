import com.etraveli.refactoring.Exception.NoMovieFoundException;
import com.etraveli.refactoring.model.Movie;
import com.etraveli.refactoring.service.MovieService;
import com.etraveli.refactoring.util.MovieCode;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;

import static com.etraveli.refactoring.util.MovieCode.NEW;
import static com.etraveli.refactoring.util.MovieCode.REGULAR;
import static java.util.Objects.nonNull;
import static org.junit.Assert.assertEquals;


public class MovieServiceTest {

    public MovieService movieService = new MovieService();
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testSaveMovieShouldCreateMovieObject() {
        Movie expected = new Movie("Avengers", NEW);
        Movie actual = movieService.saveMovie("Avengers", NEW);

        assertEquals(actual.getTitle(), expected.getTitle());
        assertEquals(actual.getCode(), expected.getCode());
    }
    @Test
    public void testSetMovieList() {
        Integer actualSize = 0;
        Integer expected = 4;
        movieService.setMovies();
        HashMap<String, Movie> actual = movieService.getMovies();
        if (nonNull(actual)) {
            actualSize = actual.size();
        }

        assertEquals(actualSize, expected);
    }

    @Test
    public void testGetMovieShouldReturnMovie() {
        movieService.setMovies();
        String expectedTitle = "Matrix";
        MovieCode expectedCode = REGULAR;
        Movie actual = movieService.getMovie("F002");

        assertEquals(expectedTitle, actual.getTitle());
        assertEquals(expectedCode, actual.getCode());
    }

    @Test
    public void testGetMovieShouldThrowExceptionIfCouldNotFindID() {
        movieService.setMovies();
        exceptionRule.expect(NoMovieFoundException.class);
        exceptionRule.expectMessage("Could not find movie with rental id of ANY ID");
        movieService.getMovie("ANY ID");
    }

}
