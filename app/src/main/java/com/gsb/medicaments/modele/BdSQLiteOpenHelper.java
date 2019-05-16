package com.gsb.medicaments.modele;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressWarnings("WeakerAccess")
public class BdSQLiteOpenHelper extends SQLiteOpenHelper {

    public BdSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Structure
        String composant = "CREATE TABLE composant ("
                +"CMP_CODE TEXT(4) NOT NULL,"
                +"CMP_LIBELLE TEXT(100) DEFAULT NULL,"
                +"PRIMARY KEY (CMP_CODE)"
                +");";

        String famille = "CREATE TABLE famille ("
                +"FAM_CODE TEXT NOT NULL,"
                +"FAM_LIBELLE TEXT DEFAULT NULL,"
                +"PRIMARY KEY (FAM_CODE)"
                +");";

        String medicament = "CREATE TABLE medicament ("
                +"MED_DEPOTLEGAL TEXT NOT NULL,"
                +"MED_NOMCOMMERCIAL TEXT DEFAULT NULL,"
                +"FAM_CODE TEXT NOT NULL,"
                +"MED_EFFETS TEXT DEFAULT NULL,"
                +"MED_CONTREINDIC TEXT DEFAULT NULL,"
                +"MED_PRIXECHANTILLON TEXT DEFAULT NULL,"
                +"PRIMARY KEY (MED_DEPOTLEGAL),"
                +"FOREIGN KEY (FAM_CODE) REFERENCES famille(FAM_CODE)"
                +");";

        String constituer = "CREATE TABLE constituer ("
                +"MED_DEPOTLEGAL TEXT NOT NULL,"
                +"CMP_CODE TEXT NOT NULL,"
                +"PRIMARY KEY (MED_DEPOTLEGAL, CMP_CODE),"
                +"FOREIGN KEY (MED_DEPOTLEGAL) REFERENCES medicament(MED_DEPOTLEGAL),"
                +"FOREIGN KEY (CMP_CODE) REFERENCES composant(CMP_CODE)"
                +");";

        // Données
        String composant_data = "INSERT INTO composant VALUES "
                +"('AACE', 'Acide acétylsalicylique (aspirine)'),"
                +"('AASC', 'Acide ascorbique (Vitamine C)'),"
                +"('ACLA', 'Acide clavulanique'),"
                +"('ADRA', 'Adrafinil'),"
                +"('AMOX', 'Amoxicilline'),"
                +"('BACI', 'Bacitracine'),"
                +"('CLAR', 'Clarithromycine'),"
                +"('CLOM', 'Clomipramine'),"
                +"('CODE', 'Codéine'),"
                +"('DEXT', 'Dextropropoxyphène'),"
                +"('DIPH', 'Diphénydramine'),"
                +"('DOXY', 'Doxylamine'),"
                +"('ERYT', 'Erythromycine'),"
                +"('FOSF', 'Fosfomycine trométamol'),"
                +"('JOSA', 'Josamycine'),"
                +"('LIDO', 'Lidocaïne'),"
                +"('LITH', 'Lithium'),"
                +"('MECL', 'Méclozine'),"
                +"('MIRT', 'Mirtazapine'),"
                +"('NEOM', 'Néomycine'),"
                +"('NYST', 'Nystatine'),"
                +"('OXYT', 'Oxytétracycline'),"
                +"('PARA', 'Paracétamol'),"
                +"('PARO', 'Paroxétine'),"
                +"('PYRA', 'Pyrazinamide'),"
                +"('SULB', 'Sulbutiamine'),"
                +"('TETR', 'Tétracaïne'),"
                +"('TRIA', 'Triamcinolone (acétonide)'),"
                +"('TYRO', 'Tyrothricine')";

