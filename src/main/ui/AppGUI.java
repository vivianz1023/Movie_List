package ui;


import model.Movie;
import model.MovieList;
import persistence.Reader;
import persistence.Writer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;



//Movie Wish List application (GUI)
public class AppGUI extends JPanel implements ListSelectionListener {


    private MovieList movieList = new MovieList();
    private Movie movie;

    private JList list;
    private DefaultListModel listModel;
    private static final String addString = "Add";
    private static final String deleteString = "Delete";
    private static final String saveString = "Save";
    private static final String upLoadString = "Upload";
    private JButton addButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton upLoadButton;
    private JLabel movieName;
    private JTextField movieNames;
    private JLabel directorName;
    private JTextField directorNames;
    private JLabel movieScore;
    private JTextField movieScores;
    private JLabel movieNumber;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu searchMenu = new JMenu("Search");
    private JMenuItem searchMovie = new JMenuItem("Search a movie");

    private static String filePath = "./data/Alesis-Fusion-Pizzicato-Strings-C4.wav";
    private Writer writer;
    private static final String MOVIELIST_FILE = "./data/saveMovies.txt";
    private File file = new File(MOVIELIST_FILE);




    public AppGUI() {
        super(new BorderLayout());
        listModel = new DefaultListModel();
        listModel.addElement("");

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(listScrollPane, BorderLayout.CENTER);

        directorNames = new JTextField(10);
        movieName = new JLabel("movie name :", SwingConstants.HORIZONTAL);
        directorName = new JLabel("director name :", SwingConstants.HORIZONTAL);
        movieScore = new JLabel("movie score : ", SwingConstants.HORIZONTAL);
        movieScores = new JTextField(10);
        movieNumber = new JLabel("movie number : 0", SwingConstants.HORIZONTAL);

        setUpSearchMenu();
        setUpAddButton();
        setUpDeleteButton();
        setUpSaveButton();
        setUpUpLoadButton();
        setUpPanel();


    }

