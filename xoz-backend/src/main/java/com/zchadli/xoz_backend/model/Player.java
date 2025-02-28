package com.zchadli.xoz_backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(
        name="player_games",
        joinColumns = @JoinColumn(name="player_id"),
        inverseJoinColumns = @JoinColumn(name="game_id")
    )
    private Set<Game> games = new HashSet<>();
    @OneToMany(mappedBy = "player")
    private Set<Move> moves = new HashSet<>();
}
