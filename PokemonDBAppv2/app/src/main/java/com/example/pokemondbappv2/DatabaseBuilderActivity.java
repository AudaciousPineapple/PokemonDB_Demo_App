package com.example.pokemondbappv2;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.pokemondbappv2.databinding.ActivityDatabaseBuilderBinding;

public class DatabaseBuilderActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDatabaseBuilderBinding binding;
    private EditText name, type, pp, pwr, acc, eff, det, rate, tm, prior, target;
    private DBHandler dbHandler;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDatabaseBuilderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHandler = new DBHandler(DatabaseBuilderActivity.this);

        name = findViewById(R.id.name_edit);
        type = findViewById(R.id.type_edit);
        pp = findViewById(R.id.pp_edit);
        pwr = findViewById(R.id.power_edit);
        acc = findViewById(R.id.acc_edit);
        eff = findViewById(R.id.effect_edit);
        det = findViewById(R.id.detail_edit);
        rate = findViewById(R.id.eRate_edit);
        tm = findViewById(R.id.tm_edit);
        prior = findViewById(R.id.prior_edit);
        target = findViewById(R.id.target_edit);

        btn = findViewById(R.id.submit_btn);

        btn.setOnClickListener(view1 -> {
            try {
                String nm = name.getText().toString();
                String mtype = type.getText().toString();
                int p = Integer.parseInt(pp.getText().toString());
                int power = Integer.parseInt(pwr.getText().toString());
                double accu = Double.parseDouble(acc.getText().toString());
                String effect = eff.getText().toString();
                int details;
                if (det.getText().toString().isEmpty())
                    details = 0;
                else
                    details = Integer.parseInt(det.getText().toString());
                int effectRate = Integer.parseInt(rate.getText().toString());
                int tmNum = Integer.parseInt(tm.getText().toString());
                int priority = Integer.parseInt(prior.getText().toString());
                int targ = Integer.parseInt(target.getText().toString());

                dbHandler.addMove(nm, mtype, p, power, accu, effect, details, effectRate, tmNum,
                        priority, targ);

                dbHandler.getMoves();

            }
            catch (NumberFormatException e) {
                Log.d("***FUCK YOU***", "Nope");
            }
        });

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_database_builder);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_database_builder);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}