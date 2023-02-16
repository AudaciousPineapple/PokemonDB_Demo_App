package com.example.pokemondbappv2.pokedex.fragments;

import static com.example.pokemondbappv2.PokemonMethods.fixLocationNameG1;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokemondbappv2.PokemonMethods;
import com.example.pokemondbappv2.R;
import com.example.pokemondbappv2.pokeEnums.Type;
import com.example.pokemondbappv2.databinding.FragmentPokemonG1DataBinding;
import com.example.pokemondbappv2.pokeEnums.XpGrowth;
import com.example.pokemondbappv2.pokedex.apiclasses.APICalls;
import com.example.pokemondbappv2.pokedex.databaseclasses.EncounterG1Entry;
import com.example.pokemondbappv2.pokedex.databaseclasses.EncounterG1Source;
import com.example.pokemondbappv2.pokedex.databaseclasses.PokemonEntryG1;
import com.example.pokemondbappv2.pokedex.databaseclasses.PokemonSourceG1;
import com.example.pokemondbappv2.pokedex.databaseclasses.bArrayConverter;

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
import java.util.ArrayList;

public class PokemonG1DataFragment extends Fragment {

    private FragmentPokemonG1DataBinding binding;
    private int dexNum;
    private static final DecimalFormat dFormat = new DecimalFormat("#000");
    private static final DecimalFormat dFormat2 = new DecimalFormat("0.#");
    private static final String NAME = "name";
    private Resources res;
    PokemonSourceG1 pokemonSource;
    EncounterG1Source encounterSource;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPokemonG1DataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        res = this.getResources();
        pokemonSource = new PokemonSourceG1(this.getContext());
        encounterSource = new EncounterG1Source(this.getContext());

