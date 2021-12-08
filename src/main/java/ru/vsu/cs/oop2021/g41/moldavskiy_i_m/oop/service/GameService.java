package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.*;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.ColorEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.PieceEnum;

import java.security.cert.PKIXCertPathChecker;
import java.util.*;

public class GameService {

    public static final int BOARD_SIZE = 10;

    public List<List<Cell>> initBoard() {
        List<List<Cell>> rows = new ArrayList<>();

        List<ColorEnum> cellColors = Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE);

        Cell prevCell;
        Cell currCell;
        Cell leftNorthDiagonalCell;
        Cell rightNorthDiagonalCell;
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
                        if(k > 0) {
                            leftNorthDiagonalCell = prevRow.get(k - 1);
                            currRowCell.getNeighbors().put(DirectionEnum.NORTH_WEST, leftNorthDiagonalCell);
                            leftNorthDiagonalCell.getNeighbors().put(DirectionEnum.SOUTH_EAST, currRowCell);
                        }
                        if(k < currRow.size() - 1) {
                            rightNorthDiagonalCell = prevRow.get(k + 1);
                            currRowCell.getNeighbors().put(DirectionEnum.NORTH_EAST, rightNorthDiagonalCell);
                            rightNorthDiagonalCell.getNeighbors().put(DirectionEnum.SOUTH_WEST, currRowCell);
                        }
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
            game.getPiece2CellMap().put(pawnPiece, board.get(8).get(i));
            game.getCell2PieceMap().put(board.get(8).get(i), pawnPiece);
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
            game.getPiece2CellMap().put(pawnPiece, board.get(1).get(i));
            game.getCell2PieceMap().put(board.get(1).get(i), pawnPiece);
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
            game.getPiece2CellMap().put(whitePiece, board.get(9).get(i));
            game.getCell2PieceMap().put(board.get(9).get(i), whitePiece);
            playerPieces.add(whitePiece);
            game.getPlayer2PieceMap().put(player, playerPieces);
            game.getPiece2PlayerMap().put(whitePiece, player);
        }
        Cell queenCell = board.get(9).get(((BOARD_SIZE - 2) / 2));
        Piece queenPiece = new Piece(PieceEnum.QUEEN, ColorEnum.WHITE);

        Cell wizardCell = board.get(BOARD_SIZE - 1).get(0).getNeighbors().get(DirectionEnum.SOUTH_WEST);
        Piece wizardPiece = new Piece(PieceEnum.WIZARD, ColorEnum.WHITE);

        initSpecialPiece(game, queenPiece, queenCell, playerPieces, player);
        initSpecialPiece(game, wizardPiece, wizardCell, playerPieces, player);
//        playerPieces.add(queenPiece);
//        game.getCell2PieceMap().put(queenCell, queenPiece);
//        game.getPiece2CellMap().put(queenPiece, queenCell);
//        game.getPlayer2PieceMap().put(player, playerPieces);
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
            game.getPiece2CellMap().put(whitePiece, board.get(BOARD_SIZE - 1).get(i));
            game.getCell2PieceMap().put(board.get(BOARD_SIZE - 1).get(i), whitePiece);
            playerPieces.add(whitePiece);
            game.getPiece2PlayerMap().put(whitePiece, player);
        }
        Cell kingCell = board.get(BOARD_SIZE - 1).get(((BOARD_SIZE - 2) / 2) + 1);
        Piece piece = new Piece(PieceEnum.KING, ColorEnum.WHITE);

        Cell wizardCell = board.get(BOARD_SIZE - 1).get(BOARD_SIZE - 1).getNeighbors().get(DirectionEnum.SOUTH_EAST);
        Piece wizardPiece = new Piece(PieceEnum.WIZARD, ColorEnum.WHITE);

        initSpecialPiece(game, piece, kingCell, playerPieces, player);
        initSpecialPiece(game, wizardPiece, wizardCell, playerPieces, player);
//        game.getCell2PieceMap().put(kingCell, piece);
//        game.getPiece2CellMap().put(piece, kingCell);
//        playerPieces.add(piece);
//        game.getPlayer2PieceMap().put(player, playerPieces);


        /**
         * Для теста :
         */

//        Cell testCell = board.get(5).get(7);
//        Piece testBishop = new Piece(PieceEnum.BISHOP, ColorEnum.WHITE);
//        playerPieces.add(testBishop);
//        game.getCell2PieceMap().put(testCell, testBishop);
//        game.getPiece2CellMap().put(testBishop, testCell);
//        game.getPlayer2PieceMap().put(player, playerPieces);
//        Cell testCell = board.get(5).get(7);
//        Piece testBishop = new Piece(PieceEnum.QUEEN, ColorEnum.WHITE);
//        playerPieces.add(testBishop);
//        game.getCell2PieceMap().put(testCell, testBishop);
//        game.getPiece2CellMap().put(testBishop, testCell);
//        game.getPlayer2PieceMap().put(player, playerPieces);
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
            game.getPiece2CellMap().put(blackPiece, board.get(0).get(i));
            game.getCell2PieceMap().put(board.get(0).get(i), blackPiece);
            playerPieces.add(blackPiece);
            game.getPiece2PlayerMap().put(blackPiece, player);
        }
        Cell queenCell = board.get(0).get(((BOARD_SIZE - 2) / 2));
        Piece queenPiece = new Piece(PieceEnum.QUEEN, ColorEnum.BLACK);

        Cell wizardCell = board.get(0).get(0).getNeighbors().get(DirectionEnum.NORTH_WEST);
        Piece wizardPiece = new Piece(PieceEnum.WIZARD, ColorEnum.BLACK);

        initSpecialPiece(game, queenPiece, queenCell, playerPieces, player);
        initSpecialPiece(game, wizardPiece, wizardCell, playerPieces, player);
