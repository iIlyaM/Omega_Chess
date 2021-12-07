package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Cell;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Game;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Piece;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Step;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;


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
                            if(isMoveAvailable(game, piece, tempCell)) {
                                possibleMoves.add(tempCell);
                            }
                    }
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

    @Override
    public Step makeMove(Game game) {
        return null;
    }
}
