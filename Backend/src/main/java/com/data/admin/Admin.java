package com.data.admin;

import javax.persistence.*;
import org.hibernate.annotations.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin_table")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotFound(action = NotFoundAction.IGNORE)
    private String firstName;

    @NotFound(action = NotFoundAction.IGNORE)
    private String lastName;

    @NotFound(action = NotFoundAction.IGNORE)
    private String birthday;

    @Column(unique = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private String username;

    @NotFound(action = NotFoundAction.IGNORE)
    private String email;

    @NotFound(action = NotFoundAction.IGNORE)
    private String password;

    @NotFound(action = NotFoundAction.IGNORE)
    private String salt;

}