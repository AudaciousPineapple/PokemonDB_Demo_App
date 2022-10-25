package com.example.pokemondbappv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pokemondbappv2.databinding.FragmentPokemonSelectBinding;

public class PokemonSelectFragment extends Fragment {

    private FragmentPokemonSelectBinding binding;
    private Button goBtn;
    private EditText numBox;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentPokemonSelectBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goBtn = binding.pkmGoButton;
        numBox = binding.numBox;
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempString = numBox.getText().toString();
                if (!tempString.isEmpty()) {
                    Bundle result = new Bundle();
                    result.putString("dex_num", tempString);
                    getParentFragmentManager().setFragmentResult("dex_num", result);

                    NavHostFragment.findNavController(PokemonSelectFragment.this)
                            .navigate(R.id.action_PokemonSelectFragment_to_PokemonDataFragment);
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}