package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.serviceutils;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Cell;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Game;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Piece;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.PieceEnum;

import java.util.Set;

public class CheckMovesUtils {

//    public static boolean isMoveAvailable(Game game, Piece piece, Cell testedCell) {
//        if (testedCell != null) {
//            return ((game.getCell2PieceMap().get(testedCell) == null) ||
//                    ((game.getCell2PieceMap().get(testedCell) != null) &&
//                            (game.getCell2PieceMap().get(testedCell).getPieceColor() != piece.getPieceColor())));
//        }
//        return false;
//    }
//
//    public static boolean checkEnemyPieceCell(Game game,Piece piece, Cell testedCell) {
//        return (game.getCell2PieceMap().get(testedCell) != null) &&
//                (game.getCell2PieceMap().get(testedCell).getPieceColor() != piece.getPieceColor());
//    }
//
//    public static boolean isTargetCellNotEmpty(Game game, Cell targetCell) {
//        return game.getCell2PieceMap().get(targetCell) != null;
//    }

    public static boolean isKingAlive(Set<Piece> pieces) {
        for (Piece piece: pieces) {
            if(piece.getPieceType().equals(PieceEnum.KING)) {
                return true;
            }
        }
        return false;
    }
}
