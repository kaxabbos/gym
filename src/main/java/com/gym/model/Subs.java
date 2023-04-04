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
public class Subs {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private int price;
    private byte term;
    private byte pause;
    private byte start_by;
    private byte before;
    private String file;
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Statics statics;

    public Subs(String name, int price, byte term, byte pause, byte start_by, byte before, String file, String description) {
        this.name = name;
        this.price = price;
        this.term = term;
        this.pause = pause;
        this.start_by = start_by;
        this.before = before;
        this.file = file;
        this.description = description;
        this.statics = new Statics(this);
    }
}
