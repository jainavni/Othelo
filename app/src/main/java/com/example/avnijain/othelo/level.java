package com.example.avnijain.othelo;



        import android.content.DialogInterface;
        import android.content.Intent;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.Window;
        import android.widget.Button;

public class level extends AppCompatActivity implements View.OnClickListener {

    Button b1;
    Button b2;
    Button b3;
    int n,level=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Intent i1 = getIntent();
        setContentView(R.layout.activity_level);

        b1 = (Button)findViewById(R.id.play1);
        b2 = (Button)findViewById(R.id.play2);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

 }

    @Override
    public void onClick(View v) {
        // Button b=(Button)v;
        if(v.getId()==b1.getId()) {
           // n = 8;

            AlertDialog.Builder builder = new AlertDialog.Builder(level.this);
            builder.setTitle("Level");
            builder.setCancelable(true);
            builder.setMessage("Select Difficulty");
            builder.setPositiveButton("Hard",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                   level=2;
                    Intent i2 = new Intent(level.this,MainActivity.class);
                    i2.putExtra("NO_OF_PLAYERS",1);
                    i2.putExtra("DIFFICULTY",level);
                    //i2.putExtra("GRID SIZE",n);
                    startActivity(i2);
                }
            });
            builder.setNegativeButton("Easy", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    level=1;
                    Intent i2 = new Intent(level.this,MainActivity.class);
                    i2.putExtra("NO_OF_PLAYERS",1);
                    i2.putExtra("DIFFICULTY",level);
                    //i2.putExtra("GRID SIZE",n);
                    startActivity(i2);
                }
            });
            builder.setIcon(R.drawable.bg1);

            AlertDialog dialog = builder.create();

//            // Make some UI changes for AlertDialog
//            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                @Override
//                public void onShow(final DialogInterface dialog) {
//
//                    // Add or create your own background drawable for AlertDialog window
//                    Window view = ((AlertDialog) dialog).getWindow();
//                    view.setBackgroundDrawableResource(R.color.black);
//                }
//                });


            builder.show();


//            Intent i2 = new Intent(level.this,MainActivity.class);
//            i2.putExtra("NO_OF_PLAYERS",1);
//            i2.putExtra("DIFFICULTY",level);
//            //i2.putExtra("GRID SIZE",n);
//            startActivity(i2);
        }
        else if(v.getId()==b2.getId()) {
           // n = 10;
            Intent i2 = new Intent(level.this,MainActivity.class);
            i2.putExtra("NO_OF_PLAYERS",2);
           // i2.putExtra("GRID SIZE",n);
            startActivity(i2);

        }
    }
}
