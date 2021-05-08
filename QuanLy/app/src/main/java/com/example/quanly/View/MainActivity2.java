package com.example.quanly.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.quanly.Model.BookingReference;
import com.example.quanly.R;
import com.example.quanly.View.Fragment.FragmentDetailOrder;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "MainActivity2";
    BookingReference bookingReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bookingReference=(BookingReference)getIntent().getSerializableExtra("bookingReference");
        getFragment(FragmentDetailOrder.newInstance(bookingReference));
    }
    public void getFragment(Fragment fragment){
        try{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_main2, fragment)
                    .commit();


        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "getFragment: "+e.getMessage());
        }
    }
}