package com.example.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.util.List;

public class AdminFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarios;
    private UsuarioDao usuarioDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        usuarioDao = Room.databaseBuilder(getContext(), AppDatabase.class, "app_database").allowMainThreadQueries().build().usuarioDao();
        usuarios = obtenerUsuarios();
        usuarioAdapter = new UsuarioAdapter(usuarios, this);
        recyclerView.setAdapter(usuarioAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        int position = recyclerView.getChildAdapterPosition((View) v.getParent().getParent());
        Usuario usuario = usuarios.get(position);

        if (v.getId() == R.id.btnEditarUsuario) {
            editarUsuario(usuario);
        } else if (v.getId() == R.id.btnBorrarUsuario) {
            borrarUsuario(usuario);
        }
    }

    public void editarUsuario(Usuario usuario) {
        // Lógica para editar el usuario
        EditarPerfilAdminFragment editarPerfilAdminFragment = new EditarPerfilAdminFragment();
        Bundle args = new Bundle();
        args.putInt("usuarioId", usuario.getId());
        editarPerfilAdminFragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editarPerfilAdminFragment)
                .addToBackStack(null)
                .commit();
    }

    private List<Usuario> obtenerUsuarios() {
        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "app_database").allowMainThreadQueries().build();
        return db.usuarioDao().getAllUsuarios();
    }

    public void borrarUsuario(Usuario usuario) {
        new Thread(() -> {
            try {
                usuarioDao.delete(usuario);
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        usuarios.clear();
                        usuarios.addAll(obtenerUsuarios());
                        usuarioAdapter.notifyDataSetChanged();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}