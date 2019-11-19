package Server;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class MidiFileData {

    private String author;
    String title;
    Date date;
    String comment;
    File file;
    MidiFileFormat midiFileFormat;
    int fileResolution;

    public MidiFileData(String file) {

        this.openFile(file);

        this.fileInformation();

        this.author = (String) midiFileFormat.getProperty("author");
        this.title = (String) midiFileFormat.getProperty("title");
        this.comment = (String) midiFileFormat.getProperty("comment");
        this.date = (Date) midiFileFormat.getProperty("date");
        this.fileResolution = midiFileFormat.getResolution();
    }

    private void openFile(String file) {

        File f = new File(file);

        if(f.exists()) {
            this.file = f;
        }else {
            System.out.println("File " + file + " not found ");
            System.exit(1);
        }
    }

    private void fileInformation() {

        try {
            this.midiFileFormat = MidiSystem.getMidiFileFormat(this.file);
        }catch (Exception exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public File getFile() {
        return file;
    }

    public Date getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public String getTitle() {
        return title;
    }

    public int getFileResolution() {
        return fileResolution;
    }
}

