package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.dto.CommentViewDto;
import bg.softuni.mobilelele.model.dto.OfferAddOrEditDto;
import bg.softuni.mobilelele.model.dto.OfferDetailDTO;
import bg.softuni.mobilelele.model.dto.OfferSearchDTO;
import bg.softuni.mobilelele.model.entity.ModelEntity;
import bg.softuni.mobilelele.model.entity.OfferEntity;
import bg.softuni.mobilelele.model.entity.UserEntity;
import bg.softuni.mobilelele.model.enums.EngineEnum;
import bg.softuni.mobilelele.model.enums.TransmissionEnum;
import bg.softuni.mobilelele.model.mapper.CommentMapper;
import bg.softuni.mobilelele.model.mapper.OfferMapper;
import bg.softuni.mobilelele.repository.ModelRepository;
import bg.softuni.mobilelele.repository.OfferRepository;
import bg.softuni.mobilelele.repository.OfferSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private final UserService userService;
    private final ModelRepository modelRepository;

    public OfferService(OfferRepository offerRepository, OfferMapper offerMapper, CommentMapper commentMapper, CommentService commentService, UserService userService, ModelRepository modelRepository) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.commentMapper = commentMapper;
        this.commentService = commentService;
        this.userService = userService;
        this.modelRepository = modelRepository;
    }

    public OfferDetailDTO addOffer(OfferAddOrEditDto addOfferDto, UserDetails userDetails) {
        OfferEntity newOffer = offerMapper.addOrEditOfferDtoToOfferEntity(addOfferDto);

        UserEntity seller = this.userService.getUserByEmail(userDetails.getUsername());

        ModelEntity model = this.modelRepository
                .findById(addOfferDto.getModelId())
                .orElseThrow();

        newOffer.setModel(model);
        newOffer.setSeller(seller);
        newOffer.setCreated(LocalDateTime.now());

        return offerMapper.offerEntityToOfferDetailDto(this.offerRepository.save(newOffer));
    }

    public Page<OfferDetailDTO> findAllOfferDetailDto(Pageable pageable) {
        return this.offerRepository.findAll(pageable)
            .map(offerMapper::offerEntityToOfferDetailDto);
    }

    public Page<OfferDetailDTO> searchOffer(OfferSearchDTO searchOfferDTO, Pageable pageable) {
        return this.offerRepository
            .findAll(new OfferSpecification(searchOfferDTO), pageable)
            .map(offerMapper::offerEntityToOfferDetailDto);
    }

    public Optional<OfferDetailDTO> findOfferByOfferId(Long offerID, String currentUser) {
        return offerRepository.
                findById(offerID).
                map(offer -> {
                    OfferDetailDTO offerDetailDTO = offerMapper.offerEntityToOfferDetailDto(offer);
                    offerDetailDTO.setCanDelete(isOfferOwner(currentUser, offerID) || this.userService.isUserAdmin(currentUser));
                    offerDetailDTO.setCanUpdate(isOfferOwner(currentUser, offerID));

                    return offerDetailDTO;
                });
    }

    public Optional<OfferAddOrEditDto> getOfferEditDetails(Long offerId) {
        return offerRepository.
                findById(offerId).
                map(offerMapper::offerEntityToAddOfferDto);
    }

    @Transactional
    public void deleteOfferById(Long offerId) {
        this.commentService.deleteAllCommentsByOfferId(offerId);
        this.offerRepository.deleteById(offerId);
    }

    public boolean isOfferOwner(String userName, Long offerId) {
        return offerRepository.
                findById(offerId).
                filter(o -> o.getSeller().getEmail().equals(userName)).
                isPresent();

        }

    public OfferDetailDTO updateOfferById(OfferAddOrEditDto addOrEditOfferDto, Long id) {
        OfferEntity updateOffer = this.offerRepository.findById(id).orElseThrow();

        updateOffer.setModel(modelRepository.findById(addOrEditOfferDto.getModelId()).orElseThrow());
        updateOffer.setPrice(addOrEditOfferDto.getPrice());
        updateOffer.setYear(addOrEditOfferDto.getYear());
        updateOffer.setDescription(addOrEditOfferDto.getDescription());
        updateOffer.setEngine(EngineEnum.valueOf(addOrEditOfferDto.getEngine()));
        updateOffer.setTransmission(TransmissionEnum.valueOf(addOrEditOfferDto.getTransmission()));
        updateOffer.setMileage(addOrEditOfferDto.getMileage());
        updateOffer.setImageUrl(addOrEditOfferDto.getImageUrl());

        return offerMapper.offerEntityToOfferDetailDto(offerRepository.save(updateOffer));
    }
}
