package scs.exe201.secondchanceshopbe.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import scs.exe201.secondchanceshopbe.models.dtos.requests.RatingCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.RatingUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.RatingResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.RatingService;

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
    @GetMapping("/product{id}")
    public ResponseEntity<ResponseObject> getRatingByProductId(@RequestParam long id) {
        List<RatingResponse> responses = ratingService.getAllByProductId(id);
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
                        .data(ratingResponse)
                        .build()
        );
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseObject> updateRating(@RequestBody RatingUpdateDTO updateDTO){
        RatingResponse ratingResponse = ratingService.updateRating(updateDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("CREATE_SUCCESS")
                        .message("get all success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(ratingResponse)
                        .build()
        );
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteRating(@RequestParam long id){
        RatingResponse ratingResponse = ratingService.deleterating(id);
        return ResponseEntity.ok().body(
            ResponseObject.builder()
            .code("DELETE_SUCCESS")
            .message("delete rating success")
            .status(HttpStatus.OK)
            .isSuccess(true)
            .data(ratingResponse)
            .build()
        );
    }
}
