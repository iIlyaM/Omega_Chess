package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model;

import java.util.*;

public class Game {

    private Map<Piece, Cell> piece2CellMap = new LinkedHashMap<>();
    private Map<Cell, Piece> cell2PieceMap = new HashMap();
    private Map<Player, Set<Piece>> player2PieceMap = new HashMap();
    private Map<Piece, Player> piece2PlayerMap = new LinkedHashMap<>();
    private Map<Cell, String> cell2StringMap = new HashMap<>();
    private Map<String, Cell> stringCellMap = new HashMap<>();
    private List<Step> steps = new ArrayList<>();

    public Map<Piece, Cell> getPiece2CellMap() {
        return piece2CellMap;
    }

    public void setPiece2CellMap(Map<Piece, Cell> piece2CellMap) {
        this.piece2CellMap = piece2CellMap;
    }

    public Map<Cell, Piece> getCell2PieceMap() {
        return cell2PieceMap;
    }

    public void setCell2PieceMap(Map<Cell, Piece> cell2PieceMap) {
        this.cell2PieceMap = cell2PieceMap;
    }

    public Map<Player, Set<Piece>> getPlayer2PieceMap() {
        return player2PieceMap;
    }

    public void setPlayer2PieceMap(Map<Player, Set<Piece>> player2PieceMap) {
        this.player2PieceMap = player2PieceMap;
    }

    public Map<Piece, Player> getPiece2PlayerMap() {
        return piece2PlayerMap;
    }

    public void setPiece2PlayerMap(Map<Piece, Player> piece2PlayerMap) {
        this.piece2PlayerMap = piece2PlayerMap;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Player> getPlayers(Map<Player,Set<Piece>> player2PieceMap) {
        List<Player> players = new ArrayList<>();

        player2PieceMap.forEach((key, value) -> players.add(key));
        return players;
    }

    public Map<Cell, String> getCell2StringMap() {
        return cell2StringMap;
    }

    public void setCell2StringMap(Map<Cell, String> cell2StringMap) {
        this.cell2StringMap = cell2StringMap;
    }

    public Map<String, Cell> getStringCellMap() {
        return stringCellMap;
    }

    public void setStringCellMap(Map<String, Cell> stringCellMap) {
        this.stringCellMap = stringCellMap;
    }
}
