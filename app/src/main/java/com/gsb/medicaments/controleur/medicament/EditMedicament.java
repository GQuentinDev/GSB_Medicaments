package com.gsb.medicaments.controleur.medicament;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.gsb.medicaments.R;
import com.gsb.medicaments.modele.Composant;
import com.gsb.medicaments.modele.Famille;
import com.gsb.medicaments.modele.Medicament;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class EditMedicament extends MedicamentEdition {

    // Paramètres
    private String depotLegal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Récupération des paramètres
        Intent intent = getIntent();
        this.depotLegal = intent.getStringExtra("depotLegal");

        // Remplissage du layout avec les info du médicament
        autoRemplissage(getFullMedicament(this.depotLegal));
    }

    /**
     * Retourne le médicament
     * @param depotLegal String
     * @return objet Medicament
     */
    private Medicament getFullMedicament(String depotLegal) {
        // Récupération du médicament
        Medicament leMedicament = super.medicamentAcces.getMedicament(depotLegal);

        // Récupération et ajout de la famille au médicament
        Famille laFamille = super.familleAcces.getFamilleMedicament(depotLegal);
        leMedicament.setFamille(laFamille);

        // Récupération et ajout des composants au médicament
        ArrayList<Composant> lesComposants = super.composantAcces.getComposants(depotLegal);
        leMedicament.setComposants(lesComposants);

        return leMedicament;
    }

    /**
     * Remplie le layout
     * @param leMedicament objet Medicament
     */
    private void autoRemplissage(Medicament leMedicament) {
        // Récupération des données de l'objet Medicament
        String depotLegal = leMedicament.getDepotLegal();
        String nomCommercial = leMedicament.getNomCommercial();
        String effets = leMedicament.getEffets();
        String contreIndic = leMedicament.getContreIndic();
        String prix = leMedicament.getPrix();
        Famille laFamille = leMedicament.getLaFamille();
        ArrayList<Composant> lesComposants = leMedicament.getLesComposants();

        // Objet Medicament permetant les modifications avant l'insertion
        super.setEditMedicament(new Medicament(depotLegal, nomCommercial, effets, contreIndic, prix, laFamille, lesComposants));

        // Remplacement des éléments du layout
        super.v_depotLegal.setText(depotLegal);
        super.v_nomCommercial.setText(nomCommercial);
        super.v_effets.setText(effets);
        super.v_contreIndic.setText(contreIndic);
        super.v_prix.setText(prix);
        super.v_composants.setText(super.getEditMedicament().getLesComposantsIntoString());
        if (laFamille != null) {
            int position = 0;
            for (Famille f : super.familleAcces.getFamilles()) {
                if (f.getCode().equals(laFamille.getCode())) {
                    super.v_famille.setSelection(position);
                }
                position++;
            }
        }
    }

    /**
     * Met à jour le médicament dans la base de données
     * @param updatedMedicament objet Medicament
     * @param depotLegal String
     * @return boolean
     */
    private boolean updateDataBase(Medicament updatedMedicament, String depotLegal) {
        boolean res = false;

        if (super.medicamentAcces.updateMedicament(updatedMedicament, depotLegal) > 0) {
            super.composantAcces.deleteComposition(depotLegal);
            super.composantAcces.addComposition(updatedMedicament.getLesComposants(), depotLegal);
            res = true;

        }

        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_done) {
            // Verification des données
            if (super.verifNotEmpty()) {
                // Vérification de la mise à jour de la base de données
                if (updateDataBase(super.getEditMedicament(), this.depotLegal)) {
                    // Fin de l'activity avec paramètre
                    Intent intent = new Intent();
                    intent.putExtra("newDepotLegal", super.getEditMedicament().getDepotLegal());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toasty.error(getApplicationContext(), getString(R.string.toast_error_medicament_edit)).show();
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // AlertDialog confirmation cancel
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        adb.setMessage(getText(R.string.adb_text_cancel_edit));
        adb.setPositiveButton(getString(R.string.adb_btn_positive_default), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        adb.setNegativeButton(getString(R.string.adb_btn_negative_default), null);
        adb.show();
    }
}
