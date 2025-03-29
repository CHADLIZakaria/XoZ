package com.zchadli.xoz_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

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
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Game> games = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name="player_parties",
            joinColumns = @JoinColumn(name="id_party"),
            inverseJoinColumns = @JoinColumn(name="id_player")
    )
    @ToString.Exclude
    private List<Player> players = new ArrayList<>();
    public Party() {
        this.uid = UUID.randomUUID().toString();
    }
}
