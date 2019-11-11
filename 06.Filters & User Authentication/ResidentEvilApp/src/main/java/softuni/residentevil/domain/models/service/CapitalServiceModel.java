package softuni.residentevil.domain.models.service;

import org.springframework.scheduling.support.SimpleTriggerContext;

public class CapitalServiceModel extends BaseServiceModel{

    private String name;
    private double latitude;
    private double longitude;

    public CapitalServiceModel() {
        super();
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
