package com.company;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {

    public Queen(Board board, Color color, Point placeAt) {
        super(board, color, placeAt);
    }

    @Override
    public Set<Point> potentialAttackSquares() {
        Set<Point> potentialAttackSquares = new HashSet<>(new Rook(getBoard(), getColor(), getPlaceAt()).potentialAttackSquares());

        Piece rook = new Rook(getBoard(), getColor(), getPlaceAt());
        Piece bishop = new Bishop(getBoard(), getColor(), getPlaceAt());

        potentialAttackSquares.addAll(rook.potentialAttackSquares());
        potentialAttackSquares.addAll(bishop.potentialAttackSquares());

        return potentialAttackSquares;
    }
}
