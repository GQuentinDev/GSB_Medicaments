package com.gsb.medicaments.controleur.medicament;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gsb.medicaments.R;
import com.gsb.medicaments.modele.Composant;
import com.gsb.medicaments.modele.ComposantDAO;
import com.gsb.medicaments.modele.Famille;
import com.gsb.medicaments.modele.FamilleDAO;
import com.gsb.medicaments.modele.Medicament;
import com.gsb.medicaments.modele.MedicamentDAO;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class InfoMedicament extends AppCompatActivity {

    // Objets de récupération de données
    private MedicamentDAO medicamentAcces;
    private FamilleDAO familleAcces;
    private ComposantDAO composantAcces;

    // Paramètres
    private String depotLegal;

    // Elements du layout
    private TextView v_depotLegal;
    private TextView v_nomCommercial;
    private TextView v_effets;
    private TextView v_contreIndic;
    private TextView v_prix;
    private TextView v_famille;
    private TextView v_composants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_medicament);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Objets de récupération des données
        this.medicamentAcces = new MedicamentDAO(this);
        this.familleAcces = new FamilleDAO(this);
        this.composantAcces = new ComposantDAO(this);

        // Localisation des éléments du layout
        this.v_depotLegal = findViewById(R.id.textView_DepotLegal);
        this.v_nomCommercial = findViewById(R.id.textView_NomCommercial);
        this.v_effets = findViewById(R.id.textView_Effets);
        this.v_contreIndic = findViewById(R.id.textView_ContreIndic);
        this.v_prix = findViewById(R.id.textView_Prix);
        this.v_famille = findViewById(R.id.textView_Famille);
        this.v_composants = findViewById(R.id.textView_Composants);

        // Récupération des paramètres
        Intent intent = getIntent();
        this.depotLegal = intent.getStringExtra("depotLegal");

        // Afficahge
        affichage(getFullMedicament(this.depotLegal));
    }

    /**
     * Met à jour le layout
     * @param leMedicament objet Medicament
     */
    private void affichage(Medicament leMedicament) {
        // Récupération des données de l'objet Medicament
        String depotLegal = leMedicament.getDepotLegal();
        String nomCommercial = leMedicament.getNomCommercial();
        String effets = leMedicament.getEffets();
        String contreIndic = leMedicament.getContreIndic();
        String prix = leMedicament.getPrix();
        String famille = leMedicament.getFamilleLibelle();
        String composants = leMedicament.getLesComposantsIntoString();

        // Remplacement des éléments du layout
        this.v_depotLegal.setText(depotLegal);
        this.v_nomCommercial.setText(nomCommercial);
        this.v_effets.setText(effets);
        this.v_contreIndic.setText(contreIndic);
        this.v_prix.setText(prix);
        this.v_famille.setText(famille);
        this.v_composants.setText(composants);
    }

    /**
     * Retourne le médicament
     * @param depotLegal String
     * @return objet Medicament
     */
    private Medicament getFullMedicament(String depotLegal) {
        // Récupération du médicament
        Medicament leMedicament = this.medicamentAcces.getMedicament(depotLegal);

        // Récupération et ajout de la famille au médicament
        Famille laFamille = this.familleAcces.getFamilleMedicament(depotLegal);
        leMedicament.setFamille(laFamille);

        // Récupération et ajout des composants au médicament
        ArrayList<Composant> lesComposants = this.composantAcces.getComposants(depotLegal);
        leMedicament.setComposants(lesComposants);

        return leMedicament;
    }

    /**
     * Suppression du médicament et de ça composition
     * @return boolean
     */
    private boolean deleteMedicamentInAllDataBase() {
        boolean res = false;

        if (this.medicamentAcces.deleteMedicament(this.depotLegal) > 0) {
            this.composantAcces.deleteComposition(depotLegal);
            res = true;
        }

        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_remove) {
            // AlertDialog confirmation remove
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage(getText(R.string.adb_text_medicament_remove));
            adb.setPositiveButton(getText(R.string.adb_btn_positive_default), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Test suppression
                    if (deleteMedicamentInAllDataBase()) {
                        Toasty.success(getApplicationContext(), R.string.toast_success_medicament_delete).show();
                        finish();
                    }
                    // Erreur
                    else {
                        Toasty.error(getApplicationContext(), R.string.toast_error_medicament_remove).show();
                    }
                }
            });
            adb.setNegativeButton(getText(R.string.adb_btn_negative_default), null);
            adb.show();
            return true;
        }

        if (id == R.id.action_edit) {
            // Activity Edit
            Intent editMedicament = new Intent(getApplicationContext(), EditMedicament.class);
            editMedicament.putExtra("depotLegal", this.depotLegal);
            startActivityForResult(editMedicament, 111);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                Toasty.success(getApplicationContext(), R.string.toast_success_medicament_edit).show();
                // Nouveau dépot légal
                this.depotLegal = data.getStringExtra("newDepotLegal");
            }
            if (resultCode == RESULT_CANCELED) {
                Toasty.info(getApplicationContext(), R.string.toast_info_edit).show();
            }
            // Actualisation de l'affichage
            affichage(getFullMedicament(this.depotLegal));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
