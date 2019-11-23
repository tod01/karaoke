package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class MidiGui extends JFrame {

    private static final String TITLE = "Midi displayed";
    private int with;
    private int height;
    String file;
    static JFrame jFrame;
    static JLabel jLabel;


    public MidiGui(int with, int height, String file) {
        this.with = with;
        this.height = height;
        this.file = file;
    }

    public void launcher() throws IOException, InterruptedException, ParseException {
        jFrame = new JFrame("Karaoke");
        jFrame.setLayout(new GridLayout());
        jLabel = new JLabel("Music");
        jFrame.add(jLabel,BorderLayout.CENTER);
        jFrame.setSize(with, height);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(new FileInputStream(file))));
        String line;
        DateFormat dateFormat = new SimpleDateFormat("mm:ss.SS");
        long reference = 0;
        Calendar calendar = Calendar.getInstance();

        while((line = bufferedReader.readLine()) != null) {

            if(line.startsWith("[")) {
                String timeText = line.split("]")[0].substring(1);
                Date date = dateFormat.parse(timeText);

                calendar.setTime(date);

                long milliseconds = 60000 * calendar.get(Calendar.MINUTE) + 1000* calendar.get(Calendar.SECOND) + calendar.get(Calendar.MILLISECOND);
                long result = milliseconds - reference;
                reference = milliseconds;
                Thread.sleep(result);
                System.out.println(line + " | " + " " + calendar.get(Calendar.MINUTE) + ":"+ calendar.get(Calendar.SECOND) + ":" + calendar.get(Calendar.MILLISECOND) + " " + milliseconds + " " + result);
                //label.setText(line);
                //add(label);*
                jLabel.setVerticalAlignment(SwingConstants.CENTER);
                jLabel.setHorizontalAlignment(SwingConstants.CENTER);
                jLabel.setForeground(getColor());
                jLabel.setText(line);
            }
        }

        bufferedReader.close();

    }

    private Color getColor() {
        Random random = new Random();

        float red   = random.nextFloat();
        float green = random.nextFloat();
        float blue  = random.nextFloat();

        return new Color(red, green, blue);
    }

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        new MidiGui(500, 500, "MidiFiles/The-Black-Eyed-Peas-I-Gotta-Feeling.lrc").launcher();
    }
}
