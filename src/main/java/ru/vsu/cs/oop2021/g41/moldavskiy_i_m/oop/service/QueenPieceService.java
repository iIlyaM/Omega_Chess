package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Cell;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Game;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Piece;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Step;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;

import java.util.ArrayList;
import java.util.List;

public class QueenPieceService implements IPieceService{
    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {

        return new ArrayList<>(findQueenSteps(game, piece));
    }



    @Override
    public Step makeMove(Game game) {
        return null;
    }

    private List<Cell> findQueenSteps(Game game, Piece piece) {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell receivedCell = game.getPiece2CellMap().get(piece);
        Cell currCell;
        Cell nextCell;

        for (DirectionEnum direction : DirectionEnum.values()) {
            nextCell = receivedCell.getNeighbors().get(direction);
            while (isMoveAvailable(game, piece, nextCell)) {
                possibleMoves.add(nextCell);
                currCell = nextCell;
                nextCell = currCell.getNeighbors().get(direction);
                if(checkEnemyPieceCell(game, piece, currCell) && isMoveAvailable(game, piece, nextCell)) {
                    break;
                }
            }
        }
        return possibleMoves;
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
