package bg.softuni.mobilelele.repository;

import bg.softuni.mobilelele.model.entity.CommentEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findAllByOffer_IdOrderByCreatedDesc(Long offerId, Pageable pageable);

    void deleteAllByOffer_id(Long offer_id);

}
