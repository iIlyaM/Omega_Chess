package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.*;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.serviceutils.CheckMovesUtils;

import java.util.ArrayList;
import java.util.List;

public class ChampionPieceService implements IPieceService {
    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        return new ArrayList<>(findChampionMoves(game, piece));
    }

    @Override
    public Step makeMove(Game game, Piece piece, Cell targetCell) {
        Step championPiece = new Step();
        Cell currPosition = game.getPiece2CellMap().get(piece);

        championPiece.setPlayer(game.getPiece2PlayerMap().get(piece));
        championPiece.setStartCell(currPosition);
        championPiece.setEndCell(targetCell);
        championPiece.setPiece(piece);
        if(CheckMovesUtils.isTargetCellNotEmpty(game, targetCell)) {
            championPiece.setKilledPiece(game.getCell2PieceMap().get(targetCell));
        }
        game.getSteps().add(championPiece);
        changeOnBoardPlacement(game, piece, targetCell, currPosition);
        return championPiece;
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
                    if (CheckMovesUtils.isMoveAvailable(game, piece, nextCell)) {
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
                    if(CheckMovesUtils.isMoveAvailable(game, piece, nextCell)) {
                        possibleMoves.add(nextCell);
                    }
                }
            }
            counter++;
        }
        return possibleMoves;
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
