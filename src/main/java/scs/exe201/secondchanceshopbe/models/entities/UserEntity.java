package scs.exe201.secondchanceshopbe.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import scs.exe201.secondchanceshopbe.models.dtos.enums.RoleEnum;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "day_of_birth")
    private LocalDate dob;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum role;

    @OneToMany(mappedBy = "createBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductEntity> productEntities;

    @OneToMany(mappedBy = "userComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentEntity> commentEntities;

    @OneToMany(mappedBy = "userRating", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RatingEntity> ratingEntities;

    @OneToMany(mappedBy = "createNotification", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NotificationEntity> notificationEntities;

    @OneToMany(mappedBy = "shopOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShopEntity> shopEntities;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderEntity> orderEntities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
