package br.com.alura.tarefas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.alura.tarefas.dao.TarefaDAO;
import br.com.alura.tarefas.modelo.Tarefa;


public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Tarefa tarefa = (Tarefa) intent.getSerializableExtra("tarefa");
        if (tarefa != null){
            helper.preencheFormulario(tarefa);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                Tarefa tarefa = helper.pegaTarefa();

                TarefaDAO dao = new TarefaDAO(this);

                if (tarefa.getTitulo().equals("") || tarefa.getTitulo().equals(null)) {
                    Toast.makeText(FormularioActivity.this, "O campo Titulo deve ser preenchido!!!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (tarefa.getDescricao().equals("") || tarefa.getDescricao().equals(null)) {
                    Toast.makeText(FormularioActivity.this, "O campo Descricao deve ser preenchido!!!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (tarefa.getId() != null){
                    dao.altera(tarefa);
                } else {
                    dao.insere(tarefa);}

                dao.close();

                Toast.makeText(FormularioActivity.this, "Tarefa: " + tarefa.getTitulo() + " salva!", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
