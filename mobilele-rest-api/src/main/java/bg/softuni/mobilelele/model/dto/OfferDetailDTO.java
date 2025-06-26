package bg.softuni.mobilelele.model.dto;

import bg.softuni.mobilelele.model.enums.EngineEnum;
import bg.softuni.mobilelele.model.enums.TransmissionEnum;

import java.math.BigDecimal;
import java.util.List;

public class OfferDetailDTO {

    private Long id;
    private String imageUrl;
    private Integer year;
    private String brandName;
    private String modelName;
    private Integer mileage;
    private BigDecimal price;
    private EngineEnum engine;
    private TransmissionEnum transmission;
    private String sellerFirstName;
    private String sellerLastName;
    private String sellerEmail;
    private boolean canDelete;
    private boolean canUpdate;
    private String description;
    private List<CommentViewDto> comments;

    public OfferDetailDTO() {
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public String getSellerFirstName() {
        return sellerFirstName;
    }

    public OfferDetailDTO setSellerFirstName(String sellerFirstName) {
        this.sellerFirstName = sellerFirstName;
        return this;
    }

    public String getSellerFullName() {
        return sellerFirstName + " " + sellerLastName;
    }

    public String getSellerLastName() {
        return sellerLastName;
    }

    public OfferDetailDTO setSellerLastName(String sellerLastName) {
        this.sellerLastName = sellerLastName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getOfferHighlight() {
        return "Offer: " + this.id + " Year: " + this.year + " Brand: " + this.brandName + " Model: " + this.modelName;
    }

    public Long getId() {
        return id;
    }

    public OfferDetailDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CommentViewDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentViewDto> comments) {
        this.comments = comments;
    }

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }
}
