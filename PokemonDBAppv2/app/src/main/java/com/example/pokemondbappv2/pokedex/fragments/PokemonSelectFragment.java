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
import android.widget.EditText;
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
    kalosSpinner, alolaSpinner, galarHisuiSpinner;
    private int selection;

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

        numberBtn = binding.searchByDexnumBtn;
        numberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetPokemonListByNum().execute();
            }
        });

        nameBtn = binding.searchByNameBtn;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class GetPokemonListByNum extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemSelectedListener {
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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

                // Sets the search layout to visible
                binding.searchByNumLayout.setVisibility(View.VISIBLE);

                try {
                    // parses the JSON result for each pokemon name and adds it to the appropriate spinner
                    JSONArray pokemonArray = new JSONObject(result).getJSONArray("results");

                    String tempStr;
                    ArrayList<CharSequence> kantoList = new ArrayList<>();
                    kantoList.add(getString(R.string.kanto_nums));
                    ArrayList<CharSequence> johtoList = new ArrayList<>();
                    johtoList.add(getString(R.string.johto_nums));
                    ArrayList<CharSequence> hoennList = new ArrayList<>();
                    hoennList.add(getString(R.string.hoenn_nums));
                    ArrayList<CharSequence> sinnohList = new ArrayList<>();
                    sinnohList.add(getString(R.string.sinnoh_nums));
                    ArrayList<CharSequence> unovaList = new ArrayList<>();
                    unovaList.add(getString(R.string.unova_nums));
                    ArrayList<CharSequence> kalosList = new ArrayList<>();
                    kalosList.add(getString(R.string.kalos_nums));
                    ArrayList<CharSequence> alolaList = new ArrayList<>();
                    alolaList.add(getString(R.string.alola_nums));
                    ArrayList<CharSequence> galarHisuiList = new ArrayList<>();
                    galarHisuiList.add(getString(R.string.galar_hisui_nums));

                    for (int i = 0; i < 905; i++) {
                        tempStr = pokemonArray.getJSONObject(i).getString("name");
                        tempStr = PokemonMethods.fixPokemonName(tempStr);

                        if (i < 151)
                            kantoList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 251)
                            johtoList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 386)
                            hoennList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 493)
                            sinnohList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 649)
                            unovaList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 721)
                            kalosList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 809)
                            alolaList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 905)
                            galarHisuiList.add(format.format(i+1) + " " + tempStr);
                        else
                            break;
                    }

                    ArrayAdapter<CharSequence> tempAdapter = new ArrayAdapter<>(getContext(),
                            R.layout.pokemon_select_spinner_item, kantoList);
                    kantoSpinner.setAdapter(tempAdapter);
                    kantoSpinner.setOnItemSelectedListener(this);

                    tempAdapter = new ArrayAdapter<>(getContext(),
                            R.layout.pokemon_select_spinner_item, johtoList);
                    johtoSpinner.setAdapter(tempAdapter);
                    johtoSpinner.setOnItemSelectedListener(this);

                    tempAdapter = new ArrayAdapter<>(getContext(),
                            R.layout.pokemon_select_spinner_item, hoennList);
                    hoennSpinner.setAdapter(tempAdapter);
                    hoennSpinner.setOnItemSelectedListener(this);

                    tempAdapter = new ArrayAdapter<>(getContext(),
                            R.layout.pokemon_select_spinner_item, sinnohList);
                    sinnohSpinner.setAdapter(tempAdapter);
                    sinnohSpinner.setOnItemSelectedListener(this);

                    tempAdapter = new ArrayAdapter<>(getContext(),
                            R.layout.pokemon_select_spinner_item, unovaList);
                    unovaSpinner.setAdapter(tempAdapter);
                    unovaSpinner.setOnItemSelectedListener(this);

                    tempAdapter = new ArrayAdapter<>(getContext(),
                            R.layout.pokemon_select_spinner_item, kalosList);
                    kalosSpinner.setAdapter(tempAdapter);
                    kalosSpinner.setOnItemSelectedListener(this);

                    tempAdapter = new ArrayAdapter<>(getContext(),
                            R.layout.pokemon_select_spinner_item, alolaList);
                    alolaSpinner.setAdapter(tempAdapter);
                    alolaSpinner.setOnItemSelectedListener(this);

                    tempAdapter = new ArrayAdapter<>(getContext(),
                            R.layout.pokemon_select_spinner_item, galarHisuiList);
                    galarHisuiSpinner.setAdapter(tempAdapter);
                    galarHisuiSpinner.setOnItemSelectedListener(this);


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

                NavHostFragment.findNavController(PokemonSelectFragment.this)
                        .navigate(R.id.action_PokemonSelectFragment_to_PokemonDataFragment);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}