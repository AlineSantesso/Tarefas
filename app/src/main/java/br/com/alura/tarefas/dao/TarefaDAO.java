package br.com.alura.tarefas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.tarefas.modelo.Tarefa;

/**
 * Created by Augusto on 09/06/2017.
 */

public class TarefaDAO extends SQLiteOpenHelper{
    public TarefaDAO(Context context) {
        super(context, "Tarefa", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Tarefas  (id INTEGER PRIMARY KEY, titulo TEXT NOT NULL, descricao TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Tarefas";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Tarefa tarefa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDaTarefa(tarefa);

        db.insert("Tarefas", null, dados);
    }

    @NonNull
    public ContentValues pegaDadosDaTarefa(Tarefa tarefa) {
        ContentValues dados = new ContentValues();
        dados.put("titulo", tarefa.getTitulo());
        dados.put("descricao", tarefa.getDescricao());
        return dados;
    }

    public List<Tarefa> buscaTarefas() {
        String sql = "SELECT * FROM Tarefas;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Tarefa> tarefas = new ArrayList<Tarefa>();
        while (c.moveToNext()){
            Tarefa tarefa = new Tarefa();
            tarefa.setId(c.getLong(c.getColumnIndex("id")));
            tarefa.setTitulo(c.getString(c.getColumnIndex("titulo")));
            tarefa.setDescricao(c.getString(c.getColumnIndex("descricao")));

            tarefas.add(tarefa);
        }
        c.close();
        return tarefas;

    }

    public void deleta(Tarefa tarefa) {
        SQLiteDatabase db = getWritableDatabase();

        String[] parms = {tarefa.getId().toString()};
        db.delete("Tarefas", "id = ?", parms);
    }

    public void altera(Tarefa tarefa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDaTarefa(tarefa);

        String[] params = {tarefa.getId().toString()};
        db.update("Tarefas", dados, "id = ?", params);

    }
}

