package edu.utep.cs.cs4330.hw3.layout;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import edu.utep.cs.cs4330.hw3.omok.R;


public class SelectOpponent extends Fragment {

    private String opponent;
    private OnSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_select_opponent, container,false);

        Button human = (Button) view.findViewById(R.id.human);
        human.setOnClickListener(OpponentChangeListener);

        Button computer = (Button) view.findViewById(R.id.computer);
        computer.setOnClickListener(OpponentChangeListener);

        return view;
    }

    private OnClickListener OpponentChangeListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String description = (String)v.getContentDescription();
            opponent = description;
            updateOpponent();
        }
    };

    public void updateOpponent(){
        listener.onOpponentSelected(opponent);
    }

    public interface OnSelectedListener {
        public void onOpponentSelected(String opponent);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnSelectedListener) {
            listener = (OnSelectedListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


}
