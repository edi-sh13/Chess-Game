package com.company;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        Piece king = new King(new Board(), Color.WHITE, new Point(0, 0));
//        System.out.println(king.getClass().getCanonicalName());


        Board board = new Board();
        new View(board);
    }
}
