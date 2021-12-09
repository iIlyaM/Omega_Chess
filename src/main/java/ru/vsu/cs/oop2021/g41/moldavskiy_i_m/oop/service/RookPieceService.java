package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.*;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.serviceutils.CheckMovesUtils;

import java.util.*;

public class RookPieceService implements IPieceService {
    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<DirectionEnum> directionEnumList =
                Arrays.asList(DirectionEnum.NORTH, DirectionEnum.EAST,
                        DirectionEnum.SOUTH, DirectionEnum.WEST);

        return new ArrayList<>(findRookMoves(game, piece, directionEnumList));
    }


    @Override
    public Step makeMove(Game game, Piece piece, Cell targetCell) {
        Step rookStep = new Step();
        Cell currPosition = game.getPiece2CellMap().get(piece);

        rookStep.setPlayer(game.getPiece2PlayerMap().get(piece));
        rookStep.setStartCell(currPosition);
        rookStep.setEndCell(targetCell);
        rookStep.setPiece(piece);
        if (isTargetCellNotEmpty(game, targetCell)) {
            rookStep.setKilledPiece(game.getCell2PieceMap().get(targetCell));
        }
        game.getSteps().add(rookStep);
        changeOnBoardPlacement(game, piece, targetCell, currPosition);
        return rookStep;
    }

    private List<Cell> findRookMoves(Game game, Piece piece, List<DirectionEnum> directionEnumList) {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell receivedCell = game.getPiece2CellMap().get(piece);
        Cell currentCell;
        Cell nextCell;
        DirectionEnum direction;

        for (int i = 0; i < directionEnumList.size(); i++) {
            direction = directionEnumList.get(i);
            nextCell = receivedCell.getNeighbors().get(direction);
            while (isMoveAvailable(game, piece, nextCell)) {
                possibleMoves.add(nextCell);
                currentCell = nextCell;
                nextCell = currentCell.getNeighbors().get(direction);
                if (checkEnemyPieceCell(game, piece, currentCell) &&
                        isMoveAvailable(game, piece, currentCell)) {
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
