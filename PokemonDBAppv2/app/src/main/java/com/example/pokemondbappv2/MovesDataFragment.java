package com.example.pokemondbappv2;

import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
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
import java.util.List;
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
                    String tmpStr;
                    if(move.getEffectRate() == 0)
                        tmpStr = "--";
                    else
                        tmpStr = move.getEffectRate() + "%";
                    binding.moveEffectRateTxt.setText(tmpStr);

                    //TODO make code for the detail special cases

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
        int lvlCount = 0, tmCount = 0, index = 0, dexNum, idx, rbCount, yCount;
        int[] stats = new int[5];
        ModelMoveset mm;
        TableRow row;
        TextView txt;
        ImageView img;
        LinearLayout linLay, linLayRB, linLayY;
        ModelPokemonG1 entryMon;

        while (index < list.size()) {
            mm = list.get(index);
            entryMon = lookup.getPokemon(mm.getDexNum());
            if (mm.getPrEvo() == 0) {
                dexNum = mm.getDexNum();
                row = new TableRow(this.getContext());
                row.setGravity(Gravity.CENTER);

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
                txt.setText(entryMon.getName());

                row.addView(txt, 2);

                // Type Column
                linLay = new LinearLayout(this.getContext());
                linLay.setOrientation(LinearLayout.VERTICAL);

                img = new ImageView(this.getContext());
                img.setScaleType(ImageView.ScaleType.CENTER);
                img.setImageDrawable(PokemonMethods.getTypeImg(res,
                        entryMon.getType1()));
                linLay.addView(img, 0);

                if (entryMon.getType2() != Type.NON) {
                    img = new ImageView(this.getContext());
                    img.setScaleType(ImageView.ScaleType.CENTER);
                    img.setImageDrawable(PokemonMethods.getTypeImg(res,
                            entryMon.getType2()));
                    linLay.addView(img, 1);
                }

                row.addView(linLay, 3);

                // Base Stats Column
                stats[0] = entryMon.getHp();
                stats[1] = entryMon.getAtk();
                stats[2] = entryMon.getDef();
                stats[3] = entryMon.getSpc();
                stats[4] = entryMon.getSpe();

                linLay = new LinearLayout(this.getContext());
                linLay.setOrientation(LinearLayout.HORIZONTAL);

                for (int i = 0; i < stats.length; i++) {
                    txt = createIntTextView("%d", stats[i], 36);
                    linLay.addView(txt, i);
                }
                row.addView(linLay, 4);

                // TODO Level Column
                if (mm.getLevel() != -1) {

                    linLay = new LinearLayout(this.getContext());
                    linLay.setOrientation(LinearLayout.HORIZONTAL);

                    linLayRB = new LinearLayout(this.getContext());
                    linLayRB.setOrientation(LinearLayout.VERTICAL);

                    linLayY = new LinearLayout(this.getContext());
                    linLayY.setOrientation(LinearLayout.VERTICAL);

                    idx = 0;
                    rbCount = 0;
                    yCount = 0;
                    int lv;
                    while ((index + idx) < list.size()
                            && list.get(index + idx).getDexNum() == dexNum
                            && list.get(index + idx).getLevel() != -1) {

                        mm = list.get(index + idx);
                        lv = mm.getLevel();
                        if (lv == 0)
                            lv = 1;
                        txt = createIntTextView("Lv. %d", lv, 40);

                        if (!mm.isYellow()) {
                            linLayRB.addView(txt, rbCount);
                            txt = createIntTextView("Lv. %d", lv, 40);
                            linLayY.addView(txt, rbCount);
                            rbCount++;
                        }
                        else {
                            if (yCount == 0)
                                linLayY.removeAllViews();
                            linLayY.addView(txt, yCount);
                            yCount++;
                        }

                        idx++;
                    }
                    linLay.addView(linLayRB, 0);
                    linLay.addView(linLayY, 1);
                    index += idx;

                    row.addView(linLay, 5);

                }


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

        if (tmCount == 0) {
            monTable2.removeAllViews();
            binding.moveTmTitleTxt.setText("");
        }
    }

    private TextView createIntTextView(String format, int entry, int width) {
        TextView text = new TextView(this.getContext());
        text.setText(String.format(Locale.getDefault(), format, entry));
        text.setGravity(Gravity.CENTER);
        text.setWidth((int)(width * res.getDisplayMetrics().density));

        return text;
    }

}