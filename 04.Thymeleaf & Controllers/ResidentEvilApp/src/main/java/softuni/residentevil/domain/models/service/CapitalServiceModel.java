package softuni.residentevil.domain.models.service;

import org.springframework.scheduling.support.SimpleTriggerContext;

public class CapitalServiceModel {
    private String id;
    private String name;
    private double latitude;
    private double longitude;

    public CapitalServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
