package model;



//Represents a movie having a movie name, director name and movie score
public class Movie {
    private double movieScore;
    private String movieName;
    private String directorName;


    // EFFECTS: movieName is set to movieName; movieScore is a non-negative number set to movieScore; directorName is
    //          the director's name of the movie set to directorName
    public Movie(double movieScore, String movieName, String directorName) {
        this.movieScore = movieScore;
        this.movieName = movieName;
        this.directorName = directorName;

    }

    //EFFECTS: returns movie name
    public String getMovieName() {
        return movieName;
    }

    //EFFECTS: returns director name
    public String getDirectorName() {
        return directorName;
    }

    //EFFECTS: returns movie score
    public double getMovieScore() {
        return movieScore;
    }



}
