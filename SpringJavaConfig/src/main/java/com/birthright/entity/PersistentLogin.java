package com.birthright.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by birthright on 05.03.17.
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "persistent_login", schema = "public", catalog = "postgres")
public class PersistentLogin {

    @Id
    private String series;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "last_used", nullable = false)
    private Date lastUsed;

}
