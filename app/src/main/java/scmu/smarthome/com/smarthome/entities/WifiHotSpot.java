package scmu.smarthome.com.smarthome.entities;

import java.util.Comparator;

public class WifiHotSpot implements Comparable {

    private String ssid;
    private int level;

    public WifiHotSpot(){

    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return getLevel() + " : " + getSsid();
    }

    @Override
    public int compareTo(Object another) {
        WifiHotSpot hotSpot = (WifiHotSpot) another;

        if(getLevel() > hotSpot.getLevel())
            return 1;
        else if(getLevel() < hotSpot.getLevel())
            return -1;

        return 0;
    }

}
