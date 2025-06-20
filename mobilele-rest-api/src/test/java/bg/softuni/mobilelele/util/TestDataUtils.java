package bg.softuni.mobilelele.util;

import bg.softuni.mobilelele.model.entity.*;
import bg.softuni.mobilelele.model.enums.CategoryEnum;
import bg.softuni.mobilelele.model.enums.EngineEnum;
import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import bg.softuni.mobilelele.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static bg.softuni.mobilelele.model.enums.TransmissionEnum.MANUAL;

@Component
public class TestDataUtils {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final OfferRepository offerRepository;
    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;
    private final CommentRepository commentRepository;

    public TestDataUtils(UserRepository userRepository,
                         UserRoleRepository userRoleRepository,
                         OfferRepository offerRepository,
                         ModelRepository modelRepository,
                         BrandRepository brandRepository,
                         CommentRepository commentRepository,
                         PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.offerRepository = offerRepository;
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
        this.commentRepository = commentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);
            UserRoleEntity userRole = new UserRoleEntity().setUserRole(UserRoleEnum.USER);

            userRoleRepository.save(adminRole);
            userRoleRepository.save(userRole);
        }
    }

    public UserEntity createTestAdmin(String email) {

        initRoles();

        var admin = new UserEntity().
                setEmail(email).
                setFirstName("Admin").
                setLastName("Adminov").
                setActive(true).
                setPassword(passwordEncoder.encode("correctPassword")).
                setUserRoles(userRoleRepository.findAll());

        return userRepository.save(admin);
    }

    public UserEntity createTestUser(String email) {

        initRoles();

        var user = new UserEntity().
                setEmail(email).
                setFirstName("User").
                setLastName("Userov").
                setActive(true).
                setPassword(passwordEncoder.encode("correctPassword")).
                setUserRoles(userRoleRepository.
                        findAll().stream().
                        filter(r -> r.getUserRole() != UserRoleEnum.ADMIN).
                        toList());

        return userRepository.save(user);
    }

    public OfferEntity createTestOffer(UserEntity seller,
                                       ModelEntity model) {
        var offerEntity = new OfferEntity().
                setEngine(EngineEnum.GASOLINE).
                setMileage(100000).
                setPrice(BigDecimal.TEN).
                setDescription("Test description").
                setTransmission(MANUAL).
                setYear(2000).
                setModel(model).
                setSeller(seller);

        return offerRepository.save(offerEntity);
    }

    public BrandEntity createTestBrand() {
        var brandEntity = new BrandEntity().
                setName("Ford");

        return brandRepository.save(brandEntity);
    }

    public ModelEntity createTestModel(BrandEntity brandEntity) {
        ModelEntity model = new ModelEntity().
                setName("Fiesta").
                setBrand(brandEntity).
                setCategory(CategoryEnum.CAR).
                setImageUrl("http://image.com/image.png").
                setStartYear(1978);

        return modelRepository.save(model);
    }

    public CommentEntity createTestComment(UserEntity testUser, OfferEntity testOffer, String message) {
        CommentEntity newComment = new CommentEntity();
        newComment.setTextContent(message);
        newComment.setCreated(LocalDateTime.now());
        newComment.setAuthor(testUser);
        newComment.setOffer(testOffer);

        return commentRepository.save(newComment);
    }

    public void cleanUpDatabase() {
        commentRepository.deleteAll();
        offerRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();
    }

    public OfferRepository getOfferRepository() {
        return offerRepository;
    }

    public CommentRepository getCommentRepository() {
        return commentRepository;
    }
}
