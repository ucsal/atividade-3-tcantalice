package br.com.mariojp.exercise3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.mariojp.exercise3.adapters.ListaTarefasAdapter;

public class MainActivity extends AppCompatActivity {

    ListaTarefasAdapter mTarefasAdapter;

    ListView listView;
    Button btnAdicionar;
    Button btnRemover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTarefasAdapter = new ListaTarefasAdapter(this);

        listView = findViewById(R.id.listView);
        listView.setAdapter(mTarefasAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.removeTarefa(position);
            }
        });

        btnAdicionar = findViewById(R.id.buttonAdicionar);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtDescricao = findViewById(R.id.editDescricao);
                EditText edtPrioridade = findViewById(R.id.editPrioridade);

                String descricao = edtDescricao.getText().toString();
                Integer prioridade = new Integer(edtPrioridade.getText().toString());

                if(MainActivity.this.addTarefa(descricao, prioridade)){
                    edtDescricao.setText("");
                    edtPrioridade.setText("");
                }
            }
        });

        btnRemover = findViewById(R.id.buttonRemover);

        btnRemover.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                MainActivity.this.removePrimeiraTarefa();
            }
        });

        deactivateRemoverIfEmpty();
    }

    protected boolean addTarefa(String descricao, int prioridade) {
        if(prioridade <= 0 || prioridade > 10) {
            Toast.makeText(this, "A prioridade deve estar entre 1 e 10.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mTarefasAdapter.exists(descricao)) {
            Toast.makeText(this, "Tarefa já cadastrada.", Toast.LENGTH_SHORT).show();
            return false;
        }

        Tarefa ntarefa = new Tarefa(descricao, prioridade);

        mTarefasAdapter.add(ntarefa);

        deactivateRemoverIfEmpty();

        return true;
    }

    protected void removePrimeiraTarefa() {
        this.removeTarefa(0);
    }

    protected void removeTarefa(int position) {
        mTarefasAdapter.remove(position);
        deactivateRemoverIfEmpty();
    }

    protected void deactivateRemoverIfEmpty() {
        btnRemover.setEnabled(mTarefasAdapter.isEmpty() ? false: true);
    }
}
