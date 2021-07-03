package model;

import model.Movie;
import model.MovieList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class WriterTest {
    private static final String TEST_FILE = "./data/testMovies.txt";
    private Writer testWriter;
    private Movie movie1;
    private Movie movie2;
    private MovieList movieList;
    private Reader reader;


    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        movie1 = new Movie(8.9,"The Lord of the Rings", "Peter Jackson");
        movie2 = new Movie(7.8,"Titanic", "James Cameron");
        movieList = new MovieList();
        movieList.addMovie(movie1);
        movieList.addMovie(movie2);
        reader = new Reader();


    }


    @Test
    void testWriteMovies() {
        // save movie1 and movie2 to file
        testWriter.write(movieList);
        testWriter.close();

        // now read them back in and verify that the movies have the expected values
        try {
            MovieList movieList = Reader.readMovies(new File(TEST_FILE));

            Movie movie1 = movieList.getMovie(0);
            assertEquals(8.9, movie1.getMovieScore());
            assertEquals("The Lord of the Rings", movie1.getMovieName());
            assertEquals("Peter Jackson", movie1.getDirectorName());

            Movie movie2 = movieList.getMovie(1);
            assertEquals(7.8, movie2.getMovieScore());
            assertEquals("Titanic", movie2.getMovieName());
            assertEquals("James Cameron", movie2.getDirectorName());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
