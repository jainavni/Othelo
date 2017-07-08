package com.example.avnijain.othelo;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by Avni Jain on 6/23/2017.
 */

public class MyButton extends Button {

  //  int ButtonColor;
   // boolean IS_VISITED;
   // boolean possible;
  //  MyButton start;
  //  boolean clicked;
   // int NoOfWins;
//    public  int blankButton=0;
//    public int  blackButton=1;
//    public  int  whiteButton=2;
    ArrayList<Integer> dirList = new ArrayList<>();
  int count;
    int value;
    int row;
    int col;

    public MyButton(Context context) {
        super(context);
        // NoOfWins=0;
     //   possible=false;

    }
}
