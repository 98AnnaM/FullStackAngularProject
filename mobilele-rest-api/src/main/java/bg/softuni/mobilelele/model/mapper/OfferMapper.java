package bg.softuni.mobilelele.model.mapper;

import bg.softuni.mobilelele.model.dto.OfferAddOrEditDto;
import bg.softuni.mobilelele.model.dto.OfferDetailDTO;
import bg.softuni.mobilelele.model.entity.OfferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    OfferEntity addOrEditOfferDtoToOfferEntity(OfferAddOrEditDto offerDto);

    @Mapping(source = "model.name", target = "modelName")
    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "model.brand.name", target = "brandName")
    @Mapping(source = "seller.firstName", target = "sellerFirstName")
    @Mapping(source = "seller.lastName", target = "sellerLastName")
    @Mapping(source = "seller.email", target = "sellerEmail")
    OfferDetailDTO offerEntityToOfferDetailDto(OfferEntity offer);

    @Mapping(source = "model.id", target = "modelId")
    OfferAddOrEditDto offerEntityToAddOfferDto(OfferEntity offerEntity);
}

