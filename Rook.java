package com.company;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {

    public Rook(Board board, Color color, Point placeAt) {
        super(board, color, placeAt);
    }

    @Override
    public Set<Point> potentialAttackSquares() {
        Set<Point> potentialAttackSquares = new HashSet<>();

        // add squares above the piece
        potentialAttackSquares.addAll(addLineOfSquares(0, 1));

        // add squares below the piece
        potentialAttackSquares.addAll(addLineOfSquares(0, -1));

        // add squares to the left of the piece
        potentialAttackSquares.addAll(addLineOfSquares(-1, 0));

        // add squares to the right of the piece
        potentialAttackSquares.addAll(addLineOfSquares(1, 0));

        return potentialAttackSquares;
    }
}