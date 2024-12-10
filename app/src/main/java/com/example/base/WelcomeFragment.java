package com.example.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class WelcomeFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        Log.d("Menu", "Menu inflated");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_login) {
            navigateToFragment(new LoginFragment());
            return true;
        } else if (itemId == R.id.nav_registro) {
            navigateToFragment(new RegistroFragment());
            return true;
        } else if (itemId == R.id.nav_perfil) {
            navigateToFragment(new EditarCuentaFragment());
            return true;
        } else if (itemId == R.id.nav_admin) {
            navigateToFragment(new AdminFragment());
            return true;
        } else if (itemId == R.id.nav_tareas) {
            navigateToFragment(new TareasFragment());
            return true;
        } else if (itemId == R.id.nav_pomodoro) {
            navigateToFragment(new PomodoroFragment());
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}