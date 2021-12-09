package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.*;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.serviceutils.CheckMovesUtils;

import java.util.ArrayList;
import java.util.List;

public class QueenPieceService implements IPieceService {
    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {

        return new ArrayList<>(findQueenSteps(game, piece));
    }


    @Override
    public Step makeMove(Game game, Piece piece, Cell targetCell) {
        Step queenStep = new Step();
        Cell currPosition = game.getPiece2CellMap().get(piece);

        queenStep.setPlayer(game.getPiece2PlayerMap().get(piece));
        queenStep.setStartCell(currPosition);
        queenStep.setEndCell(targetCell);
        queenStep.setPiece(piece);
        if (isTargetCellNotEmpty(game, targetCell)) {
            queenStep.setKilledPiece(game.getCell2PieceMap().get(targetCell));
        }
        game.getSteps().add(queenStep);
        changeOnBoardPlacement(game, piece, targetCell, currPosition);
        return queenStep;
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
                if (checkEnemyPieceCell(game, piece, currCell) && isMoveAvailable(game, piece, nextCell)) {
                    break;
                }
            }
        }
        return possibleMoves;
    }

    private void changeOnBoardPlacement(Game game, Piece piece, Cell targetCell, Cell currPosition) {
        Player rival;
        Piece targetPiece;
        if (isTargetCellNotEmpty(game, targetCell)) {
            targetPiece = game.getCell2PieceMap().get(targetCell);
            rival = game.getPiece2PlayerMap().get(targetPiece);
            game.getPlayer2PieceMap().get(rival).remove(targetPiece);
        }
        game.getPiece2CellMap().replace(piece, targetCell);
        game.getCell2PieceMap().put(targetCell, piece);
        game.getCell2PieceMap().remove(currPosition, piece);
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

    private boolean isTargetCellNotEmpty(Game game, Cell targetCell) {
        return game.getCell2PieceMap().get(targetCell) != null;
    }
}
