package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PieceSet {
    private List<Piece> pieces;
    private Board board;
    private Color color;

    public PieceSet(Board board, Color color) {
        this.board = board;
        this.color = color;

        initializePieces();
    }

    private void initializePieces() {
        this.pieces = new ArrayList<>();

        // change yPosition for the other pieces
        int yPosition = (this.color == Color.WHITE) ? 0 : 7;

        // initialize the king
        this.pieces.add(new King(board, this.color, new Point(4, yPosition)));

        // initialize the queen
        this.pieces.add(new Queen(board, this.color, new Point(3, yPosition)));

        // initialize the bishops
        this.pieces.add(new Bishop(board, this.color, new Point(2, yPosition)));
        this.pieces.add(new Bishop(board, this.color, new Point(5, yPosition)));

        // initialize the knights
        this.pieces.add(new Knight(board, this.color, new Point(1, yPosition)));
        this.pieces.add(new Knight(board, this.color, new Point(6, yPosition)));

        // initialize the rooks
        this.pieces.add(new Rook(board, this.color, new Point(0, yPosition)));
        this.pieces.add(new Rook(board, this.color, new Point(7, yPosition)));


        // initialize yPosition for the pawns
        yPosition = (this.color == Color.WHITE) ? 1 : 6;

        // initialize pawns
        for (int i = 0; i < 8; i++) {
            Piece pawn = new Pawn(board, this.color, new Point(i, yPosition));
            this.pieces.add(pawn);
        }
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public Color getColor() {
        return color;
    }
}
