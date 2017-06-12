package br.com.alura.tarefas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.tarefas.dao.TarefaDAO;
import br.com.alura.tarefas.modelo.Tarefa;

public class ListaTarefasActivity extends AppCompatActivity {

    private ListView listaTarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tarefas);

        listaTarefas = (ListView) findViewById(R.id.Lista_tarefas);

        listaTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Tarefa tarefa = (Tarefa) listaTarefas.getItemAtPosition(position);
                Intent intentVaiPraConsulta = new Intent(ListaTarefasActivity.this, DetalheActivity.class);
                intentVaiPraConsulta.putExtra("tarefa", tarefa);
                startActivity(intentVaiPraConsulta);
            }
        });

        Button novaTarefa = (Button) findViewById(R.id.tarefas_incluir);
        novaTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VaiProFormulario = new Intent (ListaTarefasActivity.this, FormularioActivity.class);
                startActivity(VaiProFormulario);
            }
        });
        
        registerForContextMenu(listaTarefas);
    }

    private void carregaLista() {
        TarefaDAO dao = new TarefaDAO(this);
        List<Tarefa> tarefas = dao.buscaTarefas();
        dao.close();

        ArrayAdapter<Tarefa> adapter = new ArrayAdapter<Tarefa>(this, android.R.layout.simple_list_item_1, tarefas);
        listaTarefas.setAdapter(adapter);

        if (adapter.isEmpty()) {
            Toast.makeText(ListaTarefasActivity.this, "Insira sua primeira tarefa", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem editar = menu.add("Editar");
        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Tarefa tarefa = (Tarefa) listaTarefas.getItemAtPosition(info.position);

                Intent intentVaiProFormulario = new Intent(ListaTarefasActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("tarefa", tarefa);
                startActivity(intentVaiProFormulario);

                carregaLista();

                return false;
            }
        });

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Tarefa tarefa = (Tarefa) listaTarefas.getItemAtPosition(info.position);

                TarefaDAO dao = new TarefaDAO(ListaTarefasActivity.this);
                dao.deleta(tarefa);
                dao.close();

                Toast.makeText(ListaTarefasActivity.this, "Tarefa deletada", Toast.LENGTH_SHORT).show();

                carregaLista();

                return false;
            }
        });
    }
}
