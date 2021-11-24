package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Cell;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Game;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Piece;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Step;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.ColorEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;

import java.util.*;

public class PawnPieceService implements IPieceService {
    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        Set<Cell> possibleMoves = new LinkedHashSet<>();
        Set<Cell> beatMoves = new LinkedHashSet<>();
        ColorEnum pieceColor = piece.getPieceColor();
        Map<Piece, Cell> pieceCellMap = game.getPiece2CellMap();
        DirectionEnum direction = getDirection(piece);

        if(isFirstPawnMove(game, piece, direction)) {
            possibleMoves.addAll(firstPawnStep(game, piece, direction));
        } else {

        }
        return null;
    }

    @Override
    public Step makeMove(Game game) {
        return null;
    }

    private List<Cell> firstPawnStep(Game game, Piece piece, DirectionEnum direction) {
        List<Cell> firstSteps = new ArrayList<>();
        Map<Piece, Cell> pieceCellMap = game.getPiece2CellMap();
        Cell currCell = pieceCellMap.get(piece);
        Cell nextCell;

        for (int i = 0; i < 3; i++) {
            nextCell = currCell.getNeighbors().get(direction);
            firstSteps.add(nextCell);
            currCell = nextCell;
        }
        return firstSteps;
    }

    private boolean isFirstPawnMove(Game game, Piece piece, DirectionEnum direction) {
        Cell currCell = game.getPiece2CellMap().get(piece);

        Cell prevCell = currCell.getNeighbors().get(direction);
        return prevCell.getNeighbors().get(direction) == null;
    }

    private DirectionEnum getDirection(Piece piece) {
        if(piece.getPieceColor() == ColorEnum.BLACK) {
            return DirectionEnum.SOUTH;
        } else {
            return DirectionEnum.NORTH;
        }
    }

    private boolean isAttackAvailable(Game game, Piece piece, Cell testedCell) {

        if (testedCell != null) {
            return (game.getCell2PieceMap().get(testedCell) != null) &&
                    (game.getCell2PieceMap().get(testedCell).getPieceColor() != piece.getPieceColor());
        }
        return false;
    }
}
