package com.zchadli.xoz_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Move> moves = new ArrayList<>();
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isFinished;
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isCurrent;
    @ManyToOne
    @JoinColumn(name="id_party")
    private Party party;
    private Long idWinner;
    private Long idCurrentPlayer;
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Game game = (Game) obj;
        return id != null && id.equals(game.getId());
    }
}
