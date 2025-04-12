export interface Player {
    id:  number;
    name: string;
}
export interface Game {
    id: number;
    finished: boolean;
    current: boolean;
    moves: Move[];
    idWinner: number | null;
    idCurrentPlayer: number;
}
export interface Move {
    id: number | undefined;
    position: string;
    id_game: number;
    id_player: number;
    id_next_player: number;
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
    players: Player[];
    history: Game[];
}
export interface GameStart {
    idPlayer: number;
    partyUid: string;
    started: boolean;
}