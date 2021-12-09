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
