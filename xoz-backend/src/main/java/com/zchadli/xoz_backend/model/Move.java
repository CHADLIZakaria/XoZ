package com.zchadli.xoz_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@ToString
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer x;
    private Integer y;
    @ManyToOne
    @JoinColumn(name="id_game")
    private Game game;
    @ManyToOne
    @JoinColumn(name="id_player")
    private Player player;
}
