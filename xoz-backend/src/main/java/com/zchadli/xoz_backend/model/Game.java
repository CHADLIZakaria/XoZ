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
    @ManyToMany
    @JoinTable(
        name="player_games",
        joinColumns = @JoinColumn(name="id_game"),
        inverseJoinColumns = @JoinColumn(name="id_player")
    )
    @ToString.Exclude
    private Set<Player> players = new HashSet<>();
    @OneToMany(mappedBy = "game")
    @ToString.Exclude
    private Set<Move> moves = new HashSet<>();
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isFinished;
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isCurrent;
    @ManyToOne
    @JoinColumn(name="id_party")
    private Party party;
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Game game = (Game) obj;
        return id != null && id.equals(game.getId());
    }
}
