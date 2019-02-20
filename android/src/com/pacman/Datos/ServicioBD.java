package com.pacman.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ServicioBD extends SQLiteOpenHelper {

    private SQLiteDatabase db;

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
        this.db = db;
        db.execSQL(SQL_DATOS_ENTRADA);
        this.inicializar();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void inicializar() {
        //Metodo que ingresa algunos datos iniciales a la base de datos
        insertarJugador(new Jugador("Agustin", "reina", 1000));
        insertarJugador(new Jugador("Gaston", "1234", 2500));
        insertarJugador(new Jugador("Yaupe", "123987456", 750));
    }

    public long insertarJugador(Jugador jugador) {
        //Metodo que ingresa un nuevo jugador a la base de datos
        return this.db.insert(JugadorContract.JugadorEntry.NOMBRE_TABLA, null, jugador.toContentValues());
    }

    public Cursor getJugadorByUsuario(String usuario) {
        //Metodo que realiza una consulta a la base de datos y retorna su resultado
        Cursor c = getReadableDatabase().query(JugadorContract.JugadorEntry.NOMBRE_TABLA, null, JugadorContract.JugadorEntry.USUARIO + " LIKE ?", new String[]{usuario},
                null, null, null);
        return c;
    }

    public void mostrarBaseDatos() {
        //Metodo que muestra todos los datos almacenados en la base de datos
        Cursor c = getReadableDatabase().query(JugadorContract.JugadorEntry.NOMBRE_TABLA, null, null, null, null, null, null);
        System.out.println("----------------------------------------------BASE DE DATOS PACMAN----------------------------------------");
        while (c.moveToNext()) {
            String nombre = c.getString(c.getColumnIndex(JugadorContract.JugadorEntry.USUARIO));
            System.out.println("Nombre de usuario: " + nombre);
            String contra = c.getString(c.getColumnIndex(JugadorContract.JugadorEntry.CONTRASEÑA));
            System.out.println("Contraseña: " + contra);
            int puntos = c.getInt(c.getColumnIndex(JugadorContract.JugadorEntry.PUNTAJE));
            System.out.println("Puntaje: " + puntos);
            System.out.println("---------------------------------------------------------------------");
        }
    }

    public boolean verificarJugador(Cursor c) {
        // Metodo que verifica que el resultado de una consulta no se vacio
        //Se utiliza para verificar que un jugador se encuntre en la base de datos
        //Retorna true si la consulta no es vacia y, por lo tanto, el jugador se encuentra en la base de datos
        return c.moveToNext();
    }

    public boolean verificarContraseña(Cursor c, String contraseña) {
        //Metodo que compara la contraseña ingresada por parametro con la contraseña de un jugador de la base de datos
        return contraseña.equals(c.getString(c.getColumnIndex(JugadorContract.JugadorEntry.CONTRASEÑA)));
    }

    public boolean actualizarPuntaje(String nombre, int puntaje) {
        //Metodo que actualiza el puntaje de un jugador si el nuevo puntaje supera al anterior
        boolean resultado = false;
        Cursor c = getJugadorByUsuario(nombre);
        if (puntaje > c.getInt(c.getColumnIndex(JugadorContract.JugadorEntry.PUNTAJE))) {
            SQLiteDatabase wdb = getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put(JugadorContract.JugadorEntry.PUNTAJE, puntaje);
            wdb.update(JugadorContract.JugadorEntry.NOMBRE_TABLA, valores, JugadorContract.JugadorEntry.USUARIO + " Like ?", new String[]{nombre});
            resultado = true;
        }
        return resultado;
    }
}


