export interface Game {
    id: number;
}
export interface Move {
    id: number | undefined;
    position: string;
    id_game: number;
    id_player: number;
}