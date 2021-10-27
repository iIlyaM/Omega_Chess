package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.GameService;

public class Main {

    public static void main(String[] args) {
        GameService gs = new GameService();
        gs.initBoard();
    }
}
