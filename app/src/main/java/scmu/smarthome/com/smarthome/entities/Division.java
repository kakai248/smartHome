package scmu.smarthome.com.smarthome.entities;

import scmu.smarthome.com.smarthome.entities.Device.Light;
import scmu.smarthome.com.smarthome.entities.Device.AirConditioner;
import scmu.smarthome.com.smarthome.entities.Device.Windows;
import scmu.smarthome.com.smarthome.entities.Device.Tv;
import scmu.smarthome.com.smarthome.entities.Device.SoundSystem;

public class Division {
    public LivingRoom livingRoom;
    public Kitchen kitchen;
    public Room1 room1;
    public Room2 room2;
    public BathRoom bathRoom;
    public Hall hall;

    private abstract static class DivisionEntity {
        public String name;
        public Tv tv;
        public Light light;
        public AirConditioner airconditioner;
        public Windows windows;
        public SoundSystem soundsystem;
    }

    public class LivingRoom extends DivisionEntity {}

    public class Kitchen extends DivisionEntity {}

    public class Room1 extends DivisionEntity {}

    public class Room2 extends DivisionEntity {}

    public class BathRoom extends DivisionEntity {}

    public class Hall extends DivisionEntity {}
}
