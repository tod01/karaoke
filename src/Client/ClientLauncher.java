package Client;

import Server.MidiFileData;

import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientLauncher {

    public static void main(String[] args) {

        /*if(args.length < 1) {
            System.out.println("Parameter missing");
            System.exit(1);
        }*/

        final int PORT = 1324;

        //String ipAdress = args[0];

        try {
            Socket socket = new Socket("127.0.0.1", PORT);
            System.out.println("Machine " + socket.getLocalAddress());

           // Scanner scannerToServer = new Scanner(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);

            // Display list of musics to display from server

            ObjectInputStream objectFromServer = new ObjectInputStream(socket.getInputStream());

            MidiFileData midiFileData = (MidiFileData) objectFromServer.readObject();
            List<String> filesList = midiFileData.getMidiFiles();
            System.out.println(filesList);

            int userChoice = 0;
            do {
                System.out.println("Choose which music do you want to hear ");
                userChoice = scanner.nextInt();
            }while(userChoice <= 0 || userChoice > filesList.size());


            // Send user choice
            PrintStream streamToServer = new PrintStream(socket.getOutputStream());
            streamToServer.println(userChoice);

            // TODO :: get the music listened by the user and send statistics

            objectFromServer.close();
        } catch (Exception io) {
            io.printStackTrace();
        }
    }
}
