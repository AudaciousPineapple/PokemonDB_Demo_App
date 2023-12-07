package com.example.pokemondbappv2.pokedex.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pokemondbappv2.PokemonMethods;
import com.example.pokemondbappv2.R;
import com.example.pokemondbappv2.databinding.FragmentPokemonSelectBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class PokemonSelectFragment extends Fragment {

    private FragmentPokemonSelectBinding binding;
    private Button numberBtn, nameBtn;
    private Spinner kantoSpinner, johtoSpinner, hoennSpinner, sinnohSpinner, unovaSpinner,
    kalosSpinner, alolaSpinner, galarHisuiSpinner, genSelectSpinner;
    private Spinner[] spinArray;
    private int selection, genSelect;

    private DecimalFormat format;

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

        format = new DecimalFormat("000");

        kantoSpinner = binding.kantoSpinner;
        johtoSpinner = binding.johtoSpinner;
        hoennSpinner = binding.hoennSpinner;
        sinnohSpinner = binding.sinnohSpinner;
        unovaSpinner = binding.unovaSpinner;
        kalosSpinner = binding.kalosSpinner;
        alolaSpinner = binding.alolaSpinner;
        galarHisuiSpinner = binding.galarHisuiSpinner;
        genSelectSpinner = binding.pokemonGenSelectSpinner;

        spinArray = new Spinner[]{ kantoSpinner, johtoSpinner, hoennSpinner, sinnohSpinner,
                unovaSpinner, kalosSpinner, alolaSpinner, galarHisuiSpinner};

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.gen_select_arr, android.R.layout.simple_spinner_item);
        genSelectSpinner.setAdapter(adapter);
        genSelect = -1;
        genSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genSelect = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        numberBtn = binding.searchByDexnumBtn;
        numberBtn.setOnClickListener(view1 -> {

            if (genSelect > 0)
                new GetPokemonListByNum(genSelect).execute();
            else
                Toast.makeText(getContext(), "You must choose a generation!", Toast.LENGTH_LONG).show();
        });

        nameBtn = binding.searchByNameBtn;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class GetPokemonListByNum extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemSelectedListener {
        String result;
        int gen;
        protected final int[] genLimit = { 151, 251, 386, 493, 649, 721, 809, 905 };
        protected ArrayList<CharSequence>[] pokeLists;
        protected ArrayList<CharSequence> kantoList, johtoList, hoennList, sinnohList, unovaList, kalosList,
                alolaList, galarHisuiList;

        private final int[] spinTopStringIds = { R.string.kanto_nums, R.string.johto_nums,
                R.string.hoenn_nums, R.string.sinnoh_nums, R.string.unova_nums, R.string.kalos_nums,
                R.string.alola_nums, R.string.galar_hisui_nums};

        public GetPokemonListByNum(int gen) {
            this.gen = gen;
            result = "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pokeLists = new ArrayList[]{ kantoList, johtoList, hoennList, sinnohList,
                    unovaList, kalosList, alolaList, galarHisuiList};
            for (int i = 0; i < gen; i++) {
                pokeLists[i] = new ArrayList<>();
                pokeLists[i].add(getString(spinTopStringIds[i]));
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpURLConnection urlConnection;
            BufferedReader reader;

            try {
                // pulls a list of all pokemon species and stores the result in a String
                URL url = new URL("https://pokeapi.co/api/v2/pokemon-species?limit=1000");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                if(inputStream == null)
                    return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String s;
                while ((s = reader.readLine()) != null)
                    result += s;

                urlConnection.disconnect();
            }
            catch (Exception e) {
                Log.i("HttpAsyncTask", "EXCEPTION: " + e.getMessage());
            }

            Log.d("API request returned: ", result);

            return null;
        }

        @Override
        protected void onPostExecute(Void r) {
            super.onPostExecute(r);

            if (result != null) {

                // Removes and disables the buttons for choosing search method
                numberBtn.setVisibility(View.INVISIBLE);
                numberBtn.setClickable(false);
                nameBtn.setVisibility(View.INVISIBLE);
                nameBtn.setClickable(false);
                genSelectSpinner.setVisibility(View.GONE);

                // Sets the search layout to visible
                binding.searchByNumLayout.setVisibility(View.VISIBLE);

                try {
                    // parses the JSON result for each pokemon name and adds it to the appropriate spinner
                    JSONArray pokemonArray = new JSONObject(result).getJSONArray("results");

                    String tempStr;

                    for (int i = 0; i < genLimit[gen - 1]; i++) {
                        tempStr = pokemonArray.getJSONObject(i).getString("name");
                        tempStr = PokemonMethods.fixPokemonName(tempStr);
                        //Log.d("PokemonSelectFragment", tempStr);

                        if (i < 151)
                            pokeLists[0].add(format.format(i+1) + " " + tempStr);
                        else if (gen >= 2 && i < 251)
                            pokeLists[1].add(format.format(i+1) + " " + tempStr);
                        else if (gen >= 3 && i < 386)
                            pokeLists[2].add(format.format(i+1) + " " + tempStr);
                        else if (gen >= 4 && i < 493)
                            pokeLists[3].add(format.format(i+1) + " " + tempStr);
                        else if (gen >= 5 && i < 649)
                            pokeLists[4].add(format.format(i+1) + " " + tempStr);
                        else if (gen >= 6 && i < 721)
                            pokeLists[5].add(format.format(i+1) + " " + tempStr);
                        else if (gen >= 7 && i < 809)
                            pokeLists[6].add(format.format(i+1) + " " + tempStr);
                        else if (gen >= 8 && i < 905)
                            pokeLists[7].add(format.format(i+1) + " " + tempStr);
                        else
                            break;


                    }

                    ArrayAdapter<CharSequence> tempAdapter;

                    for (int i = 0; i < 8; i++) {
                        if (i < gen) {
                            tempAdapter = new ArrayAdapter<>(getContext(),
                                    R.layout.pokemon_select_spinner_item, pokeLists[i]);
                            spinArray[i].setAdapter(tempAdapter);
                            spinArray[i].setOnItemSelectedListener(this);
                        }
                        else
                            spinArray[i].setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            if (i != 0) {
                selection = Integer.parseInt(adapterView.getItemAtPosition(i).toString().substring(0, 3));

                Bundle pokeBundle = new Bundle();
                pokeBundle.putInt("dex_num", selection);
                getParentFragmentManager().setFragmentResult("dex_num", pokeBundle);

                switch (gen) {
                    case 1:
                        NavHostFragment.findNavController(PokemonSelectFragment.this)
                                .navigate(R.id.action_PokemonSelectFragment_to_PokemonG1DataFragment);
                        break;
                    case 2:
                        //TODO Add Gen 2 Fragment
                        break;
                    case 3:
                        //TODO Add Gen 3 Fragment
                        break;
                    case 4:
                        //TODO Add Gen 4 Fragment
                        break;
                    case 5:
                        //TODO Add Gen 5 fragment
                        break;
                    case 6:
                        //TODO Add gen 6 fragment
                        break;
                    case 7:
                        //TODO add gen 7 fragment
                        break;
                    case 8:
                        //TODO add gen 8 fragment
                        break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}