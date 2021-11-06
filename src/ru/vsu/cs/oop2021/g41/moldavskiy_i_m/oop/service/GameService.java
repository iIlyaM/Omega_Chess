package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.*;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.ColorEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.PieceEnum;

import java.util.*;

public class GameService {

    public static final int BOARD_SIZE = 10;

    public List<List<Cell>> initBoard() {
        List<List<Cell>> rows = new ArrayList<>();

        List<ColorEnum> cellColors = Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE);

        Cell prevCell;
        Cell currCell;
        List<Cell> prevRow = null;
        for (int i = 0; i < BOARD_SIZE; i++) {
            prevCell = null;
            List<Cell> currRow = new ArrayList<>();
            for (int j = 0; j < BOARD_SIZE; j++) {
                currCell = new Cell(cellColors.get((i + j) % 2));
                if (prevCell != null) {
                    currCell.getNeighbors().put(DirectionEnum.WEST, prevCell);
                    prevCell.getNeighbors().put(DirectionEnum.EAST, currCell);
                }
                currRow.add(currCell);
                prevCell = currCell;
                if (prevRow != null) {
                    for (int k = 0; k < currRow.size(); k++) {
                        Cell currRowCell = currRow.get(k);
                        Cell prevRowCell = prevRow.get(k);
                        currRowCell.getNeighbors().put(DirectionEnum.NORTH, prevRowCell);
                        prevRowCell.getNeighbors().put(DirectionEnum.SOUTH, currRowCell);
                    }
                }
            }
            rows.add(currRow);
            prevRow = currRow;
        }
        initWizardsCells(rows);
         return rows;
    }

    private void initWhitePawns(List<List<Cell>> board, Game game, Player firstPlayer) {
        Map<Piece, Cell> whitePawn2CellMap = game.getPiece2CellMap();
        Map<Cell, Piece> cell2WhitePawnMap = game.getCell2PieceMap();
        Map<Player, Set<Piece>> firstPlayer2PieceMap = game.getPlayer2PieceMap();
        Set<Piece> playerPieces = new LinkedHashSet<>();
        Map<Piece, Player> piece2FirstPlayerMap = new LinkedHashMap<>();
        Piece insertablePawn = new Piece(PieceEnum.PAWN, ColorEnum.WHITE);
        //board.get(1).get(i)

        for (int i = 0; i < BOARD_SIZE; i++) {
            whitePawn2CellMap.put(insertablePawn, board.get(1).get(i));
            cell2WhitePawnMap.put(board.get(1).get(i), insertablePawn);
            playerPieces.add(insertablePawn);
            firstPlayer2PieceMap.put(firstPlayer, playerPieces);
            piece2FirstPlayerMap.put(insertablePawn, firstPlayer);
        }
    }

    private void initBlackPawns(List<List<Cell>> board, Game game, Player secondPlayer) {
        Map<Piece, Cell> blackPawn2CellMap = game.getPiece2CellMap();
        Map<Cell, Piece> cell2BlackPawnMap = game.getCell2PieceMap();
        Map<Player, Set<Piece>> secondPlayer2PieceMap = game.getPlayer2PieceMap();
        Set<Piece> playerPieces = new LinkedHashSet<>();
        Map<Piece, Player> piece2SecondPlayerMap = new LinkedHashMap<>();
        Piece insertablePawn = new Piece(PieceEnum.PAWN, ColorEnum.BLACK);

        for (int i = 0; i < BOARD_SIZE; i++) {
            blackPawn2CellMap.put(insertablePawn, board.get(8).get(i));
            cell2BlackPawnMap.put(board.get(8).get(i), insertablePawn);
            playerPieces.add(insertablePawn);
            secondPlayer2PieceMap.put(secondPlayer, playerPieces);
            piece2SecondPlayerMap.put(insertablePawn, secondPlayer);
        }
    }

    private void initWizardsCells(List<List<Cell>> board) {
        Cell northWestCell = board.get(0).get(0);
        Cell northEastCell = board.get(0).get(board.size() - 1);
        Cell southWestCell = board.get(board.size() - 1).get(0);
        Cell southEastCell = board.get(board.size() - 1).get(board.size() - 1);


        northWestCell.getNeighbors().put(DirectionEnum.NORTH_WEST, new Cell(northWestCell.getColor()));
        northWestCell.getNeighbors().get(DirectionEnum.NORTH_WEST).getNeighbors().
                put(DirectionEnum.SOUTH_EAST, northWestCell);

        northEastCell.getNeighbors().put(DirectionEnum.NORTH_EAST, new Cell(northEastCell.getColor()));
        northEastCell.getNeighbors().get(DirectionEnum.NORTH_EAST).getNeighbors().
                put(DirectionEnum.SOUTH_WEST, northEastCell);

        southWestCell.getNeighbors().put(DirectionEnum.SOUTH_WEST, new Cell(southWestCell.getColor()));
        southWestCell.getNeighbors().get(DirectionEnum.SOUTH_WEST).getNeighbors().
                put(DirectionEnum.NORTH_EAST, southWestCell);

        southEastCell.getNeighbors().put(DirectionEnum.SOUTH_EAST, new Cell(southEastCell.getColor()));
        southEastCell.getNeighbors().get(DirectionEnum.SOUTH_EAST).getNeighbors().
                put(DirectionEnum.NORTH_WEST, southEastCell);
        //
    }
}
