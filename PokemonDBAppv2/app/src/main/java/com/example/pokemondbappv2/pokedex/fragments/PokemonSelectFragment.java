package com.example.pokemondbappv2.pokedex.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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

    private class GetPokemonListByNum extends AsyncTask<Void, Void, Void> {
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
                numberBtn.setVisibility(View.INVISIBLE);
                numberBtn.setClickable(false);
                nameBtn.setVisibility(View.INVISIBLE);
                nameBtn.setClickable(false);

                binding.searchByNumLayout.setVisibility(View.VISIBLE);

                Spinner[] genSpinners = new Spinner[] {
                        kantoSpinner,
                        johtoSpinner,
                        hoennSpinner,
                        sinnohSpinner,
                        unovaSpinner,
                        kalosSpinner,
                        alolaSpinner,
                        galarHisuiSpinner
                };
                try {
                    JSONArray pokemonArray = new JSONObject(result).getJSONArray("results");

                    String tempStr;
                    JSONObject pokemonEntry;
                    ArrayList<CharSequence> kantoList = new ArrayList<>();
                    ArrayList<CharSequence> johtoList = new ArrayList<>();
                    ArrayList<CharSequence> hoennList = new ArrayList<>();
                    ArrayList<CharSequence> sinnohList = new ArrayList<>();
                    ArrayList<CharSequence> unovaList = new ArrayList<>();
                    ArrayList<CharSequence> kalosList = new ArrayList<>();
                    ArrayList<CharSequence> alolaList = new ArrayList<>();
                    ArrayList<CharSequence> galarHisuiList = new ArrayList<>();
                    for (int i = 0; i < 905; i++) {
                        pokemonEntry = pokemonArray.getJSONObject(i);
                        tempStr = pokemonEntry.getString("name");
                        tempStr = tempStr.substring(0, 1).toUpperCase() + tempStr.substring(1).toLowerCase();

                        if (i < 151)
                            kantoList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 251)
                            johtoList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 386)
                            johtoList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 493)
                            johtoList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 649)
                            johtoList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 721)
                            johtoList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 809)
                            johtoList.add(format.format(i+1) + " " + tempStr);
                        else if (i < 905)
                            johtoList.add(format.format(i+1) + " " + tempStr);
                        else
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }
    }

}