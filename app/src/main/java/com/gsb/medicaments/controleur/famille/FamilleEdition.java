package com.gsb.medicaments.controleur.famille;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.gsb.medicaments.R;
import com.gsb.medicaments.modele.ComposantDAO;
import com.gsb.medicaments.modele.Famille;
import com.gsb.medicaments.modele.FamilleDAO;
import com.gsb.medicaments.modele.MedicamentDAO;

public abstract class FamilleEdition extends AppCompatActivity {

    // Objets de récupération de données
    protected MedicamentDAO medicamentAcces;
    protected FamilleDAO familleAcces;
    protected ComposantDAO composantAcces;

    // Paramètres
    private Famille editFamille;

    // Elements du layout
    protected EditText v_code;
    protected EditText v_libelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famille_edition);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Objets de récupération des données
        this.medicamentAcces = new MedicamentDAO(this);
        this.familleAcces = new FamilleDAO(this);
        this.composantAcces = new ComposantDAO(this);

        // Localisation des éléments du layout
        this.v_code = findViewById(R.id.code);
        this.v_libelle = findViewById(R.id.libelle);

        this.setEditFamille(new Famille(null, null));
    }

    /**
     * Vérifie les données saisies et mise à jour de l'objet
     * @return boolean
     */
    boolean verifNotEmpty() {
        boolean res = false;
        String code = this.v_code.getText().toString();
        String libelle = this.v_libelle.getText().toString();

        // Champs à verifier
        String[] data = {code, libelle};
        // Nom de chaque champ
        int[] stringsData = {R.string.label_Code, R.string.label_Libelle};
        // Message d'erreur
        StringBuilder message = new StringBuilder(getString(R.string.adb_text_verif));
        // Compte du nombre d'erreur
        int i = 0, nb = 0;
        while (i < data.length) {
            if (data[i].isEmpty()) {
                message.append("\n - ").append(getString(stringsData[i]));
                nb++;
            }
            i++;
        }

        // Verification des erreurs
        if (nb == 0) {
            res = true;
            this.editFamille.setCode(code);
            this.editFamille.setLibelle(libelle);
        }
        else {
            // AlertDialog erreur
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage(message.toString());
            adb.setPositiveButton(getString(R.string.adb_btn_positive_validate), null);
            adb.show();
        }
        return res;
    }

    protected Famille getEditFamille() {
        return editFamille;
    }

    protected void setEditFamille(Famille editFamille) {
        this.editFamille = editFamille;
    }
}
