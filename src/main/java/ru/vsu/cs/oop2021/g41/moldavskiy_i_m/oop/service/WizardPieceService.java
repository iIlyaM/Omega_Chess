package ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service;

import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.*;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.model.enums.DirectionEnum;
import ru.vsu.cs.oop2021.g41.moldavskiy_i_m.oop.service.serviceutils.CheckMovesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WizardPieceService implements IPieceService {
    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<DirectionEnum> mainDirectionsList =
                Arrays.asList(DirectionEnum.NORTH, DirectionEnum.EAST,
                        DirectionEnum.SOUTH, DirectionEnum.WEST, DirectionEnum.NORTH);

        List<DirectionEnum> adjacentDirectionsList =
                Arrays.asList(DirectionEnum.NORTH_EAST, DirectionEnum.SOUTH_EAST,
                        DirectionEnum.SOUTH_WEST, DirectionEnum.NORTH_WEST);
        return new ArrayList<>(findWizardMoves(game, piece, mainDirectionsList, adjacentDirectionsList));
    }

    private List<Cell> findWizardMoves(
            Game game,
            Piece piece,
            List<DirectionEnum> mainDirections,
            List<DirectionEnum> adjacentDirections
    ) {
        List<Cell> possibleMoves = new ArrayList<>();
        DirectionEnum direction;
        Cell receivedCell = game.getPiece2CellMap().get(piece);
        Cell currentCell;
        Cell nextCell;
        Cell perpendicularCurrCell;
        Cell perpendicularNextCell;
        int counter = 0;


        for (int i = 0; i < adjacentDirections.size(); i++) {
            direction = adjacentDirections.get(i);
            currentCell = receivedCell;
            perpendicularCurrCell = receivedCell;
            nextCell = currentCell.getNeighbors().get(direction);
            perpendicularNextCell = perpendicularCurrCell.getNeighbors().get(direction);
            if ((nextCell != null) && (perpendicularNextCell != null)) {
                if (isMoveAvailable(game, piece, nextCell)) {
                    possibleMoves.add(nextCell);
                }

                for (int j = 0; j < 2; j++) {
                    if (nextCell == null) {
                        break;
                    }
                    currentCell = nextCell;
                    nextCell = currentCell.getNeighbors().get(mainDirections.get(counter));
                }

                if (isMoveAvailable(game, piece, nextCell)) {
                    possibleMoves.add(nextCell);
                }

                for (int k = 0; k < 2; k++) {
                    if (perpendicularNextCell == null) {
                        break;
                    }
                    perpendicularCurrCell = perpendicularNextCell;
                    perpendicularNextCell = perpendicularCurrCell.getNeighbors().get(mainDirections.get(counter + 1));
                }
                if (isMoveAvailable(game, piece, perpendicularNextCell)) {
                    possibleMoves.add(perpendicularNextCell);
                }
                counter++;
            }
        }
        return possibleMoves;
    }


    @Override
    public Step makeMove(Game game, Piece piece, Cell targetCell) {
        Step wizardStep = new Step();
        Cell currPosition = game.getPiece2CellMap().get(piece);

        wizardStep.setPlayer(game.getPiece2PlayerMap().get(piece));
        wizardStep.setStartCell(currPosition);
        wizardStep.setEndCell(targetCell);
        wizardStep.setPiece(piece);
        if (isTargetCellNotEmpty(game, targetCell)) {
            wizardStep.setKilledPiece(game.getCell2PieceMap().get(targetCell));
        }
        game.getSteps().add(wizardStep);
        changeOnBoardPlacement(game, piece, targetCell, currPosition);
        return wizardStep;
    }

    private void changeOnBoardPlacement(Game game, Piece piece, Cell targetCell, Cell currPosition) {
        Player rival;
        Piece targetPiece;
        if (isTargetCellNotEmpty(game, targetCell)) {
            targetPiece = game.getCell2PieceMap().get(targetCell);
            rival = game.getPiece2PlayerMap().get(targetPiece);
            game.getPlayer2PieceMap().get(rival).remove(targetPiece);
        }
        game.getPiece2CellMap().put(piece, targetCell);
        game.getCell2PieceMap().put(targetCell, piece);
        game.getCell2PieceMap().remove(currPosition, piece);
    }

    private boolean isMoveAvailable(Game game, Piece piece, Cell testedCell) {
        if (testedCell != null) {
            return ((game.getCell2PieceMap().get(testedCell) == null) ||
                    ((game.getCell2PieceMap().get(testedCell) != null) &&
                            (game.getCell2PieceMap().get(testedCell).getPieceColor() != piece.getPieceColor())));
        }
        return false;
    }

    private boolean isTargetCellNotEmpty(Game game, Cell targetCell) {
        return game.getCell2PieceMap().get(targetCell) != null;
    }
}