//        playerPieces.add(queenPiece);
//        game.getCell2PieceMap().put(queenCell, queenPiece);
//        game.getPiece2CellMap().put(queenPiece, queenCell);
//        game.getPlayer2PieceMap().put(player, playerPieces);

        /**
         * Для теста :
         */
//        Cell testCell = board.get(4).get(3);
//        Piece testBishop = new Piece(PieceEnum.BISHOP, ColorEnum.BLACK);
//        playerPieces.add(testBishop);
//        game.getCell2PieceMap().put(testCell, testBishop);
//        game.getPiece2CellMap().put(testBishop, testCell);
//        game.getPlayer2PieceMap().put(player, playerPieces);

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
            game.getPiece2CellMap().put(blackPiece, board.get(0).get(i));
            game.getCell2PieceMap().put(board.get(0).get(i), blackPiece);
            playerPieces.add(blackPiece);
            game.getPlayer2PieceMap().put(player, playerPieces);
            game.getPiece2PlayerMap().put(blackPiece, player);
        }

        Cell kingCell = board.get(0).get(((BOARD_SIZE - 2) / 2) + 1);
        Piece kingPiece = new Piece(PieceEnum.KING, ColorEnum.BLACK);

        Cell wizardCell = board.get(0).get(BOARD_SIZE - 1).getNeighbors().get(DirectionEnum.NORTH_EAST);
        Piece wizardPiece = new Piece(PieceEnum.WIZARD, ColorEnum.BLACK);

        initSpecialPiece(game, kingPiece, kingCell, playerPieces, player);
        initSpecialPiece(game, wizardPiece, wizardCell, playerPieces, player);

//        game.getCell2PieceMap().put(kingCell, kingPiece);
//        game.getPiece2CellMap().put(kingPiece, kingCell);
//        playerPieces.add(kingPiece);
//        game.getPlayer2PieceMap().put(player, playerPieces);
    }

    private void initWizardsCells(List<List<Cell>> board) {
//        Cell northWestCell = board.get(0).get(0);
//        Cell northEastCell = board.get(0).get(board.size() - 1);
//        Cell southWestCell = board.get(board.size() - 1).get(0);
//        Cell southEastCell = board.get(board.size() - 1).get(board.size() - 1);
        Cell borderCell;
        Cell newWizardCell;
        int[] borderIndexes = {0, board.size() - 1};

        for (int i = 0; i < borderIndexes.length; i++) {
            for (int j = 0; j <borderIndexes.length ; j++) {
                borderCell = board.get(borderIndexes[i]).get(borderIndexes[j]);
                if(borderIndexes[i] < borderIndexes[j]) {
                    newWizardCell = new Cell(borderCell.getColor());
                    borderCell.getNeighbors().put(DirectionEnum.NORTH_EAST, newWizardCell);
                    newWizardCell.getNeighbors().put(DirectionEnum.SOUTH_WEST, borderCell);
                }
                if(borderIndexes[i] > borderIndexes[j]) {
                    newWizardCell = new Cell(borderCell.getColor());
                    borderCell.getNeighbors().put(DirectionEnum.SOUTH_WEST, newWizardCell);
                    newWizardCell.getNeighbors().put(DirectionEnum.NORTH_EAST, borderCell);
                }

                if((borderIndexes[i] == 0) && (borderIndexes[i] == borderIndexes[j])) {
                    newWizardCell = new Cell(borderCell.getColor());
                    borderCell.getNeighbors().put(DirectionEnum.NORTH_WEST, newWizardCell);
                    newWizardCell.getNeighbors().put(DirectionEnum.SOUTH_EAST, borderCell);
                }
                if((borderIndexes[i] == 9) && (borderIndexes[i] == borderIndexes[j])) {
                    newWizardCell = new Cell(borderCell.getColor());
                    borderCell.getNeighbors().put(DirectionEnum.SOUTH_EAST, newWizardCell);
                    newWizardCell.getNeighbors().put(DirectionEnum.NORTH_WEST, borderCell);
                }
            }
        }
    }

    private void initSpecialPiece(Game game, Piece piece, Cell cell, Set<Piece> pieces, Player player) {
        game.getPiece2CellMap().put(piece, cell);
        game.getCell2PieceMap().put(cell, piece);
        pieces.add(piece);
        game.getPlayer2PieceMap().put(player, pieces);
        game.getPiece2PlayerMap().put(piece, player);
    }
 }
