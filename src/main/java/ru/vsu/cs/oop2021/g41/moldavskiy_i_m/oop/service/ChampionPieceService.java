package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Cell;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Game;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Piece;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Step;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChampionPieceService implements IPieceService {
    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        return new ArrayList<>(findChampionMoves(game, piece));
    }

    @Override
    public Step makeMove(Game game, Piece piece, Cell targetCell) {
        return null;
    }

    private List<Cell> findChampionMoves(Game game, Piece piece) {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell receivedCell = game.getPiece2CellMap().get(piece);
        Cell currentCell;
        Cell nextCell;
        int counter = 0;

        for (DirectionEnum direction : DirectionEnum.values()) {
            if (counter % 2 == 0) {
                nextCell = receivedCell.getNeighbors().get(direction);
                for (int i = 0; i < 2; i++) {
                    if (isMoveAvailable(game, piece, nextCell)) {
                        possibleMoves.add(nextCell);
                        currentCell = nextCell;
                        nextCell = currentCell.getNeighbors().get(direction);
                    }
                }
            }
            if(counter % 2 != 0) {
                nextCell = receivedCell.getNeighbors().get(direction);
                if(nextCell != null) {
                    currentCell = nextCell;
                    nextCell = currentCell.getNeighbors().get(direction);
                    if(isMoveAvailable(game, piece, nextCell)) {
                        possibleMoves.add(nextCell);
                    }
                }
            }
            counter++;
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
}
