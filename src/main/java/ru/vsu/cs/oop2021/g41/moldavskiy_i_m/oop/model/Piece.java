package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.ColorEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.PieceEnum;

import java.util.Objects;

public class Piece {

    private PieceEnum pieceType;

    private ColorEnum pieceColor;

    public Piece(PieceEnum pieceType, ColorEnum pieceColor) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
    }

    public PieceEnum getPieceType() {
        return pieceType;
    }

    public ColorEnum getPieceColor() {
        return pieceColor;
    }

}
