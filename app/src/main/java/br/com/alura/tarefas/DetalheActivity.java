package br.com.alura.tarefas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.alura.tarefas.modelo.Tarefa;

public class DetalheActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Tarefa tarefa = (Tarefa) intent.getSerializableExtra("tarefa");
        if (tarefa != null){
            helper.preencheFormulario(tarefa);

        }

    }
}
