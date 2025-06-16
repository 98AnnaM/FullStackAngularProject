package bg.softuni.mobilelele.model.dto;

public class OfferSearchDTO {

    private String model;
    private Integer minPrice;
    private Integer maxPrice;

    public String getModel() {
        return model;
    }

    public OfferSearchDTO setModel(String model) {
        this.model = model;
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

    public boolean isEmpty() {
        return (model == null || model.isEmpty()) &&
                minPrice == null &&
                maxPrice == null;
    }

    @Override
    public String toString() {
        return "SearchOfferDTO{" +
                "model='" + model + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}
