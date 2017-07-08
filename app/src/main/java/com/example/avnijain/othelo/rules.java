package com.example.avnijain.othelo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class rules extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        TextView rules = (TextView) findViewById(R.id.rules);
        rules.setText("\t\t\tGame Components\n\n" +
                "A square 8x8 board (you could use a chess board)\n" +
                "\n" +
                "64 discs coloured black on one side and white on the opposite side.\n" +
                "\n" +
                "\n" +
                "\t\t\tSetup\n\n" +
                "The board will start with 2 black discs and 2 white discs at the centre of the board.\n" +
                "\n" +
                "They are arranged with black forming a North-East to South-West direction.\n" +
                "\n" +
                "White is forming a North-West to South-East direction.\n" +
                "\n" +
                "\n" +
                "\t\t\tObject of the Game\n\n" +
                "The goal is to get the majority of colour discs on the board at the end of the game.\n" +
                "\n\n" +
                "\t\t\tGame Play\n\n" +
                "Othello is a strategy board game played between 2 players. One player plays black and the other white.\n" +
                "\n" +
                "Each player gets 32 discs and black always starts the game.\n" +
                "\n" +
                "Then the game alternates between white and black until:\n" +
                "\n" +
                "one player can not make a valid move to outflank the opponent.\n" +
                "both players have no valid moves.\n" +
                "When a player has no valid moves, he pass his turn and the opponent continues.\n" +
                "\n" +
                "A player can not voluntarily forfeit his turn.\n" +
                "\n" +
                "When both players can not make a valid move the gane ends.\n" +
                "\n" +
                "\n" +
                "\t\t\tValid Moves\n\n" +
                "Black always moves first.\n" +
                "\n" +
                "A move is made by placing a disc of the player's color on the board in a position that \"out-flanks\" one or more of the opponent's discs.\n" +
                "\n" +
                "A disc or row of discs is outflanked when it is surrounded at the ends by discs of the opposite color.\n" +
                "\n" +
                "A disc may outflank any number of discs in one or more rows in any direction (horizontal, vertical, diagonal).\n" +
                "\n" +
                /*"\t For example: a white piece is being placed on the board that creates a straight line made up of a white piece at either end and only black pieces in between.\n" +
                "\n" +
                "\n" +
                "White places in the illustration a disc on E3.\n" +
                "\n" +
                "The black discs on E4, E5 and E6 are now outflanked by the white disc on E3 and the white disc on E7.\n" +
                "\n" +
                "The black discs will be flipped to white.\n" +
                "\n" +
                "All the discs which are outflanked will be flipped, even if it is to the player's advantage not to flip them.\n" +
                "\n" +*/
                "Discs may only be outflanked as a direct result of a move and must fall in the direct line of the disc being played.\n" +
                "\n" +
                "If you can't outflank and flip at least one opposing disc, you must pass your turn. However, if a move is available to you, you can't forfeit your turn.\n" +
                "\n" +
                "Once a disc has been placed on a square, it can never be moved to another square later in the game.\n" +
                "\n" +
                "When a player runs out of discs, but still has opportunities to outflank an opposing disc, then the opponent must give the player a disc to use.\n" +
                "\n" +
                "\n" +
                "\t\t\tEnd of the Game\n\n" +
                "When it is no longer possible for either player to move, the game is over.\n" +
                "\n" +
                "The discs are now counted and the player with the majority of his or her color discs on the board is the winner.\n" +
                "\n" +
                "A tie is possible.\n" +
                "\n");
    }
}
