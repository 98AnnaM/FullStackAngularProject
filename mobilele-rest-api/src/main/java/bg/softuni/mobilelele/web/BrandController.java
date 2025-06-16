package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.BrandViewDto;
import bg.softuni.mobilelele.service.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brands/all")
    public ResponseEntity<List<BrandViewDto>> allBrands() {
        return ResponseEntity.ok(brandService.getAllBrandViewDto());
    }
}
