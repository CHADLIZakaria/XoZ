package com.zchadli.xoz_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@ToString
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "games")
    private Set<Player> players = new HashSet<>();
    @OneToMany(mappedBy = "game")
    private Set<Move> moves = new HashSet<>();
    private Boolean isFinished;

}
