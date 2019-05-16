package com.gsb.medicaments.modele;

import java.util.ArrayList;

public class Famille {

    // Attributs

    private String code;
    private String libelle;
    @SuppressWarnings("CanBeFinal")
    private ArrayList<Medicament> lesMedicaments;

    // Constructeurs

    /**
     * Constructeur de famille
     * @param code String
     * @param libelle String
     */
    public Famille(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
        this.lesMedicaments = new ArrayList<>();
    }

    /**
     * Constructeur de famille
     * @param code String
     * @param libelle String
     * @param lesMedicaments ArrayList<Medicament>
     */
    public Famille(String code, String libelle, ArrayList<Medicament> lesMedicaments) {
        this.code = code;
        this.libelle = libelle;
        this.lesMedicaments = lesMedicaments;
    }

    // Code

    /**
     * Retourne le code
     * @return String
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Modifie le code
     * @param code Strinf
     */
    public void setCode(String code) {
        this.code = code;
    }

    // Libelle

    /**
     * Retourne le libelle
     * @return String
     */
    public String getLibelle() {
        return this.libelle;
    }

    /**
     * Modifie le libelle
     * @param libelle String
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    // Les medicaments

    /**
     * Retourne les médicaments
     * @return ArrayList<Medicament>
     */
    public ArrayList<Medicament> getLesMedicaments() {
        return this.lesMedicaments;
    }

    /**
     * Retourne la longueur de la collection de medicament
     * @return int
     */
    public int getLesMedicamentsLength() {
        return this.lesMedicaments.size();
    }

    /**
     * Ajoute un médicament à la collection
     * @param unMedicament objet Medicament
     * @return boolean
     */
    public boolean addMedicament(Medicament unMedicament) {
        return this.lesMedicaments.add(unMedicament);
    }

    /**
     * Retire un médicament à la collection
     * @param unMedicament objet Medicament
     * @return boolean
     */
    public boolean removeMedicament(Medicament unMedicament) {
        boolean res = false;
        int position = 0;
        for (Medicament m : this.lesMedicaments) {
            if (m.getDepotLegal().equals(unMedicament.getDepotLegal())) {
                this.lesMedicaments.remove(position);
                res = true;
                break;
            }
            position++;
        }
        return res;
    }

    //Autre

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return "Famille : "+this.code+" - "+this.libelle;
    }
}
