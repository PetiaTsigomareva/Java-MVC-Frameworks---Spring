package softuni.residentevil.domain.models.view;

import java.time.LocalDate;

import softuni.residentevil.domain.enums.Magnitude;

public class VirusesViewModel extends BaseViewModel {
	private String name;
	private Magnitude magnitude;
	private LocalDate releasedOn;

	public VirusesViewModel() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Magnitude getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(Magnitude magnitude) {
		this.magnitude = magnitude;
	}

	public LocalDate getReleasedOn() {
		return releasedOn;
	}

	public void setReleasedOn(LocalDate releasedOn) {
		this.releasedOn = releasedOn;
	}
}
