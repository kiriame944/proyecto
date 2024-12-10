package com.example.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

public class EditarPerfilAdminFragment extends Fragment {

    private EditText etNombreAdmin;
    private EditText etCorreoAdmin;
    private Button btnGuardarAdmin;
    private UsuarioDao usuarioDao;
    private Usuario usuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_perfil_admin, container, false);

        etNombreAdmin = view.findViewById(R.id.etNombreAdmin);
        etCorreoAdmin = view.findViewById(R.id.etCorreoAdmin);
        btnGuardarAdmin = view.findViewById(R.id.btnGuardarAdmin);

        usuarioDao = Room.databaseBuilder(getContext(), AppDatabase.class, "app_database").allowMainThreadQueries().build().usuarioDao();

        // Obtener el ID del usuario desde los argumentos
        int usuarioId = getArguments() != null ? getArguments().getInt("usuarioId", -1) : -1;
        if (usuarioId == -1) {
            Toast.makeText(getContext(), "Error al obtener el ID del usuario", Toast.LENGTH_SHORT).show();
            return view;
        }

        new Thread(() -> {
            usuario = usuarioDao.getUsuarioById(usuarioId);
            if (usuario == null) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    });
                }
                return;
            }
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    etNombreAdmin.setText(usuario.getNombre());
                    etCorreoAdmin.setText(usuario.getCorreo());
                });
            }
        }).start();

        btnGuardarAdmin.setOnClickListener(v -> {
            String nombre = etNombreAdmin.getText().toString().trim();
            String correo = etCorreoAdmin.getText().toString().trim();

            if (nombre.isEmpty() || correo.isEmpty()) {
                Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            usuario.setNombre(nombre);
            usuario.setCorreo(correo);

            new Thread(() -> {
                try {
                    usuarioDao.update(usuario);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });

        return view;
    }
}