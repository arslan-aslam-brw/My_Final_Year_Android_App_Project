package com.example.fooddeliveryandrecommendationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.fooddeliveryandrecommendationapp.CustomerFoodPanel.CustomerCartFragment;
import com.example.fooddeliveryandrecommendationapp.CustomerFoodPanel.CustomerHomeFragment;
import com.example.fooddeliveryandrecommendationapp.CustomerFoodPanel.CustomerOrderFragment;
import com.example.fooddeliveryandrecommendationapp.CustomerFoodPanel.CustomerProfileFragment;
import com.example.fooddeliveryandrecommendationapp.CustomerFoodPanel.CustomerTrackFragment;
import com.example.fooddeliveryandrecommendationapp.SendNotification.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class CustomerFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_panel__bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        updateToken();
        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (name != null) {
            if (name.equalsIgnoreCase("Homepage")) {
                loadFragment(new CustomerHomeFragment());
            } else if (name.equalsIgnoreCase("Preparingpage")) {
                loadFragment(new CustomerTrackFragment());
            } else if (name.equalsIgnoreCase("Preparedpage")) {
                loadFragment(new CustomerTrackFragment());
            } else if (name.equalsIgnoreCase("DeliverOrderpage")) {
                loadFragment(new CustomerTrackFragment());
            } else if (name.equalsIgnoreCase("ThankYoupage")) {
                loadFragment(new CustomerHomeFragment());
            }
        } else {
            loadFragment(new CustomerHomeFragment());
        }
    }

    private void updateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    String refreshToken = task.getResult();
                    Token token = new Token(refreshToken);
                    FirebaseDatabase.getInstance().getReference("Tokens").child(firebaseUser.getUid()).setValue(token);
                }
            }
        });
    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.Home:
                fragment = new CustomerHomeFragment();
                break;


            case R.id.Cart:
                fragment = new CustomerCartFragment();
                break;

            case R.id.Order:
                fragment = new CustomerOrderFragment();
                break;

            case R.id.Track:
                fragment = new CustomerTrackFragment();
                break;

            case R.id.Profile:
                fragment = new CustomerProfileFragment();
                break;

        }
        return loadFragment(fragment);
    }
}
