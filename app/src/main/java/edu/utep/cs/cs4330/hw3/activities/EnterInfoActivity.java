package edu.utep.cs.cs4330.hw3.activities;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import edu.utep.cs.cs4330.hw3.layout.HumanVsComputer;
import edu.utep.cs.cs4330.hw3.layout.HumanVsHuman;
import edu.utep.cs.cs4330.hw3.omok.R;

public class EnterInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //VERIFY ORIENTATION HAS BEEN SWITCHED
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        //SET LAYOUT FOR ACTIVITY
        setContentView(R.layout.activity_enter_info);
        //setActionBar();

        //RETURN INTENT THAT STARTED ACTIVITY
        Intent intent = getIntent();
        String opponent = intent.getStringExtra("Opponent");

        //Fill with fragment
        if(findViewById(R.id.info_container) != null){

            if(savedInstanceState != null){
                return;
            }

            FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
            if(opponent.equalsIgnoreCase("human")){
                HumanVsHuman human = new HumanVsHuman();
                transaction.add(R.id.info_container, human).commit();
            }
            else if(opponent.equalsIgnoreCase("computer")){
                HumanVsComputer computer = new HumanVsComputer();
                transaction.add(R.id.info_container,computer).commit();
            }
        }
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
}
