package com.example.pokemondbappv2.pokedex.fragments;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pokemondbappv2.PokemonMethods;
import com.example.pokemondbappv2.pokeEnums.Type;
import com.example.pokemondbappv2.databinding.FragmentPokemonG1DataBinding;
import com.example.pokemondbappv2.pokedex.apiclasses.APICalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Objects;

public class PokemonG1DataFragment extends Fragment {

    private FragmentPokemonG1DataBinding binding;
    private int dexNum;
    private static final DecimalFormat dFormat = new DecimalFormat("#000");
    private static final DecimalFormat dFormat2 = new DecimalFormat("0.#");
    private static final String NAME = "name";
    private Resources res;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPokemonG1DataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        res = this.getResources();

        getParentFragmentManager().setFragmentResultListener("dex_num", this,
                ((requestKey, result) -> {
                    dexNum = result.getInt("dex_num");
                    new getPokemonData().execute();
                }));
    }

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

                // Set Dex Num
                binding.g1PokemonNum.setText("#" + dFormat.format(dexNum));

                // Set Name
                String pokemonName = PokemonMethods.fixPokemonName(speciesObj.getString(NAME));
                binding.g1PokemonName.setText(pokemonName);

                // Sets the sprite images
                String tempStr = pokemonObj.getJSONObject("sprites").getJSONObject("versions")
                        .getJSONObject("generation-i").getJSONObject("red-blue")
                        .getString("front_transparent");
                new APICalls.ImageLoadTask(tempStr, binding.g1PokemonSprite1).execute();

                tempStr = pokemonObj.getJSONObject("sprites").getJSONObject("versions")
                        .getJSONObject("generation-i").getJSONObject("yellow")
                        .getString("front_transparent");
                new APICalls.ImageLoadTask(tempStr, binding.g1PokemonSprite2).execute();

                // Sets the alternate language names
                JSONArray tempArray = speciesObj.getJSONArray("names");
                binding.g1PokemonNameJp.setText(tempArray.getJSONObject(0).getString(NAME));
                binding.g1PokemonNameJpEng.setText(tempArray.getJSONObject(1).getString(NAME));
                binding.g1PokemonNameFr.setText(tempArray.getJSONObject(4).getString(NAME));
                binding.g1PokemonNameGer.setText(tempArray.getJSONObject(5).getString(NAME));
                binding.g1PokemonNameKor.setText(tempArray.getJSONObject(2).getString(NAME));

                // sets the types
                if (pokemonObj.getJSONArray("past_types").length() == 0)
                    tempArray = pokemonObj.getJSONArray("types");
                else
                    tempArray = pokemonObj.getJSONArray("past_types").getJSONObject(0)
                            .getJSONArray("types");
                PokemonMethods.setTypeImage(res, Objects.requireNonNull(
                        Type.checkType(tempArray.getJSONObject(0)
                                .getJSONObject("type").getString("name"))),
                        binding.g1PokemonType1);
                if (tempArray.length() > 1)
                    PokemonMethods.setTypeImage(res, Objects.requireNonNull(
                            Type.checkType(tempArray.getJSONObject(1)
                                    .getJSONObject("type").getString("name"))),
                            binding.g1PokemonType2);
                else
                    binding.g1PokemonType2.setVisibility(View.GONE);

                // Sets the classification
                tempArray = speciesObj.optJSONArray("genera");
                for (int i = 0; i < tempArray.length(); i++) {
                    if ("en".contentEquals(tempArray.getJSONObject(i).getJSONObject("language")
                            .getString("name"))) {
                        binding.g1PokemonClass.setText(tempArray.getJSONObject(i)
                                .getString("genus"));
                        break;
                    }
                }

                // Sets the height
                BigDecimal height = new BigDecimal(pokemonObj.getInt("height")).movePointLeft(1);
                binding.g1PokemonHeight.setText(dFormat2.format(height) + "m");

                // Sets the weight
                BigDecimal weight = new BigDecimal(pokemonObj.getInt("weight")).movePointLeft(1);
                binding.g1PokemonWeight.setText(dFormat2.format(weight) + "kg");

                // Set the capture rate
                binding.g1PokemonCapRate.setText(speciesObj.getString("capture_rate"));

                // Set the XP Growth
                new APICalls.GetXpRateInfo(speciesObj.getJSONObject("growth_rate").getString("url"),
                        binding.g1PokemonXpGrowth).execute();

                // Set EV Earned values
                tempArray = pokemonObj.getJSONArray("stats");

                // HP
                tempStr = tempArray.getJSONObject(0).getString("base_stat");
                binding.g1PokemonBaseHp.setText(tempStr);
                tempStr += " HP";
                binding.g1PokemonEvHp.setText(tempStr);

                // Attack
                switch (dexNum) {
                    case 15:
                    case 51:
                        tempStr = "80";
                        break;
                    case 24:
                    case 62:
                        tempStr = "85";
                        break;
                    case 31:
                        tempStr = "82";
                        break;
                    case 34:
                        tempStr = "92";
                        break;
                    case 76:
                        tempStr = "110";
                        break;
                    case 83:
                        tempStr = "65";
                        break;
                    default:
                        tempStr = tempArray.getJSONObject(1).getString("base_stat");
                        break;
                }
                binding.g1PokemonBaseAtk.setText(tempStr);
                tempStr += " Attack";
                binding.g1PokemonEvAttack.setText(tempStr);

                // Defence
                if (dexNum == 25) {
                    tempStr = "30";
                } else {
                    tempStr = tempArray.getJSONObject(2).getString("base_stat");
                }
                binding.g1PokemonBaseDef.setText(tempStr);
                tempStr += " Defence";
                binding.g1PokemonEvDefence.setText(tempStr);

                // Special
                tempStr = Integer.toString(PokemonMethods.specialStats[dexNum-1]);
                binding.g1PokemonBaseSpc.setText(tempStr);
                tempStr += " Special";
                binding.g1PokemonEvSpecial.setText(tempStr);

                // Speed
                switch (dexNum) {
                    case 18:
                        tempStr = "91";
                        break;
                    case 26:
                    case 85:
                        tempStr = "100";
                        break;
                    case 101:
                        tempStr = "140";
                        break;
                    default:
                        tempStr = tempArray.getJSONObject(5).getString("base_stat");
                        break;
                }
                binding.g1PokemonBaseSpe.setText(tempStr);
                tempStr += " Speed";
                binding.g1PokemonEvSpeed.setText(tempStr);

                // Evolutionary Chain graphic
                tempStr = speciesObj.getJSONObject("evolution_chain").getString("url");
                Log.d("**TESTING**", tempStr);





                /*Log.d("**TESTING**", tempObj.getString("id"));

                tempStr = tempObj.getJSONObject("species").getString("url");
                int tempInt = Integer.parseInt(tempStr.substring(tempStr.indexOf
                        ('/', tempStr.lastIndexOf('/') - 3), tempStr.lastIndexOf('/')));

                Log.d("**TESTING**", Integer.toString(tempInt));*/

            } catch (JSONException e) {
                Log.i("JSON Parsing", "EXCEPTION: " + e.getMessage());
            }

        }
    }

    private static class GetPokemonSpriteFromDexNun extends AsyncTask<Void, Void, Void> {
        private final String urlString;
        private String result;
        private ImageView imgView;

        public GetPokemonSpriteFromDexNun(int dexNum, ImageView imgView) {
            result = "";
            this.imgView = imgView;
            urlString = "https://pokeapi.co/api/v2/pokemon/" + dexNum;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection;
            BufferedReader reader;

            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null)
                    return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String tempString;
                while ((tempString = reader.readLine()) != null)
                    result += tempString;

                urlConnection.disconnect();
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void r) {
            super.onPostExecute(r);

            try {
                String imgUrl = new JSONObject(result).getJSONObject("sprites")
                        .getJSONObject("versions").getJSONObject("generation-i")
                        .getJSONObject("yellow").getString("front_transparent");

                new APICalls.ImageLoadTask(imgUrl, imgView).execute();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}