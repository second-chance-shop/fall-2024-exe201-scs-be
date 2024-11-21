package scs.exe201.secondchanceshopbe.controllers;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.models.dtos.response.ShopResponse;
import scs.exe201.secondchanceshopbe.services.ShopService;

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
                        .code("CREATE_SUCCESS")
                        .message("Shop updated successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseObject> getShopsByUserId(@PathVariable Long id) {
        List<ShopResponse>  shopResponse = shopService.getByUserId(id);
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
    @GetMapping("/user")
    public ResponseEntity<ResponseObject> getShopsByUser() {
        List<ShopResponse>  shopResponse = shopService.getByUser();
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
