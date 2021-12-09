package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.ColorEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Cell {
    private final ColorEnum color;
    private String consoleCoordinates;
    private final Map<DirectionEnum, Cell> neighbors = new LinkedHashMap<>();

    public Cell(ColorEnum color, String consoleCoordinates) {
        this.color = color;
        this.consoleCoordinates = consoleCoordinates;
    }

    public ColorEnum getColor() {
        return color;
    }

    public Map<DirectionEnum, Cell> getNeighbors() {
        return neighbors;
    }

    public String getConsoleCoordinates() {
        return consoleCoordinates;
    }

}
