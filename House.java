//Rhys Johnsen
//Sep. 27, 2025
public class House {// This is meant to represent a house that the program would interact with
    
    private boolean lightsOn;
    private int fan;
    private int ac;
    private boolean acOn;
    
    public House(boolean lightsOn, int fan, int ac, boolean acOn){
        this.lightsOn = lightsOn;
        this.fan = fan;
        this.ac = ac;
        this.acOn = acOn;
    }

    public boolean getLightsOn(){
        return lightsOn;
    }

    public void toggleLights(){
        lightsOn = !lightsOn;
    }

    public int getFan(){
        return fan;
    }

    public void setFan(int strength){
        fan = strength;
    }

    public int getAc(){
        return ac;
    }

    public void setAc(int temp){
        ac = temp;
    }

    public boolean getAcOn(){
        return acOn;
    }

    public void toggleAc(){
        acOn = !acOn;
    }
}
