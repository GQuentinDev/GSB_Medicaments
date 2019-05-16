package com.gsb.medicaments.controleur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gsb.medicaments.R;
import com.gsb.medicaments.controleur.composant.Composants;
import com.gsb.medicaments.controleur.famille.Familles;
import com.gsb.medicaments.controleur.medicament.Medicaments;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    // Paramètres
    private long backPress;
    private Toast backToast;

    // Elements du layout
    private Button button_medicament;
    private Button button_famille;
    private Button button_composant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Localisation des éléments du layout
        this.button_medicament = findViewById(R.id.button_medicament);
        this.button_famille = findViewById(R.id.button_famille);
        this.button_composant = findViewById(R.id.button_composant);

        // Actions des bouttons
        this.button_medicament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent medicament = new Intent(getApplicationContext(), Medicaments.class);
                startActivity(medicament);
            }
        });
        this.button_famille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent famille = new Intent(getApplicationContext(), Familles.class);
                startActivity(famille);
            }
        });
        this.button_composant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent composant = new Intent(getApplicationContext(), Composants.class);
                startActivity(composant);
            }
        });
    }

    @Override
    public void onBackPressed() {
        long time = 2000;

        if (backPress + time > System.currentTimeMillis()) {
            this.backToast.cancel();
            super.onBackPressed();
        }
        else {
            this.backToast = Toast.makeText(getApplicationContext(), R.string.back_confirmation_home, Toast.LENGTH_SHORT);
            this.backToast.show();
        }
        this.backPress = System.currentTimeMillis();
    }
}
