package scs.exe201.secondchanceshopbe.services.Iplm;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.RatingCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UpdateRatingDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.RatingResponse;
import scs.exe201.secondchanceshopbe.models.entities.RatingEntity;
import scs.exe201.secondchanceshopbe.repositories.RatingRepository;
import scs.exe201.secondchanceshopbe.services.RatingService;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class RatingServiceIplm implements RatingService {
    @Override
    public RatingResponse craterating(RatingCreateDTO createDTO) {
        return null;
    }

    @Override
    public RatingResponse updateRating(UpdateRatingDTO updateDTO) {
        return null;
    }

    private final RatingRepository repository;
    @Override
    public List<RatingResponse> getAll() {
        List<RatingEntity>entities = repository.findAll();
        var responses = new ArrayList<RatingResponse>();
        return responses;
    }
}
