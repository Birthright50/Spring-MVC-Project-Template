package com.birthright.entity;

import com.birthright.entity.enumiration.Roles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by birth on 12.02.2017.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "roles", schema = "public", catalog = "postgres")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Roles name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Collection<User> users;

    public Role(Roles name) {
        this.name = name;
    }

}
