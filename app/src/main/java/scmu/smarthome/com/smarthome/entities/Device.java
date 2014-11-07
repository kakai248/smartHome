package scmu.smarthome.com.smarthome.entities;

public class Device {

    public Light light;
    public AirConditioner airconditioner;
    public Tv tv;
    public Windows windows;
    public SoundSystem soundsystem;

    private abstract static class DeviceEntity {
        public boolean status;
    }

    public class Light extends DeviceEntity {}

    public class AirConditioner extends DeviceEntity {}

    public class Tv extends DeviceEntity {
        public int volume;
    }

    public class Windows extends DeviceEntity {}

    public class SoundSystem extends DeviceEntity {
        public int volume;
    }
}
