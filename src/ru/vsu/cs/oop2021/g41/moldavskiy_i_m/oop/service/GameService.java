package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Cell;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.ColorEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameService {

    public static final int BOARD_SIZE = 12;
//todo map классов, с ходами
//todo класс стэп, начальная позиция, куда ходил, кого убил(не убил)

    public void initBoard() {
        List<ColorEnum> cellColors = Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE);

        Cell prevCell;
        Cell currCell;

        List<Cell> prevRow = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            prevCell = null;
            List<Cell> currRow = new ArrayList<>();
            for (int j = 0; j < BOARD_SIZE; j++) {
                currCell = new Cell(cellColors.get(i % 2));
                if (!isCellNull(prevCell)) {
                    currCell.getNeighbors().put(DirectionEnum.WEST, prevCell);
                    prevCell.getNeighbors().put(DirectionEnum.EAST, currCell);
                }
                currRow.add(currCell);
                prevCell = currCell;
                if (!isCellNull(prevCell)) {
                    for (int k = 0; k < BOARD_SIZE; k++) {
                        Cell currRowCell = currRow.get(k);
                        Cell prevRowCell = prevRow.get(k);
                        currRowCell.getNeighbors().put(DirectionEnum.SOUTH, prevRowCell);
//prevRowCell.getNeighbours().put(DirectionEnum.NORTH, currRowCell);
                    }
                }
                prevRow = currRow;
            }
        }
    }

    private static boolean isCellNull(Cell cell) {
        return cell == null;
    }
}
