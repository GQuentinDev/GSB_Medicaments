package com.gsb.medicaments.modele;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

@SuppressWarnings("FieldCanBeLocal")
abstract class MainDAO {

    // Attributs

    private static final String base = "BD_Medicament";
    private static final int version = 1;
    private final BdSQLiteOpenHelper accesBD;
    private final SQLiteDatabase db;

    // Constructeur

    MainDAO(Context ct) {
        this.accesBD = new BdSQLiteOpenHelper(ct, base, null, version);
        this.db = accesBD.getWritableDatabase();
    }

    SQLiteDatabase getDb() {
        return this.db;
    }
}
