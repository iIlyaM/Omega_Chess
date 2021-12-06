package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Cell;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Game;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Piece;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Step;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;

import java.util.*;

public class BishopPieceService implements IPieceService {
    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Cell> possibleMoves = new ArrayList<>();
        //Set<Cell> possibleMoves = new LinkedHashSet<>();
        Set<Cell> beatMoves = new LinkedHashSet<>();
        List<DirectionEnum> directionEnumList =
                Arrays.asList(DirectionEnum.NORTH_WEST, DirectionEnum.NORTH_EAST,
                        DirectionEnum.SOUTH_WEST, DirectionEnum.SOUTH_EAST);

        possibleMoves.addAll(findBishopSteps(game, piece, directionEnumList));

        return possibleMoves;
    }

    private List<Cell> findBishopSteps(Game game, Piece piece, List<DirectionEnum> directionEnumList) {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell receivedCell = game.getPiece2CellMap().get(piece);
        Cell currCell;
        Cell nextCell;
        DirectionEnum dir;
        for (int i = 0; i < directionEnumList.size(); i++) {
            dir = directionEnumList.get(i);
            nextCell = receivedCell.getNeighbors().get(dir);
            while (isMoveAvailable(game, piece, nextCell)) {
                possibleMoves.add(nextCell);
                currCell = nextCell;
                nextCell = currCell.getNeighbors().get(dir);
                if(checkEnemyPieceCell(game, piece, nextCell) && checkEnemyPieceCell(game, piece, currCell)) {
                    break;
                }
            }
        }
        return possibleMoves;
    }

    @Override
    public Step makeMove(Game game) {
        return null;
    }

    private boolean isMoveAvailable(Game game, Piece piece, Cell testedCell) {
        if (testedCell != null) {
            return ((game.getCell2PieceMap().get(testedCell) == null) ||
                    ((game.getCell2PieceMap().get(testedCell) != null) &&
                            (game.getCell2PieceMap().get(testedCell).getPieceColor() != piece.getPieceColor())));
        }
        return false;
    }

    private boolean checkEnemyPieceCell(Game game,Piece piece, Cell testedCell) {
        return (game.getCell2PieceMap().get(testedCell) != null) &&
                (game.getCell2PieceMap().get(testedCell).getPieceColor() != piece.getPieceColor());
    }
}
