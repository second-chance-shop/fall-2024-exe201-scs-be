package scs.exe201.secondchanceshopbe.services.iplm;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.dtos.requests.RatingCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.RatingUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.RatingResponse;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.models.entities.RatingEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.ConflictException;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.ProductRepository;
import scs.exe201.secondchanceshopbe.repositories.RatingRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.RatingService;
import scs.exe201.secondchanceshopbe.utils.EntityToDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RatingServiceIplm implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @Override
    public RatingResponse craterating(RatingCreateDTO createDTO) {
        RatingEntity ratingEntity = new RatingEntity();
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        ratingEntity.setUserRating(userEntity);

        Optional<RatingEntity> existingRating =
                ratingRepository.findByProductIdAndUserId(createDTO.getProductId(),userEntity.getUserId());

        if (existingRating.isPresent()) {
            throw  new ConflictException("User đã đánh giá sản phẩm này rồi"); // Hoặc custom exception phù hợp
        }
        ratingEntity.setDateCreate(LocalDate.now());
        ratingEntity.setStatus(StatusEnum.ACTIVE);
        ratingEntity.setStar(createDTO.getStart());



        ProductEntity productEntity = productRepository.findById(createDTO.getProductId()).orElseThrow(
                () -> new NotFoundException("Product not found")
        );

        ratingEntity.setProduct(productEntity);
        ratingRepository.save(ratingEntity);
        RatingResponse ratingResponse = EntityToDTO.ratingoEntityDTOT(ratingEntity);
        return ratingResponse;
    }

    @Override
    public RatingResponse updateRating(RatingUpdateDTO updateDTO) {
        RatingEntity ratingEntity = ratingRepository.findById(updateDTO.getId()).orElseThrow(
                () -> new NotFoundException("Rating not found")
        );
        ratingEntity.setStar(updateDTO.getStart());
        ratingRepository.save(ratingEntity);
        RatingResponse ratingResponse = EntityToDTO.ratingoEntityDTOT(ratingEntity);
        return ratingResponse;
    }

    @Override
    public List<RatingResponse> getAllByProductId(long id) {
        List<RatingEntity> ratingEntityList = ratingRepository.findByProductId(id);
        var ratingResponses = ratingEntityList.stream().map(EntityToDTO::ratingoEntityDTOT).toList();
        return ratingResponses;
    }

    private final RatingRepository repository;
    @Override
    public List<RatingResponse> getAll() {
        List<RatingEntity>entities = repository.findAll();
        var ratingResponses = entities.stream().map(EntityToDTO::ratingoEntityDTOT).toList();
        return ratingResponses;
    }

    @Override
    public RatingResponse deleterating(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleterating'");
    }
}
