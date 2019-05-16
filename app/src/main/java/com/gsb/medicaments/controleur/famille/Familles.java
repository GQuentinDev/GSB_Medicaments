package com.gsb.medicaments.controleur.famille;

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
import com.gsb.medicaments.modele.Famille;
import com.gsb.medicaments.modele.FamilleDAO;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class Familles extends AppCompatActivity {

    // Objets de récupération de données
    private FamilleDAO familleAcces;

    // Paramètres
    private long backPress;
    private Toast backToast;

    // Elements du layout
    private ListView listViewFamille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familles);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Objets de récupération des données
        this.familleAcces = new FamilleDAO(this);

        // Localisation des éléments du layout
        this.listViewFamille = findViewById(R.id.listComposants);

        // Affichage
        affichage();
    }

    private void affichage() {
        // Récupération des objets
        ArrayList<Famille> listeFamilles = this.familleAcces.getFamilles();

        ArrayList<HashMap<String, String>> listItem = new ArrayList<>();

        // Contien les informations pour un item
        HashMap<String, String> map;
        for (Famille uneFamille : listeFamilles) {
            map = new HashMap<>();
            map.put("code", uneFamille.getCode());
            map.put("libelle", uneFamille.getLibelle());
            listItem.add(map);
        }

        // Mise des items dans liste
        SimpleAdapter mSchedule = new SimpleAdapter(
                this.getBaseContext(), listItem, R.layout.list_famille,
                new String[] {"libelle"},
                new int[] {R.id.libelle}
        );
        this.listViewFamille.setAdapter(mSchedule);

        // Ecoute du clic sur la liste
        this.listViewFamille.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                // On récupère la HashMap contenant les infos de notre item
                HashMap<String, String> map = (HashMap<String, String>) listViewFamille.getItemAtPosition(position);

                // code de la famille
                String code = map.get("code");

                // Redirection vers le layout d'information
                Intent infoFamille = new Intent(getApplicationContext(), InfoFamille.class);
                infoFamille.putExtra("code", code);
                startActivityForResult(infoFamille, 11);
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
            Intent ajoutFamille = new Intent(getApplicationContext(), AjoutFamille.class);
            startActivityForResult(ajoutFamille, 33);
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
        // ajoutFamille
        if (requestCode == 33) {
            if (resultCode == RESULT_OK) {
                Toasty.success(getApplicationContext(), getString(R.string.toast_success_famille_add)).show();
                // code de la nouvelle famille
                String code = data.getStringExtra("newAddcode");
                // Redirection vers le layout d'information
                Intent infoFamille = new Intent(getApplicationContext(), InfoFamille.class);
                infoFamille.putExtra("code", code);
                startActivityForResult(infoFamille, 44);
            }
            if (resultCode == RESULT_CANCELED) {
                Toasty.info(getApplicationContext(), getString(R.string.toast_info_add)).show();
            }
        }
        affichage();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
