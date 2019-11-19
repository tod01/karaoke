package Server;
import javax.sound.midi.MidiChannel;

public class Event implements Comparable<Event> {
    Note note;
    int velocity;
    int command;
    int octave;
    int channel;

    public Event(Note note, int velocity, int command, int octave, int channel) {
        this.note = note;
        this.velocity = velocity;
        this.command = command;
        this.octave  = octave;
        this.channel = channel;
    }

    public int getChannel() {
        return channel;
    }

    public int getCommand() {
        return command;
    }

    public int getOctave() {
        return octave;
    }

    public int getVelocity() {
        return velocity;
    }

    public Note getNote() {
        return note;
    }

    public String toString() {
        return "("+note.noteNumber+ " "+ note.tick + ")";
    }


    public int compareTo(Event event) {
        return this.note.noteNumber > event.note.noteNumber ? 1 : this.note.noteNumber < event.note.noteNumber ? - 1 : 0;
    }

}
