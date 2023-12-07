package com.example.pokemondbappv2.moves.fragments;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pokemondbappv2.databinding.FragmentMovesSelectBinding;
import com.example.pokemondbappv2.R;
import com.example.pokemondbappv2.PokemonMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.net.URL;
import java.util.Collection;
import java.util.TreeSet;

public class MoveSelectFragment extends Fragment {

    private FragmentMovesSelectBinding binding;
    private Spinner moveSpinner;
    private Button goBtn;
    private String selection;
    private String[] names;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovesSelectBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = null;
        moveSpinner = binding.moveSelectSpinner;
        new GetMovesByName().execute();
        goBtn = binding.moveGoBtn;

    }

    private class GetMovesByName extends AsyncTask<Void, Void, Void> {
        private String result;
        private int count;
        protected Collection<String> moveList;

        public GetMovesByName() {
            result = "";
            count = 0;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            moveList = new TreeSet<>();
        }

        @Override
        protected Void doInBackground(Void...arg0) {
            HttpURLConnection urlConnection;
            BufferedReader reader;

            try {
                URL url = new URL("https://pokeapi.co/api/v2/move/?limit=1000");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                if (inputStream == null) { return null; }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String s;
                while ((s = reader.readLine()) != null) { result += s; }
                urlConnection.disconnect();

            } catch (IOException e) {
                Log.i("HttpAsyncTask", "EXCEPTION: " + e.getMessage());
            }

            Log.d("API request returned: ", result);

            return null;
        }

        @Override
        public void onPostExecute(Void r) {
            super.onPostExecute(r);

            if (result != null) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray movesArray = obj.getJSONArray("results");
                    count = obj.getInt("count");

                    String str;

                    for (int i = 0; i < count; i++) {
                        str = movesArray.getJSONObject(i).getString("name");
                        str = PokemonMethods.fixMoveName(str);

                        moveList.add(str);
                    }
                    names = moveList.toArray(new String[count]);
                    for (int i = 0; i < names.length; i++) {
                        Log.d("MOVE "+i, names[i]);
                    }
                    adapter = new ArrayAdapter<>(getContext(), R.layout.move_select_spinner, names);
                    moveSpinner.setAdapter(adapter);
                    moveSpinner.setClickable(true);
                    moveSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i != 0) {
                                selection = adapterView.getItemAtPosition(i).toString();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
