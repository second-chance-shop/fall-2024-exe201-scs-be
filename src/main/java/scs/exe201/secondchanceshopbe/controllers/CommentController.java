package scs.exe201.secondchanceshopbe.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.requests.CommentCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.CommentUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.CommentResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.models.entities.CommentEntity;
import scs.exe201.secondchanceshopbe.services.CommentService;

@RequestMapping("/api/v1/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @GetMapping
    public ResponseEntity<ResponseObject> getAllComment() {
        List<CommentResponse> commentResponses = commentService.getAll();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_SUCCESS")
                        .message("get all success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(commentResponses)
                        .build()
        );
    }

    @GetMapping("/get-by-product/{id}")
    public ResponseEntity<ResponseObject> getAllCommentByProductId(@PathVariable long id) {
        List<CommentResponse> commentResponses = commentService.getByProductId(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_SUCCESS")
                        .message("get by id product success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(commentResponses)
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createComment(CommentCreateDTO commentCreateDTO) {
        CommentEntity commentResponses = commentService.addComment(commentCreateDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("CREATE_SUCCESS")
                        .message("create success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(commentResponses)
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteComment(@PathVariable long id) {
        CommentResponse commentResponse = commentService.deleteComment(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("DELETE_SUCCESS")
                        .message("delete success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(commentResponse)
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateComment(@RequestBody CommentUpdateDTO comment) {
        CommentResponse commentResponse = commentService.updateComment(comment);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("UPDATE_SUCCESS")
                        .message("update success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(commentResponse)
                        .build()
        );
    }

}
