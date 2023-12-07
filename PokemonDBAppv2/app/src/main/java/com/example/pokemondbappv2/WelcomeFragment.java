package com.example.pokemondbappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pokemondbappv2.databinding.FragmentWelcomeBinding;
import com.example.pokemondbappv2.moves.MovesActivity;
import com.example.pokemondbappv2.pokedex.PokemonActivity;

public class WelcomeFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener  {

    private FragmentWelcomeBinding binding;
    private Spinner spinner;
    private int itemPos;
    private Button pokemonBtn, movesBtn, itemsBtn, locationsBtn;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentWelcomeBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = binding.genSelectSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.gen_select_arr, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        itemPos = -1;

        pokemonBtn = binding.pokemonBtn;
        pokemonBtn.setOnClickListener(this);
        movesBtn = binding.movesBtn;
        movesBtn.setOnClickListener(this);
        itemsBtn = binding.itemsBtn;
        itemsBtn.setOnClickListener(this);
        locationsBtn = binding.locationsBtn;
        locationsBtn.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        itemPos = (int) parent.getItemAtPosition(pos);
        //FIXME
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        Intent switchActivityIntent = null;
        switch (view.getId()) {
            case R.id.pokemon_btn:
                switchActivityIntent = new Intent(this.getContext(), PokemonActivity.class);
                break;
            case R.id.moves_btn:
                switchActivityIntent = new Intent(this.getContext(), MovesActivity.class);
                break;
            case R.id.items_btn:
                // TODO Add intent to switch to Items activity once it's been created
                break;
            case R.id.locations_btn:
                // TODO Add intent to switch to Locations activity once it's been created
                break;
            default:
                break;
        }

        if (switchActivityIntent != null)
            startActivity(switchActivityIntent);
    }
}