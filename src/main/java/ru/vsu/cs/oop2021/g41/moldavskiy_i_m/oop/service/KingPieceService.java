package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.*;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.serviceutils.CheckMovesUtils;

import java.util.ArrayList;
import java.util.List;

public class KingPieceService implements IPieceService {
    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {

        return new ArrayList<>(findKingSteps(game, piece));
    }

    private List<Cell> findKingSteps(Game game, Piece piece) {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell receivedCell = game.getPiece2CellMap().get(piece);
        Cell nextCell;
        for (DirectionEnum direction : DirectionEnum.values()) {
            nextCell = receivedCell.getNeighbors().get(direction);
            if (isMoveAvailable(game, piece, nextCell)) {
                possibleMoves.add(nextCell);
            }

        }
        return possibleMoves;
    }

    @Override
    public Step makeMove(Game game, Piece piece, Cell targetCell) {
        Step kingStep = new Step();
        Cell currPosition = game.getPiece2CellMap().get(piece);

        kingStep.setPlayer(game.getPiece2PlayerMap().get(piece));
        kingStep.setStartCell(currPosition);
        kingStep.setEndCell(targetCell);
        kingStep.setPiece(piece);
        if (isTargetCellNotEmpty(game, targetCell)) {
            kingStep.setKilledPiece(game.getCell2PieceMap().get(targetCell));
        }
        game.getSteps().add(kingStep);
        changeOnBoardPlacement(game, piece, targetCell, currPosition);
        return kingStep;
    }


    private void changeOnBoardPlacement(Game game, Piece piece, Cell targetCell, Cell currPosition) {
        Player rival;
        Piece targetPiece;
        if (isTargetCellNotEmpty(game, targetCell)) {
            targetPiece = game.getCell2PieceMap().get(targetCell);
            rival = game.getPiece2PlayerMap().get(targetPiece);
            game.getPlayer2PieceMap().get(rival).remove(targetPiece);
        }
        game.getPiece2CellMap().put(piece, targetCell);
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

    private boolean isTargetCellNotEmpty(Game game, Cell targetCell) {
        return game.getCell2PieceMap().get(targetCell) != null;
    }
}
