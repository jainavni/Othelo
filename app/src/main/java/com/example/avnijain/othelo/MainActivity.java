package com.example.avnijain.othelo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout mainLayout;
    TextView blackScoreTV;
    TextView whiteScoreTV;
    Button hintButton;

    MyButton button[][];
    LinearLayout rowlayout[];

    ArrayList<MyButton> validMoves = new ArrayList<>();
    public final static int BLACK = 0;
    public final static int WHITE = 1;
    public static String winner;
    public int blankButton = 0;
    public int blackButton = 1;
    public int whiteButton = 2;
    public final static int easy = 1;
    public final static int medium = 2;
    public final static int hard = 3;
    int difficulty=medium;
    Random r;
    public static int turn;
    int n=8;
    int blackScore=2, whiteScore=2;
    boolean hint = true;
    boolean gameOver = false;
    int direction;
    //int RIGHT=1,LEFT=2,UPPER=3,BOTTOM=4,UPPER_RIGHT=5,UPPER_LEFT=6,BOTTOM_RIGHT=7,BOTTOM_LEFT=8;
    int x[] = {-1, -1, -1, 0, 1, 1, 1, 0};
    int y[] = {-1, 0, 1, 1, 1, 0, -1, -1};
    int oppX[] = {1, 1, 1, 0, -1, -1, -1, 0};
    int oppY[] = {1, 0, -1, -1, -1, 0, 1, 1};
    int noOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        blackScoreTV = (TextView) findViewById(R.id.blackTV);
        whiteScoreTV = (TextView) findViewById(R.id.whiteTV);

        Intent i = getIntent();
        //n = i.getIntExtra("GRID SIZE", 8);
        difficulty = i.getIntExtra("DIFFICULTY",easy);
        noOfPlayers = i.getIntExtra("NO_OF_PLAYERS",2);
        setBoard();
        setInitials();
        updateScores();
        turn = BLACK;
        checkPossible();
        if (hint) showHints();
    }

    private void setBoard() {
        button = new MyButton[n][n];
        rowlayout = new LinearLayout[n];
        for (int i = 0; i < n; i++) {
            rowlayout[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
            rowlayout[i].setOrientation(LinearLayout.HORIZONTAL);
            rowlayout[i].setLayoutParams(params);
            mainLayout.addView(rowlayout[i]);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                button[i][j] = new MyButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                button[i][j].row = i;
                button[i][j].col = j;
                button[i][j].setOnClickListener(this);
                button[i][j].setLayoutParams(params);
                button[i][j].setBackgroundResource(R.drawable.square);
                button[i][j].value = blankButton;
                rowlayout[i].addView(button[i][j]);
            }
        }
    }

    private void setInitials() {

        button[(n / 2) - 1][(n / 2) - 1].value = blackButton;    //  B W
        button[(n / 2) - 1][(n / 2) - 1].setBackgroundResource(R.drawable.circle1);
        button[(n / 2) - 1][n / 2].value = whiteButton;        //  W B
        button[(n / 2) - 1][n / 2].setBackgroundResource(R.drawable.circle2);
        button[n / 2][(n / 2) - 1].value = whiteButton;
        button[n / 2][(n / 2) - 1].setBackgroundResource(R.drawable.circle2);
        button[n / 2][n / 2].value = blackButton;
        button[n / 2][n / 2].setBackgroundResource(R.drawable.circle1);


    }

    @Override
    public void onClick(View view) {

        MyButton b = (MyButton) view;
        if (!gameOver) {
            Boolean isPossible = validMoves.contains(b);
            if (isPossible) {
                changeDisc(b);
                toggleTurn();
                checkPossible();
                if (noOfPlayers == 1 && turn == WHITE) {
                    if (difficulty == easy) {
                        r = new Random();
                        if (!validMoves.isEmpty()) {
                            int index = r.nextInt(validMoves.size());
                            MyButton randomButton = validMoves.get(index);
                            changeDisc(randomButton);
                        }
                    } else if (difficulty == medium) {
                        int max = 0, index = 0;
                        if (!validMoves.isEmpty()) {
                            for (int i = 0; i < validMoves.size(); i++) {
                                if (validMoves.get(i).count > max) {
                                    max = validMoves.get(i).count;
                                    index = i;
                                }
                            }
                            changeDisc(validMoves.get(index));
                        }
                    }
                    toggleTurn();
                    checkPossible();
                }

                if (validMoves.isEmpty()) {
                    // Toast.makeText(this, "Passed : No valid move", Toast.LENGTH_SHORT).show();
                    toggleTurn();
                    checkPossible();
                    if (validMoves.isEmpty()) {
                        gameOver = true;
                        showResult();
                        return;
                    }
                }
                if (hint) showHints();
            }
        }
    }

    private void showResult() {
        if(whiteScore>blackScore) winner = "WHITE WON";
        else if(blackScore>whiteScore) winner = "BLACK WON";
        else winner = "DRAW";
        Toast.makeText(this, "WINNER : " + winner, Toast.LENGTH_SHORT).show();
    }

    private void toggleTurn() {
        if (turn == WHITE)
            turn = BLACK;
        else
            turn = WHITE;
    }

    private void checkPossible() {

        if (turn == BLACK) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (button[i][j].value == blackButton) {
                        //button[i][j].IS_VISITED=true;
                        checkAllDirections(button[i][j]);
                    }
                }
            }
        } else if (turn == WHITE) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (button[i][j].value == whiteButton) {
                        //button[i][j].IS_VISITED=true;
                        checkAllDirections(button[i][j]);
                    }
                }
            }
        }
    }

    private void checkAllDirections(MyButton b) {

        for (int i = 0; i < 8; i++) { //if(!done) {
            int p = b.row + x[i];
            int q = b.col + y[i];
            if (p >= 0 && p < n && q >= 0 && q < n) {
                if (turn == BLACK) {
                    if (button[p][q].value == whiteButton)
                        checkOneDir(p, q, i);
                } else if (turn == WHITE) {
                    if (button[p][q].value == blackButton)
                        checkOneDir(p, q, i);
                }
            }
        }
    }

    private void checkOneDir(int i, int j, int dir) {
        int cnt = 0;
        while (j >= 0 && j < n && i >= 0 && i < n) {
            if (turn == BLACK) {
                if (button[i][j].value == whiteButton) {
                    cnt++;
                    i = i + x[dir];
                    j = j + y[dir];
                } else if (button[i][j].value == blackButton)
                    return;
                else if (button[i][j].value == blankButton && cnt != 0) {
                    if (!validMoves.contains(button[i][j]))
                        validMoves.add(button[i][j]);
                    button[i][j].dirList.add(dir);
                    button[i][j].count+=cnt;
                    return;
                }
            } else if (turn == WHITE) {
                if (button[i][j].value == blackButton) {
                    cnt++;
                    i = i + x[dir];
                    j = j + y[dir];
                } else if (button[i][j].value == whiteButton)
                    return;
                else if (button[i][j].value == blankButton && cnt != 0) {
                    if (!validMoves.contains(button[i][j]))
                        validMoves.add(button[i][j]);
                    button[i][j].dirList.add(dir);
                    button[i][j].count+=cnt;
                    return;
                }
            }
        }
    }


    public void changeDisc(MyButton b) {

        if (turn == WHITE) {
            for (int i = 0; i < b.dirList.size(); i++) {
                int p = b.row;
                int q = b.col;
                do {
                    button[p][q].setBackgroundResource(R.drawable.circle2);
                    if(button[p][q].value==blackButton)
                        blackScore--;
                    if(button[p][q].value==whiteButton)
                        whiteScore--;
                    button[p][q].value = whiteButton;
                    whiteScore++;
                    p = p + oppX[b.dirList.get(i)];
                    q = q + oppY[b.dirList.get(i)];
                    if (p < 0 || p >= n || q < 0 || q >= n) break;
                }
                while (button[p][q].value == blackButton);
            }
        } else if (turn == BLACK) {
            for (int i = 0; i < b.dirList.size(); i++) {
                int p = b.row;
                int q = b.col;
                do {
                    button[p][q].setBackgroundResource(R.drawable.circle1);
                    if(button[p][q].value==whiteButton)
                        whiteScore--;
                    if(button[p][q].value==blackButton)
                        blackScore--;
                    button[p][q].value = blackButton;
                    blackScore++;
                    p = p + oppX[b.dirList.get(i)];
                    q = q + oppY[b.dirList.get(i)];
                    if (p < 0 || p >= n || q < 0 || q >= n) break;
                } while (button[p][q].value == whiteButton);
            }
        }
        updateScores();
        removeHints();
        resetLists();
    }

    private void updateScores() {
        blackScoreTV.setText("Player 1:   "+ blackScore);
        if(noOfPlayers==1)
            whiteScoreTV.setText("CPU:   "+ whiteScore);
        else
         whiteScoreTV.setText("Player 2:   "+ whiteScore);
    }

    private void resetLists() {
        for (int i = 0; i < validMoves.size(); i++) {
            MyButton butt = validMoves.get(i);
            butt.dirList.clear();
            butt.count=0;
        }
        validMoves.clear();
    }

    public void showHints() {
        for (int i = 0; i < validMoves.size(); i++) {
            MyButton b = validMoves.get(i);
            //b.setText("*");
            if(turn==BLACK)
                b.setBackgroundResource(R.drawable.hint1);
            else
                b.setBackgroundResource(R.drawable.hint2);
        }
    }

    public void removeHints() {
        for (int i = 0; i < validMoves.size(); i++) {
            MyButton b = validMoves.get(i);
            if(b.value==blankButton)
            b.setBackgroundResource(R.drawable.square);
            //b.setText("");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.NewGame)
            resetBoard();
        else if(id==R.id.HintButton)
            hintButtonListener();
        else if(id==R.id.Settings){}
            return true;
    }

    private void resetBoard() {
            for(int i=0;i<n;i++) {
                for (int j = 0; j < n; j++) {
                    button[i][j].value = blankButton;
                    button[i][j].setBackgroundResource(R.drawable.square);
                }
            }
            gameOver=false;
            removeHints();
            setInitials();
            whiteScore=2; blackScore=2;
            updateScores();
            turn=BLACK;
            resetLists();
            checkPossible();
             showHints();

        }


    private void hintButtonListener() {
        hint =!hint;
        if(!hint) removeHints();
        else if(hint) showHints();
    }
}


