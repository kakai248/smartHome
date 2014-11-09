package scmu.smarthome.com.smarthome.entities;

public class Division {
    public String division;
    public String name;
    public Light light;
    public AirConditioner airconditioner;
    public Tv tv;
    public Windows windows;
    public SoundSystem soundsystem;

    private static class DeviceEntity {
        public boolean status;
        public int volume;
    }

    public class Light extends DeviceEntity {}

    public class AirConditioner extends DeviceEntity {}

    public class Tv extends DeviceEntity {}

    public class Windows extends DeviceEntity {}

    public class SoundSystem extends DeviceEntity {}
}
