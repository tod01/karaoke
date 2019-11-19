package Server;

import javax.sound.midi.*;
import javax.sound.midi.Instrument;
import java.util.*;
import java.util.stream.Collectors;

public class MidiTrack {

    Map<String, List<Event>> events;
    private Instrument[] instruments;
    int pisteNumber;

    public MidiTrack(Track[] tracks, Instrument[] instruments) {
        this.instruments = instruments;
        events = new HashMap<>();
        this.setTrack(tracks);
    }

    private void setTrack(Track[] tracks) {

        String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

        for(Track track : tracks) {

            for(int i = 0; i < track.size(); ++i) {

                MidiEvent event = track.get(i);

                MidiMessage message = event.getMessage();

                if(message instanceof ShortMessage) {

                    ShortMessage sm = (ShortMessage) message;

                    if(sm.getCommand() == ShortMessage.NOTE_ON || sm.getCommand() == ShortMessage.NOTE_OFF) {

                        int key    = sm.getData1(); // note (hauteur)
                        int octave = (key / 12) -1;
                        int note   = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();

                        Note newNote = new Note(key, event.getTick(), noteName);

                        Event e = new Event(newNote, velocity, sm.getCommand(), octave, sm.getChannel());

                        this.addTrack(instruments[sm.getData1()].getName(), e);

                    }else {
                        //System.out.println(sm.getCommand());
                    }

                } else if(message instanceof MetaMessage) {
                    MetaMessage metaMessage = (MetaMessage) message;
                    int type    = metaMessage.getType();
                    byte[] data = metaMessage.getData();
                    int length  = message.getLength();
                    // MidiEventMessage midiEventMessage = new MidiMetaMessage(type, data, length);
                    // System.out.println(type + " " + data + " " + length);
                }
            }

            //player.addTrack(midiTrack);
        }
    }

    public Map<String, List<Event>> getEvents() {
        return events;
    }

    public void addTrack(String instrument, Event event) {

        if (events.isEmpty() || events.get(instrument) == null) {
            List<Event> events = new ArrayList<>();
            events.add(event);
            this.events.put(instrument, events);
        } else {
            events.get(instrument).add(event);
        }
    }

    protected List<Pair<Event, String>> trackList() {
        List<Pair<Event, String>> trackList = new ArrayList<>();

        for(Map.Entry<String,List<Event>> currEvent : events.entrySet()) {
            for(Event event : currEvent.getValue()) {
                trackList.add(new Pair<>(event, currEvent.getKey()));
            }
        }

        return trackList;
    }

    class SortByTrack implements Comparator<Pair<Event, String>> {

        @Override
        public int compare(Pair<Event, String> n1, Pair<Event, String> n2) {
            return n1.getL().note.noteNumber > n2.getL().note.noteNumber ? 1 :  n1.getL().note.noteNumber < n2.getL().note.noteNumber ? -1 : 0;
        }
    }

    public List<Pair<Event, String>> sortedTrack() {

        List<Pair<Event, String>> sortedList = trackList();

        Collections.sort(sortedList, new SortByTrack());

        return sortedList;
    }

    public void printSortedTracks() {

        List<Pair<Event, String>> sortedList = sortedTrack();

        for(Pair<Event, String> res : sortedList) {
            System.out.println(res.getR() + " " + res.getL().note.noteNumber);
        }

    }

}
