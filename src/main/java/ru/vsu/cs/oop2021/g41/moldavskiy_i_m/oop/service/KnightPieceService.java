package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Cell;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Game;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Piece;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Step;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.utils.PieceServiceUtil;

import java.util.*;

public class KnightPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Cell> possibleMoves = new ArrayList<>();
        //Set<Cell> possibleMoves = new LinkedHashSet<>();
        Set<Cell> beatMoves = new LinkedHashSet<>();

        List<DirectionEnum> directionEnumList =
                Arrays.asList(DirectionEnum.NORTH, DirectionEnum.EAST, DirectionEnum.SOUTH, DirectionEnum.WEST);

        return possibleMoves;
    }

    private List<Cell> findKnightMoves(
            Game game,
            Piece piece,
            DirectionEnum direction,
            List<DirectionEnum> directionsList
    ) {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell currentCell = game.getPiece2CellMap().get(piece);

        for (int i = 0; i < directionsList.size(); i++) {

        }
        return possibleMoves;
    }

    @Override
    public Step makeMove(Game game) {
        return null;
    }
}
