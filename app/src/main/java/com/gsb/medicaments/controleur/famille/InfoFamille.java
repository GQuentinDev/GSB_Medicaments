package com.gsb.medicaments.controleur.famille;

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
import com.gsb.medicaments.modele.Famille;
import com.gsb.medicaments.modele.FamilleDAO;

import es.dmoral.toasty.Toasty;

public class InfoFamille extends AppCompatActivity {

    // Objets de récupération de données
    private FamilleDAO familleAcces;

    // Paramètres
    private String code;

    // Elements du layout
    private TextView v_code;
    private TextView v_libelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_famille);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Objets de récupération des données
        this.familleAcces = new FamilleDAO(this);

        // Localisation des éléments du layout
        this.v_code = findViewById(R.id.textView_Code);
        this.v_libelle = findViewById(R.id.textView_Libelle);

        // Récupération des paramètres
        Intent intent = getIntent();
        this.code = intent.getStringExtra("code");

        // Afficahge
        affichage(this.familleAcces.getFamille(this.code));
    }

    /**
     * Met à jour le layout
     * @param laFamille objet Famille
     */
    private void affichage(Famille laFamille) {
        // Récupération des données de l'objet Medicament
        String code = laFamille.getCode();
        String libelle = laFamille.getLibelle();

        // Remplacement des éléments du layout
        this.v_code.setText(code);
        this.v_libelle.setText(libelle);
    }

    /**
     * Suppression de la famille
     * @return boolean
     */
    private boolean deleteFamilleInAllDataBase() {
        boolean res = false;

        if (this.familleAcces.deleteFamille(this.code) > 0) {
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
            adb.setMessage(getText(R.string.adb_text_famille_remove));
            adb.setPositiveButton(getText(R.string.adb_btn_positive_default), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Test suppression
                    if (deleteFamilleInAllDataBase()) {
                        Toasty.success(getApplicationContext(), R.string.toast_success_famille_delete).show();
                        finish();
                    }
                    // Erreur
                    else {
                        Toasty.error(getApplicationContext(), R.string.toast_error_famille_remove).show();
                    }
                }
            });
            adb.setNegativeButton(getText(R.string.adb_btn_negative_default), null);
            adb.show();
            return true;
        }

        if (id == R.id.action_edit) {
            // Activity Edit
            Intent editFamille = new Intent(getApplicationContext(), EditFamille.class);
            editFamille.putExtra("code", this.code);
            startActivityForResult(editFamille, 111);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                Toasty.success(getApplicationContext(), R.string.toast_success_famille_edit).show();
                // Nouveau code
                this.code = data.getStringExtra("newCode");
            }
            if (resultCode == RESULT_CANCELED) {
                Toasty.info(getApplicationContext(), R.string.toast_info_edit).show();
            }
            // Actualisation de l'affichage
            affichage(this.familleAcces.getFamille(this.code));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
