package bg.softuni.mobilelele.repository;

import bg.softuni.mobilelele.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    void deleteAllByOffer_id(Long offer_id);

}
