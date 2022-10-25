package com.example.pokemondbappv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pokemondbappv2.databinding.FragmentPokemonDataBinding;

public class PokemonDataFragment extends Fragment {

    private FragmentPokemonDataBinding binding;
    private int dexNum;
    private DBHandler pokemonLookup;
    private PokemonG1Model pokemon;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentPokemonDataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pokemonLookup = new DBHandler(this.getContext());

        getParentFragmentManager().setFragmentResultListener("dex_num", this,
                ((requestKey, result) -> {
                    dexNum = Integer.parseInt(result.getString("dex_num"));
                    pokemon = pokemonLookup.getPokemon(dexNum);

                    binding.nameText.setText(pokemon.getName());
                }));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}