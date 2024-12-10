package com.example.base;

import android.content.Context;
import android.content.SharedPreferences;
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

public class EditarCuentaFragment extends Fragment {

    private EditText etNombre;
    private EditText etCorreo;
    private Button btnGuardar;
    private Button btnLogout;
    private UsuarioDao usuarioDao;
    private Usuario usuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_cuenta, container, false);

        etNombre = view.findViewById(R.id.etNombre);
        etCorreo = view.findViewById(R.id.etCorreo);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnLogout = view.findViewById(R.id.btnLogout);

        usuarioDao = Room.databaseBuilder(getContext(), AppDatabase.class, "app_database").allowMainThreadQueries().build().usuarioDao();

        // Obtener el ID del usuario desde SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int usuarioId = preferences.getInt("usuarioId", -1);
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
                    etNombre.setText(usuario.getNombre());
                    etCorreo.setText(usuario.getCorreo());
                });
            }
        }).start();

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();

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

        btnLogout.setOnClickListener(v -> {
            SharedPreferences logoutPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = logoutPreferences.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new WelcomeFragment())
                    .commit();
        });

        return view;
    }
}