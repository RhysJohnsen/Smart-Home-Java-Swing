//Rhys Johnsen
//Sep. 27, 2025
import java.awt.event.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SmartHome {

    private static LocalDate date = LocalDate.now();
    private static LocalTime time = LocalTime.now();
    private static LocalDateTime lastOpened = LocalDateTime.now(); //Simulate that the program was last run yesterday. (It is set to yesterday in the main function) (This will be used to see if an update is needed)

    //Set bounds of all components of the frame
    private static int frameX = 150;
    private static int frameY = 150;
    private static int frameW = 375;
    private static int frameH = 425;

    private static int lightX = 50;
    private static int lightY = 50;
    private static int lightW = 100;
    private static int lightH = 125;

    private static int fanX = 175;
    private static int fanY = 100;
    private static int fanW = 125;
    private static int fanH = 50;
    private static int fanMax = 2;
    private static int fanMin = 0;

    private static int acSliX = 50;
    private static int acSliY = 250;
    private static int acSliW = 150;
    private static int acSliH = 50;
    private static int acSliMax = 30;
    private static int acSliMin = 15;

    private static int acButX = 225;
    private static int acButY = 200;
    private static int acButW = 75;
    private static int acButH = 100;

    private static int debButX = 50;
    private static int debButY = 325;
    private static int debButW = 250;
    private static int debButH = 25;

    private static int fanLabX = 175;
    private static int fanLabY = 50;
    private static int fanLabW = 125;
    private static int fanLabH = 50;

    private static int acLabX = 50;
    private static int acLabY = 200;
    private static int acLabW = 150;
    private static int acLabH = 50;

    private static int timLabX = 50;
    private static int timLabY = 25;
    private static int timLabW = 250;
    private static int timLabH = 25;

    //Create all components
    private static JButton butLight = new JButton("Lights OFF");
    private static JSlider sliFan = new JSlider(fanMin, fanMax);
    private static JSlider sliAc = new JSlider(acSliMin, acSliMax);
    private static JButton butAc = new JButton("AC OFF");
    private static JButton butDebug = new JButton("DebugOptions");
    private static JLabel labFan = new JLabel("Fan Strength", JLabel.CENTER);
    private static JLabel labAc = new JLabel("AC Temp", JLabel.CENTER);
    private static JLabel labTime = new JLabel(date.toString() + "   " + time.truncatedTo(ChronoUnit.MINUTES).toString(), JLabel.CENTER);

    //Main GUI
    private static void createGUI(House house) {
        JFrame frame = new JFrame("Smart Home");
        JFrame frameDeb = new JFrame("Smart Home Debug");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameW, frameH);
        frame.setLocation(frameX, frameY);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JPanel panelDeb = new JPanel();

        //Light button
        butLight.setBounds(lightX, lightY, lightW, lightH);
        butLight.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                house.toggleLights();
                if(house.getLightsOn()){
                    butLight.setText("Lights ON");
                }
                else{
                    butLight.setText("Lights OFF");
                }
            }
        });
        panel.add(butLight);

        //Fan slider
        sliFan.setBounds(fanX, fanY, fanW, fanH);
        sliFan.setPaintTrack(true);
        sliFan.setPaintTicks(true);
        sliFan.setPaintLabels(true);
        sliFan.setMajorTickSpacing(1);
        sliFan.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                house.setFan(Math.min(Math.max(fanMin, sliFan.getValue()), fanMax)); // Limits to possible values to not damage appliances in case of illegitimate input
            }
        });
        panel.add(sliFan);
        
        //AC slider
        sliAc.setBounds(acSliX, acSliY, acSliW, acSliH);
        sliAc.setPaintTrack(true);
        sliAc.setPaintTicks(true);
        sliAc.setPaintLabels(true);
        sliAc.setMajorTickSpacing(5);
        sliAc.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e){
                house.setAc(sliAc.getValue());
            }
        });
        panel.add(sliAc);

        //AC button
        butAc.setBounds(acButX, acButY, acButW, acButH);
        butAc.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                house.toggleAc();
                if(house.getAcOn()){
                    butAc.setText("AC ON");
                }
                else{
                    butAc.setText("AC OFF");
                }
            }
        });
        panel.add(butAc);

        //Labels
        labFan.setBounds(fanLabX, fanLabY, fanLabW, fanLabH);
        panel.add(labFan);
        labAc.setBounds(acLabX, acLabY, acLabW, acLabH);
        panel.add(labAc);
        labTime.setBounds(timLabX, timLabY, timLabW, timLabH);
        panel.add(labTime);

        //Debug button
        butDebug.setBounds(debButX, debButY, debButW, debButH);
        butDebug.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frameDeb.setSize(frameW, frameH);
                frameDeb.setLocation(frameX + 25, frameY + 25); //Offset debug window
                frameDeb.setVisible(true);
                createDebugGui(house, panelDeb);
            }
        });
        panel.add(butDebug);

        frame.add(panel);
        frameDeb.add(panelDeb);
        frame.setVisible(true);
    }

    //Run the debug GUI
    private static void createDebugGui(House house, JPanel panelDeb){
        JButton butReport = new JButton("Print Report to Console"); //Prints the status of all the house's appliances to the console
        butReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.out.println("Lights on: " + house.getLightsOn());
                System.out.println("Fan Strength: " + house.getFan());
                System.out.println("AC on: " + house.getAcOn());
                System.out.println("AC Temp: " + house.getAc());
            }
        });
        panelDeb.add(butReport);

        JButton butSetDate = new JButton("Update");
        butSetDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                date = date.plusYears(1);
                if(isUpdateNeeded()){
                    update(house);
                }
            }
        });
        panelDeb.add(butSetDate);
    }

    //Check if an update is queued
    private static boolean isUpdateNeeded(){
        boolean toReturn = false;
        if(date.getDayOfYear() > 1 || time.getHour() > 1){//check if the date is passed January 1st, or if the time is past 1AM
            if(date.getYear() > lastOpened.getYear()){//Update if it's the next year
                toReturn = true;
            }
            else if(lastOpened.getHour() < 1){//check if it was last opened before 1AM on January 1st.
                toReturn = true;
            }
        }
        return toReturn;
    }

    //Turn everything off for an update
    private static void update(House house){
        if(house.getLightsOn()){
            house.toggleLights();
            butLight.setText("Lights OFF");
        }
        butLight.setEnabled(false);
        if(house.getAcOn()){
            house.toggleAc();
            butAc.setText("AC OFF");
        }
        butAc.setEnabled(false);
        if(house.getFan() > 0){
            house.setFan(0);
            sliFan.setValue(0);
        }
        sliFan.setEnabled(false);
        sliAc.setEnabled(false);
        //Would update here, allowing control again once it's done
        butLight.setEnabled(true);
        butAc.setEnabled(true);
        sliFan.setEnabled(true);
        sliAc.setEnabled(true);
    }

    //
    public static void main(String[] args) {
        House testHouse = new House(false, 0, 20, false);//a virtual house that the system would theoretically send data to and from
        lastOpened = lastOpened.minusDays(1);
        System.out.println(lastOpened);
        if(isUpdateNeeded()){//check if update is needed upon opening the program
            update(testHouse);
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI(testHouse);
            }
        });
    }
}