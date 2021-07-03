package ui;

import model.Movie;
import model.MovieList;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.Scanner;


//Movie Wish List application
public class MovieWishListApp {
    private Scanner input;
    private Movie newMovie;
    private MovieList movieList = new MovieList();
    private double movieScore;
    private String movieName;
    private String directorName;
    private static final String MOVIELIST_FILE = "./data/movieList.txt";
    private Writer writer;
    private File file = new File(MOVIELIST_FILE);





    //EFFECTS: runs the movie wish list application
    public MovieWishListApp() {
        runMovieWishList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    //COMMENT: Source from: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    private void runMovieWishList() {
        boolean next = true;
        String work;
        input = new Scanner(System.in);

        loadMovieList();

        while (next) {
            showMenu();
            work = input.nextLine();
            work = work.toLowerCase();

            if (work.equals("q")) {
                next = false;
            } else {
                doWork(work);
            }
        }

        System.out.println("\nThanks for using Movie Wish List!");
    }



    // MODIFIES: this
    // EFFECTS: loads movie wish list from MOVIELIST_FILE, if that file exists;
    // otherwise print out a note
    private void loadMovieList() {
        try {
            movieList = Reader.readMovies(file);
            if (movieList.isEmpty()) {
                throw new IOException();
            } else {
                newMovie = movieList.getMovie(0);
            }
            System.out.println("\nmovie list is not empty");

        } catch (IOException e) {
            System.out.println("\nEmpty movie list!");
            System.out.println("\nPlease add a movie!");


        }
    }




    // EFFECTS: saves state of movie list to MOVIELIST_FILE
    private void saveMovieList() {
        try {
            writer = new Writer(file);
            writer.write(movieList);
            writer.close();
            System.out.println("Movie saved to file " + MOVIELIST_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save movie to " + MOVIELIST_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }




    //EFFECTS: show action options to the user
    //COMMENT: source from: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    private void showMenu() {
        System.out.println("\n Please select one:");
        System.out.println("\ta -> add movie");
        System.out.println("\td -> delete movie");
        System.out.println("\ts -> search a movie");
        System.out.println("\tsave -> save movie to file");
        System.out.println("\tsee -> see number of movie on the list");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
    //EFFECTS: do works for users
    //COMMENT: source from: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    private void doWork(String work) {
        if (work.equals("a")) {
            addMovie();
        } else if (work.equals("d")) {
            deleteMovie();
        } else if (work.equals("s")) {
            searchMovie();
        } else if (work.equals("see")) {
            movieNum();
        } else if (work.equals("save")) {
            saveMovieList();
        } else {
            System.out.println("Please check your selection!\n");
        }
    }


    //MODIFIES: this
    //EFFECTS: add a movie to the list
    private void addMovie() {
        System.out.println("Please enter the name of the movie:");
        movieName = input.nextLine();
        movieName = movieName.toLowerCase();

        if (movieList.searchAndReturn(movieName) == null) {
            System.out.println("Please enter the name of the director:");
            directorName = input.nextLine();
            directorName = directorName.toLowerCase();

            System.out.println("Please enter the movie score:");
            movieScore = input.nextDouble();
            input.nextLine();

            newMovie = new Movie(movieScore, movieName, directorName);
            movieList.addMovie(newMovie);

            System.out.println("Successfully adding a movie!\n");
        } else {
            System.out.println("This movie is already in the list!\n");
        }

    }

    //MODIFIES: this
    //EFFECTS: delete a movie from the list
    private void deleteMovie() {
        if (movieList.isEmpty()) {
            System.out.println("Your wish list is empty, no movie can be deleted!\n");
        } else {
            System.out.println("Please enter the name of the movie you want to delete:");
            movieName = input.nextLine();
            movieName = movieName.toLowerCase();
            if (movieList.searchAndReturn(movieName) != null) {
                movieList.removeMovie(movieList.searchAndReturn(movieName));
                System.out.println("Successfully delete your selected movie!\n");

            } else {
                System.out.println("Cannot find this movie, please check your input!\n");

            }
        }
    }


    //EFFECTS: search a movie on the list, if the movie is on the list show details of the movie.
    //         if the movie is not on the list, print error message.
    private void searchMovie() {
        System.out.println("Please enter the name of the movie you want to search:");
        movieName = input.nextLine();
        movieName = movieName.toLowerCase();


        if (movieList.searchAndReturn(movieName) != null) {
            seeDetail(movieName);
        } else {
            System.out.println("Invalid Movie Name!\n");
        }
    }

    //EFFECTS: see details of the movie on the list
    private void seeDetail(String movieName) {
        System.out.println("Show details of movie\n");
        System.out.println("Movie Name: \n" + movieList.showMovieName(movieList.searchAndReturn(movieName)));
        System.out.println("Director Name: \n" + movieList.showDirectorName(movieList.searchAndReturn(movieName)));
        System.out.println("Movie Score: \n" + movieList.showMovieScore(movieList.searchAndReturn(movieName)));
    }

    //EFFECTS: see the number of movie on the list
    private void movieNum() {
        System.out.println("The number of movie on the list: " + movieList.movieNum());
    }




}
