package model;

import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//representing a movie wish list
public class MovieList implements Saveable {
    public List<Movie> movieList;

    //EFFECTS: construct empty movieList
    public MovieList() {
        this.movieList = new LinkedList<>();
    }

    //EFFECTS: get a movie from the movie list
    public Movie getMovie(int index) {
        return movieList.get(index);
    }

    //MODIFIES: this
    //EFFECTS: remove a movie from the movieList by index, and returns false
    public boolean removeMovie(int index) {
        movieList.remove(index);
        return false;
    }

    //MODIFIES: this
    //EFFECTS: remove a movie from the movieList, and returns false
    public boolean removeMovie(Movie movie) {
        movieList.remove(movie);
        return false;
    }

    //MODIFIES: this
    //EFFECTS: add a movie to the end of the movieList, and returns true
    public boolean addMovie(Movie movie) {
        movieList.add(movie);
        return true;
    }


    //EFFECTS: returns movie name of the selected movie from the list
    public String showMovieName(Movie movie) {
        return movie.getMovieName();
    }

    //EFFECTS: returns director's name of the selected movie from the list
    public String showDirectorName(Movie movie) {
        return movie.getDirectorName();
    }

    //EFFECTS: returns movie score of the selected movie from the list
    public double showMovieScore(Movie movie) {
        return movie.getMovieScore();
    }

    //EFFECTS: returns true if the movieList is empty, false otherwise
    public boolean isEmpty() {
        if (movieList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    //EFFECTS: returns the number of movies in the list
    public int movieNum() {
        return movieList.size();
    }


    //EFFECTS: search a movie name in the list, if the movie is on the list, return the searched movie,
    //         otherwise return null
    public Movie searchAndReturn(String movieName) {
        for (int i = 0; i < movieList.size(); i++) {
            if (movieName.equals((movieList.get(i)).getMovieName())) {
                return movieList.get(i);
            }
        }
        return null;
    }


    //EFFECTS: save each movie on tbe movie list to the file
    @Override
    public void save(PrintWriter printWriter) {
        for (Movie movie : movieList) {
            printWriter.print(movie.getMovieScore());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(movie.getMovieName());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(movie.getDirectorName());
            printWriter.print("\r\n");
        }
    }
}
