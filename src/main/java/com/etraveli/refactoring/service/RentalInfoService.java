package com.etraveli.refactoring.service;

import com.etraveli.refactoring.model.Customer;
import com.etraveli.refactoring.model.Movie;
import com.etraveli.refactoring.model.MovieRental;

import java.math.BigDecimal;

import static com.etraveli.refactoring.util.Constants.*;
import static com.etraveli.refactoring.util.MovieCode.NEW;

public class RentalInfoService {
    MovieService movieService = new MovieService();

  /**
   * * This method will generate Statements for customer
   *
   * @param customer
   * @return
   */
    public String generateStatementForCustomer(Customer customer) {

        BigDecimal totalAmount = BigDecimal.ZERO;
        String result = "Rental Record for " + customer.getName() + "\n";
        int frequentEnterPoints = 0;

        for (MovieRental movieRental : customer.getRentals()) {
            Movie movie = movieService.getMovie(movieRental.getMovieId());
            BigDecimal rentalAmount = calculateRentalAmount(movieRental, movie);

            //add frequent bonus points
            frequentEnterPoints++;
            frequentEnterPoints = addFrequentBonusForNewMovies(movieRental, movie, frequentEnterPoints);

            //print figures for this rental
            result += String.format("\t%s\t%s\n", movie.getTitle(), rentalAmount);
            totalAmount = totalAmount.add(rentalAmount);
        }
        // add footer lines
        result += String.format("Amount owed is %s\n", totalAmount);
        result += String.format("You earned %s frequent points\n", frequentEnterPoints);

        return result;
    }

    /**
     * * This method will add extra bonus point for new movies
     *
     * @param movieRental
     * @param movie
     * @param frequentEnterPoints
     * @return
     */
    public Integer addFrequentBonusForNewMovies(
            MovieRental movieRental, Movie movie, Integer frequentEnterPoints) {
        if (movie.getCode() == NEW && movieRental.getDays() > DAYS_FOR_NEW_MOVIES_BONUS) {
            frequentEnterPoints++;
        }
        return frequentEnterPoints;
    }

    /**
     * * This method will calculate the rental amount based on movie code
     *
     * @param movieRental
     * @param movie
     * @return
     */
    public BigDecimal calculateRentalAmount(MovieRental movieRental, Movie movie) {
        BigDecimal rentalAmt = BigDecimal.ZERO;
        // determine amount for each movie
        switch (movie.getCode()) {
            case REGULAR:
                rentalAmt = new BigDecimal(REGULAR_MOVIE_INITIAL);
                if (movieRental.getDays() > REGULAR_MOVIE_MINIMUM_DAYS) {
                    BigDecimal rentalAmtAfterDiscount = new BigDecimal((movieRental.getDays() - REGULAR_MOVIE_MINIMUM_DAYS) * REGULAR_MOVIE_MULTIPLIER);
                    rentalAmt = rentalAmt.add(rentalAmtAfterDiscount);
                }
                break;

            case NEW:
                rentalAmt = new BigDecimal(movieRental.getDays() * NEW_MOVIE_MULTIPLIER);
                break;

            case CHILDRENS:
                rentalAmt = new BigDecimal(CHILDREN_MOVIE_INITIAL);
                if (movieRental.getDays() > CHILDREN_MOVIE_MINIMUM_DAYS) {
                    BigDecimal rentalDiscountAmt = new BigDecimal((movieRental.getDays() - CHILDREN_MOVIE_MINIMUM_DAYS) * CHILDREN_MOVIE_MULTIPLIER);
                    rentalAmt = rentalAmt.add(rentalDiscountAmt);
                }
                break;
        }
        return rentalAmt.setScale(1);
    }

}


