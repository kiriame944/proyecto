package com.example.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {

    private List<Tarea> tareas;
    private TareasFragment fragment;

    public TareaAdapter(List<Tarea> tareas, TareasFragment fragment) {
        this.tareas = tareas;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = tareas.get(position);
        holder.tvNombreTarea.setText(tarea.getNombre());
        holder.tvMateria.setText(tarea.getMateria());
        holder.tvDescripcion.setText(tarea.getDescripcion());
        holder.tvFechaHoraLimite.setText(tarea.getFechaHoraLimite().toString());
        holder.btnEditarTarea.setOnClickListener(v -> fragment.editarTarea(tarea));
        holder.btnBorrarTarea.setOnClickListener(v -> fragment.borrarTarea(tarea));
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreTarea;
        TextView tvMateria;
        TextView tvDescripcion;
        TextView tvFechaHoraLimite;
        Button btnEditarTarea;
        Button btnBorrarTarea;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreTarea = itemView.findViewById(R.id.tvNombreTarea);
            tvMateria = itemView.findViewById(R.id.tvMateria);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvFechaHoraLimite = itemView.findViewById(R.id.tvFechaHoraLimite);
            btnEditarTarea = itemView.findViewById(R.id.btnEditarTarea);
            btnBorrarTarea = itemView.findViewById(R.id.btnBorrarTarea);
        }
    }
}