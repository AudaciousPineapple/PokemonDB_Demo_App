package com.example.pokemondbappv2.pokedex.fragments;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.pokemondbappv2.DBHandler;
import com.example.pokemondbappv2.ModelMove;
import com.example.pokemondbappv2.ModelMoveset;
import com.example.pokemondbappv2.ModelPokemonG1;
import com.example.pokemondbappv2.PokemonMethods;
import com.example.pokemondbappv2.R;
import com.example.pokemondbappv2.Type;
import com.example.pokemondbappv2.databinding.FragmentPokemonDataBinding;
import com.example.pokemondbappv2.pokedex.apiclasses.APICalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class PokemonDataFragment extends Fragment{

    private DecimalFormat dFormat, dFormat2, dFormat3;

    private FragmentPokemonDataBinding binding;
    private int dexNum;

    private static final String NAME = "name";
    private static final String NUM = "num";
    ArrayList<HashMap<String, String>> inflationList;
    private Resources res;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentPokemonDataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dFormat = new DecimalFormat("000");
        inflationList = new ArrayList<HashMap<String, String>>();
        res = this.getResources();

        getParentFragmentManager().setFragmentResultListener("dex_num", this,
                ((requestKey, result) -> {
                    dexNum = result.getInt("dex_num");
                    Log.d("**TESTING**", dFormat.format(dexNum));

                    new getPokemonData().execute();
        }));
    }

    /*
     * setTypePic method - Sets the resource for the selected ImageView for Pokemon Types
     * @param type - The type being used
     * @param type1 - true if the ImageView is for the pokemon's first type, false for the 2nd
     */
    /*private void setTypePic(Type type, boolean type1) {
        ImageView imgView;
        if (type1)
            imgView = binding.type1;
        else
            imgView = binding.type2;

        imgView.setImageDrawable(PokemonMethods.getTypeImg(res, type));
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class getPokemonData extends AsyncTask<Void, Void, Void> {
        String speciesResult = "";
        String pokemonResult = "";

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
                String tempStr = "https://pokeapi.co/api/v2/pokemon-species/" + dexNum;
                URL url = new URL(tempStr);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                if (inputStream == null)
                    return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));

                while ((tempStr = reader.readLine()) != null)
                    speciesResult += tempStr;

                Log.d("API request returned: ", speciesResult);

                urlConnection.disconnect();
                reader.close();

                tempStr = "https://pokeapi.co/api/v2/pokemon/" + dexNum;
                url = new URL(tempStr);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                inputStream = urlConnection.getInputStream();

                if (inputStream == null)
                    return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));

                while ((tempStr = reader.readLine()) != null)
                    pokemonResult += tempStr;

                Log.d("API request returned: ", pokemonResult);

                urlConnection.disconnect();
                reader.close();


            } catch (IOException e) {
                Log.i("HttpAsyncTask", "EXCEPTION: " + e.getMessage());
            }

            // Log.d("API request returned: ", speciesResult);
            return null;
        }

        @Override
        protected void onPostExecute(Void r) {
            super.onPostExecute(r);


                try {
                    JSONObject speciesObj = new JSONObject(speciesResult);
                    JSONObject pokemonObj = new JSONObject(pokemonResult);

                    binding.pokemonNum.setText(dFormat.format(dexNum));

                    String pokemonName = speciesObj.getString(NAME);
                    pokemonName = PokemonMethods.fixPokemonName(pokemonName);

                    binding.pokemonName.setText(pokemonName);

                    String tempStr = pokemonObj.getJSONObject("sprites").getJSONObject("other")
                            .getJSONObject("official-artwork").getString("front_default");

                    // Log.d("**TESTING**", tempStr);
                    new APICalls.ImageLoadTask(tempStr, binding.pokemonImg).execute();

                    JSONArray tempArray = speciesObj.getJSONArray("names");

                    binding.pokemonNameEng.setText(pokemonName);
                    binding.pokemonNameJp.setText(tempArray.getJSONObject(0).getString(NAME));
                    binding.pokemonNameJpEng.setText(tempArray.getJSONObject(1).getString(NAME));
                    binding.pokemonNameFr.setText(tempArray.getJSONObject(4).getString(NAME));
                    binding.pokemonNameGer.setText(tempArray.getJSONObject(5).getString(NAME));
                    binding.pokemonNameKor.setText(tempArray.getJSONObject(2).getString(NAME));

                    tempArray = speciesObj.getJSONArray("pokedex_numbers");
                    for (int i = 0; i < tempArray.length(); i++) {
                        HashMap<String, String> tempInfo = new HashMap<>();
                        JSONObject tempObj = tempArray.getJSONObject(i);

                        tempStr = tempObj.getJSONObject("pokedex").getString(NAME);
                        tempStr = PokemonMethods.fixPokemonName(tempStr);
                        View child = getLayoutInflater().inflate(R.layout.pokemon_dexnum_entry, null);
                        ((TextView)(child.findViewById(R.id.dexName_listEntry_txt))).setText(tempStr);

                        tempStr =  "# " + tempObj.getInt("entry_number");
                        ((TextView)(child.findViewById(R.id.dexNum_listEntry_txt))).setText(tempStr);
                        binding.dexNumList.addView(child);
                    }

                } catch (JSONException e) {
                    Log.i("JSON Parsing", "EXCEPTION: " + e.getMessage());
                }

        }
    }

    /**
     * setMoveset method - FIXME
     * @param dexNum
     */
