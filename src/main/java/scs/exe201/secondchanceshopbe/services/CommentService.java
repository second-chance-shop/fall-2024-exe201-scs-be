package scs.exe201.secondchanceshopbe.services;

import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.CommentCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.CommentUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.CommentResponse;
import scs.exe201.secondchanceshopbe.models.entities.CommentEntity;

import java.util.List;

@Service
public interface CommentService {
    List<CommentResponse> getAll();

    CommentEntity addComment(CommentCreateDTO createDTO);

    List<CommentResponse> getByProductId(long id);

    CommentResponse deleteComment(long id);

    CommentResponse updateComment(CommentUpdateDTO comment);
}
