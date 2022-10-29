package com.example.pokemondbappv2;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import com.example.pokemondbappv2.databinding.FragmentMovesDataBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MovesDataFragment extends Fragment {

    private FragmentMovesDataBinding binding;
    private TableLayout monTable, monTable2;
    private DBHandler lookup;
    private ModelMove move;
    private Resources res;
    private DecimalFormat dFormat1, dFormat2;
    private String str;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMovesDataBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        monTable = binding.moveLevelLearnTable;
        monTable2 = binding.moveTmLearnTable;
        res = getResources();
        dFormat1 = new DecimalFormat("0.#");
        dFormat2 = new DecimalFormat("000");

        lookup = new DBHandler(this.getContext());

        getParentFragmentManager().setFragmentResultListener("move", this,
                ((requestKey, result) -> {
                    String moveName = result.getString("move_name");

                    move = lookup.getMove(moveName);

                    binding.moveNameTxt.setText(move.getName());
                    binding.moveTypeImg.setImageDrawable(PokemonMethods.getTypeImg(res, move.getType()));
                    binding.movePpValueTxt.setText(String.format(Locale.getDefault(), "%d",
                            move.getPp()));
                    binding.movePowerValueTxt.setText(String.format(Locale.getDefault(), "%d",
                            move.getPower()));

                    binding.moveAccValueTxt.setText(dFormat1.format(move.getAccuracy()));
                    binding.moveAccValueTxt.setText(new DecimalFormat("0.#").format(move.getAccuracy()));

                    binding.moveBattleEffectTxt.setText(move.getEffect());
                    String tmpStr = move.getEffectRate() + "%";
                    binding.moveEffectRateTxt.setText(tmpStr);

                    //TODO make code for the detail special cases cases

                    binding.moveTmTxt.setText(PokemonMethods.getTmString(move.getTmNum()));
                    binding.movePriorityTxt.setText(String.format(Locale.getDefault(), "%d", move.getPriority()));
                    binding.moveTargetTxt.setText(move.getTarget().getValue());

                    str = "Pokémon That Learn " + moveName + " By Level Up";
                    binding.moveLevelTitleTxt.setText(str);
                    str = "Pokémon That Learn " + moveName + " By TM";
                    binding.moveTmTitleTxt.setText(str);

                    //TODO add code to fill in who can learn the moves
                    ArrayList<ModelMoveset> learners = lookup.getAllLearners(moveName);

                    fillLearnerTables(learners);

                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        lookup.close();
    }

    /**
     * FIXME
     * @param list
     */
    private void fillLearnerTables(ArrayList<ModelMoveset> list) {
        int lvlCount = 0, tmCount = 0, index = 0, dexNum;
        ModelMoveset mm;
        TableRow row, inRow;
        TextView txt;
        ImageView img;
        LinearLayout linLay;

        while (index < list.size()) {
            mm = list.get(index);
            if (mm.getPrEvo() == 0) {
                dexNum = mm.getDexNum();
                row = new TableRow(this.getContext());

                // No. Column
                txt = new TextView(this.getContext());
                txt.setGravity(Gravity.CENTER);
                str = new String("#" + dFormat2.format(dexNum));
                txt.setText(str);

                row.addView(txt, 0);

                // Pic Column
                img = new ImageView(this.getContext());
                img.setScaleType(ImageView.ScaleType.CENTER);
                img.setImageDrawable(ResourcesCompat.getDrawable(res,
                        R.drawable.g1_001_1 + ((dexNum - 1) * 3), null));

                row.addView(img, 1);

                // Name Column
                txt = new TextView(this.getContext());
                txt.setGravity(Gravity.CENTER);
                txt.setText(lookup.getPokemon(dexNum).getName());

                row.addView(txt, 2);

                // Type Column
                linLay = new LinearLayout(this.getContext());
                linLay.setOrientation(LinearLayout.VERTICAL);

                img = new ImageView(this.getContext());
                img.setScaleType(ImageView.ScaleType.CENTER);
                img.setImageDrawable(PokemonMethods.getTypeImg(res,
                        lookup.getPokemon(dexNum).getType1()));
                linLay.addView(img, 0);

                if (lookup.getPokemon(dexNum).getType2() != Type.NON) {
                    img = new ImageView(this.getContext());
                    img.setScaleType(ImageView.ScaleType.CENTER);
                    img.setImageDrawable(PokemonMethods.getTypeImg(res,
                            lookup.getPokemon(dexNum).getType2()));
                    linLay.addView(img, 1);
                }

                row.addView(linLay, 3);

                // Base Stats Column
                TableLayout temptbl = new TableLayout(this.getContext());
                inRow = new TableRow(this.getContext());
                temptbl.setShrinkAllColumns(true);
                temptbl.setStretchAllColumns(true);

                txt = new TextView(this.getContext());
                txt.setGravity(Gravity.CENTER);
                txt.setText(String.format(Locale.getDefault(), "%d",
                        lookup.getPokemon(dexNum).getHp()));
                inRow.addView(txt, 0);

                txt = new TextView(this.getContext());
                txt.setGravity(Gravity.CENTER);
                txt.setText(String.format(Locale.getDefault(), "%d",
                        lookup.getPokemon(dexNum).getAtk()));
                inRow.addView(txt, 1);

                txt = new TextView(this.getContext());
                txt.setGravity(Gravity.CENTER);
                txt.setText(String.format(Locale.getDefault(), "%d",
                        lookup.getPokemon(dexNum).getDef()));
                inRow.addView(txt, 2);

                txt = new TextView(this.getContext());
                txt.setGravity(Gravity.CENTER);
                txt.setText(String.format(Locale.getDefault(), "%d",
                        lookup.getPokemon(dexNum).getSpc()));
                inRow.addView(txt, 3);

                txt = new TextView(this.getContext());
                txt.setGravity(Gravity.CENTER);
                txt.setText(String.format(Locale.getDefault(), "%d",
                        lookup.getPokemon(dexNum).getSpe()));
                inRow.addView(txt, 4);

                temptbl.addView(inRow, 0);
                row.addView(temptbl, 4);

                // TODO Level Column


                // Adds the column to the appropriate table and increments
                if (mm.getLevel() == -1) {
                    tmCount++;
                    monTable2.addView(row, tmCount);
                }
                else {
                    lvlCount++;
                    monTable.addView(row, lvlCount);
                }

            }
            index++;
        }
    }

}