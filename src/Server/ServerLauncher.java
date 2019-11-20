package Server;


import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerLauncher {

    public static void main(String[] args) {

        /*
        if(args.length < 1) {
            System.out.println("file ");
            System.exit(1);
        }*/

        final int PORT = 1324;

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket socket = serverSocket.accept();

            System.out.println("Server connected to " + socket.getInetAddress());

            PrintStream streamToClient = new PrintStream(socket.getOutputStream());
            ObjectOutputStream objectToClient = new ObjectOutputStream(streamToClient);

            // sent all musics title
            MidiFileData midiFileData = new MidiFileData();

            objectToClient.writeObject(midiFileData);

            Scanner streamFromClient = new Scanner(socket.getInputStream());

            // get user choice
            int userChoice = streamFromClient.nextInt();
            System.out.println("choice " + userChoice);

            MidiSequencer midiSequencer = new MidiSequencer(midiFileData.getMidiFiles().get(userChoice-1));
            midiSequencer.play();

        }catch (Exception io) {
            io.printStackTrace();
        }
    }
}
