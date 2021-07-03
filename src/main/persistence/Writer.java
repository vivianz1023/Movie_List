package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

// A writer that can write movie data to a file
public class Writer {
    private PrintWriter printWriter;

    // EFFECTS: constructs a writer that will write data to file
    //COMMENT: Source from: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    public Writer(File file) throws FileNotFoundException, UnsupportedEncodingException {
        printWriter = new PrintWriter(file, "UTF-8");
    }

    // MODIFIES: this
    // EFFECTS: writes saveable to file
    //COMMENT: Source from: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    public void write(Saveable saveable) {
        saveable.save(printWriter);
    }

    // MODIFIES: this
    // EFFECTS: close print writer
    // NOTE: you MUST call this method when you are done writing data!
    //COMMENT: Source from: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    public void close() {
        printWriter.close();
    }
}

