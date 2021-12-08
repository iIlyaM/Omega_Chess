package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.*;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.serviceutils.CheckMovesUtils;


import java.util.*;

public class KnightPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {

        List<DirectionEnum> directionEnumList =
                Arrays.asList(DirectionEnum.NORTH, DirectionEnum.EAST, DirectionEnum.SOUTH, DirectionEnum.WEST);

        return new ArrayList<>(findKnightMoves(game, piece, directionEnumList));
    }

    private List<Cell> findKnightMoves(Game game, Piece piece, List<DirectionEnum> directionsList) {
        List<Cell> possibleMoves = new ArrayList<>();
        DirectionEnum dir;
        Cell receivedCell = game.getPiece2CellMap().get(piece);
        Cell currentCell;
        Cell nextCell;
        Cell tempCell;

        for (int i = 0; i < directionsList.size(); i++) {
            dir = directionsList.get(i);
            currentCell = receivedCell;
            nextCell = currentCell.getNeighbors().get(dir);
            if(nextCell!= null) {
                currentCell = nextCell;
                nextCell = currentCell.getNeighbors().get(dir);
                if(nextCell != null) {
                    currentCell = nextCell;


                    for (int j = 0, k = 1; j < directionsList.size(); j += 2, k += 2) {
                        if(i % 2 == 0) {
                            dir = directionsList.get(k);
                        } else {
                            dir = directionsList.get(j);
                        }
                            tempCell = currentCell.getNeighbors().get(dir);
                            if(CheckMovesUtils.isMoveAvailable(game, piece, tempCell)) {
                                possibleMoves.add(tempCell);
                            }
                    }
                }
            }
        }
        return possibleMoves;
    }


    @Override
    public Step makeMove(Game game, Piece piece, Cell targetCell) {
        Step knightStep = new Step();
        Cell currPosition = game.getPiece2CellMap().get(piece);

        knightStep.setPlayer(game.getPiece2PlayerMap().get(piece));
        knightStep.setStartCell(currPosition);
        knightStep.setEndCell(targetCell);
        knightStep.setPiece(piece);
        if(CheckMovesUtils.isTargetCellNotEmpty(game, targetCell)) {
            knightStep.setKilledPiece(game.getCell2PieceMap().get(targetCell));
        }
        game.getSteps().add(knightStep);
        changeOnBoardPlacement(game, piece, targetCell, currPosition);
        return knightStep;
    }

    private void changeOnBoardPlacement(Game game, Piece piece, Cell targetCell, Cell currPosition) {
        Player rival;
        Piece targetPiece;
        game.getPiece2CellMap().replace(piece, targetCell);
        game.getCell2PieceMap().put(targetCell, piece);
        game.getCell2PieceMap().remove(currPosition, piece);
        if(CheckMovesUtils.isTargetCellNotEmpty(game, targetCell)) {
            targetPiece = game.getCell2PieceMap().get(targetCell);
            rival = game.getPiece2PlayerMap().get(targetPiece);
            game.getPlayer2PieceMap().get(rival).remove(targetPiece);
        }
    }
}
