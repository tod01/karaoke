package Server;


public class ServerLauncher {

    public static void main(String[] args) {

        if(args.length < 1) {
            System.out.println("file ");
            System.exit(1);
        }

        MidiSequencer midiSequencer = new MidiSequencer(args[0]);
        midiSequencer.play();
    }
}
