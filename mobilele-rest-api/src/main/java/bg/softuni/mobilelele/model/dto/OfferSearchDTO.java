package bg.softuni.mobilelele.model.dto;

import bg.softuni.mobilelele.model.enums.EngineEnum;
import bg.softuni.mobilelele.model.enums.TransmissionEnum;

public class OfferSearchDTO {

    private String modelId;
    private Integer minPrice;
    private Integer maxPrice;
    private EngineEnum engine;
    private TransmissionEnum transmission;

    public String getModelId() {
        return modelId;
    }

    public OfferSearchDTO setModelId(String modelId) {
        this.modelId = modelId;
        return this;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public OfferSearchDTO setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public OfferSearchDTO setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public EngineEnum getEngine() {
        return engine;
    }

    public void setEngine(EngineEnum engine) {
        this.engine = engine;
    }

    public TransmissionEnum getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionEnum transmission) {
        this.transmission = transmission;
    }

    public boolean isEmpty() {
        return (modelId == null || modelId.isEmpty()) &&
                minPrice == null &&
                maxPrice == null &&
                engine == null &&
                transmission == null;
    }

    @Override
    public String toString() {
        return "SearchOfferDTO{" +
                "model='" + modelId + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}
