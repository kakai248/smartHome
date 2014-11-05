package scmu.smarthome.com.smarthome.entities;

import android.support.annotation.NonNull;

public class WifiHotSpot implements Comparable {

    private String ssid;
    private int level;

    public WifiHotSpot(String ssid, int level){
        this.ssid = ssid;
        this.level = level;
    }

    public String getSsid() {
        return ssid;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return getLevel() + " dBm | " + getSsid();
    }

    @Override
    public int compareTo(@NonNull Object another) {
        WifiHotSpot hotSpot = (WifiHotSpot) another;

        if(getLevel() > hotSpot.getLevel())
            return -1;
        else if(getLevel() < hotSpot.getLevel())
            return 1;

        return 0;
    }

}
