package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //private Button[] [] buttons;
    private Context context;
    private TicTacToe tttGame;
    private TextView status;
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9;

    public static String MA = "MainActivity";
    public static int ACTION_BAR_HEIGHT = 56; // vertical, in dp units

    private float pixelDensity;
    private boolean verticalDimensionsSet;
    public static int screenHeightInVP;
    private int spacingInVP;

    private boolean horizontalDimensionsSet;
    public static int screenHeightInHP;
    private int spacingInHP;

    //private Button b1, b2, b3;
    private int actionBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tttGame = new TicTacToe();
        buildGUIByCode();

    }

    private void buildGUIByCode() {
        //Get width of the screen
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x/TicTacToe.SIDE;

        // Create the layout Manager as a GridLayout
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(TicTacToe.SIDE);
        gridLayout.setRowCount(TicTacToe.SIDE + 1);

        // Create the buttons and add them to the GridLayout
        //buttons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];
        b1 = new Button(this);
        b2 = new Button(this);
        b3 = new Button(this);
        b4 = new Button(this);
        b5 = new Button(this);
        b6 = new Button(this);
        b7 = new Button(this);
        b8 = new Button(this);
        b9 = new Button(this);
        ButtonHandler bh = new ButtonHandler();

        for (int row = 0; row <TicTacToe.SIDE; row ++) {
            for (int col = 0; col < TicTacToe.SIDE; col++) {
                //buttons[row][col] = new Button(this);
                b1 = new Button(this);
                b2 = new Button(this);
                b3 = new Button(this);
                b4 = new Button(this);
                b5 = new Button(this);
                b6 = new Button(this);
                b7 = new Button(this);
                b8 = new Button(this);
                b9 = new Button(this);
                //buttons[row][col].setTextSize((int)(w * .2));
                b1.setTextSize((int)(w * .2));
                b2.setTextSize((int)(w * .2));
                b3.setTextSize((int)(w * .2));
                b4.setTextSize((int)(w * .2));
                b5.setTextSize((int)(w * .2));
                b6.setTextSize((int)(w * .2));
                b7.setTextSize((int)(w * .2));
                b8.setTextSize((int)(w * .2));
                b9.setTextSize((int)(w * .2));
                //buttons[row][col].setOnClickListener(bh);
                b1.setOnClickListener(bh);
                b2.setOnClickListener(bh);
                b3.setOnClickListener(bh);
                b4.setOnClickListener(bh);
                b5.setOnClickListener(bh);
                b6.setOnClickListener(bh);
                b7.setOnClickListener(bh);
                b8.setOnClickListener(bh);
                b9.setOnClickListener(bh);


                //gridLayout.addView(buttons[row][col], w, w);
                gridLayout.addView(b1, w, w);
                gridLayout.addView(b2, w, w);
                gridLayout.addView(b3, w, w);
                gridLayout.addView(b4, w, w);
                gridLayout.addView(b5, w, w);
                gridLayout.addView(b6, w, w);
                gridLayout.addView(b7, w, w);
                gridLayout.addView(b8, w, w);
                gridLayout.addView(b9, w, w);

            }
        }

        // Set up layout paramaters for the 4th row of the gridlayout
        status = new TextView(this);
        GridLayout.Spec rowSpec = GridLayout.spec(TicTacToe.SIDE, 1);
        GridLayout.Spec columnSpec = GridLayout.spec(0, TicTacToe.SIDE);
        GridLayout.LayoutParams lpStatus
                = new GridLayout.LayoutParams(rowSpec, columnSpec);
        status.setLayoutParams(lpStatus);

        // Set up status characteristics
        status.setWidth(TicTacToe.SIDE * w);
        status.setHeight(w);
        status.setGravity(Gravity.CENTER);
        status.setBackgroundColor(Color.GREEN);
        status.setTextSize((int)(w * .15));
        status.setText(tttGame.result());

        gridLayout.addView(status);

        // Set the GridLayout as the View of this Activity
        setContentView(gridLayout);
    }


    private class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.w("MainActivity", "Inside onClick, v=" + v);
            for (int row = 0; row < TicTacToe.SIDE; row ++)
                for(int column = 0; column < TicTacToe.SIDE; column ++)
                    /*if(v == buttons[row][column])
                        update(row,column);*/
                    if(v == b1)
                        update(row,column);

        }
    }

    private void update(int row, int column) {
        Log.w("MainActivity", "Inside update: " + row + ", " + column);
        int play = tttGame.play(row, column);
        if (play == 1)
            //buttons[row][column].setText("X");
            b1.setText("X");
        else if (play == 2)
            //buttons[row][column].setText("O");
            b1.setText("O");
        if (tttGame.isGameOver()) {// Game over, disable buttons
            status.setBackgroundColor(Color.RED);
            enableButtons(false);
            status.setText(tttGame.result());
            showNewGameDialog();
        }
    }

    private void showNewGameDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("This is fun");
        alert.setMessage("Play again?");
        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("YES", playAgain);
        alert.setNegativeButton("NO", playAgain);
        alert.show();
    }

    private void resetButtons() {
        for(int row = 0; row <TicTacToe.SIDE; row++)
            for (int col = 0; col <TicTacToe.SIDE; col++)
                //buttons[row][col].setText("");
                b1.setText("");

    }

    private void enableButtons(boolean enabled) {
        for(int row = 0; row <TicTacToe.SIDE; row++)
            for (int col = 0; col <TicTacToe.SIDE; col++)
                //buttons[row][col].setEnabled(enabled);
                b1.setEnabled(enabled);


    }

    private class PlayDialog implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int id) {
            // yes button
            if(id == -1){
                tttGame.resetGame();
                enableButtons(true);
                resetButtons();

            }
            else if(id == -2) // no button
                MainActivity.this.finish();

        }
    }


}