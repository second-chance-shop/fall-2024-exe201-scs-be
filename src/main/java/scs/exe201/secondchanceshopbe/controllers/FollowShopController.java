package scs.exe201.secondchanceshopbe.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scs.exe201.secondchanceshopbe.models.dtos.response.FollowResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.FollowService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class FollowShopController {
    private final FollowService followService;

    @PostMapping("/action/{id}")
    public ResponseEntity<ResponseObject> followShop(@PathVariable long id) {
        FollowResponse followResponse = followService.actionFollow(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("SUCCESS")
                        .message("action follow successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(followResponse)
                        .build()
        );
    }
    @GetMapping(path = "shop/{id}")
    public ResponseEntity<ResponseObject> getShop(@PathVariable long id) {
        List<FollowResponse> followResponses = followService.getAllByShopId(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("SUCCESS")
                        .message("get follow successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(followResponses)
                        .build()
        );
    }
}
