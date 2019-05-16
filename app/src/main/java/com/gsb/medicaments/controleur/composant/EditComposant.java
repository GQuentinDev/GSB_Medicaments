package com.gsb.medicaments.controleur.composant;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.gsb.medicaments.R;
import com.gsb.medicaments.modele.Composant;

import es.dmoral.toasty.Toasty;

public class EditComposant extends ComposantEdition {

    // Paramètres
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Récupération des paramètres
        Intent intent = getIntent();
        this.code = intent.getStringExtra("code");

        // Remplissage du layout avec les info du composant
        autoRemplissage(composantAcces.getComposant(this.code));
    }

    /**
     * Remplie le layout
     * @param leComposant objet Composant
     */
    private void autoRemplissage(Composant leComposant) {
        // Récupération des données de l'objet composant
        String code = leComposant.getCode();
        String libelle = leComposant.getLibelle();

        // Objet Medicament permetant les modifications avant l'insertion
        super.setEditComposant(new Composant(code, libelle));

        // Remplacement des éléments du layout
        super.v_code.setText(code);
        super.v_libelle.setText(libelle);
    }

    /**
     * Met à jour le composant dans la base de données
     * @param updatedComposant objet Composant
     * @param code String
     * @return boolean
     */
    private boolean updateDataBase(Composant updatedComposant, String code) {
        boolean res = false;

        if (super.composantAcces.updateComposant(updatedComposant, code) > 0) {
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
                if (updateDataBase(super.getEditComposant(), this.code)) {
                    // Fin de l'activity avec paramètre
                    Intent intent = new Intent();
                    intent.putExtra("newCode", super.getEditComposant().getCode());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toasty.error(getApplicationContext(), getString(R.string.toast_error_composant_edit)).show();
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
