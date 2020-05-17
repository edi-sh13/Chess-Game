package com.company;

import java.awt.*;

public class Board {
    private Piece[][] squaresArray;
    private PieceSet whitePieceSet;
    private PieceSet blackPieceSet;
    private Game game;

    public Board() {
        game = new Game(this);
        whitePieceSet = new PieceSet(this, Color.WHITE);
        blackPieceSet = new PieceSet(this, Color.BLACK);

        initializeSquaresArray();
    }

    private void initializeSquaresArray() {
        squaresArray = new Piece[8][8];

        putPiecesOnBoard(whitePieceSet);
        putPiecesOnBoard(blackPieceSet);
    }

    private void putPiecesOnBoard(PieceSet pieceSet) {
        for (Piece piece: pieceSet.getPieces()) {
            squaresArray[piece.getPlaceAt().x][piece.getPlaceAt().y] = piece;
        }
    }

    public Piece[][] getSquaresArray() {
        return squaresArray;
    }

    public PieceSet getWhitePieceSet() {
        return whitePieceSet;
    }

    public PieceSet getBlackPieceSet() {
        return blackPieceSet;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("White Pieces\n");
        for (Piece piece: whitePieceSet.getPieces()) {
            result.append(piece).append("\n");
        }

        result.append("\n\nBlack Pieces\n");
        for (Piece piece: blackPieceSet.getPieces()) {
            result.append(piece).append("\n");
        }

        return result.toString();
    }

    public static boolean isPointInBoard(Point point) {
        return (point.x >= 0 && point.x <=7) && (point.y >= 0 && point.y <=7);
    }
}
