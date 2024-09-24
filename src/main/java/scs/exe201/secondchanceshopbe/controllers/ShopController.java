package scs.exe201.secondchanceshopbe.controllers;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopRequestDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ShopResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.ShopService;

@RestController
@RequestMapping("/api/v1/shop")
@AllArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/getAllShops")
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
        ShopResponse shop = shopService.getShopById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
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

    @PostMapping("/addShop")
    public ResponseEntity<ResponseObject> addShop(@RequestBody ShopRequestDTO shopRequest) {
        ShopResponse response = shopService.createShop(shopRequest);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("ADD_SUCCESS")
                        .message("Shop added successfully")
                        .status(HttpStatus.CREATED)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/updateShop/{id}")
    public ResponseEntity<ResponseObject> updateShop(@PathVariable Long id, @RequestBody ShopRequestDTO shopRequest) {
        ShopResponse response = shopService.updateShop(id, shopRequest);
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

    @DeleteMapping("/deleteShop/{id}")
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
}
