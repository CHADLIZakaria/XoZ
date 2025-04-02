package com.zchadli.xoz_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GameStartDto {
    private List<String> players;
    private String partyUid;
}