/*

    // compute whether a given move is valid, and, possibly, do it.
    public int move(int i, int j, Boolean doMove) {
//    {   assert (i >= 0);
//        assert (i < n);
//        assert (j >= 0);
//        assert (j < n);

        if (i < 0 || i >= n || j < 0 || j >= n) return 0;
        // if the proposed space is already occupied, bail.
        if (button[i][j].value != blankButton) {
            return 0;
        }

        // explore whether any of the eight 'rays' extending from the current piece
        // have a line of at least one opponent piece terminating in one of our own pieces.
        int dx, dy;
        int totalCaptured = 0;
        for (dx = -1; dx <= 1; dx++) {
            for (dy = -1; dy <= 1; dy++) {
                // (skip the null movement case)
                if (dx == 0 && dy == 0) {
                    continue;
                }

                // explore the ray for potential captures.
                for (int steps = 1; steps < n; steps++) {
                    int ray_i = i + (dx * steps);
                    int ray_j = j + (dy * steps);

                    // if the ray has gone out of bounds, give up
                    if (ray_i < 0 || ray_i >= n || ray_j < 0 || ray_j >= n) {
                        break;
                    }

                    MyButton ray_cell = button[ray_i][ray_j];

                    // if we hit a blank cell before terminating a sequence, give up
                    if (ray_cell.value == blankButton) {
                        break;
                    }

                    // if we hit a piece that's our own, let's capture the sequence
                    if (turn == WHITE) {
                        if (ray_cell.value == whiteButton) {
                            if (steps > 1) {
                                // we've gone at least one step, capture the ray.
                                totalCaptured += steps - 1;
                                if (doMove) {
                                    // okay, let's actually execute on this
                                    while (steps-- > 0) {
                                        button[i + (dx * steps)][j + (dy * steps)].value = whiteButton;
                                        button[i + (dx * steps)][j + (dy * steps)].setBackgroundResource(R.drawable.circle2);
                                    }
                                }
                            }
                        }
                        break;
                    } else if (turn == BLACK) {
                        if (ray_cell.value == blackButton) {
                            if (steps > 1) {
                                // we've gone at least one step, capture the ray.
                                totalCaptured += steps - 1;
                                // if (doMove)
                                { // okay, let's actually execute on this
                                    while (steps-- > 0) {
                                        button[i + (dx * steps)][j + (dy * steps)].value = blackButton;
                                        button[i + (dx * steps)][j + (dy * steps)].setBackgroundResource(R.drawable.circle1);

                                    }
                                }
                            }
                        }
                        break;
                    }

                }
            }
        }

        return totalCaptured;
        //return true;
    }

    // checks to see if the current player can make any move at all.
    public boolean isAMovePossible(MyButton b) {
        int i, j;
        for(i=0;i<n;i++){
            for(j=0;j<n;j++){
                int numCaptured = move(b.row, b.col, false);
                if(numCaptured > 0) {
                    return true;
                }
            }
        }
        return false;
    }

}
*/






