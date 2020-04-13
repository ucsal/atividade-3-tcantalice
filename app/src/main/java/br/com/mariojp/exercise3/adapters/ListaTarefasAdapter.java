package br.com.mariojp.exercise3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.mariojp.exercise3.Tarefa;

public class ListaTarefasAdapter extends BaseAdapter {

    private static final int POS_INSERT_CONTROL = -1;

    private Set<String> descricaoTarefas = new HashSet<String>();
    private List<Tarefa> listaTarefas;

    private Context context;

    public ListaTarefasAdapter(Context context, List<Tarefa> listaTarefas) {
        this.context = context;
        this.listaTarefas = listaTarefas;
    }

    public ListaTarefasAdapter(Context context) {
        this(context, new ArrayList<Tarefa>());
    }

    @Override
    public int getCount() {
        return listaTarefas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaTarefas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int resource = android.R.layout.simple_list_item_2;
        final View view;

        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        TextView descricao = view.findViewById(android.R.id.text1);
        TextView prioridade = view.findViewById(android.R.id.text2);

        Tarefa tarefa = listaTarefas.get(position);

        descricao.setText(tarefa.getDescricao());
        prioridade.setText("Prioridade: " + tarefa.getPrioridade());

        return view;
    }

    @Override
    public boolean isEmpty() {
        return listaTarefas.isEmpty();
    }

    public void add(Tarefa tarefa) {
        descricaoTarefas.add(tarefa.getDescricao());

        if(this.isEmpty()) {
            listaTarefas.add(tarefa);
        } else {
            int prioridade = tarefa.getPrioridade();
            int posInsercao = POS_INSERT_CONTROL;

            for(int i = 0; i < listaTarefas.size(); i++) {
                if(prioridade < listaTarefas.get(i).getPrioridade()) {
                    posInsercao = i;
                    break;
                }
            }

            if(posInsercao != POS_INSERT_CONTROL)
                listaTarefas.add(posInsercao, tarefa);
            else
                listaTarefas.add(tarefa);

        }

        notifyDataSetChanged();
    }

    public boolean exists(String descricao) {
        return descricaoTarefas.contains(descricao);
    }

    public void remove(int position) {
        Tarefa t = listaTarefas.get(position);
        descricaoTarefas.remove(t.getDescricao());
        listaTarefas.remove(t);

        notifyDataSetInvalidated();
    }
}
