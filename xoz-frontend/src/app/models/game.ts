export interface Player {
    id:  number;
    name: string;
}
export interface Game {
    id: number;
    players: Player[];
    finished: boolean;
    current: boolean;
    moves: Move[]
}
export interface Move {
    id: number | undefined;
    position: string;
    id_game: number;
    id_player: number;
}
export interface GameResult {
    movesWin: Move[];
    finished: boolean;
}
export interface CurrentGame {
    game: Game;
    gameResult: GameResult;
}
export interface Party {
    id: number | undefined;
    uid: string;
    currentGame: CurrentGame;
}