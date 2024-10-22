package scs.exe201.secondchanceshopbe.services.iplm;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Literal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.dtos.response.FollowResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ShopResponse;
import scs.exe201.secondchanceshopbe.models.entities.FollowShopEntity;
import scs.exe201.secondchanceshopbe.models.entities.ShopEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.FollowShopRepository;
import scs.exe201.secondchanceshopbe.repositories.ShopRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.FollowService;
import scs.exe201.secondchanceshopbe.utils.EntityToDTO;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceIplm  implements FollowService {

    private final FollowShopRepository followShopRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    @Override
    public List<FollowResponse> getAllByShopId(long id) {
        List<FollowShopEntity> followShopEntities =followShopRepository.findAllByShopIdAndStatus(id,StatusEnum.FOLLOW);

        return followShopEntities.stream().map(EntityToDTO::followShopEntityToDTO).toList();
    }

    @Override
    public FollowResponse actionFollow(long id) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));

        ShopEntity shop = shopRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shop not found"));

        // Find existing follow relationship or create a new one
        FollowShopEntity followShop = followShopRepository.findByUserIdAndShopId(user.getUserId(), id);

        if (followShop == null) {
            // Create a new follow entity if it doesn't exist
            followShop = new FollowShopEntity();
            followShop.setDateFollow(LocalDate.now());
            followShop.setShopFollow(shop);
            followShop.setUserFollow(user);
            followShop.setStatus(StatusEnum.FOLLOW); // Set initial status to FOLLOW
        } else {

            followShop.setStatus(followShop.getStatus() == StatusEnum.FOLLOW ? StatusEnum.UNFOLLOW : StatusEnum.FOLLOW);
        }
        followShopRepository.save(followShop);

        // Convert entity to DTO and return response
        return EntityToDTO.followShopEntityToDTO(followShop);
    }

}