// ***********    onClick

  /* if(b.possible) {
            int p = b.row;
            int q = b.col;
            if (turn == WHITE) {
                while (button[p][q].value=blackButton) {
                    button[p][q].setBackgroundResource(R.drawable.circle2);
                    button[p][q].value=whiteButton = true;
                    button[p][q].value=blankButton = false;
                    p = p + oppX[b.dir];
                    q = q + oppY[b.dir];
                }
            } else if (turn == BLACK) {
                while (button[p][q].value=whiteButton) {
                    button[p][q].setBackgroundResource(R.drawable.circle1);
                    button[p][q].value=blackButton = true;
                    button[p][q].value=blankButton = false;
                    p = p + oppX[b.dir];
                    q = q + oppY[b.dir];
                }
            }

            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (button[u][v].possible)
                        button[u][v].possible = false;
                }
            }
        }
*/



 // ************ checkOneDir
/*                else if (button[i][j].value==blankButton && cnt != 0) {
                    validMoves.add(button[i][j]);
                    button[i][j].dirList.add(dir);
                   // button[i][j].possible = true;
                    //button[i][j].dir=dir;
                   // button[i][j].start=b;

                    if (button[i][j].clicked) {
                        while (cnt != -2) {
                            int s = b.row + x[dir];
                            int t = b.col + y[dir];
                            button[s][t].setBackgroundResource(R.drawable.circle1);
                            button[s][t].value=blackButton ;
                            button[s][t].value=whiteButton;
                            button[s][t].value=blankButton;
                            cnt--;
                        }
                        for (int u = 0; u < n; u++) {
                            for (int v = 0; v < n; v++)
                                if (button[u][v].possible)
                                    button[u][v].possible = false;
                        }
                        done=true;
                        turn=WHITE;
                    }

                            // if(button[i][j].NoOfWins < cnt)
                                // button[i][j].NoOfWins = cnt;

*/











