package scs.exe201.secondchanceshopbe.models.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<CategoryEntity> categories;

    @Column(name = "prices")
    private Long prices;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status ;


    @Column(name = "image", columnDefinition = "TEXT") // Sử dụng "TEXT" nếu cần lưu trữ JSON array dài
    private String image;

    public void setImages(List<String> imageUrls) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            this.image = objectMapper.writeValueAsString(imageUrls);
    }

    public List<String> getImage() throws JsonProcessingException {
        if (this.image == null || this.image.isEmpty()) {
            return new ArrayList<>(); // Trả về danh sách rỗng
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(this.image, List.class);
    }




    @Column(name = "date_create")
    private LocalDate dateCreate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity createBy;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RatingEntity> ratings;

    @OneToOne(mappedBy = "productOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderEntity orderEntity;

}
