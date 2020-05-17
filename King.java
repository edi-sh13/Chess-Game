package com.company;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class King extends Piece {

    public King(Board board, Color color, Point placeAt) {
        super(board, color, placeAt);
    }

    @Override
    public Set<Point> potentialAttackSquares() {
        Set<Point> potentialAttackSquares = new HashSet<>();

        int x = this.getPlaceAt().x;
        int y = this.getPlaceAt().y;

        Point[] potentialBoardPoints = new Point[]{new Point(x + 1, y + 1), new Point(x + 1, y),
                new Point(x + 1, y - 1), new Point(x, y - 1),
                new Point(x - 1, y - 1), new Point(x - 1, y),
                new Point(x - 1, y + 1), new Point(x , y + 1)};

        for (Point point: potentialBoardPoints) {
            if (Board.isPointInBoard(point)
                    && (getBoard().getSquaresArray()[point.x][point.y] == null
                    || !this.getColor().equals(getBoard().getSquaresArray()[point.x][point.y].getColor()))) {
                potentialAttackSquares.add(point);
            }
        }

        return potentialAttackSquares;
    }

    @Override
    public Set<Point> attackSquares() {
        Set<Point> attackSquares = new HashSet<>();

        for (Point attackPoint: super.attackSquares()) {
            // save the reference to the piece that actually belongs in that place in the board
            // save the reference to the kings old place
            Piece actualPiece = getBoard().getSquaresArray()[attackPoint.x][attackPoint.y];
            Point kingsOldPlace = getPlaceAt();

            // change the pointer of the squares array in (x, y) to point at the king
            // then set the kings place to where the actual piece was
            getBoard().getSquaresArray()[attackPoint.x][attackPoint.y] = this;
            this.setPlaceAt(actualPiece.getPlaceAt());

            // if the king in the new place is not at risk to be captured
            // then add this point to the attack squares set
            if (!this.toBeCaptured()) {
                attackSquares.add(actualPiece.getPlaceAt());
            }

            // then set the board and the king back to normal
            getBoard().getSquaresArray()[attackPoint.x][attackPoint.y] = actualPiece;
            this.setPlaceAt(kingsOldPlace);
        }

        return attackSquares;
    }
}
