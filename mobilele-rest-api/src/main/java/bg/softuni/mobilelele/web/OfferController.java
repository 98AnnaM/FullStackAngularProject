package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.OfferAddOrEditDto;
import bg.softuni.mobilelele.model.dto.OfferDetailDTO;
import bg.softuni.mobilelele.model.dto.OfferSearchDTO;
import bg.softuni.mobilelele.model.user.MobileleUserDetails;
import bg.softuni.mobilelele.service.BrandService;
import bg.softuni.mobilelele.service.OfferService;
import bg.softuni.mobilelele.service.UserService;
import bg.softuni.mobilelele.web.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

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
    public ResponseEntity<List<OfferDetailDTO>> allOffers() {
        return ResponseEntity.ok(this.offerService.findAllOfferDetailDto());
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

    @GetMapping("/offers/search")
    public ResponseEntity<?> searchQuery(@Valid OfferSearchDTO searchOfferDTO) {

        if (searchOfferDTO.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<OfferDetailDTO> offers = offerService.searchOffer(searchOfferDTO);
        return ResponseEntity.ok(offers);
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

    @PreAuthorize("isAuthenticated() && @offerService.isOfferOwner(#principal.name, #id)")
    @PutMapping("/offers/edit/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody OfferAddOrEditDto offerModel,
            @AuthenticationPrincipal UserDetails userDetails) {
        OfferDetailDTO updatedOffer = offerService.updateOfferById(offerModel, id, userDetails);
        return ResponseEntity.ok(updatedOffer);
    }
}
