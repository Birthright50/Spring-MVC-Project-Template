package com.birthright.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Collection;

/**
 * Created by birth on 12.02.2017.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "roles", schema = "public", catalog = "postgres")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public Role(String name) {
        this.name = name;
    }
}
