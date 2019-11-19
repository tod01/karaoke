package Server;

import javax.sound.midi.MidiChannel;

public class Note {

    long tick;
    int noteNumber;
    String noteName;

    public Note(int noteNumber, long tick, String noteName) {
        this.noteNumber = noteNumber;
        this.tick       = tick;
    }

    public String toString() {
        return tick + " " + noteNumber;
    }
}
