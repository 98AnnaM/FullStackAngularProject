package bg.softuni.mobilelele.model.dto;

import java.util.List;

public class BrandViewDto {

    private String name;
    private List<ModelViewDto> models;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModelViewDto> getModels() {
        return models;
    }

    public void setModels(List<ModelViewDto> models) {
        this.models = models;
    }
}
