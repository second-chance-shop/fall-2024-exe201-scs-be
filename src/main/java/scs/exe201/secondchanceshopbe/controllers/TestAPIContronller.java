package scs.exe201.secondchanceshopbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import scs.exe201.secondchanceshopbe.models.dtos.response.FileObjectResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.FileDatabaseService;
import scs.exe201.secondchanceshopbe.services.FileService;
import scs.exe201.secondchanceshopbe.services.iplm.ShippingFeeService;
import scs.exe201.secondchanceshopbe.services.iplm.TestAPIService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class TestAPIContronller {
    @Autowired
    private ShippingFeeService shippingFeeService;

    private final FileService fileService;

    private final FileDatabaseService fileDatabaseService;

    // ...

    @GetMapping
    public double getShippingFee(@RequestParam String origin, @RequestParam String destination, @RequestParam double weight) {
        return shippingFeeService.calculateFee(origin, destination, weight);
    }

    private final TestAPIService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<Map> uploadImage(@RequestParam("image")MultipartFile file){
        Map data = this.cloudinaryService.upload(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @Operation(summary = "Upload a file to S3",
            description = "Uploads a file and returns the URL of the uploaded file.")
    @PostMapping(value = "uploads3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> uploadImageS3(@RequestPart("file") MultipartFile file){
        FileObjectResponse fileObjectResponse = fileDatabaseService.uploadFile(file);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("ULOAD_SUCCESS")
                        .message("upload file success")
                        .status(HttpStatus.OK)
                        .data(fileObjectResponse)
                        .build()
        );
    }

    @Operation(summary = "Upload multi file to S3",
            description = "Uploads a file and returns the URL of the uploaded file.")
    @PostMapping(value = "uploadmultis3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> uploadMultiImageS3(@RequestPart("file") List<MultipartFile> files){

            List<FileObjectResponse>  fileObjectResponse = fileDatabaseService.upNhieufile(files);
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .code("ULOAD_SUCCESS")
                            .message("upload file success")
                            .status(HttpStatus.OK)
                            .data(fileObjectResponse)
                            .build()
            );

    }

}
