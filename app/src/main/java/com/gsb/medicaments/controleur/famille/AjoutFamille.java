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

public class AjoutFamille extends FamilleEdition {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Ajoute la famille dans la base de données
     *
     * @param newFamille objet Famille
     * @return boolean
     */
    private boolean addToDataBase(Famille newFamille) {
        boolean res = false;

        if (this.familleAcces.addFamille(newFamille) > 0) {
            res = true;
        }

        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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
            if (verifNotEmpty()) {
                if (addToDataBase(super.getEditFamille())) {
                    Intent intent = new Intent();
                    intent.putExtra("newAddCode", super.getEditFamille().getCode());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toasty.error(getApplicationContext(), getString(R.string.toast_error_famille_add)).show();
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
        adb.setMessage(getText(R.string.adb_text_cancel_add));
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
