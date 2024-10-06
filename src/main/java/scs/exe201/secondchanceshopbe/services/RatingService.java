package scs.exe201.secondchanceshopbe.services;


import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.RatingCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UpdateRatingDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.RatingResponse;

import java.util.List;

@Service
public interface RatingService {
    List<RatingResponse> getAll();
    RatingResponse craterating(RatingCreateDTO createDTO);
    RatingResponse updateRating(UpdateRatingDTO updateDTO);

    List<RatingResponse> getAllByProductId(long id);

    RatingResponse deleterating(long id);
}
