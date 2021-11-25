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
            Piece pawnPiece = new Piece(PieceEnum.PAWN, ColorEnum.WHITE);
            game.getPiece2CellMap().put(pawnPiece, board.get(1).get(i));
            game.getCell2PieceMap().put(board.get(1).get(i), pawnPiece);
            playerPieces.add(pawnPiece);
            game.getPlayer2PieceMap().put(firstPlayer, playerPieces);
            game.getPiece2PlayerMap().put(pawnPiece, firstPlayer);
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
            Piece pawnPiece = new Piece(PieceEnum.PAWN, ColorEnum.BLACK);
            game.getPiece2CellMap().put(pawnPiece, board.get(8).get(i));
            game.getCell2PieceMap().put(board.get(8).get(i), pawnPiece);
            playerPieces.add(pawnPiece);
            game.getPlayer2PieceMap().put(secondPlayer, playerPieces);
            game.getPiece2PlayerMap().put(pawnPiece, secondPlayer);
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
            Piece whitePiece = new Piece(pieces.get(i), ColorEnum.WHITE);
            game.getPiece2CellMap().put(whitePiece, board.get(0).get(i));
            game.getCell2PieceMap().put(board.get(0).get(i), whitePiece);
            playerPieces.add(whitePiece);
            game.getPlayer2PieceMap().put(player, playerPieces);
            game.getPiece2PlayerMap().put(whitePiece, player);
        }
        Cell queenCell = board.get(0).get(((BOARD_SIZE - 2) / 2) - 1);
        Piece queenPiece = new Piece(PieceEnum.QUEEN, ColorEnum.WHITE);
        playerPieces.add(queenPiece);
        game.getCell2PieceMap().put(queenCell, queenPiece);
        game.getPiece2CellMap().put(queenPiece, queenCell);
        game.getPlayer2PieceMap().put(player, playerPieces);
    }

    private void initRightWhitePieces(
            List<List<Cell>> board,
            List<PieceEnum> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player player
    ) {
        for (int i = BOARD_SIZE - 1, k = 0; i > (BOARD_SIZE - 2) / 2 && k < pieces.size(); i--, k++) {
            Piece whitePiece = new Piece(pieces.get(k), ColorEnum.WHITE);
            game.getPiece2CellMap().put(whitePiece, board.get(1).get(i));
            game.getCell2PieceMap().put(board.get(1).get(i), whitePiece);
            playerPieces.add(whitePiece);
            game.getPiece2PlayerMap().put(whitePiece, player);
        }
        Cell kingCell = board.get(0).get(((BOARD_SIZE - 2) / 2) + 2);
        Piece piece = new Piece(PieceEnum.KING, ColorEnum.WHITE);
        game.getCell2PieceMap().put(kingCell, piece);
        game.getPiece2CellMap().put(piece, kingCell);
        playerPieces.add(piece);
        game.getPlayer2PieceMap().put(player, playerPieces);
    }

    private void initLeftBlackPieces(
            List<List<Cell>> board,
            List<PieceEnum> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player player
    ) {
        for (int i = 0; i < (BOARD_SIZE - 2) / 2; i++) {
            Piece blackPiece = new Piece(pieces.get(i), ColorEnum.BLACK);
            game.getPiece2CellMap().put(blackPiece, board.get(BOARD_SIZE - 1).get(i));
            game.getCell2PieceMap().put(board.get(BOARD_SIZE - 1).get(i), blackPiece);
            playerPieces.add(blackPiece);
            game.getPiece2PlayerMap().put(blackPiece, player);
        }
        Cell queenCell = board.get(BOARD_SIZE - 1).get(((BOARD_SIZE - 2) / 2) - 1);
        Piece queenPiece = new Piece(PieceEnum.QUEEN, ColorEnum.BLACK);
        playerPieces.add(queenPiece);
        game.getCell2PieceMap().put(queenCell, queenPiece);
        game.getPiece2CellMap().put(queenPiece, queenCell);
        game.getPlayer2PieceMap().put(player, playerPieces);
    }

    private void initRightBlackPieces(
            List<List<Cell>> board,
            List<PieceEnum> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player player
    ) {
        for (int i = BOARD_SIZE - 1, k = 0; i > (BOARD_SIZE - 2) / 2 && k < pieces.size(); i--, k++) {
            Piece blackPiece = new Piece(pieces.get(k), ColorEnum.BLACK);
            game.getPiece2CellMap().put(blackPiece, board.get(BOARD_SIZE - 1).get(i));
            game.getCell2PieceMap().put(board.get(BOARD_SIZE - 1).get(i), blackPiece);
            playerPieces.add(blackPiece);
            game.getPlayer2PieceMap().put(player, playerPieces);
            game.getPiece2PlayerMap().put(blackPiece, player);
        }

        Cell kingCell = board.get(BOARD_SIZE - 1).get(((BOARD_SIZE - 2) / 2) + 2);
        Piece kingPiece = new Piece(PieceEnum.KING, ColorEnum.BLACK);
        game.getCell2PieceMap().put(kingCell, kingPiece);
        game.getPiece2CellMap().put(kingPiece, kingCell);
        playerPieces.add(kingPiece);
        game.getPlayer2PieceMap().put(player, playerPieces);
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