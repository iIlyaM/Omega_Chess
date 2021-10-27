package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.Cell;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.ColorEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameService {

    public static final int BOARD_SIZE = 10;
//todo map классов, с ходами
//todo класс стэп, начальная позиция, куда ходил, кого убил(не убил)

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
                currCell = new Cell(cellColors.get(j % 2));
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
        return rows;
    }

    private void initWizardsCells(List<List<Cell>> board) {
        board.get(1).get(0).getNeighbors().put(DirectionEnum.NORTH_WEST, new Cell(board.get(1).get(0).getColor()));
        //
    }
}
