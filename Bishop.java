package com.company;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {

    public Bishop(Board board, Color color, Point placeAt) {
        super(board, color, placeAt);
    }

    @Override
    public Set<Point> potentialAttackSquares() {
        Set<Point> potentialAttackSquares = new HashSet<>();

        // add squares above and to the right of the piece
        potentialAttackSquares.addAll(addLineOfSquares(1, 1));

        // add squares below and to the right of the piece
        potentialAttackSquares.addAll(addLineOfSquares(1, -1));

        // add squares below and to the left of the piece
        potentialAttackSquares.addAll(addLineOfSquares(-1, -1));

        // add squares above and to the left of the piece
        potentialAttackSquares.addAll(addLineOfSquares(-1, 1));

        return potentialAttackSquares;
    }
}
