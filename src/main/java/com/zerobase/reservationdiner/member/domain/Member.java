package com.zerobase.reservationdiner.member.domain;

import com.zerobase.reservationdiner.common.domain.BaseEntity;
import com.zerobase.reservationdiner.customer.domain.Reservation;
import com.zerobase.reservationdiner.customer.domain.Review;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 5,max = 10)
    private String memberId;

    private String memberPassword;

    @NotBlank
    private String memberName;

    @Pattern(regexp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$")
    @NotBlank
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @OneToMany(fetch = FetchType.LAZY,mappedBy ="member" )
    private List<Reservation> reservations=new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "owner")
    private List<OwnerStore> stores=new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "member")
    private List<Review> reviews=new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.memberPassword;
    }

    @Override
    public String getUsername() {
        return this.memberId;
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
