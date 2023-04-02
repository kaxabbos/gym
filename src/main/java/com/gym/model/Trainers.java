package com.gym.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Trainers {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private String file;
    private String tel;
    private String ach;
    private String spec;
    private int price;
    private byte exp;

    public Trainers(String name, String file, String tel, String ach, String spec, int price, byte exp) {
        this.name = name;
        this.file = file;
        this.tel = tel;
        this.ach = ach;
        this.spec = spec;
        this.price = price;
        this.exp = exp;
    }
}