    //EFFECTS: set up the panel
    private void setUpPanel() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));

        buttonPane.add(menuBar,SwingConstants.HORIZONTAL);
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(saveButton);
        buttonPane.add(deleteButton);
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(movieName);
        buttonPane.add(movieNames);
        buttonPane.add(directorName);
        buttonPane.add(directorNames);
        buttonPane.add(movieScore);
        buttonPane.add(movieScores);
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(addButton);
        buttonPane.add(upLoadButton);
        buttonPane.add(movieNumber);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        add(buttonPane, BorderLayout.PAGE_END);
    }

    //EFFECTS: set up the search menu
    private void setUpSearchMenu() {
        menuBar.add(searchMenu);
        searchMenu.add(searchMovie);
        searchMovie.addActionListener(new SearchDialog());

    }

    //EFFECTS: set up the saveButton
    private void setUpUpLoadButton() {
        upLoadButton = new JButton(upLoadString);
        UpLoadListener upLoadListener = new UpLoadListener(upLoadButton);
        upLoadButton.setActionCommand(upLoadString);
        upLoadButton.addActionListener(upLoadListener);
        upLoadButton.setEnabled(true);

    }

    //EFFECTS: set up the saveButton
    private void setUpSaveButton() {
        saveButton = new JButton(saveString);
        SaveListener saveListener = new SaveListener(saveButton);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(saveListener);
        saveButton.setEnabled(true);
    }

    //EFFECTS: set up the deleteButton
    private void setUpDeleteButton() {
        deleteButton = new JButton(deleteString);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new DeleteListener());

    }

    //EFFECT: set up the addButton
    private void setUpAddButton() {

        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
        movieNames = new JTextField(10);
        movieNames.addActionListener(addListener);
        movieNames.getDocument().addDocumentListener(addListener);
        String name = listModel.getElementAt(
                list.getSelectedIndex()).toString();
    }

    //EFFECTS:play the sound of the file
    private static void playMusic(String fileName) {
        InputStream music;
        try {
            music = new FileInputStream(new File(fileName));
            AudioStream audios = new AudioStream(music);
            AudioPlayer.player.start(audios);

        } catch (Exception e) {
            System.out.println("" + e);
        }
    }




    //represents a class of the search dialog
    class SearchDialog extends JDialog implements ActionListener {

        @Override
        //pop up an input dialog and a message dialog
        public void actionPerformed(ActionEvent e) {
            String movieName = JOptionPane.showInputDialog(null, "movie name: ");
            movieName = movieName.toLowerCase();

            if (movieList.searchAndReturn(movieName) != null) {
                movie = movieList.searchAndReturn(movieName);
                String movieDetail = movie.getMovieName() + "   " + movie.getDirectorName() + "   "
                        + movie.getMovieScore();
                JOptionPane.showMessageDialog(null, movieDetail);
            } else {
                JOptionPane.showMessageDialog(null, "Can not find the movie!");
            }

        }

    }



    //represents a class of the delete listener
    //COMMENTS: source from: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/
    //                       javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    class DeleteListener implements ActionListener {
        @Override
        //show actions when press the delete button
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            playMusic(filePath);

            int index = list.getSelectedIndex();
            listModel.remove(index);
            movieList.removeMovie(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                deleteButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }

            movieNumber.setText("movie number: " + movieList.movieNum());
        }
    }


    //represent a class of an upload listener
    class UpLoadListener implements ActionListener {

        private JButton button;

        public UpLoadListener(JButton button) {
            this.button = button;
        }

        @Override
        //EFFECTS: upload movies in the saved file
        public void actionPerformed(ActionEvent e) {
            playMusic(filePath);
            try {
                movieList = Reader.readMovies(new File(MOVIELIST_FILE));
                //get the number of movie in the movie list
                int val = movieList.movieNum();
                listModel.clear();
                for (int i = 0; i < val; i++) {
                    //read data from the movie list
                    String s = movieList.getMovie(i).getMovieName() + "  ,  " + movieList.getMovie(i).getDirectorName()
                            + "  ,  " + movieList.getMovie(i).getMovieScore();
                    //write data to list model object
                    listModel.addElement(s);
                }

                list.setModel(listModel);
                // update the movieNumber label
                movieNumber.setText("movie number: " + movieList.movieNum());

            } catch (Exception exception) {
                System.out.println("" + exception);
            }
        }
    }


    //represents a class that implement action for save button
    class SaveListener implements ActionListener {
        private JButton button;

        public SaveListener(JButton button) {
            this.button = button;
        }

        @Override
        //EFFECTS: save the movie list to the file
        public void actionPerformed(ActionEvent e) {
            playMusic(filePath);

            try {
                writer = new Writer(file);
                writer.write(movieList);
                writer.close();
                System.out.println("Movie saved to file " + MOVIELIST_FILE);
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to save movie to " + MOVIELIST_FILE);
            } catch (UnsupportedEncodingException exception) {
                // this is due to a programming error
                exception.printStackTrace();
            }
        }
    }




    //represents a class of add listener
    //COMMENTS: source from: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/
    //                       javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    class AddListener implements ActionListener, DocumentListener {

        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }


        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        //EFFECTS: enable the button
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        //EFFECTS: deal with empty textField
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }

        @Override
        //EFFECTS: give action to the add button
        public void actionPerformed(ActionEvent e) {
            listModel.removeElement("");
            playMusic(filePath);
            createNewMovie();

            movieNumber.setText("movie number: " + movieList.movieNum());
        }
    }

    //EFFECTS: create a new movie based on the input and check duplicates
    //         if the movie is already in the movie list does not add it to the list,
    //         and does not add to the JList
    private void createNewMovie() {
        String movieName = movieNames.getText();
        movieName = movieName.toLowerCase();
        String directorName = directorNames.getText();
        directorName = directorName.toLowerCase();
        double movieScore = Double.parseDouble(movieScores.getText());
        movie = new Movie(movieScore,movieName,directorName);

        if (movieList.searchAndReturn(movieName) != null) {
            Toolkit.getDefaultToolkit().beep();
            movieNames.requestFocusInWindow();
            movieNames.selectAll();
            return;
        } else {
            movieList.addMovie(movie);
            addMovieToList();
        }


    }

    //add movie to the JList
    private void addMovieToList() {

        int index = list.getSelectedIndex(); //get selected index
        if (index == -1) { //no selection, so insert at beginning
            index = 0;
        } else { //add after the selected item
            index++;
        }

        listModel.insertElementAt(movie.getMovieName() + "  ,  " + movie.getDirectorName() + "  ,  "
                + movie.getMovieScore(), index);

        //Reset the text field.
        resetTextField();

        //Select the new item and make it visible.
        list.setSelectedIndex(index);
        list.ensureIndexIsVisible(index);

    }



    //EFFECTS: reset the text fields to be empty
    private void resetTextField() {
        movieNames.requestFocusInWindow();
        movieNames.setText("");
        directorNames.setText("");
        movieScores.setText("");

    }

    @Override
    //EFFECTS: deal with the delete button
    //COMMENTS: source from: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/
    //                       javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable delete button.
                deleteButton.setEnabled(false);

            } else {
                //Selection, enable the delete button.
                deleteButton.setEnabled(true);
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    //COMMENTS: source from: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/
    //                       javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("MovieListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new AppGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
