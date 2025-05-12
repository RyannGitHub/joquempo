package com.example.joquempo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "joquempo.db";
    private static final int VERSAO = 1;

    private static final String TABELA = "placar";
    private static final String COL_ID = "id";
    private static final String COL_USUARIO = "pontos_usuario";
    private static final String COL_MAQUINA = "pontos_maquina";

    public BancoHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + " (" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USUARIO + " INTEGER, " +
                COL_MAQUINA + " INTEGER)";
        db.execSQL(sql);

        // placar inicial
        ContentValues valores = new ContentValues();
        valores.put(COL_ID, 1);
        valores.put(COL_USUARIO, 0);
        valores.put(COL_MAQUINA, 0);
        db.insert(TABELA, null, valores);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }

    public void atualizarPlacar(int pontosUsuario, int pontosMaquina) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COL_USUARIO, pontosUsuario);
        valores.put(COL_MAQUINA, pontosMaquina);
        db.update(TABELA, valores, COL_ID + " = ?", new String[]{"1"});
    }

    public int[] obterPlacar() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_USUARIO + ", " + COL_MAQUINA + " FROM " + TABELA + " WHERE " + COL_ID + " = 1", null);
        int[] resultado = new int[]{0, 0};
        if (cursor.moveToFirst()) {
            resultado[0] = cursor.getInt(0);
            resultado[1] = cursor.getInt(1);
        }
        cursor.close();
        return resultado;
    }

    public void resetarPlacar() {
        atualizarPlacar(0, 0);
    }
}
