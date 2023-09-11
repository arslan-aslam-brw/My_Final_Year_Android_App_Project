package com.example.fooddeliveryandrecommendationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.fooddeliveryandrecommendationapp.SellerFoodPanel.SellerHomeFragment;
import com.example.fooddeliveryandrecommendationapp.SellerFoodPanel.SellerOrderFragment;
import com.example.fooddeliveryandrecommendationapp.SellerFoodPanel.SellerPendingOrdersFragment;
import com.example.fooddeliveryandrecommendationapp.SellerFoodPanel.SellerProfileFragment;
import com.example.fooddeliveryandrecommendationapp.SendNotification.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class SellerFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_food_panel__bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.seller_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        updateToken();
        String name = getIntent().getStringExtra("PAGE");
        if (name != null) {
            if (name.equalsIgnoreCase("Orderpage")) {
                loadsellerfragment(new SellerPendingOrdersFragment());
            } else if (name.equalsIgnoreCase("Confirmpage")) {
                loadsellerfragment(new SellerOrderFragment());
            } else if (name.equalsIgnoreCase("AcceptOrderpage")) {
                loadsellerfragment(new SellerHomeFragment());
            } else if (name.equalsIgnoreCase("Deliveredpage")) {
                loadsellerfragment(new SellerHomeFragment());
            }
        } else {
            loadsellerfragment(new SellerHomeFragment());
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


    private boolean loadsellerfragment(Fragment fragment) {
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
            case R.id.sellerHome:
                fragment = new SellerHomeFragment();
                break;

            case R.id.PendingOrders:
                fragment = new SellerPendingOrdersFragment();
                break;

            case R.id.Orders:
                fragment = new SellerOrderFragment();
                break;
            case R.id.sellerProfile:
                fragment = new SellerProfileFragment();
                break;
        }
        return loadsellerfragment(fragment);
    }
}
