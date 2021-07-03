package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

//test methods in the movieList class
public class MovieListTest {
    Movie movie1;
    Movie movie2;
    Movie movie3;

    MovieList movieList;

    @BeforeEach
    void runBefore() {
        movie1 = new Movie(8.7, "The Lord of the Rings : The Two Towers",
                "Peter Jackson");
        movie2 = new Movie(7.9, "Iron Man", "Jon Favreau");
        movie3 = new Movie(7.8, "Titanic", "James Cameron");

        movieList = new MovieList();

    }

    @Test
    void testAddMovie() {
        assertTrue(movieList.addMovie(movie1));
        assertTrue(movieList.addMovie(movie2));
    }

    @Test
    void testRemoveMovie() {
        movieList.addMovie(movie1);
        movieList.addMovie(movie2);
        movieList.addMovie(movie3);
        assertFalse(movieList.removeMovie(movie2));
        assertFalse(movieList.removeMovie(movie3));
        assertFalse(movieList.removeMovie(movie1));

    }

    @Test
    void testGetMovie() {
        movieList.addMovie(movie1);
        movieList.addMovie(movie2);
        movieList.addMovie(movie3);
        assertEquals(movie1, movieList.getMovie(0));
        assertEquals(movie2, movieList.getMovie(1));
        assertEquals(movie3, movieList.getMovie(2));


    }

    @Test
    void testShowMovieName() {
        movieList.addMovie(movie1);
        movieList.addMovie(movie2);
        assertEquals("The Lord of the Rings : The Two Towers", movieList.showMovieName(movie1));
        assertEquals("Iron Man", movieList.showMovieName(movie2));
    }

    @Test
    void testShowDirectorName() {
        movieList.addMovie(movie1);
        movieList.addMovie(movie2);
        assertEquals("Peter Jackson", movieList.showDirectorName(movie1));
        assertEquals("Jon Favreau", movieList.showDirectorName(movie2));
    }

    @Test
    void testShowMovieScore() {
        movieList.addMovie(movie1);
        movieList.addMovie(movie2);
        movieList.addMovie(movie3);
        assertEquals(8.7, movieList.showMovieScore(movie1));
        assertEquals(7.9, movieList.showMovieScore(movie2));
        assertEquals(7.8, movieList.showMovieScore(movie3));
    }

    @Test
    void testIsEmpty() {
        assertTrue(movieList.isEmpty());
        movieList.addMovie(movie1);
        assertFalse(movieList.isEmpty());
    }

    @Test
    void testMovieNum() {
        assertEquals(0, movieList.movieNum());
        movieList.addMovie(movie1);
        movieList.addMovie(movie2);
        movieList.addMovie(movie3);
        assertEquals(3, movieList.movieNum());
    }

    @Test
    void testSearchAndReturn() {
        movieList.addMovie(movie1);
        movieList.addMovie(movie2);

        assertEquals(movie1, movieList.searchAndReturn("The Lord of the Rings : The Two Towers"));
        assertEquals(null, movieList.searchAndReturn("Titanic"));
    }

    @Test
    void testRemoveMovieByIndex() {
        movieList.addMovie(movie1);
        movieList.addMovie(movie2);
        assertFalse(movieList.removeMovie(0));
    }
}

