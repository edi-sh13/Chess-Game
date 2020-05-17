package com.company;

import java.awt.*;

public class Game {
    private final Board board;
    private static int playedMoves;
    private Color turn;

    public Game(Board board) {
        this.board = board;
        playedMoves = 0;
        this.turn = Color.WHITE;
    }

    public static int getPlayedMoves() {
        return playedMoves;
    }

    public static void incrementPlayedMoves() {
        playedMoves++;
    }

    public Color getTurn() {
        return turn;
    }

    public void setTurn(Color turn) {
        this.turn = turn;
    }

    public Color getResult() {
        Color result = null;

        if (board.getWhitePieceSet().getPieces().get(0).toBeCaptured()
                && board.getWhitePieceSet().getPieces().get(0).captureFreeMove().isEmpty()) {
            result = Color.BLACK;
        } else if (board.getBlackPieceSet().getPieces().get(0).toBeCaptured()
                && board.getBlackPieceSet().getPieces().get(0).captureFreeMove().isEmpty()) {
            result = Color.WHITE;
        }

        return result;
    }

    public Color getCheckStatus() {
        Color checkStatus = null;

        if (this.board.getWhitePieceSet().getPieces().get(0).toBeCaptured()) {
            checkStatus = Color.WHITE;
        } else if (this.board.getBlackPieceSet().getPieces().get(0).toBeCaptured()) {
            checkStatus = Color.BLACK;
        }

        return checkStatus;
    }
}
