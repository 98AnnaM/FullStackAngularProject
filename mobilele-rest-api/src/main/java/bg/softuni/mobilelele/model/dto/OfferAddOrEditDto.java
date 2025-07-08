package bg.softuni.mobilelele.model.dto;

import bg.softuni.mobilelele.model.enums.EngineEnum;
import bg.softuni.mobilelele.model.enums.TransmissionEnum;
import bg.softuni.mobilelele.model.validation.EnumValue;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class OfferAddOrEditDto {

    private Long id;

    @NotNull
    @Min(1)
    private Long modelId;

    @Positive
    @NotNull
    private BigDecimal price;

    @Min(1900)
    @NotNull
    private int year;

    @NotEmpty
    private String description;

    @NotEmpty
    @EnumValue(enumClass = EngineEnum.class, message = "Invalid engine type")
    private String engine;

    @NotEmpty
    @EnumValue(enumClass = TransmissionEnum.class, message = "Invalid transmission type")
    private String transmission;

    @NotEmpty
    private String imageUrl;

    @Positive
    @NotNull
    private int mileage;


    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty String getEngine() {
        return engine;
    }

    public void setEngine(@NotEmpty String engine) {
        this.engine = engine;
    }

    public @NotEmpty String getTransmission() {
        return transmission;
    }

    public void setTransmission(@NotEmpty String transmission) {
        this.transmission = transmission;
    }
}
