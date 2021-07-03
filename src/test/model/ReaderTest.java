package model;

import model.Movie;
import model.MovieList;
import org.junit.jupiter.api.Test;
import persistence.Reader;

import java.io.File;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {
    private Reader reader = new Reader();

    @Test
    void testParseMovieFile() {
        try {
//            File movieFile = new File("./data/testMovieFile.txt");
            MovieList movieList = reader.readMovies(new File("./data/testMovieFile.txt"));

            Movie movie1 = movieList.getMovie(0);
            assertEquals(8.9, movie1.getMovieScore());
            assertEquals("The Lord of the Rings", movie1.getMovieName());
            assertEquals("Peter Jackson", movie1.getDirectorName());

            Movie movie2 = movieList.getMovie(1);
            assertEquals(7.9, movie2.getMovieScore());
            assertEquals("Iron Man", movie2.getMovieName());
            assertEquals("Jon Favreau", movie2.getDirectorName());

            Movie movie3 = movieList.getMovie(2);
            assertEquals(7.8, movie3.getMovieScore());
            assertEquals("Titanic", movie3.getMovieName());
            assertEquals("James Cameron", movie3.getDirectorName());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testThrowIOException() {
        try {
            reader.readMovies(new File("./path/does/not/exist/testAccount.txt"));

        } catch (IOException e) {
            //expect
        }
    }
}
