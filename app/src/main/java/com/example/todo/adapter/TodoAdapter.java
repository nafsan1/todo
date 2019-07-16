package com.example.todo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.model.MTodo;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {
    private List<MTodo> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemVIew = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoHolder(itemVIew);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        MTodo mTodo = list.get(position);
        holder.txt_title.setText(mTodo.getTitle());
        holder.txt_body.setText(String.valueOf(mTodo.getOrder()));
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, list.get(position), position);
                }
            }
        });
    }

    public void setTodo(List<MTodo> todo) {
        this.list = todo;
        notifyDataSetChanged();
    }
    public void removeToDo(int position){
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public String  getIdTodo(int position) {
        return list.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TodoHolder extends RecyclerView.ViewHolder {
        private TextView txt_title;
        private TextView txt_body;
        private LinearLayout lyt_parent;

        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_body = itemView.findViewById(R.id.txt_body);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, MTodo obj, int position);
    }
}
