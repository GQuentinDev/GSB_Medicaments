package com.gsb.medicaments.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class FamilleDAO extends MainDAO{

    // Attributs
    private final String table = "famille";

    // Constructeur

    public FamilleDAO(Context ct) {
        super(ct);
    }

    // Méthodes

    /**
     * Retourne la Famille
     * @param code String
     * @return objet Famille
     */
    public Famille getFamille(String code) {
        Famille laFamille = null;
        Cursor curseur;
        String req = "SELECT FAM_CODE, FAM_LIBELLE FROM "+table+" WHERE FAM_CODE='"+code+"'";

        curseur = super.getDb().rawQuery(req, null);
        if (curseur.getCount() > 0) {
            curseur.moveToFirst();
            laFamille = new Famille(
                    curseur.getString(0),
                    curseur.getString(1)
            );
        }
        curseur.close();
        return laFamille;
    }

    /**
     * Retourne la Famille d'un médicament
     * @param depotLegal String
     * @return objet Famille
     */
    public Famille getFamilleMedicament(String depotLegal) {
        Famille laFamille = null;
        Cursor curseur;
        String req = "SELECT f.FAM_CODE, f.FAM_LIBELLE FROM "+table+" f INNER JOIN medicament m ON m.FAM_CODE = f.FAM_CODE WHERE MED_DEPOTLEGAL='"+depotLegal+"'";

        curseur = super.getDb().rawQuery(req, null);
        if (curseur.getCount() > 0) {
            curseur.moveToFirst();
            laFamille = new Famille(
                    curseur.getString(0),
                    curseur.getString(1)
            );
        }
        curseur.close();
        return laFamille;
    }

    /**
     * Retourne la famille d'un médicament
     * @param unMedicament objet Medicament
     * @return objet Famille
     */
    public Famille getFamilleMedicament(Medicament unMedicament) {
        Famille laFamille = null;
        Cursor curseur;
        String depotLegal = unMedicament.getDepotLegal();
        String req = "SELECT f.FAM_CODE, f.FAM_LIBELLE FROM "+table+" f INNER JOIN medicament m ON m.FAM_CODE = f.FAM_CODE WHERE MED_DEPOTLEGAL='"+depotLegal+"'";

        curseur = super.getDb().rawQuery(req, null);
        if (curseur.getCount() > 0) {
            curseur.moveToFirst();
            laFamille = new Famille(
                    curseur.getString(0),
                    curseur.getString(1)
            );
        }
        curseur.close();
        return laFamille;
    }

    /**
     * Retourne toutes les familles
     * @return ArrayList<Famille>
     */
    public ArrayList<Famille> getFamilles() {
        Cursor curseur;
        String req = "SELECT * FROM "+table+" ORDER BY FAM_LIBELLE";
        curseur = super.getDb().rawQuery(req,null);
        return cursorToFamilleArrayList(curseur);
    }

    private ArrayList<Famille> cursorToFamilleArrayList(Cursor curseur){
        ArrayList<Famille> listeFamilles = new ArrayList<>();
        Famille laFamille;

        if (curseur.getCount() > 0) {
            curseur.moveToFirst();
            while (!curseur.isAfterLast()) {
                laFamille = new Famille(
                        curseur.getString(0),
                        curseur.getString(1)
                );
                listeFamilles.add(laFamille);
                curseur.moveToNext();
            }
            curseur.close();
        }
        return listeFamilles;
    }

    /**
     * Ajoute une famille
     * @param uneFamille objet Famille
     * @return long
     */
    public long addFamille(Famille uneFamille) {
        ContentValues value = new ContentValues();

        value.put("FAM_CODE", uneFamille.getCode());
        value.put("FAM_LIBELLE", uneFamille.getLibelle());

        return super.getDb().insert(table, null, value);
    }

    /**
     * Met à jour la famille
     * @param laFamille objet Famille
     * @param old_Code String
     * @return long
     */
    public long updateFamille(Famille laFamille, String old_Code) {
        ContentValues value = new ContentValues();

        value.put("FAM_CODE", laFamille.getCode());
        value.put("FAM_LIBELLE", laFamille.getLibelle());

        return super.getDb().update(table, value, "FAM_CODE='"+old_Code+"'", null);
    }

    /**
     * Met à jour la famille
     * @param laFamille objet Famille
     * @param old_Famille objet Famille
     * @return long
     */
    public long updateFamille(Famille laFamille, Famille old_Famille) {
        ContentValues value = new ContentValues();

        value.put("FAM_CODE", laFamille.getCode());
        value.put("FAM_LIBELLE", laFamille.getLibelle());

        return super.getDb().update(table, value, "FAM_CODE='"+old_Famille.getCode()+"'", null);
    }

    /**
     * Supprime la famille
     * @param code String
     * @return long
     */
    public long deleteFamille(String code) {
        return super.getDb().delete(table, "FAM_CODE='"+code+"'", null);
    }

    /**
     * Supprime la famille
     * @param laFamille objet Famille
     * @return long
     */
    public long deleteFamille(Famille laFamille) {
        return super.getDb().delete(table, "FAM_CODE='"+laFamille.getCode()+"'", null);
    }
}
