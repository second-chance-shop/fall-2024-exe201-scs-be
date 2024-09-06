package scs.exe201.secondchanceshopbe.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private Set<ProductEntity> products;

    @OneToMany(mappedBy = "typeShop", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShopEntity> shopEntities;
}
