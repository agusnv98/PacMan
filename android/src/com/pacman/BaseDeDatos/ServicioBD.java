package com.pacman.BaseDeDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ServicioBD extends SQLiteOpenHelper {

    // Versión de la base de datos
    private static final int VERSION_BD = 1;

    // Nombre de la base de datos
    private static final String NOMBRE_BD = "gamescore";

    // Nombre de la tabla
    private static final String TABLE_SCORE = "score";

    // Nombres de cada columna de la tabla
    private static final String KEY_ID = "_id";
    private static final String KEY_SCORE = "score_value";

    public ServicioBD(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //aca hay que definir los metodos para ingresar el usuario, verificarlo, etc...
}