        String famille_data = "INSERT INTO famille VALUES "
                +"('AA', 'Antalgiques en association'),"
                +"('AAA', 'Antalgiques antipyrétiques en association'),"
                +"('AAC', 'Antidépresseur d action centrale'),"
                +"('AAH', 'Antivertigineux antihistaminique H1'),"
                +"('ABA', 'Antibiotique antituberculeux'),"
                +"('ABC', 'Antibiotique antiacnéique local'),"
                +"('ABP', 'Antibiotique de la famille des béta-lactamines (pénicilline A)'),"
                +"('AFC', 'Antibiotique de la famille des cyclines'),"
                +"('AFM', 'Antibiotique de la famille des macrolides'),"
                +"('AH', 'Antihistaminique H1 local'),"
                +"('AIM', 'Antidépresseur imipraminique (tricyclique)'),"
                +"('AIN', 'Antidépresseur inhibiteur sélectif de la recapture de la sérotonine'),"
                +"('ALO', 'Antibiotique local (ORL)'),"
                +"('ANS', 'Antidépresseur IMAO non sélectif'),"
                +"('AO', 'Antibiotique ophtalmique'),"
                +"('AP', 'Antipsychotique normothymique'),"
                +"('AUM', 'Antibiotique urinaire minute'),"
                +"('CRT', 'Corticoïde, antibiotique et antifongique à usage local'),"
                +"('HYP', 'Hypnotique antihistaminique'),"
                +"('PSA', 'Psychostimulant, antiasthénique')";

