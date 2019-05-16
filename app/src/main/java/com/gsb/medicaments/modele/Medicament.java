package com.gsb.medicaments.modele;

import java.util.ArrayList;

public class Medicament {

    // Attributs

    private String depotLegal;
    private String nomCommercial;
    private String effets;
    private String contreIndic;
    private String prix;
    private Famille laFamille;
    private ArrayList<Composant> lesComposants;

    // Constructeurs

    /**
     * Constructeur de medicament
     * @param depotLegal String dépot légal
     * @param nomCommercial String nom commercial
     * @param effets String effets
     * @param contreIndic String contre indications
     * @param prix String prix
     */
    public Medicament(String depotLegal, String nomCommercial, String effets, String contreIndic, String prix) {
        this.depotLegal = depotLegal;
        this.nomCommercial = nomCommercial;
        this.effets = effets;
        this.contreIndic = contreIndic;
        this.prix = prix;
        this.laFamille = null;
        this.lesComposants = new ArrayList<>();
    }

    /**
     * Constructeur de medicament
     * @param depotLegal String dépot légal
     * @param nomCommercial String nom commercial
     * @param effets String effets
     * @param contreIndic String contre indications
     * @param prix String prix
     * @param laFamille objet Famille
     */
    public Medicament(String depotLegal, String nomCommercial, String effets, String contreIndic, String prix, Famille laFamille) {
        this.depotLegal = depotLegal;
        this.nomCommercial = nomCommercial;
        this.effets = effets;
        this.contreIndic = contreIndic;
        this.prix = prix;
        this.laFamille = laFamille;
        this.lesComposants = new ArrayList<>();
    }

    /**
     * Constructeur de medicament
     * @param depotLegal String dépot légal
     * @param nomCommercial String nom commercial
     * @param effets String effets
     * @param contreIndic String contre indications
     * @param prix String prix
     * @param laFamille objet Famille
     * @param lesComposants ArrayList<Composant>
     */
    public Medicament(String depotLegal, String nomCommercial, String effets, String contreIndic, String prix, Famille laFamille, ArrayList<Composant> lesComposants) {
        this.depotLegal = depotLegal;
        this.nomCommercial = nomCommercial;
        this.effets = effets;
        this.contreIndic = contreIndic;
        this.prix = prix;
        this.laFamille = laFamille;
        this.lesComposants = lesComposants;
    }

    // DepotLegal

    /**
     * Retourne le dépot légal
     * @return String
     */
    public String getDepotLegal() {
        return this.depotLegal;
    }

    /**
     * Modifie le dépot légal
     * @param depotLegal String
     */
    public void setDepotLegal(String depotLegal) {
        this.depotLegal = depotLegal;
    }

    // NomCommercial

    /**
     * Retourne le mon commercial
     * @return String
     */
    public String getNomCommercial() {
        return this.nomCommercial;
    }

    /**
     * Modifie le nom commercial
     * @param nomCommercial String
     */
    public void setNomCommercial(String nomCommercial) {
        this.nomCommercial = nomCommercial;
    }

    // Effets

    /**
     * Retourne les effets
     * @return String
     */
    public String getEffets() {
        return this.effets;
    }

    /**
     * Modifie les effets
     * @param effets String
     */
    public void setEffets(String effets) {
        this.effets = effets;
    }

    // ContreIndic

    /**
     * Retourne les contre indications
     * @return String
     */
    public String getContreIndic() {
        return this.contreIndic;
    }

    /**
     * Modifie les contre indications
     * @param contreIndic String
     */
    public void setContreIndic(String contreIndic) {
        this.contreIndic = contreIndic;
    }

    // Prix

    /**
     * Retourne le prix
     * @return String
     */
    public String getPrix() {
        return this.prix;
    }

    /**
     * Modifie le prix
     * @param prix String
     */
    public void setPrix(String prix) {
        this.prix = prix;
    }

    // Famille

    /**
     * Retourne le code de la famille
     * @return String
     */
    public String getFamilleCode() {
        String famCode;
        try {
            famCode = this.laFamille.getCode();
        }
        catch (NullPointerException npe) {
            famCode = "";
        }
        return famCode;
    }

    /**
     * Retourne le libélle de la famille
     * @return String
     */
    public String getFamilleLibelle() {
        String famLib;
        try {
            famLib = this.laFamille.getLibelle();
        }
        catch (NullPointerException npe) {
            famLib = "";
        }
        return famLib;
    }

    /**
     * Retourne la famille
     * @return objet Famille
     */
    public Famille getLaFamille() {
        return this.laFamille;
    }

    /**
     * Modifie la faille
     * @param laFamille objet Famille
     */
    public void setFamille(Famille laFamille) {
        this.laFamille = laFamille;
    }

    // Composants

    /**
     * Retourne les composants
     * @return ArrayList<Composant>
     */
    public ArrayList<Composant> getLesComposants() {
        return this.lesComposants;
    }

    /**
     * Retourne les composants en un String (chaque composant à la ligne)
     * @return String
     */
    public String getLesComposantsIntoString() {
        StringBuilder composants = new StringBuilder();
        int i = 1;
        for (Composant leComposant : this.lesComposants) {
            composants.append(leComposant.getLibelle());
            if (i < this.lesComposants.size()) {
                composants.append("\n");
            }
            i++;
        }
        return composants.toString();
    }

    /**
     * Retourne la longueur de la collection de composants
     * @return int
     */
    public int getLesComposantsLength() {
        return this.lesComposants.size();
    }

    /**
     * Modifie la collection de composants
     * @param lesComposants ArrayList<Composant>
     */
    public void setComposants(ArrayList<Composant> lesComposants) {
        this.lesComposants = lesComposants;
    }

    /**
     * Ajoute un composant à la collection
     * @param unComposant Composant
     * @return boolean
     */
    public boolean addComposant(Composant unComposant) {
        return this.lesComposants.add(unComposant);
    }

    /**
     * Retire un composant à la collection
     * @param unComposant Composant
     * @return boolean
     */
    public boolean removeComposant(Composant unComposant) {
        boolean res = false;
        int position = 0;
        for (Composant c : this.lesComposants) {
            if (c.getCode().equals(unComposant.getCode())) {
                this.lesComposants.remove(position);
                res = true;
                break;
            }
            position++;
        }
        return res;
    }

    // Autre

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        String msg = "Medicament : "+this.depotLegal+" - "+this.nomCommercial+" - "+this.prix;
        if (!getFamilleCode().isEmpty()) {
            msg += " | "+getFamilleCode()+" - "+getFamilleLibelle();
        }
        return msg;
    }
}
