package com.gsb.medicaments.modele;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class MedicamentDAO extends MainDAO {

    // Attributs
    private final String table = "medicament";

    // Constructeur

    public MedicamentDAO(Context ct) {
        super(ct);
    }

    // Méthodes

    /**
     * Retourne le médicament (sans famille ni composants)
     * @param depotLegal String
     * @return Medicament
     */
    public Medicament getMedicament(String depotLegal) {
        Medicament leMedicament = null;
        Cursor curseur;
        String req;

        req = "SELECT * FROM "+table+" WHERE MED_DEPOTLEGAL='"+depotLegal+"' ORDER BY MED_NOMCOMMERCIAL";

        curseur = super.getDb().rawQuery(req, null);
        if (curseur.getCount() > 0) {
            curseur.moveToFirst();
            leMedicament = new Medicament(
                    curseur.getString(0),
                    curseur.getString(1),
                    curseur.getString(3),
                    curseur.getString(4),
                    curseur.getString(5)
            );
        }
        curseur.close();
        return leMedicament;
    }

    /**
     * Retourne tout les médicaments
     * @return ArrayList<Medicament>
     */
    public ArrayList<Medicament> getMedicaments() {
        Cursor curseur;
        String req;

        req = "SELECT * FROM "+table+" ORDER BY MED_NOMCOMMERCIAL";
        curseur = super.getDb().rawQuery(req, null);
        return cursorToMedicamentArrayList(curseur);
    }

    private ArrayList<Medicament> cursorToMedicamentArrayList(Cursor curseur) {
        ArrayList<Medicament> listeMedicaments = new ArrayList<>();
        Medicament leMedicament;

        curseur.moveToFirst();
        while (!curseur.isAfterLast()) {
            leMedicament = new Medicament(
                    curseur.getString(0),
                    curseur.getString(1),
                    curseur.getString(3),
                    curseur.getString(4),
                    curseur.getString(5)
            );
            listeMedicaments.add(leMedicament);
            curseur.moveToNext();
        }
        curseur.close();
        return listeMedicaments;
    }

    /**
     * Ajoute un médicament
     * @param unMedicament objet Medicament
     * @return long
     */
    public long addMedicament(Medicament unMedicament) {
        ContentValues value = new ContentValues();

        value.put("MED_DEPOTLEGAL", unMedicament.getDepotLegal());
        value.put("MED_NOMCOMMERCIAL", unMedicament.getNomCommercial());
        value.put("FAM_CODE", unMedicament.getFamilleCode());
        value.put("MED_EFFETS", unMedicament.getEffets());
        value.put("MED_CONTREINDIC", unMedicament.getContreIndic());
        value.put("MED_PRIXECHANTILLON", unMedicament.getPrix());

        return super.getDb().insert(table, null, value);
    }

    /**
     * Met à jour le médicament
     * @param leMedicament objet Medicament
     * @param old_DepotLegal String
     * @return long
     */
    public long updateMedicament(Medicament leMedicament, String old_DepotLegal) {
        ContentValues value = new ContentValues();

        value.put("MED_DEPOTLEGAL", leMedicament.getDepotLegal());
        value.put("MED_NOMCOMMERCIAL", leMedicament.getNomCommercial());
        value.put("FAM_CODE", leMedicament.getFamilleCode());
        value.put("MED_EFFETS", leMedicament.getEffets());
        value.put("MED_CONTREINDIC", leMedicament.getContreIndic());
        value.put("MED_PRIXECHANTILLON", leMedicament.getPrix());

        return super.getDb().update(table, value, "MED_DEPOTLEGAL='"+old_DepotLegal+"'", null);
    }

    /**
     * Met à jour le médicament
     * @param leMedicament objet Medicament
     * @param old_Medicament objet Medicament
     * @return long
     */
    public long updateMedicament(Medicament leMedicament, Medicament old_Medicament) {
        ContentValues value = new ContentValues();

        value.put("MED_DEPOTLEGAL", leMedicament.getDepotLegal());
        value.put("MED_NOMCOMMERCIAL", leMedicament.getNomCommercial());
        value.put("FAM_CODE", leMedicament.getFamilleCode());
        value.put("MED_EFFETS", leMedicament.getEffets());
        value.put("MED_CONTREINDIC", leMedicament.getContreIndic());
        value.put("MED_PRIXECHANTILLON", leMedicament.getPrix());

        return super.getDb().update(table, value, "MED_DEPOTLEGAL='"+old_Medicament.getDepotLegal()+"'", null);
    }

    /**
     * Supprime le médicament
     * @param depotLegal String
     * @return long
     */
    public long deleteMedicament(String depotLegal) {
        return super.getDb().delete(table, "MED_DEPOTLEGAL='"+depotLegal+"'", null);
    }

    /**
     * Supprime le médicament
     * @param leMedicament objet Medicament
     * @return long
     */
    public long deleteMedicament(Medicament leMedicament) {
        return super.getDb().delete(table, "MED_DEPOTLEGAL='"+leMedicament.getDepotLegal()+"'", null);
    }
}
