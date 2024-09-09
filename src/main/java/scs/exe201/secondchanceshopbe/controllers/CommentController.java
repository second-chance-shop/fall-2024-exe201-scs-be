package scs.exe201.secondchanceshopbe.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scs.exe201.secondchanceshopbe.models.dtos.respones.ResponseObject;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.CommentService;

@RequestMapping("/api/v1/comment")
@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/get-all")
    public ResponseEntity<ResponseObject> getAllComment() {
        return null;
    }

    @GetMapping("/get-all-by-product/{id}")
    public ResponseEntity<ResponseObject> getAllCommentByProductId(@PathVariable int id) {
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createComment(){
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteComment(@PathVariable int id) {
        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateComment(@PathVariable int id, @RequestBody UserRepository comment) {
        return null;
    }

}
