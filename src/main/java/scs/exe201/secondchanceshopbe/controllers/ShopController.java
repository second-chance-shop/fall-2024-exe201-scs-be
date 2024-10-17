package scs.exe201.secondchanceshopbe.controllers;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopRequestDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ProductResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.models.dtos.response.ShopResponse;
import scs.exe201.secondchanceshopbe.services.ShopService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop")
@AllArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllShops(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Page<ShopResponse> shops = shopService.getAllShops(page, size);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("FETCH_SUCCESS")
                        .message("Shops retrieved successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(shops)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getShop(@PathVariable Long id) {
        ShopResponse shop = shopService.getShopById(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("FETCH_SUCCESS")
                        .message("Shop retrieved successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(shop)
                        .build()
        );
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteShop(@PathVariable Long id) {
        ShopResponse response = shopService.deleteShop(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("DELETE_SUCCESS")
                        .message("Shop deleted successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }

    @PutMapping(value = "update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> updateShopV1(
            @RequestPart("product") ShopUpdateDTO shopUpdateDTO,
            @RequestPart(value = "file", required = false) MultipartFile files) {
        ShopResponse response = shopService.updatev1(shopUpdateDTO,files);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("UPDATE_SUCCESS")
                        .message("Shop updated successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }

    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> addShop(
            @RequestPart("product") ShopCreateDTO shopCreateDTO,
            @RequestPart(value = "imageShop", required = false) MultipartFile imageShop,
            @RequestPart(value = "cccdFont") MultipartFile cccdFont,
            @RequestPart(value = "cccdBack") MultipartFile cccdBack) {
        ShopResponse response = shopService.addV1(shopCreateDTO,imageShop,cccdFont,cccdBack);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("UPDATE_SUCCESS")
                        .message("Shop updated successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }
    @GetMapping("user/{id}")
    public ResponseEntity<ResponseObject> getShopsByUser(@PathVariable Long id) {
        ShopResponse shopResponse = shopService.getByUserId(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("FETCH_SUCCESS")
                        .message("Shops retrieved successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(shopResponse)
                        .build()
        );
    }
}
