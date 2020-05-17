package com.company;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {

    public Knight(Board board, Color color, Point placeAt) {
        super(board, color, placeAt);
    }

    @Override
    public Set<Point> potentialAttackSquares() {
        Set<Point> potentialAttackSquares = new HashSet<>();

        int x = this.getPlaceAt().x;
        int y = this.getPlaceAt().y;

        Point[] potentialBoardPoints = new Point[]{new Point(x + 1, y + 2), new Point(x + 2, y + 1),
                                                    new Point(x + 2, y - 1), new Point(x + 1, y - 2),
                                                    new Point(x - 1, y - 2), new Point(x - 2, y - 1),
                                                    new Point(x - 2, y + 1), new Point(x - 1, y + 2)};

        for (Point point: potentialBoardPoints) {
            if (Board.isPointInBoard(point)
                    && (getBoard().getSquaresArray()[point.x][point.y] == null
                    || !this.getColor().equals(getBoard().getSquaresArray()[point.x][point.y].getColor()))) {
                potentialAttackSquares.add(point);
            }
        }

        return potentialAttackSquares;
    }
}
