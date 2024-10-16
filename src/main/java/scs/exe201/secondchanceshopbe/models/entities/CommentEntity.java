package scs.exe201.secondchanceshopbe.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "content")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status ;

    @Column(name = "date_create")
    private LocalDate dateCreate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity userComment;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private ProductEntity product;
}
