package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//test methods in the Movie class
class MovieTest {
    Movie movie1;
    Movie movie2;

    @BeforeEach
    void runBefore() {
        movie1 = new Movie(8.7, "The Lord of the Rings : The Two Towers",
                "Peter Jackson");
        movie2 = new Movie(7.9, "Iron Man", "Jon Favreau");

    }

    @Test
    void testGetMovieName() {
        assertEquals("The Lord of the Rings : The Two Towers", movie1.getMovieName());
        assertEquals("Iron Man", movie2.getMovieName());
    }

    @Test
    void testGetDirectorName() {
        assertEquals("Peter Jackson", movie1.getDirectorName());
        assertEquals("Jon Favreau", movie2.getDirectorName());
    }

    @Test
    void testGetMovieScore() {
        assertEquals(8.7, movie1.getMovieScore());
        assertEquals(7.9, movie2.getMovieScore());
    }


}