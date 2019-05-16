package com.gsb.medicaments.modele;

public class Composant {

    // Attributs

    private String code;
    private String libelle;

    // Constructeurs

    /**
     * Constructeur de Composant
     * @param code String
     * @param libelle String
     */
    public Composant(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
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
     * @param code String
     */
    public void setCode(String code) {
        this.code = code;
    }

    // Libelle

    /**
     * Return le libelle
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

    // Autre

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return "Composant : "+this.code+" - "+this.libelle;
    }
}
