package com.gsb.medicaments.controleur.famille;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.gsb.medicaments.R;
import com.gsb.medicaments.modele.Famille;

import es.dmoral.toasty.Toasty;

public class EditFamille extends FamilleEdition {

    // Paramètres
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Récupération des paramètres
        Intent intent = getIntent();
        this.code = intent.getStringExtra("code");

        // Remplissage du layout avec les info du médicament
        autoRemplissage(super.familleAcces.getFamille(this.code));
    }

    /**
     * Remplie le layout
     * @param laFamille objet Medicament
     */
    private void autoRemplissage(Famille laFamille) {
        // Récupération des données de l'objet Famille
        String code = laFamille.getCode();
        String libelle = laFamille.getLibelle();

        // Objet Famille permetant les modifications avant l'insertion
        super.setEditFamille(new Famille(code, libelle));

        // Remplacement des éléments du layout
        super.v_code.setText(code);
        super.v_libelle.setText(libelle);
    }

    /**
     * Met à jour le médicament dans la base de données
     * @param updatedFamille objet Medicament
     * @param code String
     * @return boolean
     */
    private boolean updateDataBase(Famille updatedFamille, String code) {
        boolean res = false;

        if (super.familleAcces.updateFamille(updatedFamille, code) > 0) {
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
                if (updateDataBase(super.getEditFamille(), this.code)) {
                    // Fin de l'activity avec paramètre
                    Intent intent = new Intent();
                    intent.putExtra("newCode", super.getEditFamille().getCode());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toasty.error(getApplicationContext(), getString(R.string.toast_error_famille_edit)).show();
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
