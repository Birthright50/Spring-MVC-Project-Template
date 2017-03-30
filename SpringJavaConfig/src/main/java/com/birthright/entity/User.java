package com.birthright.entity;

import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by birth on 07.02.2017.
 */
@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public", catalog = "postgres")
public class User extends BaseAuditingEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

//    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
//    @LazyToOne(value = LazyToOneOption.PROXY)
//    private VerificationToken verificationToken;
//
//    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
//    @LazyToOne(value = LazyToOneOption.PROXY)
//    private PasswordResetToken passwordResetToken;

}
