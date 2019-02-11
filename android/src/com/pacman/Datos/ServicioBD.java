package com.pacman.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ServicioBD extends SQLiteOpenHelper {

    // Versión de la base de datos
    private static final int VERSION_BD = 1;

    // Nombre de la base de datos
    private static final String NOMBRE_BD = "gamescore";

    // Instrucciones SQL para la creacion de una tabla
    public static final String SQL_DATOS_ENTRADA = "CREATE TABLE " + JugadorContract.JugadorEntry.NOMBRE_TABLA + " ("
            + JugadorContract.JugadorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + JugadorContract.JugadorEntry.USUARIO + " TEXT NOT NULL,"
            + JugadorContract.JugadorEntry.CONTRASEÑA + " TEXT NOT NULL,"
            + JugadorContract.JugadorEntry.PUNTAJE + " INTEGER"
            + ")";

    public ServicioBD(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DATOS_ENTRADA);
        this.inicializar(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //Metodos que ingresan algunos datos iniciales a la base de datos

    private void inicializar(SQLiteDatabase db) {
        inicializarJugador(db, new Jugador("Agustin", "reina", 1000));
        inicializarJugador(db, new Jugador("Gaston", "1234", 2500));
        inicializarJugador(db, new Jugador("Yaupe", "123987456", 750));
    }

    private long inicializarJugador(SQLiteDatabase db, Jugador jugador) {
        return db.insert(JugadorContract.JugadorEntry.NOMBRE_TABLA, null, jugador.toContentValues());
    }

    public void getJugadorByUsuario(String usuario) {
        Cursor c = getReadableDatabase().query(JugadorContract.JugadorEntry.NOMBRE_TABLA, null, JugadorContract.JugadorEntry.USUARIO + " LIKE ?", new String[]{usuario},
                null, null, null);
        if (c.getColumnCount() != 0) {
            System.out.println("NRO COL " + c.getColumnCount());
            System.out.println("----------------------------------Consulta exitosa------------------------------------");
            while (c.moveToNext()) {
                System.out.println("--------------------------------ENTRO AL BUCLE---------------------------------------------");
                System.out.println(c.getPosition());
                String nombre = c.getString(c.getColumnIndex(JugadorContract.JugadorEntry.USUARIO));
                System.out.println("Nombre de usuario: " + nombre);
                String contra = c.getString(c.getColumnIndex(JugadorContract.JugadorEntry.CONTRASEÑA));
                System.out.println("Contraseña: " + contra);
                int puntos = c.getInt(c.getColumnIndex(JugadorContract.JugadorEntry.PUNTAJE));
                System.out.println("Puntaje: " + puntos);
            }
        } else {
            System.out.println("Consulta fallida");
        }
    }
}