/*
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                    if (button[i][j].possible) {
                    //button[i][j].setBackgroundResource(R.drawable.bg);
                    //button[i][j].setBackgroundResource(R.drawable.hint);
                }
            }
        }
*/



 /*
       private void checkAllDirections(MyButton b) {
       {  dir=RIGHT;
            int i = b.row;
            int j = b.col + 1;
            // Log.i("ABCD","i="+i+" j="+j +" dir= " +dir);
            checkOneDir(i,j,dir);

        }
        {   dir=LEFT;
            int i=b.row;
            int j=b.col-1;
            checkOneDir(i,j,dir);
        }
        {
            dir = UPPER;
            int i = b.row-1 ;
            int j = b.col;
            checkOneDir(i, j, dir);
        }
        {   dir=BOTTOM;
            int i=b.row+1;
            int j=b.col;
            checkOneDir(i,j,dir);

        }
        {   dir=UPPER_RIGHT;
            int i=b.row-1;
            int j=b.col+1;
            checkOneDir(i,j,dir);

        }

        {   dir=UPPER_LEFT;
            int i=b.row-1;
            int j=b.col-1;
            checkOneDir(i,j,dir);

        }
        {   dir=BOTTOM_RIGHT;
            int i=b.row+1;
            int j=b.col+1;
            checkOneDir(i,j,dir);

        }
        {   dir=BOTTOM_LEFT;
            int i=b.row+1;
            int j=b.col-1;
            checkOneDir(i,j,dir);

        }


    }

     if(dir==RIGHT) j++;
                    else if (dir==LEFT) j--;
                    else if(dir==UPPER) i--;
                    else if(dir==BOTTOM) i++;
                    else if(dir==UPPER_RIGHT){i--;j++;}
                    else if(dir==UPPER_LEFT){i--;j--;}
                    else if(dir==BOTTOM_LEFT){i++;j--;}
                    else if(dir==BOTTOM_RIGHT){i++;j++;}     */




