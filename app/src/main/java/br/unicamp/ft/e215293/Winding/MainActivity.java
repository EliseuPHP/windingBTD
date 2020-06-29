package br.unicamp.ft.e215293.Winding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import br.unicamp.ft.e215293.Winding.internet.ImageLoadTask;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private AppBarConfiguration mAppBarConfiguration;

    private NavigationView navigationView;


    /* Broadcast receiver */
    private ServiceReceiver serviceReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.musicsFragment, R.id.favoritesFragment, R.id.artistFragment)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateUi();
            }
        });

        /* Registrar o BroadCastReceiver */
        serviceReceiver = new ServiceReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("REDIRECTING");
        registerReceiver(serviceReceiver, intentFilter);
    }

    private void updateUi() {

        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser != null) {
            //nav header
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            //change header size
            LinearLayout layout = (LinearLayout) headerView.findViewById(R.id.linLayoutHeader);
            layout.setLayoutParams(new LinearLayout.LayoutParams(layout.getLayoutParams().width, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 176, getResources().getDisplayMetrics())));
            //change data
            TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_name);
            navUsername.setVisibility(View.VISIBLE);
            navUsername.setText(mFirebaseUser.getDisplayName());
            TextView navEmail = (TextView) headerView.findViewById(R.id.nav_header_email);
            navEmail.setVisibility(View.VISIBLE);
            navEmail.setText(mFirebaseUser.getEmail());
            CardView cardView = (CardView) headerView.findViewById(R.id.cardView);
            cardView.setVisibility(View.VISIBLE);
            ImageView navImage = (ImageView) headerView.findViewById(R.id.nav_header_photo);
            navImage.setVisibility(View.VISIBLE);
            new ImageLoadTask(mFirebaseUser.getPhotoUrl().toString(), navImage).execute();
            //sidebar
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.favoritesFragment).setVisible(true);
        } else {

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            //change header size
            LinearLayout layout = (LinearLayout) headerView.findViewById(R.id.linLayoutHeader);
            layout.setLayoutParams(new LinearLayout.LayoutParams(layout.getLayoutParams().width, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 82, getResources().getDisplayMetrics())));
            //change data
            TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_name);
            navUsername.setVisibility(View.GONE);
            TextView navEmail = (TextView) headerView.findViewById(R.id.nav_header_email);
            navEmail.setVisibility(View.GONE);
            CardView cardView = (CardView) headerView.findViewById(R.id.cardView);
            cardView.setVisibility(View.GONE);
            ImageView navImage = (ImageView) headerView.findViewById(R.id.nav_header_photo);
            navImage.setVisibility(View.GONE);
            //sidebar
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.favoritesFragment).setVisible(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private class ServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
        }
    }
}

