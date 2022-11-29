# Refactoring Java

This code creates an information slip about movie rentals.

Refactored the Movie Rental Application project, I have changed the project src structures and added packages.
Added the possible test cases.

#Test Cases: 

Below list of test cases crated for MovieService and RentalInfoService modules for postive and negeative scenario's. 
Also, added test results document to this repo.

MovieService Unit Test cases : 
-----------------------------
 - testSaveMovieShouldCreateMovieObject
 - testSetMovieList
 - testGetMovieShouldReturnMovie
 - testGetMovieShouldThrowExceptionIfCouldNotFindID

RentalInfoService Unit Test Cases : 
----------------------------------

 - testCalculateRentalAmountForMinimumDaysRegular
 - testCalculateRentalAmountForFourDaysRegular
 - testCalculateRentalAmountForSixDaysChildren
 - testCalculateRentalAmountForNewMovie
 - testCalculateRentalAmountForNewMovieForFiveDays
 - testGetStatementForCustomerForZeroMoviesRental
 - testGetStatementForCustomerForSingleMoviesRental
 - testGetStatementForCustomerForMultipleRentals
 - testAddFrequentBonusForNewMoviesShouldNotIncrementForRegularMovies
 - testAddFrequentBonusForNewMoviesShouldNotIncrementForChildrenMovies
 - testAddFrequentBonusForNewMoviesShouldIncrementOnce
 - testAddFrequentBonusForNewMoviesWithOneDayShouldNotIncrement
 

## To run the test:

```
javac src/*.java
java -cp src MovieRentalApplication
```
