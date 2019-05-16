package com.gsb.medicaments.controleur.medicament;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.gsb.medicaments.R;
import com.gsb.medicaments.modele.Medicament;
import com.gsb.medicaments.modele.MedicamentDAO;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class Medicaments extends AppCompatActivity {

    // Objets de récupération de données
    private MedicamentDAO medicamentAcces;

    // Paramètres
    private long backPress;
    private Toast backToast;

    // Elements du layout
    private ListView listViewMedicament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaments);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Objets de récupération des données
        this.medicamentAcces = new MedicamentDAO(this);

        // Localisation des éléments du layout
        this.listViewMedicament = findViewById(R.id.listMedicaments);

        // Affichage
        affichage();
    }

    private void affichage() {
        // Récupération des objets
        ArrayList<Medicament> listeMedicaments = this.medicamentAcces.getMedicaments();

        ArrayList<HashMap<String, String>> listItem = new ArrayList<>();

        // Contien les informations pour un item
        HashMap<String, String> map;
        for (Medicament unMedicament : listeMedicaments) {
            map = new HashMap<>();
            map.put("nomCommercial", unMedicament.getNomCommercial());
            map.put("depotLegal", unMedicament.getDepotLegal());
            listItem.add(map);
        }

        // Mise des items dans liste
        SimpleAdapter mSchedule = new SimpleAdapter(
                this.getBaseContext(), listItem, R.layout.list_medicament,
                new String[] {"nomCommercial"},
                new int[] {R.id.libelle}
        );
        this.listViewMedicament.setAdapter(mSchedule);

        // Ecoute du clic sur la liste
        this.listViewMedicament.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                // On récupère la HashMap contenant les infos de notre item
                HashMap<String, String> map = (HashMap<String, String>) listViewMedicament.getItemAtPosition(position);

                // depotLegal du medicament
                String depotLegal = map.get("depotLegal");

                // Redirection vers le layout d'information
                Intent infoMedicament = new Intent(getApplicationContext(), InfoMedicament.class);
                infoMedicament.putExtra("depotLegal", depotLegal);
                startActivityForResult(infoMedicament, 11);
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
            Intent ajoutMedicament = new Intent(getApplicationContext(), AjoutMedicament.class);
            startActivityForResult(ajoutMedicament, 22);
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
        // AjoutMedicament
        if (requestCode == 22) {
            if (resultCode == RESULT_OK) {
                Toasty.success(getApplicationContext(), getString(R.string.toast_success_medicament_add)).show();
                // Depot legal du nouveau medicament
                String depotLegal = data.getStringExtra("newAddDepotLegal");
                // Redirection vers le layout d'information
                Intent infoMedicament = new Intent(getApplicationContext(), InfoMedicament.class);
                infoMedicament.putExtra("depotLegal", depotLegal);
                startActivityForResult(infoMedicament, 11);
            }
            if (resultCode == RESULT_CANCELED) {
                Toasty.info(getApplicationContext(), getString(R.string.toast_info_add)).show();
            }
        }
        affichage();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
