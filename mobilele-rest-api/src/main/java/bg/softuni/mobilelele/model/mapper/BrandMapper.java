package bg.softuni.mobilelele.model.mapper;

import bg.softuni.mobilelele.model.dto.BrandDto;
import bg.softuni.mobilelele.model.dto.BrandViewDto;
import bg.softuni.mobilelele.model.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    BrandDto brandEntityToBrandDto(BrandEntity brandEntity);

    BrandViewDto brandEntityToBrandViewDto(BrandEntity brandEntity);
}
