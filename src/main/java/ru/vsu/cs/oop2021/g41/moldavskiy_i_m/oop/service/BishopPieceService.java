package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.*;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.serviceutils.CheckMovesUtils;

import java.util.*;

public class BishopPieceService implements IPieceService {
    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<DirectionEnum> directionEnumList =
                Arrays.asList(DirectionEnum.NORTH_WEST, DirectionEnum.NORTH_EAST,
                        DirectionEnum.SOUTH_WEST, DirectionEnum.SOUTH_EAST);

        return new ArrayList<>(findBishopSteps(game, piece, directionEnumList));
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
                if (checkEnemyPieceCell(game, piece, currCell) && isMoveAvailable(game, piece, nextCell)) {
                    break;
                }
            }
        }
        return possibleMoves;
    }

    @Override
    public Step makeMove(Game game, Piece piece, Cell targetCell) {
        Step bishopStep = new Step();
        Cell currPosition = game.getPiece2CellMap().get(piece);

        bishopStep.setPlayer(game.getPiece2PlayerMap().get(piece));
        bishopStep.setStartCell(currPosition);
        bishopStep.setEndCell(targetCell);
        bishopStep.setPiece(piece);
        if (isTargetCellNotEmpty(game, targetCell)) {
            bishopStep.setKilledPiece(game.getCell2PieceMap().get(targetCell));
        }
        game.getSteps().add(bishopStep);
        changeOnBoardPlacement(game, piece, targetCell, currPosition);
        return bishopStep;
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

    private boolean checkEnemyPieceCell(Game game,Piece piece, Cell testedCell) {
        return (game.getCell2PieceMap().get(testedCell) != null) &&
                (game.getCell2PieceMap().get(testedCell).getPieceColor() != piece.getPieceColor());
    }

    private boolean isTargetCellNotEmpty(Game game, Cell targetCell) {
        return game.getCell2PieceMap().get(targetCell) != null;
    }
}
