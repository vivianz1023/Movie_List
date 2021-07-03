package persistence;


import model.Movie;
import model.MovieList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A reader that can read movie data from a file
public class Reader {
    public static final String DELIMITER = ",";

    public Reader() {

    }

    // EFFECTS: returns a movie list parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    //COMMENT: Source from: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    public static MovieList readMovies(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return addMovieToList(fileContent);
    }


    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    //COMMENT: Source from: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of movies parsed from list of strings
    // where each string contains data for one movie
    //COMMENT: Source from: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    private static MovieList addMovieToList(List<String> fileContent) {
        MovieList movieList1 = new MovieList();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            movieList1.addMovie(parseMovie(lineComponents));
        }

        return movieList1;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    //COMMENT: Source from: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 3 where element 0 represents the
    // movie score, element 1 represents the name of the movie, element 2 represents the name of the director
    // EFFECTS: returns an movie constructed from components
    //COMMENT: Source from: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    private static Movie parseMovie(List<String> components) {
        double movieScore = Double.parseDouble(components.get(0));
        String movieName = components.get(1);
        String directorName = components.get(2);

        return new Movie(movieScore, movieName, directorName);
    }
}
