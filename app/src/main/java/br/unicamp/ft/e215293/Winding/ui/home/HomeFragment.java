package br.unicamp.ft.e215293.Winding.ui.home;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.unicamp.ft.e215293.Winding.ArtistFragment;
import br.unicamp.ft.e215293.Winding.R;
import br.unicamp.ft.e215293.Winding.SignInActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View view;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        view = inflater.inflate(R.layout.fragment_home, container, false);

        final EditText searchText = (EditText) view.findViewById(R.id.search_1);

        view.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //buscar musica
                Bundle bundle = new Bundle();
                bundle.putString("data", searchText.getText().toString());
                bundle.putInt("origin", 1);
                NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                navController.navigate(R.id.arestaHM, bundle);

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

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser != null) {
            TextView welcomeText = (TextView) view.findViewById(R.id.text_welcome);
            System.out.println("************************" + R.string.welcomeBack);
            welcomeText.setText(getContext().getResources().getString(R.string.welcomeBack) + " " + mFirebaseUser.getDisplayName());
            final TextView authText = (TextView) view.findViewById(R.id.text_auth);
            authText.setText(getContext().getResources().getString(R.string.logout));
            authText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SignInActivity.class);
                    startActivity(intent);
                    System.out.println(authText.getText().toString());
                }
            });
        } else {
            TextView welcomeText = (TextView) view.findViewById(R.id.text_welcome);
            welcomeText.setText(getContext().getResources().getString(R.string.welcome));
            final TextView authText = (TextView) view.findViewById(R.id.text_auth);
            authText.setText(getContext().getResources().getString(R.string.login));
            authText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SignInActivity.class);
                    startActivity(intent);
                    System.out.println(authText.getText().toString());
                }
            });
        }
    }
}
