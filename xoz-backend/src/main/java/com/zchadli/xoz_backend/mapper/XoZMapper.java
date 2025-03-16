package com.zchadli.xoz_backend.mapper;

import com.zchadli.xoz_backend.dto.*;
import com.zchadli.xoz_backend.model.Game;
import com.zchadli.xoz_backend.model.Move;
import com.zchadli.xoz_backend.model.Party;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;

@Mapper(componentModel = "spring")
public interface XoZMapper {
    XoZMapper xoZMapper = Mappers.getMapper(XoZMapper.class);

    GameDto toGameDto(Game game);
    Game toGame(GameDto gameDto);

    @Mapping(source = "position", target = "x", qualifiedByName = "getXPosition")
    @Mapping(source = "position", target = "y", qualifiedByName = "getYPosition")
    @Mapping(source = "id_game", target = "game.id")
    @Mapping(source = "id_player", target = "player.id")
    Move toMove(MoveDto moveDto);
    @Mapping(expression = "java(move.getX() + \",\"+move.getY())", target = "position")
    @Mapping(source = "game.id", target = "id_game")
    @Mapping(source =  "player.id", target = "id_player")
    MoveDto toMoveDto(Move move);

    @Mapping(source = "party.id", target = "id")
    @Mapping(target = "currentGame", expression = "java(toCurrentGameDto(currentGame, gameResult))") // Call method explicitly
    PartyDto toPartyDto(Party party, GameDto currentGame, GameResultDto gameResult);

    default CurrentGameDto toCurrentGameDto(GameDto gameDto, GameResultDto gameResult) {
        return (gameDto != null && gameResult != null) ? new CurrentGameDto(gameDto, gameResult) : null;
    }

    default Integer[] splitPositions(String position) {
        if (position != null && !position.isEmpty()) {
            return Arrays.stream(position.split(","))
                    .map(element -> Integer.valueOf(element.trim()))
                    .toArray(Integer[]::new);
        }
        return new Integer[]{-1, -1};
    }

    @Named(value = "getXPosition")
    default Integer getXPosition(String position) {
        Integer[] res = splitPositions(position);
        return res.length > 0 ? res[0] : -1;
    }

    @Named(value = "getYPosition")
    default Integer getYPosition(String position) {
        Integer[] res = splitPositions(position);
        return res.length > 1 ? res[1] : -1;
    }
}
