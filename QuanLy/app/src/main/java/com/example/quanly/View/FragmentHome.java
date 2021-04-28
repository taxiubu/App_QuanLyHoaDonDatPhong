 package com.example.quanly.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanly.Model.BookingReference;
import com.example.quanly.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    RelativeLayout btPending,btCheck;
    TextView tvNewOrders,tvPendingOrders,tvCancelOrders,tvPaidOrders,tvConfirmOrder;
    ArrayList<BookingReference> list;
    public static FragmentHome newInstance() {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        btCheck=view.findViewById(R.id.btCheck);
        btPending=view.findViewById(R.id.btPending);
        tvNewOrders=view.findViewById(R.id.tvNewOrders);
        tvPendingOrders=view.findViewById(R.id.tvPendingOrder);
        tvCancelOrders=view.findViewById(R.id.tvCancelOrders);
        tvPaidOrders=view.findViewById(R.id.tvPaidOrders);
        tvConfirmOrder=view.findViewById(R.id.tvConfirmOrder);
        getBookingReference();
        btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Chị dung 2k",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    public void getBookingReference(){
        list= new ArrayList<>();
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("bookingreference");
        mData.orderByChild("dateIn").equalTo("22-4-2021").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for (DataSnapshot e:snapshot.getChildren()){
                        String dateIn=e.getValue(BookingReference.class).getDateIn();
                        String dateOut=e.getValue(BookingReference.class).getDateOut();
                        String email=e.getValue(BookingReference.class).getEmail();
                        String phone=e.getValue(BookingReference.class).getPhone();
                        String nameResidence=e.getValue(BookingReference.class).getNameResidence();
                        String countRoom=e.getValue(BookingReference.class).getCountRoom();
                        String status=e.getValue(BookingReference.class).getStatus();
                        String id=e.getKey();
                        list.add(new BookingReference(id,dateIn,dateOut,email,phone,nameResidence,countRoom,status));
                    }
                }
                setTextBookingReference();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void setTextBookingReference(){
        int countPending=0,countPaid=0,countCancel=0,countConfirm=0;
        for (BookingReference e:list){
            if(e.getStatus().equals("Chờ duyệt"))
                countPending++;
            if(e.getStatus().equals("Đã duyệt"))
                countPending++;
            if(e.getStatus().equals("Đã hủy"))
                countConfirm++;
            if(e.getStatus().equals("Đã thanh toán"))
                countPaid++;
        }
        tvNewOrders.setText(String.valueOf(list.size()));
        tvPendingOrders.setText(String.valueOf(countPending));
        tvCancelOrders.setText(String.valueOf(countCancel));
        tvPaidOrders.setText(String.valueOf(countPaid));
        tvConfirmOrder.setText(String.valueOf(countConfirm));
    }
}