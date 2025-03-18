package com.zchadli.xoz_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@ToString
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uid;
    @OneToMany(mappedBy = "party")
    @ToString.Exclude
    private Set<Game> games = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name="player_parties",
            joinColumns = @JoinColumn(name="id_party"),
            inverseJoinColumns = @JoinColumn(name="id_player")
    )
    @ToString.Exclude
    private Set<Player> players = new HashSet<>();
    public Party() {
        this.uid = UUID.randomUUID().toString();
    }
}
