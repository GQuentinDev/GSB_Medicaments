package com.gsb.medicaments.controleur.medicament;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.gsb.medicaments.R;
import com.gsb.medicaments.modele.Composant;
import com.gsb.medicaments.modele.ComposantDAO;
import com.gsb.medicaments.modele.Famille;
import com.gsb.medicaments.modele.FamilleDAO;
import com.gsb.medicaments.modele.Medicament;
import com.gsb.medicaments.modele.MedicamentDAO;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public abstract class MedicamentEdition extends AppCompatActivity {

    // Objets de récupération de données
    protected MedicamentDAO medicamentAcces;
    protected FamilleDAO familleAcces;
    protected ComposantDAO composantAcces;

    // Paramètres
    private Medicament editMedicament;
    private ArrayList<HashMap<String, String>> listItemFamille;
    private ArrayList<HashMap<String, String>> listItemComposant;

    // Elements du layout
    protected EditText v_depotLegal;
    protected EditText v_nomCommercial;
    protected EditText v_effets;
    protected EditText v_contreIndic;
    protected EditText v_prix;
    protected Spinner v_famille;
    protected TextView v_composants;
    private Button v_add_composant;
    private Button v_delete_composant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament_edition);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Objets de récupération des données
        this.medicamentAcces = new MedicamentDAO(this);
        this.familleAcces = new FamilleDAO(this);
        this.composantAcces = new ComposantDAO(this);

        // Localisation des éléments du layout
        this.v_depotLegal = findViewById(R.id.depotLegal);
        this.v_nomCommercial = findViewById(R.id.nomCommercial);
        this.v_effets = findViewById(R.id.effets);
        this.v_contreIndic = findViewById(R.id.contreIndic);
        this.v_prix = findViewById(R.id.prix);
        this.v_famille = findViewById(R.id.famille);
        this.v_composants = findViewById(R.id.composants);
        this.v_add_composant = findViewById(R.id.add_composant);
        this.v_delete_composant = findViewById(R.id.delete_composant);

        this.setEditMedicament(new Medicament(null, null, null, null, null));

        // Liste déroulante des familles
        getFamillesSpinner();

        // Bouton ajout
        getButtonAddComposant();

        // Bouton suppression
        getButtonRemoveComposant();
    }

    /**
     * Action du boutton supprimer
     */
    private void getButtonRemoveComposant() {
        v_delete_composant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!editMedicament.getLesComposants().isEmpty()) {
                    // AlertDialog suppression composant
                    AlertDialog.Builder adb = new AlertDialog.Builder(MedicamentEdition.this);

                    // Layout de référence
                    @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.adb_compoant, null);
                    adb.setView(view);

                    // Localisation des éléments du layout
                    final Spinner v_adb_composants = view.findViewById(R.id.composantsSpinner);

                    // Remplissage du layout
                    v_adb_composants.setAdapter(getComposantsSpinner(editMedicament.getLesComposants()));
                    adb.setMessage(getString(R.string.adb_text_medicament_composant_remove));
                    adb.setPositiveButton(getString(R.string.adb_btn_positive_remove), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Composant séléctionné
                            HashMap<String, String> map = listItemComposant.get(v_adb_composants.getSelectedItemPosition());
                            String composantCode = map.get("code");
                            Composant leComposant = composantAcces.getComposant(composantCode);

                            // Suppression de la collection
                            if (editMedicament.removeComposant(leComposant)) {
                                // Actualisation de la vue
                                v_composants.setText(editMedicament.getLesComposantsIntoString());
                            }
                            else {
                                Toasty.error(getApplicationContext(), getString(R.string.toast_error_medicament_composant_remove)).show();
                            }
                        }
                    });
                    adb.setNegativeButton(getString(R.string.adb_btn_negative_cancel), null);
                    adb.show();
                }
                else {
                    Toasty.error(getApplicationContext(), getString(R.string.toast_error_medicament_composant_remove_nothing)).show();
                }
            }
        });
    }

    /**
     * Action du boutton ajouter
     */
    private void getButtonAddComposant() {
        this.v_add_composant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!composantAcces.getComposants().isEmpty()) {
                    // AlertDialog ajout composant
                    AlertDialog.Builder adb = new AlertDialog.Builder(MedicamentEdition.this);

                    // Layout de référence
                    @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.adb_compoant, null);
                    adb.setView(view);

                    // Localisation des éléments du layout
                    final Spinner v_adb_composants = view.findViewById(R.id.composantsSpinner);

                    // Remplissage du layout
                    v_adb_composants.setAdapter(getComposantsSpinner(composantAcces.getComposants()));
                    adb.setMessage(getString(R.string.adb_text_composant_add));
                    adb.setPositiveButton(getString(R.string.adb_btn_positive_add), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Composant séléctionné
                            HashMap<String, String> map = listItemComposant.get(v_adb_composants.getSelectedItemPosition());
                            String composantCode = map.get("code");
                            Composant leComposant = composantAcces.getComposant(composantCode);

                            // Présence dans la collection
                            boolean present = false;
                            for (Composant c : editMedicament.getLesComposants()) {
                                if (c.getCode().equals(leComposant.getCode()))
                                    present = true;
                            }
                            if (!present) {
                                // Ajout à la collection
                                if (editMedicament.addComposant(leComposant)) {
                                    // Actualisation des composants
                                    v_composants.setText(editMedicament.getLesComposantsIntoString());
                                } else {
                                    Toasty.error(getApplicationContext(), getString(R.string.toast_error_medicament_composant_add)).show();
                                }
                            } else {
                                Toasty.error(getApplicationContext(), getString(R.string.toast_error_medicament_composant_add_already_in)).show();
                            }

                        }
                    });
                    adb.setNegativeButton(getString(R.string.adb_btn_negative_cancel), null);
                    adb.show();
                }
                else {
                    Toasty.error(getApplicationContext(), getString(R.string.toast_error_medicament_composant_add_nothing)).show();
                }
            }
        });
    }

    /**
     * Remplie le Spinner avec les familles
     */
    private void getFamillesSpinner() {
        ArrayList<Famille> lesFamille = this.familleAcces.getFamilles();
        this.listItemFamille = new ArrayList<>();

        HashMap<String, String> map;
        for (Famille uneFamille : lesFamille) {
            map = new HashMap<>();
            map.put("libelle", uneFamille.getLibelle());
            map.put("code", uneFamille.getCode());
            this.listItemFamille.add(map);
        }

        SimpleAdapter mSchedule = new SimpleAdapter(
                this.getBaseContext(),
                this.listItemFamille,
                R.layout.list_famille,
                new String[]{"libelle"},
                new int[]{R.id.libelle}
        );

        this.v_famille.setAdapter(mSchedule);
    }

    /**
     * Retourne l'adapter du spinner de composant contenant les composants passées en paramètres
     * @return SimpleAdapter
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    private SimpleAdapter getComposantsSpinner(ArrayList<Composant> lesComposants) {
        this.listItemComposant = new ArrayList<>();

        HashMap<String, String> map;
        for (Composant unComposant : lesComposants) {
            map = new HashMap<>();
            map.put("code", unComposant.getCode());
            map.put("libelle", unComposant.getLibelle());
            this.listItemComposant.add(map);
        }

        SimpleAdapter mSchedule = new SimpleAdapter(
                this.getBaseContext(),
                this.listItemComposant,
                R.layout.list_composant,
                new String[] {"libelle"},
                new int[] {R.id.libelle}
        );

        return mSchedule;
    }

    /**
     * Vérifie les données saisies et mise à jour de l'objet
     * @return boolean
     */
    boolean verifNotEmpty() {
        boolean res = false;
        String depotLegal = this.v_depotLegal.getText().toString();
        String nomCommercial = this.v_nomCommercial.getText().toString();
        String effets = this.v_effets.getText().toString();
        String contreIndic = this.v_contreIndic.getText().toString();
        String prix = this.v_prix.getText().toString();

        // Champs à verifier
        String[] data = {depotLegal, nomCommercial, effets, contreIndic};
        // Nom de chaque champ
        int[] stringsData = {R.string.label_DepotLegal, R.string.label_NomCommercial, R.string.label_Effets, R.string.label_ContreIndic, R.string.label_Prix};
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

        // Famille selectionnée
        HashMap<String, String> map = this.listItemFamille.get(this.v_famille.getSelectedItemPosition());
        String familleCode = map.get("code");
        Famille laFamille = this.familleAcces.getFamille(familleCode);

        // Verification des erreurs
        if (nb == 0) {
            res = true;
            this.editMedicament.setDepotLegal(depotLegal);
            this.editMedicament.setNomCommercial(nomCommercial);
            this.editMedicament.setEffets(effets);
            this.editMedicament.setContreIndic(contreIndic);
            this.editMedicament.setPrix(prix);
            this.editMedicament.setFamille(laFamille);
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

    protected Medicament getEditMedicament() {
        return editMedicament;
    }

    protected void setEditMedicament(Medicament editMedicament) {
        this.editMedicament = editMedicament;
    }
}
