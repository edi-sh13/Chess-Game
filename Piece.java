package com.company;

import java.awt.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Piece {
    private Board board;
    private Color color;
    private Point placeAt;

    public Piece(Board board, Color color, Point placeAt) {
        this.board = board;
        this.color = color;
        this.placeAt = placeAt;
    }

    public abstract Set<Point> potentialAttackSquares();

    public Set<Point> validMoves() {
        Set<Point> validMoves = new HashSet<>();

        for (Point validPoint: potentialAttackSquares()) {
            if (validPoint != null && this.getBoard().getSquaresArray()[validPoint.x][validPoint.y] == null) {
                validMoves.add(validPoint);
            }
        }

        return validMoves;
    }

    public Set<Point> attackSquares() {
        Set<Point> attackSquares = new HashSet<>(potentialAttackSquares());

        for (Point point: potentialAttackSquares()) {
            if (point != null && board.getSquaresArray()[point.x][point.y] == null) {
                attackSquares.remove(point);
            }
        }

        return attackSquares;
    }

    public Set<Point> captureFreeMove() {
        Set<Point> captureFreeMoves = new HashSet<>(validMoves());

        this.board.getSquaresArray()[placeAt.x][placeAt.y] = null;

        for (Piece p: opponentPieceSet().getPieces()) {
            if (p.potentialAttackSquares() != null) {
                captureFreeMoves.removeAll(p.potentialAttackSquares());
            }
        }

        this.board.getSquaresArray()[placeAt.x][placeAt.y] = this;

        return captureFreeMoves;
    }

    public boolean toBeCaptured() {
        for (Piece piece: opponentPieceSet().getPieces()) {
            for(Point point: piece.attackSquares()) {
                if (point != null && point.equals(this.getPlaceAt())) {
                    return true;
                }
            }
        }

        return false;
    }

    private PieceSet opponentPieceSet() {
        PieceSet opponentPieceSet;

        if (this.color.equals(Color.WHITE)) {
            opponentPieceSet = board.getBlackPieceSet();
        } else {
            opponentPieceSet = board.getWhitePieceSet();
        }

        return opponentPieceSet;
    }

    public void movePiece(Point point) {
        if (validMoves().contains(point) || attackSquares().contains(point)) {
            board.getSquaresArray()[placeAt.x][placeAt.y] = null;

            opponentPieceSet().getPieces().remove(board.getSquaresArray()[point.x][point.y]);
            board.getGame().setTurn((this.color.equals(Color.WHITE) ? Color.BLACK : Color.WHITE));

            board.getSquaresArray()[point.x][point.y] = this;

            placeAt = point;

            Game.incrementPlayedMoves();
        }
    }

    public void addToSetIfNull(Set<Point> pointSet, Point point) {
        if (Board.isPointInBoard(point) && board.getSquaresArray()[point.x][point.y] == null) {
            pointSet.add(point);
        }

    }

    public boolean isPotentialAttackSquare(Point point) {
        return Board.isPointInBoard(point)
                && (this.getBoard().getSquaresArray()[point.x][point.y] == null
                || !this.getBoard().getSquaresArray()[point.x][point.y].getColor().equals(this.getColor()));
    }

    public Set<Point> addLineOfSquares(int a, int b) {
        Set<Point> lineOfSquares = new HashSet<>();

        int x = this.getPlaceAt().x;
        int y = this.getPlaceAt().y;

        for (int i = 1; i < 8; i++) {
            Point potentialPoint = new Point(x + a * i, y + b * i);

            if (isPotentialAttackSquare(potentialPoint)) {
                lineOfSquares.add(potentialPoint);
                if (getBoard().getSquaresArray()[potentialPoint.x][potentialPoint.y] != null) {
                    break;
                }
            } else {
                break;
            }
        }

        return lineOfSquares;
    }

    public Board getBoard() {
        return board;
    }

    public Color getColor() {
        return color;
    }

    public Point getPlaceAt() {
        return placeAt;
    }

    public void setPlaceAt(Point placeAt) {
        this.placeAt = placeAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return Objects.equals(color, piece.color) &&
                Objects.equals(placeAt, piece.placeAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, placeAt);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "color=" + color +
                ", placeAt=" + placeAt +
                '}';
    }
}
