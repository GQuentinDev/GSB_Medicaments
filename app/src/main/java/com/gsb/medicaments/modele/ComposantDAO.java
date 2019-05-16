package com.gsb.medicaments.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

@SuppressWarnings("UnusedReturnValue")
public class ComposantDAO extends MainDAO {

    // Attributs
    private final String table_composant = "composant";
    private final String table_constituer = "constituer";

    // Constructeur

    public ComposantDAO(Context ct) {
        super(ct);
    }

    // Méthodes

    /**
     * Retourne le composant
     * @param code String
     * @return objet Composant
     */
    public Composant getComposant(String code) {
        Composant leComposant;

        Cursor curseur;
        String req;

        req = "SELECT CMP_CODE, CMP_LIBELLE FROM composant WHERE CMP_CODE='"+code+"'";
        curseur = super.getDb().rawQuery(req,null);

        curseur.moveToFirst();
        leComposant = new Composant(
                curseur.getString(0),
                curseur.getString(1)
        );
        curseur.close();

        return leComposant;
    }

    /**
     * Retourne tout les composants d'un médicament
     * @param depotLegal String
     * @return ArrayList<Composant>
     */
    public ArrayList<Composant> getComposants(String depotLegal) {
        ArrayList<Composant> lesComposants = new ArrayList<>();
        Composant leComposant;

        Cursor curseur;
        String req;

        req = "SELECT com.CMP_CODE, com.CMP_LIBELLE FROM constituer con INNER JOIN composant com ON con.CMP_CODE = com.CMP_CODE WHERE con.MED_DEPOTLEGAL='"+depotLegal+"' ORDER BY CMP_LIBELLE";
        curseur = super.getDb().rawQuery(req,null);

        curseur.moveToFirst();
        while (!curseur.isAfterLast()) {
            leComposant = new Composant(
                    curseur.getString(0),
                    curseur.getString(1)
            );
            lesComposants.add(leComposant);
            curseur.moveToNext();
        }
        curseur.close();

        return lesComposants;
    }

    /**
     * Retourne tout les composants
     * @return ArrayList<Composant>
     */
    public ArrayList<Composant> getComposants() {
        Cursor curseur;
        String req = "SELECT * FROM composant ORDER BY CMP_LIBELLE";
        curseur = super.getDb().rawQuery(req,null);
        return cursorToComposantArrayList(curseur);
    }

    private ArrayList<Composant> cursorToComposantArrayList(Cursor curseur){
        ArrayList<Composant> listeComposants = new ArrayList<>();
        Composant leComposant;

        if (curseur.getCount() > 0) {
            curseur.moveToFirst();
            while (!curseur.isAfterLast()) {
                leComposant = new Composant(
                        curseur.getString(0),
                        curseur.getString(1)
                );
                listeComposants.add(leComposant);
                curseur.moveToNext();
            }
        }
        curseur.close();

        return listeComposants;
    }

    /**
     * Ajoute un composant
     * @param unComposant objet Composant
     * @return long
     */
    public long addComposant(Composant unComposant) {
        ContentValues value = new ContentValues();

        value.put("CMP_CODE", unComposant.getCode());
        value.put("CMP_LIBELLE", unComposant.getLibelle());

        return super.getDb().insert(table_composant, null, value);
    }

    /**
     * Ajoute les composants d'un médicament
     * @param lesComposants ArrayList<Composant>
     * @param depotLegal String
     * @return long
     */
    public long addComposition(ArrayList<Composant> lesComposants, String depotLegal) {
        ContentValues value = new ContentValues();
        long nb = 0;

        for (Composant unComposant : lesComposants) {
            value.put("MED_DEPOTLEGAL", depotLegal);
            value.put("CMP_CODE", unComposant.getCode());
            super.getDb().insert(table_constituer, null, value);
            nb++;
        }

        return nb;
    }

    /**
     * Ajoute les composants d'un médicament
     * @param lesComposants ArrayList<Composant>
     * @param leMedicament objet Medicament
     * @return long
     */
    public long addComposition(ArrayList<Composant> lesComposants, Medicament leMedicament) {
        ContentValues value = new ContentValues();
        long nb = 0;

        for (Composant unComposant : lesComposants) {
            value.put("MED_DEPOTLEGAL", leMedicament.getDepotLegal());
            value.put("CMP_CODE", unComposant.getCode());
            super.getDb().insert(table_constituer, null, value);
            nb++;
        }

        return nb;
    }

    /**
     * Ajoute le composant d'un médicament
     * @param leComposant Composant
     * @param depotLegal String
     * @return long
     */
    public long addComposition(Composant leComposant, String depotLegal) {
        ContentValues value = new ContentValues();

        value.put("MED_DEPOTLEGAL", depotLegal);
        value.put("CMP_CODE", leComposant.getCode());

        return super.getDb().insert(table_constituer, null, value);
    }

    /**
     * Ajoute le composant d'un médicament
     * @param leComposant Composant
     * @param leMedicament objet Medicament
     * @return long
     */
    public long addComposition(Composant leComposant, Medicament leMedicament) {
        ContentValues value = new ContentValues();

        value.put("MED_DEPOTLEGAL", leMedicament.getDepotLegal());
        value.put("CMP_CODE", leComposant.getCode());

        return super.getDb().insert(table_constituer, null, value);
    }

    /**
     * Met à jour le composant
     * @param leComposant objet Composant
     * @param old_code String
     * @return long
     */
    public long updateComposant(Composant leComposant, String old_code) {
        ContentValues value = new ContentValues();

        value.put("CMP_CODE", leComposant.getCode());
        value.put("CMP_LIBELLE", leComposant.getLibelle());

        return super.getDb().update(table_composant, value, "CMP_CODE='"+old_code+"'", null);
    }

    /**
     * Met à jour le composant
     * @param leComposant objet Composant
     * @param old_Composant objet Composant
     * @return long
     */
    public long updateComposant(Composant leComposant, Composant old_Composant) {
        ContentValues value = new ContentValues();

        value.put("CMP_CODE", leComposant.getCode());
        value.put("CMP_LIBELLE", leComposant.getLibelle());

        return super.getDb().update(table_composant, value, "CMP_CODE='"+old_Composant.getCode()+"'", null);
    }

    /**
     * Supprime le composant
     * @param code String
     * @return long
     */
    public long deleteComposant(String code) {
        return super.getDb().delete(table_composant, "CMP_CODE='"+code+"'", null);
    }

    /**
     * Supprime le composant
     * @param leComposant objet Composant
     * @return long
     */
    public long deleteComposant(Composant leComposant) {
        return super.getDb().delete(table_composant, "CMP_CODE='"+leComposant.getCode()+"'", null);
    }

    /**
     * Supprime la composition d'un médicament
     * @param depotLegal String
     * @return long
     */
    public long deleteComposition(String depotLegal) {
        return super.getDb().delete(table_constituer, "MED_DEPOTLEGAL='"+depotLegal+"'", null);
    }

    /**
     * Supprime la composition d'un médicament
     * @param leMedicament objet Medicament
     * @return long
     */
    public long deleteComposition(Medicament leMedicament) {
        return super.getDb().delete(table_constituer, "MED_DEPOTLEGAL='"+leMedicament.getDepotLegal()+"'", null);
    }
}
