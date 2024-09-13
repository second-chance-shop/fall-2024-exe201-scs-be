package scs.exe201.secondchanceshopbe.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scs.exe201.secondchanceshopbe.models.dtos.requests.RatingCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UpdateRatingDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.RatingResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingContronller {
    private final RatingService ratingService;

    public RatingContronller(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    @GetMapping
    public ResponseEntity<ResponseObject> getRating() {
        List<RatingResponse> responses = ratingService.getAll();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("CREATE_SUCCESS")
                        .message("get all success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(responses)
                        .build()
        );
    }
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createRating(@RequestBody RatingCreateDTO createDTO){
        RatingResponse ratingResponse = ratingService.craterating(createDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("CREATE_SUCCESS")
                        .message("get all success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(null)
                        .build()
        );
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseObject> updateRating(@RequestBody UpdateRatingDTO updateDTO){
        RatingResponse ratingResponse = ratingService.updateRating(updateDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("CREATE_SUCCESS")
                        .message("get all success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(null)
                        .build()
        );
    }
}
