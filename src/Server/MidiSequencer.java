
package Server;
import javax.sound.midi.*;
import javax.sound.midi.Instrument;
import java.util.List;

public class MidiSequencer {

    private Synthesizer synthesizer;
    private Sequencer sequencer;
    private MidiTrack midiTrack;
    private MidiFileData midiFileData;
    private Sequence sequence;
    private Instrument[] instruments;

    public MidiSequencer(String fileName) {
        this.midiFileData = new MidiFileData(fileName);
        this.initSequencerInfo();
        this.setSequenceMidi();

        Soundbank soundbank =synthesizer.getDefaultSoundbank();

        this.instruments = soundbank.getInstruments();
        this.midiTrack = new MidiTrack(sequence.getTracks(), this.instruments);
    }

    private void setSequenceMidi() {

        try {
            this.sequence = MidiSystem.getSequence(this.midiFileData.getFile());
        }catch (Exception exception) {
            exception.printStackTrace();
            System.exit(2);
        }

    }

    private void initSequencerInfo() {

        try {
            this.synthesizer = MidiSystem.getSynthesizer();
            this.synthesizer.open();
            this.sequencer = MidiSystem.getSequencer();
        }catch (Exception exception) {
            exception.printStackTrace();
            System.exit(2);
        }

    }

    public Instrument[] getInstruments() {
        return instruments;
    }

    public MidiTrack getMidiTrack() {
        return midiTrack;
    }

    public Sequencer getSequencer() {
        return sequencer;
    }

    public Synthesizer getSynthesizer() {
        return synthesizer;
    }

    public  List<Pair<Event, String>> sortedTracks() {
        return midiTrack.sortedTrack();
    }

    public MidiFileData getMidiFileData() {
        return midiFileData;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void play() {

        try {

            Sequence sequence1 = new Sequence(Sequence.PPQ, this.getSequence().getResolution());
            Track track = sequence1.createTrack();

            List<Pair<Event, String>> tracks  = this.sortedTracks();

            sequencer.open(); // to review
            //System.out.println(tracks);
            for(Pair<Event, String> note : tracks) {
                ShortMessage shortMessage = new ShortMessage();
                shortMessage.setMessage(note.getL().getCommand(), note.getL().getChannel(), note.getL().getNote().noteNumber, note.getL().getVelocity());
                track.add(new MidiEvent(shortMessage , note.getL().getNote().tick));
                //System.out.println(note.getL().command +"," + note.getL().channel+ ","+ note.getL().note.noteNumber +","+ note.getL().velocity);
            }

            sequencer.setSequence(sequence1);
            sequencer.setTempoInBPM(getSequencer().getTempoInBPM());
            sequencer.start();


            while(sequencer.isRunning()) {

            }

            sequencer.close();

        }catch (MidiUnavailableException exception) {
            System.out.println("Sequencer problem");
            System.exit(2);
        }catch (InvalidMidiDataException messageException) {
            System.out.println("Message problem!");
            sequencer.close();
            System.exit(3);
        }

    }
}
