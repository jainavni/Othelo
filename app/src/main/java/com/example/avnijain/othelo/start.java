package com.example.avnijain.othelo;


        import android.content.DialogInterface;
        import android.content.Intent;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class start extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button b = (Button)findViewById(R.id.startButton) ;
        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i= new Intent(start.this,level.class);
                startActivity(i);
            }
        });

        Button Rule = (Button) findViewById(R.id.RulesButton);
        Rule.setOnClickListener(this);

        Button credit = (Button) findViewById(R.id.CreditButton);
        credit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(start.this);
                builder.setTitle("CREDITS");
                builder.setMessage("\t\t\t\t OTHELO \n Created By : Avni Jain \n For Feedback email: aj.jainavni1998@gmail.com");
                builder.setCancelable(false);
                builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface d, int i) {
                        d.dismiss();
                    }
                });
                AlertDialog d  = builder.create();
                d.show();
            }
        });



    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(start.this,rules.class);
        startActivity(i);




       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("RULES");
        builder.setMessage("");
        builder.setCancelable(false);
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface d, int i) {
                d.dismiss();
            }
        });
        AlertDialog d  = builder.create();
        d.show();
*/
    }
}
