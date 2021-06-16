package com.ecare.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "user", schema = "public", catalog = "eCare")
@NamedQueries({
        @NamedQuery(name = "User.findByEmail",
                query = "SELECT u FROM UserEntity u where u.email = :email"),

        @NamedQuery(name = "User.findAll",
                query = "SELECT u FROM UserEntity u order by u.email"),
})

public class UserEntity implements Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "email", nullable = false, unique = true, length = 129)
    // @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$")
    private String email;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @OneToOne(mappedBy = "user")
    public ClientEntity clientByUserId;

    public void setRole(UserRole role) {
        if(this.roles==null)
            this.roles=new HashSet<>();
        this.roles.add(role);
    }

    @ElementCollection(targetClass = UserRole.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
      }

}
