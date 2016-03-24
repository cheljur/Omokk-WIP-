package edu.utep.cs.cs4330.hw3.activities;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import edu.utep.cs.cs4330.hw3.layout.HumanVsComputer;
import edu.utep.cs.cs4330.hw3.layout.HumanVsHuman;
import edu.utep.cs.cs4330.hw3.omok.R;
import edu.utep.cs.cs4330.hw3.layout.SelectOpponent;

public class MainActivity extends AppCompatActivity
        implements SelectOpponent.OnSelectedListener {

    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
    }

    /***MENU SHIT**/
   @Override
   public boolean onCreateOptionsMenu(Menu menu){
       getMenuInflater().inflate(R.menu.menu_main, menu);
       return true;
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.settings){
            Log.d("SHIT", "settings chose");
            return true;
        }
        else if(id == R.id.exit){
            Log.d("SHIT", "exit chose");
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*************************************************************/
    @Override
    public void onOpponentSelected(String opponent) {
        //TASK1: CHECK IF FRAGMENT CONTAINER EXIST
        if (findViewById(R.id.info_container) != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (opponent.equalsIgnoreCase("human")) {
                HumanVsHuman human = new HumanVsHuman();
                transaction.replace(R.id.info_container, human).commit();
            } else if (opponent.equalsIgnoreCase("computer")) {
                HumanVsComputer computer = new HumanVsComputer();
                transaction.replace(R.id.info_container, computer).commit();
            }
        }

        //NOT Landscape
        else {
            Intent intent = new Intent(this, EnterInfoActivity.class);
            intent.putExtra("Opponent", opponent);
            startActivity(intent);
        }
    }
}