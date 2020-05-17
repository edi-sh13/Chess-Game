package com.company;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece{

    public Pawn(Board board, Color color, Point placeAt) {
        super(board, color, placeAt);
    }

    @Override
    public Set<Point> validMoves() {
        Set<Point> validMoves = new HashSet<>();

        int x = this.getPlaceAt().x;
        int y = this.getPlaceAt().y;

        if (this.getColor() == Color.WHITE) {
            addToSetIfNull(validMoves, new Point(x, y + 1));

            if (y == 1 && super.getBoard().getSquaresArray()[x][y + 1] == null) {
                addToSetIfNull(validMoves, new Point(x, y + 2));
            }
        } else {
            addToSetIfNull(validMoves, new Point(x, y - 1));

            if (y == 6 && super.getBoard().getSquaresArray()[x][y - 1] == null) {
                addToSetIfNull(validMoves, new Point(x, y - 2));
            }
        }

        return validMoves;
    }

    @Override
    public Set<Point> potentialAttackSquares() {
        Set<Point> potentialAttackSquares = new HashSet<>();

        int x = this.getPlaceAt().x;
        int y = this.getPlaceAt().y;
        int direction = (this.getColor() == Color.WHITE) ? 1 : -1;

        if (isPotentialAttackSquare(new Point(x - 1, y + direction))) {
            potentialAttackSquares.add(new Point(x - 1, y + direction));
        }

        if (isPotentialAttackSquare(new Point(x + 1, y + direction))) {
            potentialAttackSquares.add(new Point(x + 1, y + direction));
        }

        return potentialAttackSquares;
    }
}