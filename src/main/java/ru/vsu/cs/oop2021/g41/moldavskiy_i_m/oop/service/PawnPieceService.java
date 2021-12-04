package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Cell;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Game;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Piece;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Step;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.ColorEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.utils.PieceServiceUtil;

import java.util.*;

public class PawnPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Cell> possibleMoves = new ArrayList<>();
        //Set<Cell> possibleMoves = new LinkedHashSet<>();
        Set<Cell> beatMoves = new LinkedHashSet<>();
        DirectionEnum direction = PieceServiceUtil.getDirection(piece);

        if(isFirstPawnMove(game, piece)) {
            possibleMoves.addAll(firstPawnStep(game, piece, direction));
        } else {
            possibleMoves.addAll(findPawnStep(game, piece, direction));
        }
        return possibleMoves;
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
        Cell nextLeftCell;
        Cell nextRightCell;

        for (int i = 0; i < 3; i++) {
            nextCell = currCell.getNeighbors().get(direction);
            nextLeftCell = nextCell.getNeighbors().get(DirectionEnum.WEST);
            nextRightCell = nextCell.getNeighbors().get(DirectionEnum.EAST);
            firstSteps.add(nextCell);

            nextCell = currCell.getNeighbors().get(direction);
            if(PieceServiceUtil.isMoveAvailable(game, piece, nextLeftCell)) {
                firstSteps.add(nextLeftCell);
            }

            if(PieceServiceUtil.isMoveAvailable(game, piece, nextRightCell)) {
                firstSteps.add(nextRightCell);
            }
            currCell = nextCell;
        }
        return firstSteps;
    }

    private List<Cell> findPawnStep(Game game, Piece piece, DirectionEnum direction) {
        List<Cell> availableCells = new ArrayList<>();
        Cell currentCell = game.getPiece2CellMap().get(piece);
        Cell nextCell = currentCell.getNeighbors().get(direction);
        Cell nextLeftCell = nextCell.getNeighbors().get(DirectionEnum.WEST);
        Cell nextRightCell = nextCell.getNeighbors().get(DirectionEnum.EAST);

        if (PieceServiceUtil.isPawnMoveAvailable(game, nextCell)) {
            availableCells.add(nextCell);
        }

        if (PieceServiceUtil.isPawnAttackMoveAvailable(game, piece, nextLeftCell)) {
            availableCells.add(nextLeftCell);
        }

        if (PieceServiceUtil.isPawnAttackMoveAvailable(game, piece, nextRightCell)) {
            availableCells.add(nextCell);
        }

        return availableCells;
    }

    private boolean isFirstPawnMove(Game game, Piece piece) {
        DirectionEnum dir;
        Cell currCell = game.getPiece2CellMap().get(piece);

        if(piece.getPieceColor() == ColorEnum.WHITE) {
            dir = DirectionEnum.SOUTH;
        } else {
            dir = DirectionEnum.NORTH;
        }

        Cell prevCell = currCell.getNeighbors().get(dir);
        return prevCell.getNeighbors().get(dir) == null;
    }


}
