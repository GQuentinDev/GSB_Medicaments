package com.gsb.medicaments.controleur.composant;

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
import es.dmoral.toasty.Toasty;

public class InfoComposant extends AppCompatActivity {

    // Objets de récupération de données
    private ComposantDAO composantAcces;

    // Paramètres
    private String code;

    // Elements du layout
    private TextView v_code;
    private TextView v_libelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_composant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Objets de récupération des données
        this.composantAcces = new ComposantDAO(this);

        // Localisation des éléments du layout
        this.v_code = findViewById(R.id.textView_Code);
        this.v_libelle = findViewById(R.id.textView_Libelle);

        // Récupération des paramètres
        Intent intent = getIntent();
        this.code = intent.getStringExtra("code");

        // Afficahge
        affichage(composantAcces.getComposant(this.code));
    }

    /**
     * Met à jour le layout
     * @param leComposant objet Medicament
     */
    private void affichage(Composant leComposant) {
        // Récupération des données de l'objet Composant
        String code = leComposant.getCode();
        String libelle = leComposant.getLibelle();

        // Remplacement des éléments du layout
        this.v_code.setText(code);
        this.v_libelle.setText(libelle);
    }

    /**
     * Suppression du composant
     * @return boolean
     */
    private boolean deleteComposantInAllDataBase() {
        boolean res = false;

        if (this.composantAcces.deleteComposant(this.code) > 0) {
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
            adb.setMessage(getText(R.string.adb_text_composant_remove));
            adb.setPositiveButton(getText(R.string.adb_btn_positive_default), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Test suppression
                    if (deleteComposantInAllDataBase()) {
                        Toasty.success(getApplicationContext(), R.string.toast_success_composant_delete).show();
                        finish();
                    }
                    // Erreur
                    else {
                        Toasty.error(getApplicationContext(), R.string.toast_error_composant_remove).show();
                    }
                }
            });
            adb.setNegativeButton(getText(R.string.adb_btn_negative_default), null);
            adb.show();
            return true;
        }

        if (id == R.id.action_edit) {
            // Activity Edit
            Intent editComposant = new Intent(getApplicationContext(), EditComposant.class);
            editComposant.putExtra("code", this.code);
            startActivityForResult(editComposant, 777);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 777) {
            if (resultCode == RESULT_OK) {
                Toasty.success(getApplicationContext(), R.string.toast_success_composant_edit).show();
                // code du nouveau composant
                this.code = data.getStringExtra("newCode");
            }
            if (resultCode == RESULT_CANCELED) {
                Toasty.info(getApplicationContext(), R.string.toast_info_edit).show();
            }
            // Actualisation de l'affichage
            affichage(composantAcces.getComposant(this.code));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
