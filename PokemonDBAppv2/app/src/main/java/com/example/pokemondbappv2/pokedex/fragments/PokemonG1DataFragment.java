package com.example.pokemondbappv2.pokedex.fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
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
import com.example.pokemondbappv2.pokedex.databaseclasses.PokemonEntryG1;
import com.example.pokemondbappv2.pokedex.databaseclasses.PokemonSourceG1;

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
import java.util.concurrent.ExecutionException;

public class PokemonG1DataFragment extends Fragment {

    private FragmentPokemonG1DataBinding binding;
    private int dexNum;
    private static final DecimalFormat dFormat = new DecimalFormat("#000");
    private static final DecimalFormat dFormat2 = new DecimalFormat("0.#");
    private static final String NAME = "name";
    private Resources res;
    PokemonSourceG1 source;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPokemonG1DataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        res = this.getResources();
        source = new PokemonSourceG1(this.getContext());

        getParentFragmentManager().setFragmentResultListener("dex_num", this,
                ((requestKey, result) -> {
                    dexNum = result.getInt("dex_num");
                    Log.d ("**TESTING**", Integer.toString(dexNum));

                    if (!source.isCached(dexNum))
                        new getPokemonDataAPI().execute();
                    else {
                        PokemonEntryG1 pokemon = source.getPokemonEntry(dexNum);


                    }
                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class getPokemonDataAPI extends AsyncTask<Void, Void, Void> {
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
            PokemonEntryG1 pokemon = new PokemonEntryG1();

            try {
                JSONObject speciesObj = new JSONObject(speciesResult);
                JSONObject pokemonObj = new JSONObject(pokemonResult);

                // Set Dex Num
                binding.g1PokemonNum.setText("#" + dFormat.format(dexNum));
                pokemon.setDexNum(dexNum);
                Log.d("**Num**", Integer.toString(dexNum));

                // Set Name
                String pokemonName = PokemonMethods.fixPokemonName(speciesObj.getString(NAME));
                binding.g1PokemonName.setText(pokemonName);
                pokemon.setName(pokemonName);
                Log.d("**Name**", pokemonName);

                // Sets the sprite images
                String sprite1Url = pokemonObj.getJSONObject("sprites").getJSONObject("versions")
                        .getJSONObject("generation-i").getJSONObject("red-blue")
                        .getString("front_transparent");
                Log.d("**Sprite 1 URL**", sprite1Url);
                APICalls.ImageLoadTask spriteLoader = new APICalls.ImageLoadTask(sprite1Url, binding.g1PokemonSprite1);
                spriteLoader.execute();
                pokemon.setSprite1(spriteLoader.getBitmap());

                String sprite2Url = pokemonObj.getJSONObject("sprites").getJSONObject("versions")
                        .getJSONObject("generation-i").getJSONObject("yellow")
                        .getString("front_transparent");
                Log.d("**Sprite 2 URL**", sprite2Url);
                spriteLoader = new APICalls.ImageLoadTask(sprite2Url, binding.g1PokemonSprite2);
                spriteLoader.execute();
                pokemon.setSprite2(spriteLoader.getBitmap());

                // Sets the alternate language names
                JSONArray tempArray = speciesObj.getJSONArray("names");

                String nameJp = tempArray.getJSONObject(0).getString(NAME);
                binding.g1PokemonNameJp.setText(nameJp);
                pokemon.setNameJp(nameJp);
                Log.d("**Japanese Name**", nameJp);

                String nameJpEng = tempArray.getJSONObject(1).getString(NAME);
                binding.g1PokemonNameJpEng.setText(nameJpEng);
                pokemon.setNameJpEng(nameJpEng);
                Log.d("**Jp-English Name**", nameJpEng);

                String nameFr = tempArray.getJSONObject(4).getString(NAME);
                binding.g1PokemonNameFr.setText(nameFr);
                pokemon.setNameFr(nameFr);
                Log.d("**French Name**", nameFr);

                String nameGer = tempArray.getJSONObject(5).getString(NAME);
                binding.g1PokemonNameGer.setText(nameGer);
                pokemon.setNameGer(nameGer);
                Log.d("**German Name**", nameGer);

                String nameKor = tempArray.getJSONObject(2).getString(NAME);
                binding.g1PokemonNameKor.setText(nameKor);
                pokemon.setNameKor(nameKor);
                Log.d("**Korean Name**", nameKor);

                // sets the types
                if (pokemonObj.getJSONArray("past_types").length() == 0)
                    tempArray = pokemonObj.getJSONArray("types");
                else
                    tempArray = pokemonObj.getJSONArray("past_types").getJSONObject(0)
                            .getJSONArray("types");

                Type type1 = Type.checkType(tempArray.getJSONObject(0)
                        .getJSONObject("type").getString("name"));
                PokemonMethods.setTypeImage(res, type1, binding.g1PokemonType1);
                pokemon.setType1(type1);
                Log.d("**Type 1**", type1.getName());

                if (tempArray.length() > 1) {
                    Type type2 = Type.checkType(tempArray.getJSONObject(1)
                            .getJSONObject("type").getString("name"));
                    PokemonMethods.setTypeImage(res, type2, binding.g1PokemonType2);
                    pokemon.setType2(type2);
                    Log.d("**Type 2**", type2.getName());
                }
                else {
                    pokemon.setType2(Type.UNK);
                    binding.g1PokemonType2.setVisibility(View.GONE);
                }

                // Sets the classification
                tempArray = speciesObj.optJSONArray("genera");
                for (int i = 0; i < tempArray.length(); i++) {
                    if ("en".contentEquals(tempArray.getJSONObject(i).getJSONObject("language")
                            .getString("name"))) {
                        String cls = tempArray.getJSONObject(i).getString("genus");
                        binding.g1PokemonClass.setText(cls);
                        pokemon.setClassification(cls);
                        Log.d("**Classification**", cls);
                        break;
                    }
                }

                // Sets the height
                int height = pokemonObj.getInt("height");
                BigDecimal heightDec = new BigDecimal(height).movePointLeft(1);
                String heightStr = dFormat2.format(heightDec) + "m";
                binding.g1PokemonHeight.setText(heightStr);
                pokemon.setHeight(height);
                Log.d("**Height**", heightStr);

                // Sets the weight
                int weight = pokemonObj.getInt("weight");
                BigDecimal weightDec = new BigDecimal(weight).movePointLeft(1);
                String weightStr = dFormat2.format(weightDec) + "kg";
                binding.g1PokemonWeight.setText(weightStr);
                pokemon.setWeight(weight);
                Log.d("**Weight**", weightStr);

                // Set the capture rate
                String capRate = speciesObj.getString("capture_rate");
                binding.g1PokemonCapRate.setText(capRate);
                pokemon.setCapRate(Integer.parseInt(capRate));
                Log.d("**Capture Rate**", capRate);

                // Set the XP Growth
                APICalls.GetXpRateInfo xpRateClass = new APICalls.GetXpRateInfo(
                        speciesObj.getJSONObject("growth_rate").getString("url"),
                        binding.g1PokemonXpGrowth);
                xpRateClass.execute();
                String xpString = xpRateClass.getXpRateResult();
                pokemon.setXpRate(xpString);
                Log.d("**XP Growth Rate**", xpString);

                // Set Stats & EV Earned values
                tempArray = pokemonObj.getJSONArray("stats");

                // HP
                String baseHp = tempArray.getJSONObject(0).getString("base_stat");
                binding.g1PokemonBaseHp.setText(baseHp);
                pokemon.setBaseHp(Integer.parseInt(baseHp));
                baseHp += " HP";
                binding.g1PokemonEvHp.setText(baseHp);
                Log.d("**Base HP**", baseHp);

                // Attack
                String baseAtk;
                switch (dexNum) {
                    case 15:
                    case 51:
                        baseAtk = "80";
                        break;
                    case 24:
                    case 62:
                        baseAtk = "85";
                        break;
                    case 31:
                        baseAtk = "82";
                        break;
                    case 34:
                        baseAtk = "92";
                        break;
                    case 76:
                        baseAtk = "110";
                        break;
                    case 83:
                        baseAtk = "65";
                        break;
                    default:
                        baseAtk = tempArray.getJSONObject(1).getString("base_stat");
                        break;
                }
                binding.g1PokemonBaseAtk.setText(baseAtk);
                pokemon.setBaseAtk(Integer.parseInt(baseAtk));
                baseAtk += " Attack";
                binding.g1PokemonEvAttack.setText(baseAtk);
                Log.d("**Base Attack**", baseAtk);

                // Defence
                String baseDef;
                if (dexNum == 25) {
                    baseDef = "30";
                } else {
                    baseDef = tempArray.getJSONObject(2).getString("base_stat");
                }
                binding.g1PokemonBaseDef.setText(baseDef);
                pokemon.setBaseDef(Integer.parseInt(baseDef));
                baseDef += " Defence";
                binding.g1PokemonEvDefence.setText(baseDef);
                Log.d("**Base Defence**", baseDef);

                // Special
                String baseSpc = Integer.toString(PokemonMethods.specialStats[dexNum-1]);
                binding.g1PokemonBaseSpc.setText(baseSpc);
                pokemon.setBaseSpc(Integer.parseInt(baseSpc));
                baseSpc += " Special";
                binding.g1PokemonEvSpecial.setText(baseSpc);
                Log.d("**Base Special", baseSpc);

                // Speed
                String baseSpe;
                switch (dexNum) {
                    case 18:
                        baseSpe = "91";
                        break;
                    case 26:
                    case 85:
                        baseSpe = "100";
                        break;
                    case 101:
                        baseSpe = "140";
                        break;
                    default:
                        baseSpe = tempArray.getJSONObject(5).getString("base_stat");
                        break;
                }
                binding.g1PokemonBaseSpe.setText(baseSpe);
                pokemon.setBaseSpe(Integer.parseInt(baseSpe));
                baseSpe += " Speed";
                binding.g1PokemonEvSpeed.setText(baseSpe);
                Log.d("**Base Speed**", baseSpe);

                source.addPokemon(pokemon);

                // Evolutionary Chain graphic
                String evoChainUrl = speciesObj.getJSONObject("evolution_chain").getString("url");
                Log.d("**Evo Chain URL**", evoChainUrl);





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
    /*
    private static class GetPokemonSpriteFromDexNum extends AsyncTask<Void, Void, Void> {
        private final String urlString;
        private String result;
        private ImageView imgView;

        public GetPokemonSpriteFromDexNum(int dexNum, ImageView imgView) {
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
    }*/

    private void getPokemonDataSQL(PokemonEntryG1 pokemon) {
        binding.g1PokemonNum.setText("#" + dFormat.format(pokemon.getDexNum()));
        binding.g1PokemonName.setText(pokemon.getName());



        binding.g1PokemonNameJp.setText(pokemon.getNameJp());
        binding.g1PokemonNameJpEng.setText(pokemon.getNameJpEng());
        binding.g1PokemonNameFr.setText(pokemon.getNameFr());
        binding.g1PokemonNameGer.setText(pokemon.getNameGer());
        binding.g1PokemonNameKor.setText(pokemon.getNameKor());
        PokemonMethods.setTypeImage(res, pokemon.getType1(), binding.g1PokemonType1);
        if (pokemon.getType2() != Type.UNK)
            PokemonMethods.setTypeImage(res, pokemon.getType2(), binding.g1PokemonType2);
        binding.g1PokemonClass.setText(pokemon.getClassification());
        binding.g1PokemonHeight.setText(String.valueOf(pokemon.getHeight()));
        binding.g1PokemonWeight.setText(String.valueOf(pokemon.getWeight()));
        binding.g1PokemonCapRate.setText(String.valueOf(pokemon.getCapRate()));
        binding.g1PokemonXpGrowth.setText(pokemon.getXpRate());
        binding.g1PokemonBaseHp.setText(String.valueOf(pokemon.getBaseHp()));
        binding.g1PokemonBaseAtk.setText(String.valueOf(pokemon.getBaseAtk()));
        binding.g1PokemonBaseDef.setText(String.valueOf(pokemon.getBaseDef()));
        binding.g1PokemonBaseSpc.setText(String.valueOf(pokemon.getBaseSpc()));
        binding.g1PokemonBaseSpe.setText(String.valueOf(pokemon.getBaseSpe()));
    }
}