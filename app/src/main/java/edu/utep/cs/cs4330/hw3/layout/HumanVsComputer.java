package edu.utep.cs.cs4330.hw3.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.utep.cs.cs4330.hw3.activities.OmokActivity;
import edu.utep.cs.cs4330.hw3.omok.R;

public class HumanVsComputer extends Fragment {
    private EditText player1nameText;
    private Spinner spinner;
    private String player1name, computerStategy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.fragment_human_vs_computer, container, false);

        Button start = (Button) view.findViewById(R.id.computerStart);
        start.setOnClickListener(StartListener);

        player1nameText = (EditText) view.findViewById(R.id.computerPlayer1Name);

        setSpinner(view);

        return view;
    }

    private void setSpinner(View view){
        spinner = (Spinner) view.findViewById(R.id.computerStrategySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.strategies_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private View.OnClickListener StartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startGame();
        }
    };

    public void startGame(){
        getNames();
        getStrategy();
        Intent intent = new Intent(getActivity(),OmokActivity.class);
        intent.putExtra("Opponent", "computer");
        intent.putExtra("Player1Name", player1name);
        intent.putExtra("Strategy", computerStategy);
        startActivity(intent);
    }

    public void getNames(){
        player1name = player1nameText.getText().toString();
    }

    public void getStrategy(){
        computerStategy = spinner.getSelectedItem().toString();
    }
}
