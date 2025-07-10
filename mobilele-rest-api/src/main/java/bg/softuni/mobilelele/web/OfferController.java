package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.OfferAddOrEditDto;
import bg.softuni.mobilelele.model.dto.OfferDetailDTO;
import bg.softuni.mobilelele.model.dto.OfferSearchDTO;
import bg.softuni.mobilelele.model.user.MobileleUserDetails;
import bg.softuni.mobilelele.service.BrandService;
import bg.softuni.mobilelele.service.OfferService;
import bg.softuni.mobilelele.service.UserService;
import bg.softuni.mobilelele.web.exception.ObjectNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class OfferController {

    private final OfferService offerService;
    private final BrandService brandService;
    private final UserService userService;

    public OfferController(OfferService offerService, BrandService brandService, UserService userService) {
        this.offerService = offerService;
        this.brandService = brandService;
        this.userService = userService;
    }

    @GetMapping("/offers/all")
    public ResponseEntity<Page<OfferDetailDTO>> allOffers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "created"));
        Page<OfferDetailDTO> pagedOffers = offerService.findAllOfferDetailDto(pageable);
        return ResponseEntity.ok(pagedOffers);
    }

    @GetMapping("/offers/search")
    public ResponseEntity<Page<OfferDetailDTO>> searchQuery(
        @Valid OfferSearchDTO searchOfferDTO,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());
        Page<OfferDetailDTO> offers = offerService.searchOffer(searchOfferDTO, pageable);

        return ResponseEntity.ok(offers);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/offers/add")
    public ResponseEntity<?> addOffer(@Valid @RequestBody OfferAddOrEditDto addOfferModel,
        @AuthenticationPrincipal UserDetails userDetails) {
        OfferDetailDTO createdOffer = this.offerService.addOffer(addOfferModel, userDetails);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(createdOffer);
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<OfferDetailDTO> getOfferDetail(@PathVariable("id") Long id,
                                                         @AuthenticationPrincipal MobileleUserDetails currentUser) {

        OfferDetailDTO offerDto =
                offerService.findOfferByOfferId(id, currentUser != null ? currentUser.getUsername() : "").
                        orElseThrow(() -> new ObjectNotFoundException("Offer with ID " +
                                id + " not found!"));

        return ResponseEntity.ok(offerDto);
    }

    @PreAuthorize("isAuthenticated() and (@offerService.isOfferOwner(#principal.name, #offerId) or @userService.isUserAdmin(#principal.name))")
    @DeleteMapping("/offers/{id}")
    public ResponseEntity<Void> deleteOffer(
            Principal principal,
            @PathVariable("id") Long offerId) {
        offerService.deleteOfferById(offerId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated() and @offerService.isOfferOwner(#principal.name, #id)")
    @PutMapping("/offers/edit/{id}")
    public ResponseEntity<?> update(
            Principal principal,
            @PathVariable("id") Long id,
            @Valid @RequestBody OfferAddOrEditDto offerModel) {
        OfferDetailDTO updatedOffer = offerService.updateOfferById(offerModel, id);
        return ResponseEntity.ok(updatedOffer);
    }
}
