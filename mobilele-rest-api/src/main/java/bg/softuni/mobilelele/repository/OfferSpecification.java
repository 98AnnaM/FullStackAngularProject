package bg.softuni.mobilelele.repository;


import bg.softuni.mobilelele.model.dto.OfferSearchDTO;
import bg.softuni.mobilelele.model.entity.OfferEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class OfferSpecification implements Specification<OfferEntity> {

    private final OfferSearchDTO searchOfferDTO;

    public OfferSpecification(OfferSearchDTO searchOfferDTO) {
        this.searchOfferDTO = searchOfferDTO;
    }

    @Override
    public Predicate toPredicate(Root<OfferEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {

        Predicate p = cb.conjunction();

        if (searchOfferDTO.getModelId() != null && !searchOfferDTO.getModelId().isEmpty()) {
            p.getExpressions().add(
                    cb.and(cb.equal(root.join("model").get("id"), searchOfferDTO.getModelId())));
        }

        if (searchOfferDTO.getMinPrice() != null) {
            p.getExpressions().add(
                    cb.and(cb.greaterThanOrEqualTo(root.get("price"), searchOfferDTO.getMinPrice())));
        }

        if (searchOfferDTO.getMaxPrice() != null) {
            p.getExpressions().add(
                    cb.and(cb.lessThanOrEqualTo(root.get("price"), searchOfferDTO.getMaxPrice())));
        }

        if (searchOfferDTO.getEngine() != null) {
            p.getExpressions().add(
                cb.equal(root.get("engine"), searchOfferDTO.getEngine()));
        }

        if (searchOfferDTO.getTransmission() != null) {
            p.getExpressions().add(
                cb.equal(root.get("transmission"), searchOfferDTO.getTransmission()));
        }

        return p;
    }
}