/*    private void setMoveset(int dexNum) {
        ArrayList<ModelMoveset> list = pokemonLookup.getMoveset(dexNum);
        TableRow newRow;
        String moveName, tempString;
        ImageView img;
        TextView textView;
        ModelMove move;
        int lvlCount = 0, ylwCount = 0, tmCount = 0, prevoCount = 0, specialCount = 0;

        for (int i = 0; i < list.size(); i++) {
            newRow = new TableRow(this.getContext());
            moveName = list.get(i).getMoveName();
            move = pokemonLookup.getMove(moveName);

            if (list.get(i).getLevel() >= 0) {
                if (list.get(i).getPrEvo() == 0) {
                    textView = new TextView(this.getContext());

                    // level learned
                    if (list.get(i).getLevel() == 0)
                        textView.setText("--");
                    else
                        textView.setText(String.format(Locale.getDefault(), "%d", list.get(i).getLevel()));
                    newRow.addView(textView, 0);

                    // name
                    textView = new TextView(this.getContext());
                    textView.setText(moveName);
                    newRow.addView(textView, 1);

                    finishMovesetEntry(move, newRow);

                    if (list.get(i).isYellow()) {
                        yellowTable.addView(newRow, ylwCount + 1);
                        ylwCount++;
                    }
                    else {
                        levelTable.addView(newRow, lvlCount + 1);
                        lvlCount++;
                    }
                }
                else {
                    // prevo method
                    LinearLayout newLinLay = new LinearLayout(this.getContext());
                    newLinLay.setOrientation(LinearLayout.VERTICAL);
                    img = new ImageView(this.getContext());
                    img.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.g1_001_1 + ((list.get(i).getPrEvo() - 1) * 3), null));
                    textView = new TextView(this.getContext());
                    textView.setText(String.format(Locale.getDefault(), "Lv.%d", list.get(i).getLevel()));
                    newLinLay.addView(img, 0);
                    newLinLay.addView(textView, 1);
                    newRow.addView(newLinLay, 0);

                    // move name
                    textView = new TextView(this.getContext());
                    textView.setText(moveName);
                    newRow.addView(textView, 1);

                    finishMovesetEntry(move, newRow);

                    prevoTable.addView(newRow, prevoCount + 1);
                    prevoCount++;

                }
            }
            else if (list.get(i).getLevel() == -1) {

                // TM/HM Num
                textView = new TextView(this.getContext());
                if (move.getTmNum() > 0)
                    tempString = "TM" + dFormat3.format(move.getTmNum());
                else
                    tempString = "HM" + dFormat3.format(-move.getTmNum());
                textView.setText(tempString);
                newRow.addView(textView, 0);

                // move name
                textView = new TextView(this.getContext());
                tempString = moveName;
                if(list.get(i).isYellow())
                    tempString += getString(R.string.yellow_only);
                textView.setText(tempString);
                newRow.addView(textView, 1);

                finishMovesetEntry(move, newRow);

                tmTable.addView(newRow, tmCount + 1);
                tmCount++;

            }
            else if (list.get(i).getLevel() == -2) {

                // method
                textView = new TextView(this.getContext());
                textView.setText(list.get(i).getSource());
                newRow.addView(textView, 0);

                // move name
                textView = new TextView(this.getContext());
                tempString = moveName;
                if(list.get(i).isYellow())
                    tempString += R.string.yellow_only;
                textView.setText(tempString);
                newRow.addView(textView, 1);

                finishMovesetEntry(move, newRow);

                specialTable.addView(newRow, specialCount + 1);
                specialCount++;
            }
        }

        if (tmCount == 0) {
            tmTable.removeAllViews();
            binding.tmMoveTxt.setText("");
        }
        if (specialCount == 0) {
            specialTable.removeAllViews();
            binding.specialMoveTxt.setText("");
        }
        if (prevoCount == 0) {
            prevoTable.removeAllViews();
            binding.prevoMoveTxt.setText("");
        }
        if (ylwCount == 0) {
            binding.levelTxt.setText(R.string.generation_1_level_up_);
            binding.yellowLevelTxt.setText("");
            yellowTable.removeAllViews();
        }
    }*/

    /**
     * finishMovesetEntry method - FIXME
     * @param move
     * @param row
     */
/*    private void finishMovesetEntry(ModelMove move, TableRow row) {
        // move type
        ImageView img = new ImageView(this.getContext());
        img.setImageDrawable(PokemonMethods.getTypeImg(res, move.getType()));
        row.addView(img, 2);

        // Base power
        TextView textView = new TextView(this.getContext());
        textView.setText(String.format(Locale.getDefault(), "%d", move.getPower()));
        row.addView(textView, 3);

        // Accuracy
        textView = new TextView(this.getContext());
        textView.setText(dFormat.format(move.getAccuracy()));
        row.addView(textView, 4);

        // PP
        textView = new TextView(this.getContext());
        textView.setText(String.format(Locale.getDefault(), "%d", move.getPp()));
        row.addView(textView, 5);

        // Effect Rate
        textView = new TextView(this.getContext());
        if (move.getEffectRate() == 0)
            textView.setText("--");
        else
            textView.setText(String.format(Locale.getDefault(), "%d", move.getEffectRate()));
        row.addView(textView, 6);

        // Effect
        textView = new TextView(this.getContext());
        textView.setText(move.getEffect());
        row.addView(textView, 7);
    }*/

}

