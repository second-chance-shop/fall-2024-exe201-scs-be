package scs.exe201.secondchanceshopbe.services.Iplm;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.CommentCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.CommentUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.CommentResponse;
import scs.exe201.secondchanceshopbe.models.entities.CommentEntity;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.CommentRepository;
import scs.exe201.secondchanceshopbe.repositories.ProductRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.CommentService;
import scs.exe201.secondchanceshopbe.utils.EntityToDTO;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@Service
public class CommentServiceIplm implements CommentService {


    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @Override
    public List<CommentResponse> getAll() {
        List<CommentEntity> comments = commentRepository.findAll();
        var userResponses = comments.stream().map(EntityToDTO::commentToEntityDTO).toList();
        return userResponses;
    }
    @Override
    public CommentEntity addComment(CommentCreateDTO commentCreateDTO) {

        CommentEntity comment = new CommentEntity();
        ProductEntity product = productRepository.findById(commentCreateDTO.getProductId()).orElseThrow(
                () -> new NotFoundException("Product not found")
        );
        UserEntity user = userRepository.findById(commentCreateDTO.getUserId()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        comment.setProduct(product);
        comment.setUserComment(user);
        comment.setContent(commentCreateDTO.getContent());
        comment.setDateCreate(LocalDate.now());
        commentRepository.save(comment);
        return null;
    }
    @Override
    public List<CommentResponse> getByProductId(long id) {
        List<CommentEntity> entities = commentRepository.findByProductId(id);
        var userResponses = entities.stream().map(EntityToDTO::commentToEntityDTO).toList();
        return userResponses;
    }

    @Override
    public CommentResponse deleteComment(long id) {
        CommentEntity comment = commentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Comment not found")
        );
        comment.setStatus("DELETE");
        commentRepository.save(comment);
        CommentResponse commentResponse = EntityToDTO.commentToEntityDTO(comment);
        return commentResponse;
    }

    @Override
    public CommentResponse updateComment(CommentUpdateDTO comment) {
        CommentEntity currentComment = commentRepository.findById(comment.getId()).orElseThrow(
                () -> new NotFoundException("Comment not found")
        );
        currentComment.setContent(comment.getContent());
        commentRepository.save(currentComment);
        CommentResponse commentResponse = EntityToDTO.commentToEntityDTO(currentComment);
        return commentResponse;
    }

}