        String medicament_data = "INSERT INTO medicament VALUES "
                +"('3MYC7', 'TRIMYCINE', 'CRT', 'Ce médicament est un corticoïde à activité forte ou très forte associé à un antibiotique et un antifongique, utilisé en application locale dans certaines atteintes cutanées surinfectées.', 'Ce médicament est contre-indiqué en cas d allergie à l un des constituants, d infections de la peau ou de parasitisme non traités, d acné. Ne pas appliquer sur une plaie, ni sous un pansement occlusif.', NULL),"
                +"('ADIMOL9', 'ADIMOL', 'ABP', 'Ce médicament, plus puissant que les pénicillines simples, est utilisé pour traiter des infections bactériennes spécifiques.', 'Ce médicament est contre-indiqué en cas d allergie aux pénicillines ou aux céphalosporines.', NULL),"
                +"('AMOPIL7', 'AMOPIL', 'ABP', 'Ce médicament, plus puissant que les pénicillines simples, est utilisé pour traiter des infections bactériennes spécifiques.', 'Ce médicament est contre-indiqué en cas d allergie aux pénicillines. Il doit être administré avec prudence en cas d allergie aux céphalosporines.', NULL),"
                +"('AMOX45', 'AMOXAR', 'ABP', 'Ce médicament, plus puissant que les pénicillines simples, est utilisé pour traiter des infections bactériennes spécifiques.', 'La prise de ce médicament peut rendre positifs les tests de dépistage du dopage.', NULL),"
                +"('AMOXIG12', 'AMOXI Gé', 'ABP', 'Ce médicament, plus puissant que les pénicillines simples, est utilisé pour traiter des infections bactériennes spécifiques.', 'Ce médicament est contre-indiqué en cas d allergie aux pénicillines. Il doit être administré avec prudence en cas d allergie aux céphalosporines.', NULL),"
                +"('APATOUX22', 'APATOUX Vitamine C', 'ALO', 'Ce médicament est utilisé pour traiter les affections de la bouche et de la gorge.', 'Ce médicament est contre-indiqué en cas d allergie à  l un des constituants, en cas de phénylcétonurie et chez l enfant de moins de 6 ans.', NULL),"
                +"('BACTIG10', 'BACTIGEL', 'ABC', 'Ce médicament est utilisé en application locale pour traiter l acné et les infections cutanées bactériennes associées.', 'Ce médicament est contre-indiqué en cas d allergie aux antibiotiques de la famille des macrolides ou des lincosanides.', NULL),"
                +"('BACTIV13', 'BACTIVIL', 'AFM', 'Ce médicament est utilisé pour traiter des infections bactériennes spécifiques.', 'Ce médicament est contre-indiqué en cas d allergie aux macrolides (dont le chef de file est l érythromycine).', NULL),"
                +"('BITALV', 'BIVALIC', 'AAA', 'Ce médicament est utilisé pour traiter les douleurs d intensité modérée ou intense.', 'Ce médicament est contre-indiqué en cas d allergie aux médicaments de cette famille, d insuffisance hépatique ou d insuffisance rénale.', NULL),"
                +"('CARTION6', 'CARTION', 'AAA', 'Ce médicament est utilisé dans le traitement symptomatique de la douleur ou de la fièvre.', 'Ce médicament est contre-indiqué en cas de troubles de la coagulation (tendances aux hémorragies), d ulcère gastroduodénal, maladies graves du foie.', NULL),"
                +"('CLAZER6', 'CLAZER', 'AFM', 'Ce médicament est utilisé pour traiter des infections bactériennes spécifiques. Il est également utilisé dans le traitement de l ulcère gastro-duodénal, en association avec d autres médicaments.', 'Ce médicament est contre-indiqué en cas d allergie aux macrolides (dont le chef de file est l érythromycine).', NULL),"
                +"('DEPRIL9', 'DEPRAMIL', 'AIM', 'Ce médicament est utilisé pour traiter les épisodes dépressifs sévères, certaines douleurs rebelles, les troubles obsessionnels compulsifs et certaines énurésies chez l enfant.', 'Ce médicament est contre-indiqué en cas de glaucome ou d adénome de la prostate, d infarctus récent, ou si vous avez reà§u un traitement par IMAO durant les 2 semaines précédentes ou en cas d allergie aux antidépresseurs imipraminiques.', NULL),"
                +"('DIMIRTAM6', 'DIMIRTAM', 'AAC', 'Ce médicament est utilisé pour traiter les épisodes dépressifs sévères.', 'La prise de ce produit est contre-indiquée en cas de d allergie à  l un des constituants.', NULL),"
                +"('DOLRIL7', 'DOLORIL', 'AAA', 'Ce médicament est utilisé dans le traitement symptomatique de la douleur ou de la fièvre.', 'Ce médicament est contre-indiqué en cas d allergie au paracétamol ou aux salicylates.', NULL),"
                +"('DORNOM8', 'NORMADOR', 'HYP', 'Ce médicament est utilisé pour traiter l insomnie chez l adulte.', 'Ce médicament est contre-indiqué en cas de glaucome, de certains troubles urinaires (rétention urinaire) et chez l enfant de moins de 15 ans.', NULL),"
                +"('EQUILARX6', 'EQUILAR', 'AAH', 'Ce médicament est utilisé pour traiter les vertiges et pour prévenir le mal des transports.', 'Ce médicament ne doit pas être utilisé en cas d allergie au produit, en cas de glaucome ou de rétention urinaire.', NULL),"
                +"('EVILR7', 'EVEILLOR', 'PSA', 'Ce médicament est utilisé pour traiter les troubles de la vigilance et certains symptomes neurologiques chez le sujet agé.', 'Ce médicament est contre-indiqué en cas d allergie à  l un des constituants.', NULL),"
                +"('INSXT5', 'INSECTIL', 'AH', 'Ce médicament est utilisé en application locale sur les piqûres d insecte et l urticaire.', 'Ce médicament est contre-indiqué en cas d allergie aux antihistaminiques.', NULL),"
                +"('JOVAI8', 'JOVENIL', 'AFM', 'Ce médicament est utilisé pour traiter des infections bactériennes spécifiques.', 'Ce médicament est contre-indiqué en cas d allergie aux macrolides (dont le chef de file est l érythromycine).', NULL),"
                +"('LIDOXY23', 'LIDOXYTRACINE', 'AFC', 'Ce médicament est utilisé en injection intramusculaire pour traiter certaines infections spécifiques.', 'Ce médicament est contre-indiqué en cas d allergie à  l un des constituants. Il ne doit pas être associé aux rétinoïdes.', NULL),"
                +"('LITHOR12', 'LITHORINE', 'AP', 'Ce médicament est indiqué dans la prévention des psychoses maniaco-dépressives ou pour traiter les états maniaques.', 'Ce médicament ne doit pas être utilisé si vous êtes allergique au lithium. Avant de prendre ce traitement, signalez à votre médecin traitant si vous souffrez d insuffisance rénale, ou si vous avez un régime sans sel.', NULL),"
                +"('PARMOL16', 'PARMOCODEINE', 'AA', 'Ce médicament est utilisé pour le traitement des douleurs lorsque des antalgiques simples ne sont pas assez efficaces.', 'Ce médicament est contre-indiqué en cas d allergie à  l un des constituants, chez l enfant de moins de 15 Kg, en cas d insuffisance hépatique ou respiratoire, d asthme, de phénylcétonurie et chez la femme qui allaite.', NULL),"
                +"('PHYSOI8', 'PHYSICOR', 'PSA', 'Ce médicament est utilisé pour traiter les baisses d activité physique ou psychique, souvent dans un contexte de dépression.', 'Ce médicament est contre-indiqué en cas d allergie à  l un des constituants.', NULL),"
                +"('PIRIZ8', 'PIRIZAN', 'ABA', 'Ce médicament est utilisé, en association à  d autres antibiotiques, pour traiter la tuberculose.', 'Ce médicament est contre-indiqué en cas d allergie à l un des constituants, d insuffisance rénale ou hépatique, d hyperuricémie ou de porphyrie.', NULL),"
                +"('POMDI20', 'POMADINE', 'AO', 'Ce médicament est utilisé pour traiter les infections oculaires de la surface de l oeil.', 'Ce médicament est contre-indiqué en cas d allergie aux antibiotiques appliqués localement.', NULL),"
                +"('TROXT21', 'TROXADET', 'AIN', 'Ce médicament est utilisé pour traiter la dépression et les troubles obsessionnels compulsifs. Il peut également être utilisé en prévention des crises de panique avec ou sans agoraphobie.', 'Ce médicament est contre-indiqué en cas d allergie au produit.', NULL),"
                +"('TXISOL22', 'TOUXISOL Vitamine C', 'ALO', 'Ce médicament est utilisé pour traiter les affections de la bouche et de la gorge.', 'Ce médicament est contre-indiqué en cas d allergie à l un des constituants et chez l enfant de moins de 6 ans.', NULL),"
                +"('URIEG6', 'URIREGUL', 'AUM', 'Ce médicament est utilisé pour traiter les infections urinaires simples chez la femme de moins de 65 ans.', 'La prise de ce médicament est contre-indiquée en cas d allergie à  l un des constituants et d insuffisance rénale.', NULL)";

