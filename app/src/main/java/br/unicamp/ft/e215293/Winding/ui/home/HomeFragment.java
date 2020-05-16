package br.unicamp.ft.e215293.Winding.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import br.unicamp.ft.e215293.Winding.ArtistFragment;
import br.unicamp.ft.e215293.Winding.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View view;
    private int selected;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        view = inflater.inflate(R.layout.fragment_home, container, false);

        final EditText searchText = (EditText) view.findViewById(R.id.search_1);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_select);


        view.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {

                } else {
                    selected = radioGroup.getCheckedRadioButtonId();
                    final RadioButton radioButton = (RadioButton) view.findViewById(selected);

                    if (radioButton.getText().toString().equals(getString(R.string.music))) {
                        //buscar musica
                        Log.i("zipzop", radioButton.getText().toString() + "|" + getString(R.string.music));
                        Bundle bundle = new Bundle();
                        bundle.putString("data", searchText.getText().toString());
                        NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                        navController.navigate(R.id.arestaHM, bundle);

                    } else if (radioButton.getText().toString().equals(getString(R.string.artist))) {
                        //buscar artista
                        Log.i("zipzop", radioButton.getText().toString() + "|" + getString(R.string.artist) + "|" + searchText.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("data", searchText.getText().toString());
                        NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                        navController.navigate(R.id.arestaHA, bundle);
                    }
                }
            }
        });
        final TextView textView = view.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return view;
    }
}
