package softuni.residentevil.domain.models.binding;

import org.springframework.format.annotation.DateTimeFormat;
import softuni.residentevil.cofigurations.Constants;
import softuni.residentevil.domain.enums.Creator;
import softuni.residentevil.validation.CapitalListValidation;
import softuni.residentevil.validation.CreatorEnumValidation;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class VirusEditBindingModel {

    private String name;
    private String description;
    private String sideEffects;
    private String creator;
    private boolean isDeadly;
    private boolean isCurable;
    private String mutation;
    private int turnoverRate;
    private int hoursUntilTurn;
    private String magnitude;
    private LocalDate releasedOn;
    private List<String> capitals;

    public VirusEditBindingModel() {

    }

    @NotNull(message = Constants.EMPTY_MESSAGE)
    @Size(min = 3, max = 10, message = Constants.INVALID_NAME_MESSAGE)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = Constants.EMPTY_MESSAGE)
    @Size(min = 50, max = 100, message = Constants.INVALID_DESCRIPTION_MESSAGE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = Constants.EMPTY_MESSAGE)
    @Size(max = 50, message = Constants.INVALID_SIDE_EFFECT_MESSAGE)
    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @CreatorEnumValidation(enumClass = Creator.class, ignoreCase = true)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isDeadly() {
        return isDeadly;
    }

    public void setDeadly(boolean deadly) {
        isDeadly = deadly;
    }

    public boolean isCurable() {
        return isCurable;
    }

    public void setCurable(boolean curable) {
        isCurable = curable;
    }

    @NotNull(message = Constants.EMPTY_MESSAGE)
    public String getMutation() {
        return mutation;
    }

    public void setMutation(String mutation) {
        this.mutation = mutation;
    }

    @NotNull(message = Constants.EMPTY_MESSAGE)
    @DecimalMin(value = "0", message = Constants.INVALID_MUTATION_MESSAGE)
    @DecimalMax(value = "100", message = Constants.INVALID_MUTATION_MESSAGE)
    public int getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(int turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    @NotNull(message = Constants.EMPTY_MESSAGE)
    @DecimalMin(value = "1", message = Constants.INVALID_TURN_OVER_RATE_MESSAGE)
    @DecimalMax(value = "12", message = Constants.INVALID_TURN_OVER_RATE_MESSAGE)
    public int getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    public void setHoursUntilTurn(int hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    @NotNull(message = Constants.EMPTY_MESSAGE)
    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(LocalDate releasedOn) {
        this.releasedOn = releasedOn;
    }

    @CapitalListValidation
    public List<String> getCapitals() {
        return capitals;
    }

    public void setCapitals(List<String> capitals) {
        this.capitals = capitals;
    }
}
