package com.example.pokemondbappv2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pokemondbappv2.databinding.FragmentMovesSelectBinding;

import java.util.ArrayList;

public class MoveSelectFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private FragmentMovesSelectBinding binding;
    private DBHandler dbHandler;
    private Spinner moveSpinner;
    private String selection;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMovesSelectBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHandler = new DBHandler(this.getContext());

        moveSpinner = binding.moveSelectSpinner;
        ArrayList<CharSequence> moveList = dbHandler.getAllMoveNames();
        ArrayAdapter<CharSequence> moveAdapter = new ArrayAdapter<CharSequence>(this.getContext(),
                android.R.layout.simple_spinner_item, moveList);
        moveSpinner.setAdapter(moveAdapter);
        moveSpinner.setOnItemSelectedListener(this);

        binding.moveGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle result = new Bundle();
                result.putString("move_name", selection);
                getParentFragmentManager().setFragmentResult("move", result);

                NavHostFragment.findNavController(MoveSelectFragment.this)
                        .navigate(R.id.action_MoveSelectFragment_to_MoveDataFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        dbHandler.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
         selection = parent.getItemAtPosition(pos).toString();
         Log.d("**TESTING**", selection);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}