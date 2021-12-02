package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Cell;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Game;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Piece;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Player;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.GameService;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.PawnPieceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        Player player1 = new Player("Василий");
        Player player2 = new Player("Геннадий");
        GameService gs = new GameService();
        List<List<Cell>> board = gs.initBoard();
        gs.initPieces(board, game, player1, player2);
        PawnPieceService pawnPieceService = new PawnPieceService();
        Set<Piece> pieces = game.getPlayer2PieceMap().get(player2);
        List<Piece> pieces1 = new ArrayList<>(pieces);
        Cell testCell = game.getPiece2CellMap().get(pieces1.get(0));
        List<Cell> testList = pawnPieceService.getPossibleMoves(game, pieces1.get(0));

//        for (int i = 0; i < board.size(); i++) {
//            for (int j = 0; j < board.size(); j++) {
//                if(game.getCell2PieceMap().get(board.get(i).get(j)).getPieceType() != null) {
//                    System.out.println(i + " " + j + " " + "фигура - " + game.getCell2PieceMap().get(board.get(i).get(j)).getPieceType().name());
//                } else System.out.println(i + " " + j);
//            }
//        }

//        gs.initWhitePawns(board, game, player1);
//        gs.initBlackPawns(board, game, player2);
    }
}
