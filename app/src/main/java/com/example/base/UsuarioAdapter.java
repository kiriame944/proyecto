package com.example.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> usuarios;
    private AdminFragment fragment;

    public UsuarioAdapter(List<Usuario> usuarios, AdminFragment fragment) {
        this.usuarios = usuarios;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.tvNombre.setText(usuario.getNombre());
        holder.tvCorreo.setText(usuario.getCorreo());
        holder.btnEditarUsuario.setOnClickListener(v -> fragment.editarUsuario(usuario));
        holder.btnBorrarUsuario.setOnClickListener(v -> fragment.borrarUsuario(usuario));
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvCorreo;
        Button btnEditarUsuario;
        Button btnBorrarUsuario;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCorreo = itemView.findViewById(R.id.tvCorreo);
            btnEditarUsuario = itemView.findViewById(R.id.btnEditarUsuario);
            btnBorrarUsuario = itemView.findViewById(R.id.btnBorrarUsuario);
        }
    }
}