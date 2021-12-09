package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.*;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.ColorEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.serviceutils.CheckMovesUtils;


import java.util.*;

public class PawnPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Cell> possibleMoves = new ArrayList<>();
        DirectionEnum direction = getDirection(piece);

        if(isFirstPawnMove(game, piece)) {
            possibleMoves.addAll(firstPawnStep(game, piece, direction));
        } else {
            possibleMoves.addAll(findPawnStep(game, piece, direction));
        }
        return possibleMoves;
    }

    @Override
    public Step makeMove(Game game, Piece piece, Cell targetCell) {
        Step pawnStep = new Step();
        Cell currPosition = game.getPiece2CellMap().get(piece);

        pawnStep.setPlayer(game.getPiece2PlayerMap().get(piece));
        pawnStep.setStartCell(currPosition);
        pawnStep.setEndCell(targetCell);
        pawnStep.setPiece(piece);
        if(CheckMovesUtils.isTargetCellNotEmpty(game, targetCell)) {
            pawnStep.setKilledPiece(game.getCell2PieceMap().get(targetCell));
        }
        game.getSteps().add(pawnStep);
        changeOnBoardPlacement(game, piece, targetCell, currPosition);
        return pawnStep;
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
            if(isPawnAttackMoveAvailable(game, piece, nextLeftCell)) {
                firstSteps.add(nextLeftCell);
            }

            if(isPawnAttackMoveAvailable(game, piece, nextRightCell)) {
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


        if (isPawnMoveAvailable(game, nextCell)) {
            availableCells.add(nextCell);
            Cell nextLeftCell = nextCell.getNeighbors().get(DirectionEnum.WEST);
            Cell nextRightCell = nextCell.getNeighbors().get(DirectionEnum.EAST);
            if (isPawnAttackMoveAvailable(game, piece, nextLeftCell)) {
                availableCells.add(nextLeftCell);
            }

            if (isPawnAttackMoveAvailable(game, piece, nextRightCell)) {
                availableCells.add(nextCell);
            }

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

    private boolean isPawnMoveAvailable(Game game, Cell testedCell) {
        if (testedCell != null) {
            return game.getCell2PieceMap().get(testedCell) == null;
        }
        return false;
    }

    private boolean isPawnAttackMoveAvailable(Game game, Piece piece, Cell testedCell) {
        if (testedCell != null) {
            return (game.getCell2PieceMap().get(testedCell) != null) &&
                    (game.getCell2PieceMap().get(testedCell).getPieceColor() != piece.getPieceColor());
        }
        return false;
    }


    private DirectionEnum getDirection(Piece piece) {
        if(piece.getPieceColor() == ColorEnum.BLACK) {
            return DirectionEnum.SOUTH;
        } else {
            return DirectionEnum.NORTH;
        }
    }

    private void changeOnBoardPlacement(Game game, Piece piece, Cell targetCell, Cell currPosition) {
        Player rival;
        Piece targetPiece;
        if(CheckMovesUtils.isTargetCellNotEmpty(game, targetCell)) {
            targetPiece = game.getCell2PieceMap().get(targetCell);
            rival = game.getPiece2PlayerMap().get(targetPiece);
            game.getPlayer2PieceMap().get(rival).remove(targetPiece);
        }
        game.getPiece2CellMap().put(piece, targetCell);
        game.getCell2PieceMap().put(targetCell, piece);
        game.getCell2PieceMap().remove(currPosition, piece);
    }


}