        String constituer_data = "INSERT INTO constituer VALUES "
                +"('3MYC7', 'NEOM'),"
                +"('3MYC7', 'NYST'),"
                +"('3MYC7', 'TRIA'),"
                +"('ADIMOL9', 'ACLA'),"
                +"('ADIMOL9', 'AMOX'),"
                +"('AMOPIL7', 'AMOX'),"
                +"('AMOX45', 'AMOX'),"
                +"('AMOXIG12', 'AMOX'),"
                +"('APATOUX22', 'AASC'),"
                +"('APATOUX22', 'TETR'),"
                +"('APATOUX22', 'TYRO'),"
                +"('BACTIG10', 'ERYT'),"
                +"('BACTIV13', 'ERYT'),"
                +"('BITALV', 'DEXT'),"
                +"('BITALV', 'PARA'),"
                +"('CARTION6', 'AACE'),"
                +"('CARTION6', 'AASC'),"
                +"('CARTION6', 'PARA'),"
                +"('CLAZER6', 'CLAR'),"
                +"('DEPRIL9', 'CLOM'),"
                +"('DIMIRTAM6', 'MIRT'),"
                +"('DOLRIL7', 'AACE'),"
                +"('DOLRIL7', 'AASC'),"
                +"('DOLRIL7', 'PARA'),"
                +"('DORNOM8', 'DOXY'),"
                +"('EQUILARX6', 'MECL'),"
                +"('EVILR7', 'ADRA'),"
                +"('INSXT5', 'DIPH'),"
                +"('JOVAI8', 'JOSA'),"
                +"('LIDOXY23', 'LIDO'),"
                +"('LIDOXY23', 'OXYT'),"
                +"('LITHOR12', 'LITH'),"
                +"('PARMOL16', 'CODE'),"
                +"('PARMOL16', 'PARA'),"
                +"('PHYSOI8', 'SULB'),"
                +"('PIRIZ8', 'PYRA'),"
                +"('POMDI20', 'BACI'),"
                +"('TROXT21', 'PARO'),"
                +"('TXISOL22', 'AASC'),"
                +"('TXISOL22', 'TYRO'),"
                +"('URIEG6', 'FOSF')";

        // Structure
        db.execSQL(composant);
        db.execSQL(famille);
        db.execSQL(medicament);
        db.execSQL(constituer);
        // Données
        db.execSQL(composant_data);
        db.execSQL(famille_data);
        db.execSQL(medicament_data);
        db.execSQL(constituer_data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


