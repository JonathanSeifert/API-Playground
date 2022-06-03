package com.JS.RESTPlayground.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;
    @Column(name = "user_name")
    private String name;

    public User(String name) {
        this.name = name;
    }
}
