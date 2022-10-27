package com.example.pokemondbappv2;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.pokemondbappv2.databinding.FragmentPokemonDataBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PokemonDataFragment extends Fragment {

    private DecimalFormat dFormat, dFormat2, dFormat3;

    private FragmentPokemonDataBinding binding;
    private int dexNum;
    private DBHandler pokemonLookup;
    private ModelPokemonG1 pokemon;
    private LinearLayout evoLay;
    private Resources res;
    private TableLayout levelTable, yellowTable, tmTable, prevoTable;

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

        res = getResources();
        pokemonLookup = new DBHandler(this.getContext());
        dFormat = new DecimalFormat("0.0");
        dFormat2 = new DecimalFormat("#,###");
        dFormat3 = new DecimalFormat("00");
        evoLay = binding.evoLayout;
        levelTable = binding.levelMoveTable;
        yellowTable = binding.yellowLevelMoveTable;
        tmTable = binding.tmMoveTable;
        prevoTable = binding.prevoMoveTable;

        getParentFragmentManager().setFragmentResultListener("num", this,
                ((requestKey, result) -> {
                    dexNum = result.getInt("dex_num");
                    // Log.d("**TESTING**", "dexNum is "+dexNum);
                    pokemon = pokemonLookup.getPokemon(dexNum);
                    String tempStr;
                    int tempInt;

                    binding.nameText.setText(pokemon.getName());
                    binding.numText.setText(String.format(Locale.getDefault(), "%d", dexNum));

                    tempInt = setDexPics(dexNum);

                    setTypePic(pokemon.getType1(), true);
                    setTypePic(pokemon.getType2(), false);

                    tempStr = pokemon.getClassification() + getString(R.string.classification_2);
                    binding.classText.setText(tempStr);
                    binding.heightText.setText(dFormat.format(pokemon.getHeight()));
                    binding.weightText.setText(dFormat.format(pokemon.getWeight()));
                    binding.capRateText.setText(String.format(Locale.getDefault(), "%d", pokemon.getCaptureRate()));

                    tempStr = dFormat2.format(pokemon.getXpGrowth().getRate()) + " | "
                            + pokemon.getXpGrowth().getValue();
                    binding.xpRateText.setText(tempStr);

                    binding.hpText.setText(String.format(Locale.getDefault(), "%d", pokemon.getHp()));
                    binding.atkText.setText(String.format(Locale.getDefault(), "%d", pokemon.getAtk()));
                    binding.defText.setText(String.format(Locale.getDefault(), "%d", pokemon.getDef()));
                    binding.spcText.setText(String.format(Locale.getDefault(), "%d", pokemon.getSpc()));
                    binding.spdText.setText(String.format(Locale.getDefault(), "%d", pokemon.getSpe()));

                    setEvoImages(dexNum, pokemon.getEvo(), pokemon.getEvoLvl(), pokemon.getPrEvo(), tempInt);

                    binding.redLocationsTxt.setText(modLocString(pokemon.getRedLoc()));
                    binding.jpGreenUsBlueLocationsTxt.setText(modLocString(pokemon.getBlueLoc()));
                    binding.jpBlueLocationsTxt.setText(modLocString(pokemon.getGreenLoc()));
                    binding.yellowLocationsTxt.setText(modLocString(pokemon.getYellowLoc()));

                    setMoveset(dexNum);

                }));

    }

    /**
     * setTypePic method - Sets the resource for the selected ImageView for Pokemon Types
     * @param type - The type being used
     * @param type1 - true if the ImageView is for the pokemon's first type, false for the 2nd
     */
    private void setTypePic(Type type, boolean type1) {
        ImageView imgView;
        if (type1)
            imgView = binding.type1;
        else
            imgView = binding.type2;

        imgView.setImageDrawable(checkTypeImage(type));

    }

    private Drawable checkTypeImage(Type type) {
        switch (type) {
            case NOR:
                return ResourcesCompat.getDrawable(res, R.drawable.type_normal, null);
            case FIR:
                return ResourcesCompat.getDrawable(res, R.drawable.type_fire, null);
            case WAT:
                return ResourcesCompat.getDrawable(res, R.drawable.type_water, null);
            case GRA:
                return ResourcesCompat.getDrawable(res, R.drawable.type_grass, null);
            case ELE:
                return ResourcesCompat.getDrawable(res, R.drawable.type_electric, null);
            case ICE:
                return ResourcesCompat.getDrawable(res, R.drawable.type_ice, null);
            case FIG:
                return ResourcesCompat.getDrawable(res, R.drawable.type_fighting, null);
            case POI:
                return ResourcesCompat.getDrawable(res, R.drawable.type_poison, null);
            case GRO:
                return ResourcesCompat.getDrawable(res, R.drawable.type_ground, null);
            case FLY:
                return ResourcesCompat.getDrawable(res, R.drawable.type_flying, null);
            case PSY:
                return ResourcesCompat.getDrawable(res, R.drawable.type_psychic, null);
            case BUG:
                return ResourcesCompat.getDrawable(res, R.drawable.type_bug, null);
            case ROC:
                return ResourcesCompat.getDrawable(res, R.drawable.type_rock, null);
            case GHO:
                return ResourcesCompat.getDrawable(res, R.drawable.type_ghost, null);
            default:
                return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * setDexPics method - Sets the resources for the 3 main ImageViews for the pokemon's sprites
     * @param dexNum - The dex number of the pokemon who's images will be loaded
     * @return - The id of the 1st drawable resource used within the method
     */
    private int setDexPics(int dexNum) {
        int id = R.drawable.g1_001_1 + ((dexNum - 1) * 3);

        binding.sprite1.setImageDrawable(ResourcesCompat.getDrawable(res, id, null));
        binding.sprite2.setImageDrawable(ResourcesCompat.getDrawable(res, id + 1, null));
        binding.sprite3.setImageDrawable(ResourcesCompat.getDrawable(res, id + 2, null));

        return id;
        /*switch (dexNum) {
            case 1:
                binding.sprite1.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.g1_001_1, null));
                binding.sprite2.setImageDrawable(res.getDrawable(R.drawable.g1_001_2));
                binding.sprite3.setImageDrawable(res.getDrawable(R.drawable.g1_001_3));
                Log.d("**TESTING**", Integer.toString(R.drawable.g1_001_1));
                return res.getDrawable(R.drawable.g1_001_1);
            case 2:
                binding.sprite1.setImageDrawable(res.getDrawable(R.drawable.g1_002_1));
                binding.sprite2.setImageDrawable(res.getDrawable(R.drawable.g1_002_2));
                binding.sprite3.setImageDrawable(res.getDrawable(R.drawable.g1_002_3));
                return res.getDrawable(R.drawable.g1_002_1);
            case 3:
                binding.sprite1.setImageDrawable(res.getDrawable(R.drawable.g1_003_1));
                binding.sprite2.setImageDrawable(res.getDrawable(R.drawable.g1_003_2));
                binding.sprite3.setImageDrawable(res.getDrawable(R.drawable.g1_003_3));
                return res.getDrawable(R.drawable.g1_003_1);
            case 4:
                binding.sprite1.setImageDrawable(res.getDrawable(R.drawable.g1_004_1));
                binding.sprite2.setImageDrawable(res.getDrawable(R.drawable.g1_004_2));
                binding.sprite3.setImageDrawable(res.getDrawable(R.drawable.g1_004_3));
                return res.getDrawable(R.drawable.g1_004_1);
            case 5:
                binding.sprite1.setImageDrawable(res.getDrawable(R.drawable.g1_005_1));
                binding.sprite2.setImageDrawable(res.getDrawable(R.drawable.g1_005_2));
                binding.sprite3.setImageDrawable(res.getDrawable(R.drawable.g1_005_3));
                return res.getDrawable(R.drawable.g1_005_1);
            case 6:
                binding.sprite1.setImageDrawable(res.getDrawable(R.drawable.g1_006_1));
                binding.sprite2.setImageDrawable(res.getDrawable(R.drawable.g1_006_2));
                binding.sprite3.setImageDrawable(res.getDrawable(R.drawable.g1_006_3));
                return res.getDrawable(R.drawable.g1_006_1);
            case 7:
                binding.sprite1.setImageDrawable(res.getDrawable(R.drawable.g1_007_1));
                binding.sprite2.setImageDrawable(res.getDrawable(R.drawable.g1_007_2));
                binding.sprite3.setImageDrawable(res.getDrawable(R.drawable.g1_007_3));
                return res.getDrawable(R.drawable.g1_007_1);
            case 8:
                binding.sprite1.setImageDrawable(res.getDrawable(R.drawable.g1_008_1));
                binding.sprite2.setImageDrawable(res.getDrawable(R.drawable.g1_008_2));
                binding.sprite3.setImageDrawable(res.getDrawable(R.drawable.g1_008_3));
                return res.getDrawable(R.drawable.g1_008_1);
            case 9:
                binding.sprite1.setImageDrawable(res.getDrawable(R.drawable.g1_009_1));
                binding.sprite2.setImageDrawable(res.getDrawable(R.drawable.g1_009_2));
                binding.sprite3.setImageDrawable(res.getDrawable(R.drawable.g1_009_3));
                return res.getDrawable(R.drawable.g1_009_1);
            case 10:
                binding.sprite1.setImageDrawable(res.getDrawable(R.drawable.g1_010_1));
                binding.sprite2.setImageDrawable(res.getDrawable(R.drawable.g1_010_2));
                binding.sprite3.setImageDrawable(res.getDrawable(R.drawable.g1_010_3));
                return res.getDrawable(R.drawable.g1_010_1);
            case 11:
                binding.sprite1.setImageDrawable(res.getDrawable(R.drawable.g1_011_1));
                binding.sprite2.setImageDrawable(res.getDrawable(R.drawable.g1_011_2));
                binding.sprite3.setImageDrawable(res.getDrawable(R.drawable.g1_011_3));
                return res.getDrawable(R.drawable.g1_011_1);
            case 12:
                binding.sprite1.setImageDrawable(res.getDrawable(R.drawable.g1_012_1));
                binding.sprite2.setImageDrawable(res.getDrawable(R.drawable.g1_012_2));
                binding.sprite3.setImageDrawable(res.getDrawable(R.drawable.g1_012_3));
                return res.getDrawable(R.drawable.g1_012_1);
            default:
                return null;


        }*/
    }

    /**
     * setEvoImages - Sets the images in the pokemon's evolution graphic
     * @param evoNum - The dex num of the pokemon who is the evolution
     * @param prevoNum - The dex num of the pokemon who is the pre-evolution
     * @param drawId the int ID of the current pokemon's first drawable
     */
    private void setEvoImages(int curNum, int evoNum, int evoLvl, int prevoNum, int drawId) {
        int idx, pNum = prevoNum, eNum = evoNum, cNum = curNum, vNum = evoLvl;
        ImageView[] evoLayout = new ImageView[5];

        if (eNum == 0) {
            idx = 4;

            evoLayout[idx] = new ImageView(this.getContext());
            evoLayout[idx].setImageDrawable(ResourcesCompat.getDrawable(res, drawId, null));

            while (pNum != 0) {
                idx--;

                evoLayout[idx] = new ImageView(this.getContext());
                evoLayout[idx].setImageDrawable(getEvoArrow(pokemonLookup.getPokemon(pNum).getEvoLvl()));

                idx--;

                evoLayout[idx] = new ImageView(this.getContext());
                evoLayout[idx].setImageDrawable(ResourcesCompat.getDrawable(res, drawId + ((pNum - curNum) * 3) , null));

                cNum = pNum;
                // String str = "The prevo of " + pNum;
                pNum = pokemonLookup.getPokemon(cNum).getPrEvo();
                //Log.d("**TESTING**", str + " is " + pNum);
            }

            for (int i = 0; idx <= 4; i++) {
                evoLay.addView(evoLayout[idx], i);
                idx++;
            }
        }
        else if (pNum == 0) {
            idx = 0;

            evoLayout[idx] = new ImageView(this.getContext());
            evoLayout[idx].setImageDrawable(ResourcesCompat.getDrawable(res, drawId, null));

            while (eNum != 0) {
                idx++;

                evoLayout[idx] = new ImageView(this.getContext());
                evoLayout[idx].setImageDrawable(getEvoArrow(vNum)); //TODO add specific level-up arrows

                idx++;

                evoLayout[idx] = new ImageView(this.getContext());
                evoLayout[idx].setImageDrawable(ResourcesCompat.getDrawable(res, drawId + ((eNum - curNum) * 3), null));

                cNum = eNum;
                eNum = pokemonLookup.getPokemon(cNum).getEvo();
                vNum = pokemonLookup.getPokemon(cNum).getEvoLvl();
            }

            for (int i = 0; i <= 4; i++) {
                if (evoLayout[i] == null)
                    break;
                evoLay.addView(evoLayout[i]);
            }
        }
        else {
            evoLayout[2] = new ImageView(this.getContext());
            evoLayout[2].setImageDrawable(ResourcesCompat.getDrawable(res, drawId, null));

            evoLayout[1] = new ImageView(this.getContext());
            evoLayout[1].setImageDrawable(getEvoArrow(pokemonLookup.getPokemon(pNum).getEvoLvl()));

            evoLayout[0] = new ImageView(this.getContext());
            evoLayout[0].setImageDrawable(ResourcesCompat.getDrawable(res, drawId + ((pNum - curNum) * 3), null));

            evoLayout[3] = new ImageView(this.getContext());
            evoLayout[3].setImageDrawable(getEvoArrow(vNum));

            evoLayout[4] = new ImageView(this.getContext());
            evoLayout[4].setImageDrawable(ResourcesCompat.getDrawable(res, drawId + ((eNum - curNum) * 3), null));

            idx = 0;
            for (ImageView imageView : evoLayout) {
                if (imageView != null) {
                    evoLay.addView(imageView, idx);
                    idx++;
                }
            }
        }
    }

    private Drawable getEvoArrow(int evoLvl) {
        switch (evoLvl) {
            case 7:
                return ResourcesCompat.getDrawable(res, R.drawable.l_07, null);
            case 10:
                return ResourcesCompat.getDrawable(res, R.drawable.l_10, null);
            case 16:
                return ResourcesCompat.getDrawable(res, R.drawable.l_16, null);
            case 18:
                return ResourcesCompat.getDrawable(res, R.drawable.l_18, null);
            case 20:
                return ResourcesCompat.getDrawable(res, R.drawable.l_20, null);
            case 21:
                return ResourcesCompat.getDrawable(res, R.drawable.l_21, null);
            case 22:
                return ResourcesCompat.getDrawable(res, R.drawable.l_22, null);
            case 24:
                return ResourcesCompat.getDrawable(res, R.drawable.l_24, null);
            case 25:
                return ResourcesCompat.getDrawable(res, R.drawable.l_25, null);
            case 26:
                return ResourcesCompat.getDrawable(res, R.drawable.l_26, null);
            case 28:
                return ResourcesCompat.getDrawable(res, R.drawable.l_28, null);
            case 30:
                return ResourcesCompat.getDrawable(res, R.drawable.l_30, null);
            case 31:
                return ResourcesCompat.getDrawable(res, R.drawable.l_31, null);
            case 32:
                return ResourcesCompat.getDrawable(res, R.drawable.l_32, null);
            case 33:
                return ResourcesCompat.getDrawable(res, R.drawable.l_33, null);
            case 34:
                return ResourcesCompat.getDrawable(res, R.drawable.l_34, null);
            case 35:
                return ResourcesCompat.getDrawable(res, R.drawable.l_35, null);
            case 36:
                return ResourcesCompat.getDrawable(res, R.drawable.l_36, null);
            case 37:
                return ResourcesCompat.getDrawable(res, R.drawable.l_37, null);
            case 38:
                return ResourcesCompat.getDrawable(res, R.drawable.l_38, null);
            case 40:
                return ResourcesCompat.getDrawable(res, R.drawable.l_40, null);
            case 42:
                return ResourcesCompat.getDrawable(res, R.drawable.l_42, null);
            case 55:
                return ResourcesCompat.getDrawable(res, R.drawable.l_55, null);
            case 101:
                return ResourcesCompat.getDrawable(res, R.drawable.l_trade, null);
            case 102:
                return ResourcesCompat.getDrawable(res, R.drawable.l_thunderstone, null);
            case 103:
                return ResourcesCompat.getDrawable(res, R.drawable.l_moonstone, null);
            case 104:
                return ResourcesCompat.getDrawable(res, R.drawable.l_firestone, null);
            case 105:
                return ResourcesCompat.getDrawable(res, R.drawable.l_leafstone, null);
            case 106:
                return ResourcesCompat.getDrawable(res, R.drawable.l_waterstone, null);
            default:
                return null;
        }
    }

    private String modLocString(String locString) {
        StringBuffer strBuf = new StringBuffer(locString);

        int idx = 0;
        boolean cont = true;
        while (cont) {
            if (Character.isDigit(strBuf.charAt(idx)))
                strBuf.insert(idx, "Route ");

            // Log.d("**TESTING**", strBuf.toString());

            if (strBuf.indexOf(",") != -1) {
                strBuf.replace(strBuf.indexOf(","), strBuf.indexOf(",") + 1, " | ");
                idx = strBuf.indexOf("|", idx) + 2;
            }
            else
                cont = false;
        }
        return strBuf.toString();
    }

    private void setMoveset(int dexNum) {
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
                        binding.yellowLevelMoveTable.addView(newRow, ylwCount + 1);
                        ylwCount++;
                    }
                    else {
                        binding.levelMoveTable.addView(newRow, lvlCount + 1);
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

                    binding.prevoMoveTable.addView(newRow, prevoCount + 1);
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
                    tempString += R.string.yellow_only;
                textView.setText(tempString);
                newRow.addView(textView, 1);

                finishMovesetEntry(move, newRow);

                binding.tmMoveTable.addView(newRow, tmCount + 1);
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

                binding.specialMoveTable.addView(newRow, specialCount + 1);
                specialCount++;
            }
        }

        if (tmCount == 0) {
            binding.tmMoveTable.removeAllViews();
            binding.specialMoveTxt.setText("");
        }
        if (specialCount == 0) {
            binding.specialMoveTable.removeAllViews();
            binding.specialMoveTxt.setText("");
        }
        if (prevoCount == 0) {
            binding.prevoMoveTable.removeAllViews();
            binding.prevoMoveTxt.setText("");
        }
        if (ylwCount == 0) {
            binding.levelTxt.setText(R.string.generation_1_level_up_);
            binding.yellowLevelTxt.setText("");
            binding.yellowLevelMoveTable.removeAllViews();
        }
    }

    /**
     * FIXME
     * @param move
     * @param row
     */
    private void finishMovesetEntry(ModelMove move, TableRow row) {
        // move type
        ImageView img = new ImageView(this.getContext());
        img.setImageDrawable(checkTypeImage(move.getType()));
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
    }
}

