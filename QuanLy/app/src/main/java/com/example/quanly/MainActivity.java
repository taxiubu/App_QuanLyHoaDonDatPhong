package com.example.quanly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.quanly.View.FragmentAccount;
import com.example.quanly.View.FragmentHome;
import com.example.quanly.View.FragmentOrder;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragment(FragmentHome.newInstance());
        navigationView= findViewById(R.id.navigation_bottom);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeFragment:{
                        getFragment(FragmentHome.newInstance());
                        break;
                    }

                    case R.id.orderFragment:
                    {
                        getFragment(FragmentOrder.newInstance());
                        break;

                    }
                    case R.id.accountFragment:
                    {
                        getFragment(FragmentAccount.newInstance());
                        break;
                    }
                }
                return false;
            }
        });
    }
    public void getFragment(Fragment fragment){
        try{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_fragment_container, fragment)
                    .commit();


        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "getFragment: "+e.getMessage());
        }
    }
}