package vladislavmaltsev.paymenttaskapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vladislavmaltsev.paymenttaskapi.util.Role;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    long id;
    @Column(name = "name", unique = true, nullable = false)
    String name;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;
    @Column(name = "pass", nullable = false)
    String pass;
    @Column(name = "islocked")
    boolean isLocked;
    @Column(name = "lockdate")
    Date lockDate;
    @Column(name = "lockcount")
    int lockCount;


    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    @ToString.Exclude
    List<Payment> paymentList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return name;
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
