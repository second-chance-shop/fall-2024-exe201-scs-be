package scs.exe201.secondchanceshopbe.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;

    @Column(name = "status")
    private String status ;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private Set<ProductEntity> products;

    @OneToMany(mappedBy = "typeShop", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShopEntity> shopEntities;
}
