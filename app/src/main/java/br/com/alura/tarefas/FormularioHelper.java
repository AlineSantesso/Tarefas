package br.com.alura.tarefas;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.alura.tarefas.modelo.Tarefa;

/**
 * Created by Augusto on 09/06/2017.
 */

public class FormularioHelper extends FormularioActivity {

    private final TextView campoTitulo;
    private final TextView campoDescricao;

    private Tarefa tarefa;

    public FormularioHelper(FormularioActivity activity){
        campoTitulo = (EditText) activity.findViewById(R.id.formulario_titulo);
        campoDescricao = (EditText) activity.findViewById(R.id.formulario_descricao);
        tarefa = new Tarefa();
    }

    public FormularioHelper(DetalheActivity detalheActivity) {
        campoTitulo = (TextView) detalheActivity.findViewById(R.id.detalhe_titulo);
        campoDescricao = (TextView) detalheActivity.findViewById(R.id.detalhe_descricao);
        tarefa = new Tarefa();
    }

    public Tarefa pegaTarefa() {
        tarefa.setTitulo(campoTitulo.getText().toString());
        tarefa.setDescricao(campoDescricao.getText().toString());

        return tarefa;
    }

    public void preencheFormulario(Tarefa tarefa) {
        campoTitulo.setText(tarefa.getTitulo());
        campoDescricao.setText(tarefa.getDescricao());
        this.tarefa = tarefa;
    }


}
