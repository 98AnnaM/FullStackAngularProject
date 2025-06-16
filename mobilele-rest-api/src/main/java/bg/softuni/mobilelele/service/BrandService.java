package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.dto.BrandDto;
import bg.softuni.mobilelele.model.dto.BrandViewDto;
import bg.softuni.mobilelele.model.mapper.BrandMapper;
import bg.softuni.mobilelele.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public List<BrandDto> getAllBrandDto() {
        return this.brandRepository
                .findAll()
                .stream()
                .map(brandMapper::brandEntityToBrandDto)
                .collect(Collectors.toList());

    }

    public List<BrandViewDto> getAllBrandViewDto() {
        return this.brandRepository
                .findAll()
                .stream()
                .map(brandMapper::brandEntityToBrandViewDto)
                .collect(Collectors.toList());
    }
}
