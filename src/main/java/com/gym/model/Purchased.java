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
public class Purchased {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Subs sub;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;
    @OneToOne(fetch = FetchType.LAZY)
    private Trainers trainer;

    public Purchased(Subs sub, Users owner, Trainers trainer) {
        this.sub = sub;
        this.owner = owner;
        this.trainer = trainer;
    }
}
