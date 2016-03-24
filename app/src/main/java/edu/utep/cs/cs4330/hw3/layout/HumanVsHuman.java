package edu.utep.cs.cs4330.hw3.layout;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.utep.cs.cs4330.hw3.activities.OmokActivity;
import edu.utep.cs.cs4330.hw3.omok.R;


public class HumanVsHuman extends Fragment {
    private EditText player1nameText, player2nameText;
    private String player1name,player2name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.fragment_human_vs_human, container, false);

        Button start = (Button) view.findViewById(R.id.humanStart);
        start.setOnClickListener(StartListener);

        player1nameText = (EditText) view.findViewById(R.id.humanPlayer1Name);
        player2nameText = (EditText) view.findViewById(R.id.humanPlayer2Name);
        return view;
    }

    private View.OnClickListener StartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startGame();
        }
    };

    public void startGame(){
        getNames();
        Intent intent = new Intent(getActivity(),OmokActivity.class);
        intent.putExtra("Opponent", "human");
        intent.putExtra("Player1Name", player1name);
        intent.putExtra("Player2Name", player2name);
        startActivity(intent);
    }

    public void getNames(){
        player1name = player1nameText.getText().toString();
        player2name = player2nameText.getText().toString();
    }
}