        getParentFragmentManager().setFragmentResultListener("dex_num", this,
                ((requestKey, result) -> {
                    dexNum = result.getInt("dex_num");
                    // Log.d ("**TESTING**", Integer.toString(dexNum));

                    if (!pokemonSource.isCached(dexNum))
                        new getPokemonDataAPI().execute();
                    else {
                        PokemonEntryG1 pokemon = pokemonSource.getPokemonEntry(dexNum);
                        Log.d("**Pull from DB**", pokemon.toString());
                        getPokemonData(pokemon);
                    }
                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        pokemonSource.close();
    }

    private class getPokemonDataAPI extends AsyncTask<Void, Void, Void> implements APICalls.OnTaskCompleted {
        String speciesResult = "";
        String pokemonResult = "";
        String locationsResult = "";
        PokemonEntryG1 pokemon;
        ArrayList<EncounterG1Entry> encounters;

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

                tempStr = "https://pokeapi.co/api/v2/pokemon/" + dexNum + "/encounters";
                url = new URL(tempStr);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                inputStream = urlConnection.getInputStream();

                if (inputStream == null)
                    return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));

                while ((tempStr = reader.readLine()) != null)
                    locationsResult += tempStr;

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
            pokemon = new PokemonEntryG1();
            encounters = new ArrayList<EncounterG1Entry>();

            boolean done = false;

            try {
                JSONObject speciesObj = new JSONObject(speciesResult);
                JSONObject pokemonObj = new JSONObject(pokemonResult);
                JSONArray locationsArr = new JSONArray(locationsResult);

                // Set Dex Num
                binding.g1PokemonNum.setText("#" + dFormat.format(dexNum));
                pokemon.setDexNum(dexNum);
                Log.d("**Num**", Integer.toString(dexNum));

                // Set Name
                String pokemonName = PokemonMethods.fixPokemonName(speciesObj.getString(NAME));
                binding.g1PokemonName.setText(pokemonName);
                pokemon.setName(pokemonName);
                Log.d("**Name**", pokemonName);

                // Sets the alternate language names
                JSONArray nameArray = speciesObj.getJSONArray("names");

                for (int i = 0; i < nameArray.length(); i++) {
                    String language = nameArray.getJSONObject(i)
                            .getJSONObject("language").getString("name");
                    String name = nameArray.getJSONObject(i).getString("name");

                    if (language.contentEquals("ja")) {
                        binding.g1PokemonNameJp.setText(name);
                        pokemon.setNameJp(name);
                        Log.d("**Japanese Name**", name);
                    }
                    else if (language.contentEquals("roomaji")) {
                        binding.g1PokemonNameJpEng.setText(name);
                        pokemon.setNameJpEng(name);
                        Log.d("**Jp-English Name**", name);
                    }
                    else if (language.contentEquals("fr")) {
                        binding.g1PokemonNameFr.setText(name);
                        pokemon.setNameFr(name);
                        Log.d("**French Name**", name);
                    }
                    else if (language.contentEquals("de")) {
                        binding.g1PokemonNameGer.setText(name);
                        pokemon.setNameGer(name);
                        Log.d("**German Name**", name);
                    }
                    else if (language.contentEquals("ko")) {
                        binding.g1PokemonNameKor.setText(name);
                        pokemon.setNameKor(name);
                        Log.d("**Korean Name**", name);
                    }
                }

                // sets the types
                JSONArray typeArray;
                if (pokemonObj.getJSONArray("past_types").length() == 0)
                    typeArray = pokemonObj.getJSONArray("types");
                else
                    typeArray = pokemonObj.getJSONArray("past_types").getJSONObject(0)
                            .getJSONArray("types");

                Type type1 = Type.checkType(typeArray.getJSONObject(0)
                        .getJSONObject("type").getString("name"));
                PokemonMethods.setTypeImage(res, type1, binding.g1PokemonType1);
                pokemon.setType1(type1);
                Log.d("**Type 1**", type1.getName());

                if (typeArray.length() > 1) {
                    Type type2 = Type.checkType(typeArray.getJSONObject(1)
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
                JSONArray classArray;
                classArray = speciesObj.optJSONArray("genera");
                for (int i = 0; i < classArray.length(); i++) {
                    if ("en".contentEquals(classArray.getJSONObject(i).getJSONObject("language")
                            .getString("name"))) {
                        String cls = classArray.getJSONObject(i).getString("genus");
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
                XpGrowth xpGrowth = XpGrowth.getGrowthRate(speciesObj.getJSONObject("growth_rate")
                        .getString("name"));
                String xpString = PokemonMethods.formatXpString(xpGrowth);
                binding.g1PokemonXpGrowth.setText(xpString);
                pokemon.setXpRate(xpGrowth);
                Log.d("**XP Growth Rate**", xpString);

                // Set Stats & EV Earned values
                JSONArray statsArray = pokemonObj.getJSONArray("stats");

                // HP
                String baseHp = statsArray.getJSONObject(0).getString("base_stat");
                binding.g1PokemonBaseHp.setText(baseHp);
                pokemon.setBaseHp(Integer.parseInt(baseHp));
                binding.g1PokemonEvHp.setText(baseHp + " HP");
                Log.d("**Base HP**", baseHp);

                int hp = Integer.parseInt(baseHp);
                int hpMin50 = PokemonMethods.getHpMinG1G2(hp, 50);
                int hpMax50 = PokemonMethods.getHpMaxG1G2(hp, 50);
                int hpMin100 = PokemonMethods.getHpMinG1G2(hp, 100);
                int hpMax100 = PokemonMethods.getHpMaxG1G2(hp, 100);

                binding.g1Pokemon50Hp.setText(formatStatRange(hpMin50, hpMax50));
                pokemon.setMinHp50(hpMin50);
                pokemon.setMaxHp50(hpMax50);
                binding.g1Pokemon100Hp.setText(formatStatRange(hpMin100, hpMax100));
                pokemon.setMinHp100(hpMin100);
                pokemon.setMaxHp100(hpMax100);

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
                        baseAtk = statsArray.getJSONObject(1).getString("base_stat");
                        break;
                }

                binding.g1PokemonBaseAtk.setText(baseAtk);
                pokemon.setBaseAtk(Integer.parseInt(baseAtk));
                binding.g1PokemonEvAttack.setText(baseAtk + " Attack");
                Log.d("**Base Attack**", baseAtk);

                int atk = Integer.parseInt(baseAtk);
                int atkMin50 = PokemonMethods.getNotHpMinG1G2(atk, 50);
                int atkMax50 = PokemonMethods.getNotHpMaxG1G2(atk, 50);
                int atkMin100 = PokemonMethods.getNotHpMinG1G2(atk, 100);
                int atkMax100 = PokemonMethods.getNotHpMaxG1G2(atk, 100);

                binding.g1Pokemon50Atk.setText(formatStatRange(atkMin50, atkMax50));
                pokemon.setMinAtk50(atkMin50);
                pokemon.setMaxAtk50(atkMax50);
                binding.g1Pokemon100Atk.setText(formatStatRange(atkMin100, atkMax100));
                pokemon.setMinAtk100(atkMin100);
                pokemon.setMaxAtk100(atkMax100);

                // Defence
                String baseDef;
                if (dexNum == 25) {
                    baseDef = "30";
                } else {
                    baseDef = statsArray.getJSONObject(2).getString("base_stat");
                }
                binding.g1PokemonBaseDef.setText(baseDef);
                pokemon.setBaseDef(Integer.parseInt(baseDef));
                binding.g1PokemonEvDefence.setText(baseDef + " Defence");
                Log.d("**Base Defence**", baseDef);

                int def = Integer.parseInt(baseDef);
                int defMin50 = PokemonMethods.getNotHpMinG1G2(def, 50);
                int defMax50 = PokemonMethods.getNotHpMaxG1G2(def, 50);
                int defMin100 = PokemonMethods.getNotHpMinG1G2(def, 100);
                int defMax100 = PokemonMethods.getNotHpMaxG1G2(def, 100);

                binding.g1Pokemon50Def.setText(formatStatRange(defMin50, defMax50));
                pokemon.setMinDef50(defMin50);
                pokemon.setMaxDef50(defMax50);
                binding.g1Pokemon100Def.setText(formatStatRange(defMin100, defMax100));
                pokemon.setMinDef100(defMin100);
                pokemon.setMaxDef100(defMax100);

                // Special
                String baseSpc = Integer.toString(PokemonMethods.specialStats[dexNum-1]);
                binding.g1PokemonBaseSpc.setText(baseSpc);
                pokemon.setBaseSpc(Integer.parseInt(baseSpc));
                binding.g1PokemonEvSpecial.setText(baseSpc + " Special");
                Log.d("**Base Special", baseSpc);

                int spc = Integer.parseInt(baseSpc);
                int spcMin50 = PokemonMethods.getNotHpMinG1G2(spc, 50);
                int spcMax50 = PokemonMethods.getNotHpMaxG1G2(spc, 50);
                int spcMin100 = PokemonMethods.getNotHpMinG1G2(spc, 100);
                int spcMax100 = PokemonMethods.getNotHpMaxG1G2(spc, 100);

                binding.g1Pokemon50Spc.setText(formatStatRange(spcMin50, spcMax50));
                pokemon.setMinSpc50(spcMin50);
                pokemon.setMaxSpc50(spcMax50);
                binding.g1Pokemon100Spc.setText(formatStatRange(spcMin100, spcMax100));
                pokemon.setMinSpc100(spcMin100);
                pokemon.setMaxSpc100(spcMax100);

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
                        baseSpe = statsArray.getJSONObject(5).getString("base_stat");
                        break;
                }
                binding.g1PokemonBaseSpe.setText(baseSpe);
                pokemon.setBaseSpe(Integer.parseInt(baseSpe));
                binding.g1PokemonEvSpeed.setText(baseSpe + " Speed");
                Log.d("**Base Speed**", baseSpe);

                int spe = Integer.parseInt(baseSpe);
                int speMin50 = PokemonMethods.getNotHpMinG1G2(spe, 50);
                int speMax50 = PokemonMethods.getNotHpMaxG1G2(spe, 50);
                int speMin100 = PokemonMethods.getNotHpMinG1G2(spe, 100);
                int speMax100 = PokemonMethods.getNotHpMaxG1G2(spe, 100);

                binding.g1Pokemon50Spe.setText(formatStatRange(speMin50, speMax50));
                pokemon.setMinSpe50(speMin50);
                pokemon.setMaxSpe50(speMax50);
                binding.g1Pokemon100Spe.setText(formatStatRange(speMin100, speMax100));
                pokemon.setMinSpe100(speMin100);
                pokemon.setMaxSpe100(speMax100);

                // Sets the sprite images
                String sprite1Url = pokemonObj.getJSONObject("sprites").getJSONObject("versions")
                        .getJSONObject("generation-i").getJSONObject("red-blue")
                        .getString("front_transparent");
                Log.d("**Sprite 1 URL**", sprite1Url);
                new APICalls.SpriteLoadTask(sprite1Url, binding.g1PokemonSprite1, true,
                        pokemon, this).execute();

                String sprite2Url = pokemonObj.getJSONObject("sprites").getJSONObject("versions")
                        .getJSONObject("generation-i").getJSONObject("yellow")
                        .getString("front_transparent");
                Log.d("**Sprite 2 URL**", sprite2Url);
                new APICalls.SpriteLoadTask(sprite2Url, binding.g1PokemonSprite2, false,
                        pokemon, this).execute();

                // TODO Evolutionary Chain graphic
                String evoChainUrl = speciesObj.getJSONObject("evolution_chain").getString("url");
                Log.d("**Evo Chain URL**", evoChainUrl);

                //Locations
                //TODO add to encountersG1 db table
                String redLocationStr = "";
                String blueLocationStr = "";
                String yellowLocationStr = "";

                for (int i = 0; i < locationsArr.length(); i++) {
                    String locationName = locationsArr.getJSONObject(i).getJSONObject("location_area")
                            .getString("name");

                    JSONArray tempArr = locationsArr.getJSONObject(i).getJSONArray("version_details");
                    for (int j = 0; j < tempArr.length(); j++) {
                        JSONObject details = tempArr.getJSONObject(j)
                                .getJSONArray("encounter_details").getJSONObject(0);

                        String method = details.getJSONObject("method").getString("name");

                        int chance = details.getInt("chance");
                        int minLevel = details.getInt("min_level");
                        int maxLevel = details.getInt("max_level");

                        String versionName = tempArr.getJSONObject(j).getJSONObject("version")
                                .getString("name");

                        EncounterG1Entry encounter = new EncounterG1Entry(0, dexNum, locationName,
                                "", method, chance, minLevel, maxLevel, versionName);
                        encounters.add(encounter);

                        if (versionName.contentEquals("red")) {
                            if (!redLocationStr.isEmpty())
                                redLocationStr += ", ";
                            redLocationStr += fixLocationNameG1(locationName);
                        }
                        else if (versionName.contentEquals("blue")) {
                            if (!blueLocationStr.isEmpty())
                                blueLocationStr += ", ";
                            blueLocationStr += fixLocationNameG1(locationName);
                        }
                        else if (versionName.contentEquals("yellow")) {
                            if (!yellowLocationStr.isEmpty())
                                yellowLocationStr += ", ";
                            yellowLocationStr += fixLocationNameG1(locationName);
                        }
                    }
                }
                if (redLocationStr.isEmpty())
                    binding.g1PokemonRedLocations.setText(R.string.evolution);
                else
                    binding.g1PokemonRedLocations.setText(
                            PokemonMethods.fixLocationNameG1(redLocationStr));

                if (blueLocationStr.isEmpty())
                    binding.g1PokemonBlueLocations.setText(R.string.evolution);
                else
                    binding.g1PokemonBlueLocations.setText(
                            PokemonMethods.fixLocationNameG1(blueLocationStr));

                if (yellowLocationStr.isEmpty())
                    binding.g1PokemonYellowLocations.setText(R.string.evolution);
                else
                    binding.g1PokemonYellowLocations.setText(yellowLocationStr);

            } catch (JSONException e) {
                Log.i("JSON Parsing", "EXCEPTION: " + e.getMessage());
            }

        }

        @Override
        public void OnTaskCompleted() {
            pokemonSource.addPokemon(pokemon);

            for (int i = 0; i < encounters.size(); i++) {
                Log.d("Encounter " + i + ": ", encounters.toString());
                encounterSource.addEncounter(encounters.get(i));
            }
            //Log.d("**DATA_TESTING**", String.valueOf(pokemon.getSprite1().length));
            //Log.d("**DATA_TESTING**", String.valueOf(pokemon.getSprite2().length));
        }
    }

    /**
     * FIXME
     * @param pokemon
     */
    private void getPokemonData(PokemonEntryG1 pokemon) {
        binding.g1PokemonNum.setText("#" + dFormat.format(pokemon.getDexNum()));
        binding.g1PokemonName.setText(pokemon.getName());

        String bArray = pokemon.getSprite1();
        binding.g1PokemonSprite1.setImageBitmap(bArrayConverter.byteToBitmap(bArray));
        bArray = pokemon.getSprite2();
        binding.g1PokemonSprite2.setImageBitmap(bArrayConverter.byteToBitmap(bArray));

        binding.g1PokemonNameJp.setText(pokemon.getNameJp());
        binding.g1PokemonNameJpEng.setText(pokemon.getNameJpEng());
        binding.g1PokemonNameFr.setText(pokemon.getNameFr());
        binding.g1PokemonNameGer.setText(pokemon.getNameGer());
        binding.g1PokemonNameKor.setText(pokemon.getNameKor());

        PokemonMethods.setTypeImage(res, pokemon.getType1(), binding.g1PokemonType1);
        if (!PokemonMethods.setTypeImage(res, pokemon.getType2(), binding.g1PokemonType2))
            binding.g1PokemonType2.setVisibility(View.GONE);

        binding.g1PokemonClass.setText(pokemon.getClassification());
        BigDecimal heightDec = new BigDecimal(pokemon.getHeight()).movePointLeft(1);
        binding.g1PokemonHeight.setText(heightDec.toString() + "m");
        BigDecimal weightDec = new BigDecimal(pokemon.getWeight()).movePointLeft(1);
        binding.g1PokemonWeight.setText(weightDec.toString() + "kg");
        binding.g1PokemonCapRate.setText(String.valueOf(pokemon.getCapRate()));
        binding.g1PokemonXpGrowth.setText(PokemonMethods.formatXpString(pokemon.getXpRate()));

        String hpString = Integer.toString(pokemon.getBaseHp());
        binding.g1PokemonBaseHp.setText(hpString);
        hpString += " HP";
        binding.g1PokemonEvHp.setText(hpString);
        binding.g1Pokemon50Hp.setText(formatStatRange(pokemon.getMinAtk50(), pokemon.getMaxAtk50()));
        binding.g1Pokemon100Hp.setText(formatStatRange(pokemon.getMinHp100(), pokemon.getMaxHp100()));

        String atkString = Integer.toString(pokemon.getBaseAtk());
        binding.g1PokemonBaseAtk.setText(atkString);
        atkString += " Attack";
        binding.g1PokemonEvAttack.setText(atkString);
        binding.g1Pokemon50Atk.setText(formatStatRange(pokemon.getMinAtk50(), pokemon.getMaxAtk50()));
        binding.g1Pokemon100Atk.setText(formatStatRange(pokemon.getMinAtk100(), pokemon.getMaxAtk100()));

        String defString = Integer.toString(pokemon.getBaseDef());
        binding.g1PokemonBaseDef.setText(defString);
        defString += " Defence";
        binding.g1PokemonEvDefence.setText(defString);
        binding.g1Pokemon50Def.setText(formatStatRange(pokemon.getMinDef50(), pokemon.getMaxDef50()));
        binding.g1Pokemon100Def.setText(formatStatRange(pokemon.getMinDef100(), pokemon.getMaxDef100()));

        String spcString = Integer.toString(pokemon.getBaseSpc());
        binding.g1PokemonBaseSpc.setText(spcString);
        spcString += " Special";
        binding.g1PokemonEvSpecial.setText(spcString);
        binding.g1Pokemon50Spc.setText(formatStatRange(pokemon.getMinSpc50(), pokemon.getMaxSpc50()));
        binding.g1Pokemon100Spc.setText(formatStatRange(pokemon.getMinSpc100(), pokemon.getMaxSpc100()));

        String speString = Integer.toString(pokemon.getBaseSpe());
        binding.g1PokemonBaseSpe.setText(speString);
        speString += " Speed";
        binding.g1PokemonEvSpeed.setText(speString);
        binding.g1Pokemon50Spe.setText(formatStatRange(pokemon.getMinSpe50(), pokemon.getMaxSpe50()));
        binding.g1Pokemon100Spe.setText(formatStatRange(pokemon.getMinSpe100(), pokemon.getMaxSpe100()));
    }

    private String formatStatRange(int min, int max) {
        return min + " - " + max;
    }

}