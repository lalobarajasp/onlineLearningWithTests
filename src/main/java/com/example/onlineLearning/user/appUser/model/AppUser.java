package com.example.onlineLearning.user.appUser.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode //Create appropriate equals and hashcode methods
@NoArgsConstructor //Generate constructor with no parameters
@Entity
@Table(name = "appUser")
public class AppUser implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Long passwordCode;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean locked = false; //Check whether the Account is locked
    private Boolean enabled = false; //It enables the User once they confirm their email.

    private Boolean deletedAccount = false;


    public AppUser(String name, String lastName, String email, String password, Long passwordCode, AppUserRole appUserRole) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordCode = passwordCode;
        this.appUserRole = appUserRole;
    }

    public AppUser(Long id, String name, String lastName, String email, String password, Long passwordCode, AppUserRole appUserRole) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordCode = passwordCode;
        this.appUserRole = appUserRole;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getPasswordCode() {
        return passwordCode;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Boolean getDeletedAccount() {
        return deletedAccount;
    }

    public void setDeletedAccount(Boolean deletedAccount) {
        this.deletedAccount = deletedAccount;
    }
}
