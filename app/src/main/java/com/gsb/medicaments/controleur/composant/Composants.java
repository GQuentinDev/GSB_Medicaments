package com.gsb.medicaments.controleur.composant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.gsb.medicaments.R;
import com.gsb.medicaments.modele.Composant;
import com.gsb.medicaments.modele.ComposantDAO;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class Composants extends AppCompatActivity {

    // Objets de récupération de données
    private ComposantDAO composantAcces;

    // Paramètres
    private long backPress;
    private Toast backToast;

    // Elements du layout
    private ListView listViewComposant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composants);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Objets de récupération des données
        this.composantAcces = new ComposantDAO(this);

        // Localisation des éléments du layout
        this.listViewComposant = findViewById(R.id.listComposants);

        // Affichage
        affichage();
    }

    private void affichage() {
        // Récupération des objets
        ArrayList<Composant> listeComposants = this.composantAcces.getComposants();

        ArrayList<HashMap<String, String>> listItem = new ArrayList<>();

        // Contien les informations pour un item
        HashMap<String, String> map;
        for (Composant unComposant : listeComposants) {
            map = new HashMap<>();
            map.put("code", unComposant.getCode());
            map.put("libelle", unComposant.getLibelle());
            listItem.add(map);
        }

        // Mise des items dans liste
        SimpleAdapter mSchedule = new SimpleAdapter(
                this.getBaseContext(), listItem, R.layout.list_composant,
                new String[] {"libelle"},
                new int[] {R.id.libelle}
        );
        this.listViewComposant.setAdapter(mSchedule);

        // Ecoute du clic sur la liste
        this.listViewComposant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                // On récupère la HashMap contenant les infos de notre item
                HashMap<String, String> map = (HashMap<String, String>) listViewComposant.getItemAtPosition(position);

                // code du composant
                String code = map.get("code");

                // Redirection vers le layout d'information
                Intent infoComposant = new Intent(getApplicationContext(), InfoComposant.class);
                infoComposant.putExtra("code", code);
                startActivityForResult(infoComposant, 77);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent ajoutComposant = new Intent(getApplicationContext(), AjoutComposant.class);
            startActivityForResult(ajoutComposant, 55);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        long time = 2000;

        if (backPress + time > System.currentTimeMillis()) {
            this.backToast.cancel();
            super.onBackPressed();
        }
        else {
            this.backToast = Toast.makeText(getApplicationContext(), R.string.back_confirmation_main, Toast.LENGTH_SHORT);
            this.backToast.show();
        }
        this.backPress = System.currentTimeMillis();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // ajoutComposant
        if (requestCode == 55) {
            if (resultCode == RESULT_OK) {
                Toasty.success(getApplicationContext(), getString(R.string.toast_success_composant_add)).show();
                // code du nouveau composant
                String code = data.getStringExtra("newAddCode");
                // Redirection vers le layout d'information
                Intent infoComposant = new Intent(getApplicationContext(), InfoComposant.class);
                infoComposant.putExtra("code", code);
                startActivityForResult(infoComposant, 66);
            }
            if (resultCode == RESULT_CANCELED) {
                Toasty.info(getApplicationContext(), getString(R.string.toast_info_add)).show();
            }
        }
        affichage();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
