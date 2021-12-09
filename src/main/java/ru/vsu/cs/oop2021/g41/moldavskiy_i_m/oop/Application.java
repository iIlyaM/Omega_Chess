package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Cell;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Game;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Piece;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Player;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.PieceEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.*;

import java.util.*;

public class Application {

    public static void main(String[] args) {
//        Game game = new Game();
//        Player player1 = new Player("Василий");
//        Player player2 = new Player("Геннадий");
//        //GameService gs = new GameService();
//        List<List<Cell>> board = gs.initBoard();
//        gs.initPieces(board, game, player1, player2);
//        PawnPieceService pawnPieceService = new PawnPieceService();
//        KnightPieceService knightPieceService = new KnightPieceService();
//        BishopPieceService bishopPieceService = new BishopPieceService();
//        RookPieceService rookPieceService = new RookPieceService();
//        ChampionPieceService championPieceService = new ChampionPieceService();
//        KingPieceService kingPieceService = new KingPieceService();
//        QueenPieceService queenPieceService = new QueenPieceService();
//        WizardPieceService wizardPieceService = new WizardPieceService();
//        Set<Piece> pieces = game.getPlayer2PieceMap().get(player1);
//        List<Piece> pieces1 = new ArrayList<>(pieces);
//        List<Cell> testList = wizardPieceService.getPossibleMoves(game, pieces1.get(22));
        Map<PieceEnum, IPieceService> piece2ServiceMap = new HashMap<>();
        piece2ServiceMap.put(PieceEnum.PAWN, new PawnPieceService());
        piece2ServiceMap.put(PieceEnum.ROOK, new RookPieceService());
        piece2ServiceMap.put(PieceEnum.KNIGHT, new KnightPieceService());
        piece2ServiceMap.put(PieceEnum.BISHOP, new BishopPieceService());
        piece2ServiceMap.put(PieceEnum.QUEEN, new QueenPieceService());
        piece2ServiceMap.put(PieceEnum.KING, new KingPieceService());
        piece2ServiceMap.put(PieceEnum.CHAMPION, new ChampionPieceService());
        piece2ServiceMap.put(PieceEnum.WIZARD, new WizardPieceService());
        GameService gameService = new GameService(piece2ServiceMap);
        Game newGame = gameService.initGame();
        gameService.startGameProcess(newGame);
        gameService.printGameResult(newGame);
    }
}
