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

    public void initWhitePawns(List<List<Cell>> board, Game game, Player firstPlayer) {
        Map<Piece, Cell> pawn2CellMap = game.getPiece2CellMap();
        Map<Cell, Piece> cell2PieceMap = game.getCell2PieceMap();
        Map<Player, Set<Piece>> player2PieceMap = game.getPlayer2PieceMap();
        Set<Piece> playerPieces = new LinkedHashSet<>();
        Map<Piece, Player> piece2PlayerMap = new LinkedHashMap<>();

        //board.get(1).get(i)

        for (int i = 0; i < BOARD_SIZE; i++) {
            pawn2CellMap.put(new Piece(PieceEnum.PAWN, ColorEnum.WHITE), board.get(1).get(i));
            cell2PieceMap.put(board.get(1).get(i), new Piece(PieceEnum.PAWN, ColorEnum.WHITE));
            playerPieces.add(new Piece(PieceEnum.PAWN, ColorEnum.WHITE));
            player2PieceMap.put(firstPlayer, playerPieces);
            piece2PlayerMap.put(new Piece(PieceEnum.PAWN, ColorEnum.WHITE), firstPlayer);
        }
    }

    public void initWhitePieces(List<List<Cell>> board, Game game, Player firstPlayer) {
        List<PieceEnum> pieces =
                Arrays.asList(PieceEnum.CHAMPION, PieceEnum.ROOK, PieceEnum.KNIGHT, PieceEnum.BISHOP);

        Map<Piece, Cell> pawn2CellMap = game.getPiece2CellMap();
        Map<Cell, Piece> cell2PieceMap = game.getCell2PieceMap();
        Map<Player, Set<Piece>> player2PieceMap = game.getPlayer2PieceMap();
        Set<Piece> playerPieces = new LinkedHashSet<>();
        Map<Piece, Player> piece2PlayerMap = new LinkedHashMap<>();

        for (int i = 0; i <= pieces.size(); i++) {
            pawn2CellMap.put(new Piece(pieces.get(i), ColorEnum.WHITE), board.get(0).get(i));
            cell2PieceMap.put(board.get(1).get(i), new Piece(pieces.get(i), ColorEnum.WHITE));
            playerPieces.add(new Piece(pieces.get(i), ColorEnum.WHITE));
            player2PieceMap.put(firstPlayer, playerPieces);
            piece2PlayerMap.put(new Piece(pieces.get(i), ColorEnum.WHITE), firstPlayer);
        }
    }

    public void initBlackPawns(List<List<Cell>> board, Game game, Player secondPlayer) {
        Map<Piece, Cell> pawn2CellMap = game.getPiece2CellMap();
        Map<Cell, Piece> cell2PieceMap = game.getCell2PieceMap();
        Map<Player, Set<Piece>> player2PieceMap = game.getPlayer2PieceMap();
        Set<Piece> playerPieces = new LinkedHashSet<>();
        Map<Piece, Player> piece2PlayerMap = new LinkedHashMap<>();

        for (int i = 0; i < BOARD_SIZE; i++) {
            pawn2CellMap.put(new Piece(PieceEnum.PAWN, ColorEnum.BLACK), board.get(8).get(i));
            cell2PieceMap.put(board.get(8).get(i), new Piece(PieceEnum.PAWN, ColorEnum.BLACK));
            playerPieces.add(new Piece(PieceEnum.PAWN, ColorEnum.BLACK));
            player2PieceMap.put(secondPlayer, playerPieces);
            piece2PlayerMap.put(new Piece(PieceEnum.PAWN, ColorEnum.BLACK), secondPlayer);
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
