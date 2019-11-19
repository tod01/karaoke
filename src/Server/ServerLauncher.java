package Server;


import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerLauncher {

    public static void main(String[] args) {

        if(args.length < 1) {
            System.out.println("file ");
            System.exit(1);
        }

        final int PORT = 1324;

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket socket = serverSocket.accept();

            System.out.println("Server connected to " + socket.getInetAddress());

            PrintStream streamToClient = new PrintStream(socket.getOutputStream());

            // sent all musics title
            MidiFileData midiFileData = new MidiFileData();

            streamToClient.println(midiFileData);

            Scanner streamFromClient = new Scanner(socket.getInputStream());

            // TODO :: get user choice

        }catch (Exception io) {
            io.printStackTrace();
        }

        MidiSequencer midiSequencer = new MidiSequencer(args[0]);
        midiSequencer.play();
    }
}
