package com.example.pokemondbappv2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        Context context = this.getContext();
        goBtn = binding.pkmGoButton;
        numBox = binding.numBox;
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int num = Integer.parseInt(numBox.getText().toString());
                    if (num > 0 && num < 152) {
                        Bundle result = new Bundle();
                        result.putInt("dex_num", num);
                        getParentFragmentManager().setFragmentResult("num", result);

                        NavHostFragment.findNavController(PokemonSelectFragment.this)
                                .navigate(R.id.action_PokemonSelectFragment_to_PokemonDataFragment);
                    }
                    else
                        Toast.makeText(context, getString(R.string.num_error), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(context, getString(R.string.num_error), Toast.LENGTH_LONG).show();
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