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

    public void initPieces(List<List<Cell>> board, Game game, Player firstPlayer, Player secondPlayer) {
        List<PieceEnum> pieces =
                Arrays.asList(PieceEnum.CHAMPION, PieceEnum.ROOK, PieceEnum.KNIGHT, PieceEnum.BISHOP);
        Set<Piece> firstPlayerPieces = new LinkedHashSet<>();
        Set<Piece> secondPlayerPieces = new LinkedHashSet<>();

        initWhitePawnsPieces(board, firstPlayerPieces, game, firstPlayer);
        initWhitePieces(board, pieces, firstPlayerPieces, game, firstPlayer);
        initBlackPawnsPieces(board, secondPlayerPieces, game, secondPlayer);
        initBlackPieces(board, pieces, secondPlayerPieces, game, secondPlayer);
    }

    private void initWhitePawnsPieces(
            List<List<Cell>> board,
            Set<Piece> playerPieces,
            Game game,
            Player firstPlayer
    ) {

        for (int i = 0; i < BOARD_SIZE; i++) {
            game.getPiece2CellMap().put(new Piece(PieceEnum.PAWN, ColorEnum.WHITE), board.get(1).get(i));
            game.getCell2PieceMap().put(board.get(1).get(i), new Piece(PieceEnum.PAWN, ColorEnum.WHITE));
            playerPieces.add(new Piece(PieceEnum.PAWN, ColorEnum.WHITE));
            game.getPlayer2PieceMap().put(firstPlayer, playerPieces);
            game.getPiece2PlayerMap().put(new Piece(PieceEnum.PAWN, ColorEnum.WHITE), firstPlayer);
        }
    }

    private void initWhitePieces(
            List<List<Cell>> board,
            List<PieceEnum> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player firstPlayer
    ) {
        initLeftWhitePieces(board, pieces, playerPieces, game, firstPlayer);
        initRightWhitePieces(board, pieces, playerPieces, game, firstPlayer);
    }

    private void initBlackPawnsPieces(
            List<List<Cell>> board,
            Set<Piece> playerPieces,
            Game game,
            Player secondPlayer
    ) {

        for (int i = 0; i < BOARD_SIZE; i++) {
            game.getPiece2CellMap().put(new Piece(PieceEnum.PAWN, ColorEnum.BLACK), board.get(8).get(i));
            game.getCell2PieceMap().put(board.get(8).get(i), new Piece(PieceEnum.PAWN, ColorEnum.BLACK));
            playerPieces.add(new Piece(PieceEnum.PAWN, ColorEnum.BLACK));
            game.getPlayer2PieceMap().put(secondPlayer, playerPieces);
            game.getPiece2PlayerMap().put(new Piece(PieceEnum.PAWN, ColorEnum.BLACK), secondPlayer);
        }
    }

    private void initBlackPieces(
            List<List<Cell>> board,
            List<PieceEnum> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player secondPlayer
    ) {
        initLeftBlackPieces(board, pieces, playerPieces, game, secondPlayer);
        initRightBlackPieces(board, pieces, playerPieces, game, secondPlayer);
    }

    private void initLeftWhitePieces(
            List<List<Cell>> board,
            List<PieceEnum> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player player
    ) {
        for (int i = 0; i < (BOARD_SIZE - 2) / 2; i++) {
            game.getPiece2CellMap().put(new Piece(pieces.get(i), ColorEnum.WHITE), board.get(0).get(i));
            game.getCell2PieceMap().put(board.get(0).get(i), new Piece(pieces.get(i), ColorEnum.WHITE));
            playerPieces.add(new Piece(pieces.get(i), ColorEnum.WHITE));
            game.getPlayer2PieceMap().put(player, playerPieces);
            game.getPiece2PlayerMap().put(new Piece(pieces.get(i), ColorEnum.WHITE), player);
        }
        Cell queenCell = board.get(0).get(((BOARD_SIZE - 2) / 2) - 1);
        game.getCell2PieceMap().put(queenCell, new Piece(PieceEnum.QUEEN, ColorEnum.WHITE));
    }

    private void initRightWhitePieces(
            List<List<Cell>> board,
            List<PieceEnum> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player player
    ) {
        for (int i = BOARD_SIZE - 1, k = 0; i > (BOARD_SIZE - 2) / 2 && k < pieces.size(); i--, k++) {
            game.getPiece2CellMap().put(new Piece(pieces.get(k), ColorEnum.WHITE), board.get(1).get(i));
            game.getCell2PieceMap().put(board.get(1).get(i), new Piece(pieces.get(k), ColorEnum.WHITE));
            playerPieces.add(new Piece(pieces.get(k), ColorEnum.WHITE));
            game.getPlayer2PieceMap().put(player, playerPieces);
            game.getPiece2PlayerMap().put(new Piece(pieces.get(k), ColorEnum.WHITE), player);
        }
        Cell kingCell = board.get(0).get(((BOARD_SIZE - 2) / 2) + 2);
        game.getCell2PieceMap().put(kingCell, new Piece(PieceEnum.KING, ColorEnum.WHITE));
    }

    private void initLeftBlackPieces(
            List<List<Cell>> board,
            List<PieceEnum> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player player
    ) {
        for (int i = 0; i < (BOARD_SIZE - 2) / 2; i++) {
            game.getPiece2CellMap().put(new Piece(pieces.get(i), ColorEnum.BLACK), board.get(BOARD_SIZE - 1).get(i));
            game.getCell2PieceMap().put(board.get(BOARD_SIZE - 1).get(i), new Piece(pieces.get(i), ColorEnum.BLACK));
            playerPieces.add(new Piece(pieces.get(i), ColorEnum.BLACK));
            game.getPlayer2PieceMap().put(player, playerPieces);
            game.getPiece2PlayerMap().put(new Piece(pieces.get(i), ColorEnum.BLACK), player);
        }
        Cell queenCell = board.get(BOARD_SIZE - 1).get(((BOARD_SIZE - 2) / 2) - 1);
        game.getCell2PieceMap().put(queenCell, new Piece(PieceEnum.QUEEN, ColorEnum.BLACK));
    }

    private void initRightBlackPieces(
            List<List<Cell>> board,
            List<PieceEnum> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player player
    ) {
        for (int i = BOARD_SIZE - 1, k = 0; i > (BOARD_SIZE - 2) / 2 && k < pieces.size(); i--, k++) {
            game.getPiece2CellMap().put(new Piece(pieces.get(k), ColorEnum.BLACK), board.get(BOARD_SIZE - 1).get(i));
            game.getCell2PieceMap().put(board.get(BOARD_SIZE - 1).get(i), new Piece(pieces.get(k), ColorEnum.BLACK));
            playerPieces.add(new Piece(pieces.get(k), ColorEnum.BLACK));
            game.getPlayer2PieceMap().put(player, playerPieces);
            game.getPiece2PlayerMap().put(new Piece(pieces.get(k), ColorEnum.BLACK), player);
        }

        Cell kingCell = board.get(BOARD_SIZE - 1).get(((BOARD_SIZE - 2) / 2) + 2);
        game.getCell2PieceMap().put(kingCell, new Piece(PieceEnum.KING, ColorEnum.WHITE));
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
        //todo от рефакторить
    }
}
