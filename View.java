package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class View {
    private Board board;
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    private final JLabel turnMessage = new JLabel(
            "White player's turn");
    private final JLabel playedMovesMessage = new JLabel(
            "Played moves: 0");
    private final JLabel checkStatusMessage = new JLabel(
            "Check status: ");
    private final JLabel resultMessage = new JLabel(
            "Game won by: ");
    private static final String COLS = "ABCDEFGH";
    private JFrame f;
    private boolean addOne = true;
    final boolean[] isAnyButtonClicked = {false};

    View(Board board) {
        this.board = board;
        initializeGui();

        f = new JFrame("Chess Game");
        f.add(this.getGui());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);

        // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        f.pack();
        // ensures the minimum size is enforced.
        f.setMinimumSize(f.getSize());
        f.setVisible(true);
    }

    public final void initializeGui() {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);

        tools.add(turnMessage);
        tools.addSeparator();
        tools.add(playedMovesMessage);
        tools.addSeparator();
        tools.add(checkStatusMessage);
        tools.addSeparator();
        tools.add(resultMessage);

        gui.add(new JLabel("?"), BorderLayout.LINE_START);


        createChessBoardSquares();
    }

    // draws all chess piece icons on the board
    private void drawPieceIcons() {

        for (Piece piece: board.getWhitePieceSet().getPieces()) {
            switch (piece.getClass().getCanonicalName()) {
                case "com.company.Pawn":
                    chessBoardSquares[piece.getPlaceAt().x][piece.getPlaceAt().y].setIcon(new ImageIcon(ChessSprites.SILVER_PAWN));
                    break;
                case "com.company.Rook":
                    chessBoardSquares[piece.getPlaceAt().x][piece.getPlaceAt().y].setIcon(new ImageIcon(ChessSprites.SILVER_ROOK));
                    break;
                case "com.company.Knight":
                    chessBoardSquares[piece.getPlaceAt().x][piece.getPlaceAt().y].setIcon(new ImageIcon(ChessSprites.SILVER_KNIGHT));
                    break;
                case "com.company.Bishop":
                    chessBoardSquares[piece.getPlaceAt().x][piece.getPlaceAt().y].setIcon(new ImageIcon(ChessSprites.SILVER_BISHOP));
                    break;
                case "com.company.Queen":
                    chessBoardSquares[piece.getPlaceAt().x][piece.getPlaceAt().y].setIcon(new ImageIcon(ChessSprites.SILVER_QUEEN));
                    break;
                case "com.company.King":
                    chessBoardSquares[piece.getPlaceAt().x][piece.getPlaceAt().y].setIcon(new ImageIcon(ChessSprites.SILVER_KING));
                    break;
            }
        }

        for (Piece piece: board.getBlackPieceSet().getPieces()) {
            switch (piece.getClass().getCanonicalName()) {
                case "com.company.Pawn":
                    chessBoardSquares[piece.getPlaceAt().x][piece.getPlaceAt().y].setIcon(new ImageIcon(ChessSprites.GOLD_PAWN));
                    break;
                case "com.company.Rook":
                    chessBoardSquares[piece.getPlaceAt().x][piece.getPlaceAt().y].setIcon(new ImageIcon(ChessSprites.GOLD_ROOK));
                    break;
                case "com.company.Knight":
                    chessBoardSquares[piece.getPlaceAt().x][piece.getPlaceAt().y].setIcon(new ImageIcon(ChessSprites.GOLD_KNIGHT));
                    break;
                case "com.company.Bishop":
                    chessBoardSquares[piece.getPlaceAt().x][piece.getPlaceAt().y].setIcon(new ImageIcon(ChessSprites.GOLD_BISHOP));
                    break;
                case "com.company.Queen":
                    chessBoardSquares[piece.getPlaceAt().x][piece.getPlaceAt().y].setIcon(new ImageIcon(ChessSprites.GOLD_QUEEN));
                    break;
                case "com.company.King":
                    chessBoardSquares[piece.getPlaceAt().x][piece.getPlaceAt().y].setIcon(new ImageIcon(ChessSprites.GOLD_KING));
                    break;
            }
        }
    }

    private void createChessBoardSquares() {
        chessBoardSquares = new JButton[8][8];
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);


        turnMessage.setText((board.getGame().getTurn() == Color.WHITE) ? "White player's turn" : "Black player's turn");
        playedMovesMessage.setText("Played moves: " + Game.getPlayedMoves());

        if (this.board.getGame().getCheckStatus() == null) {
            checkStatusMessage.setText("Being checked: ");
        }
        else if (this.board.getGame().getCheckStatus().equals(Color.WHITE)) {
            checkStatusMessage.setText("Being checked: White piece set");
        } else if (this.board.getBlackPieceSet().getPieces().get(0).toBeCaptured()) {
            checkStatusMessage.setText("Being checked: Black piece set");
        }

        if (this.board.getGame().getResult() == null) {
            resultMessage.setText("Game won by: ");
        } else if (this.board.getGame().getResult().equals(Color.WHITE)) {
            resultMessage.setText("Game won by: White Set");
        } else if (this.board.getGame().getResult().equals(Color.BLACK)) {
            resultMessage.setText("Game won by: Black Set");
        }

        isAnyButtonClicked[0] = false;

        // create the chess board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                JButton b = new JButton();
                int finalIi = ii;
                int finalJj = jj;

                b.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Piece pieceOfButton = board.getSquaresArray()[finalJj][finalIi];

                        // only allow moves based on turn
                        if (pieceOfButton != null && board.getGame().getTurn().equals(pieceOfButton.getColor())
                            && (board.getGame().getCheckStatus() == null
                                || pieceOfButton.getClass().getCanonicalName().equals("com.company.King"))
                                && board.getGame().getResult() == null) {
                            b.setBackground(Color.YELLOW);

                            // if another button was clicked, repaint the board so that this is the only button clicked
                            if (isAnyButtonClicked[0]) {
                                gui.remove(chessBoard);

                                f.setSize(f.getWidth() + (addOne ? 1 : -1), f.getHeight());
                                addOne = !addOne;

                                createChessBoardSquares();
                            }
                            isAnyButtonClicked[0] = true;

                            // put all the valid moves for this piece into the set moves,
                            // then if it has attack squares, add them to moves
                            // also, if piece is a king, only allow capture free moves and attack moves
                            Set<Point> moves;
                            if (pieceOfButton.getClass().getCanonicalName().equals("com.company.King")) {
                                moves = new HashSet<>(pieceOfButton.captureFreeMove());
                            } else {
                                moves = new HashSet<>(pieceOfButton.validMoves());
                            }

                            if (pieceOfButton.attackSquares() != null) {
                                moves.addAll(pieceOfButton.attackSquares());
                            }

                            // iterate through non null move points and create buttons for them
                            for (Point point: moves) {
                                if (point != null) {
                                    JButton validMoveButton = chessBoardSquares[point.x][point.y];

                                    // color buttons background green if it is a capture free move,
                                    // orange if not,
                                    // red if you can attack there
                                    if (pieceOfButton.validMoves().contains(point)) {
                                        if (pieceOfButton.captureFreeMove().contains(point)) {
                                            validMoveButton.setBackground(Color.GREEN);
                                        } else {
                                            validMoveButton.setBackground(Color.ORANGE);
                                        }
                                    } else {
                                        validMoveButton.setBackground(Color.RED);
                                    }

                                    // add the appropriate movement mouse listener
                                    validMoveButton.addMouseListener(new MouseListener() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            pieceOfButton.movePiece(point);

                                            gui.remove(chessBoard);

                                            f.setSize(f.getWidth() + (addOne ? 1 : -1), f.getHeight());
                                            addOne = !addOne;

                                            createChessBoardSquares();
                                        }
                                        @Override
                                        public void mousePressed(MouseEvent e) {}
                                        @Override
                                        public void mouseReleased(MouseEvent e) {}
                                        @Override
                                        public void mouseEntered(MouseEvent e) {}
                                        @Override
                                        public void mouseExited(MouseEvent e) {}
                                    });
                                }
                            }
                        }
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                chessBoardSquares[jj][ii] = b;
            }
        }

        //fill the chess board
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                            SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                if (jj == 0) {
                    chessBoard.add(new JLabel("" + (ii + 1),
                            SwingConstants.CENTER));
                }
                chessBoard.add(chessBoardSquares[jj][ii]);
            }
        }

        drawPieceIcons();
    }

    public final JComponent getGui() {
        return gui;
    }
}