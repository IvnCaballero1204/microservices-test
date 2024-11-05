package com.ivancaballero.demo.model;

import jakarta.persistence.*;

@Entity
@Table
public class User {
    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String username;
    private String emial;
    private String password;

    public User() { }
    public User(Long id, String username, String emial, String password) {
        this.id = id;
        this.username = username;
        this.emial = emial;
        this.password = password;
    }

    public User(String username, String emial, String password) {
        this.username = username;
        this.emial = emial;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", emial='" + emial + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
