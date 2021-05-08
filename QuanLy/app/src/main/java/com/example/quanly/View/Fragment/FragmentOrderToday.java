package com.example.quanly.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanly.Adapter.AItemOrders;
import com.example.quanly.Model.BookingReference;
import com.example.quanly.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FragmentOrderToday extends Fragment {
    RecyclerView rcvOrdersToday;
    public static FragmentOrderToday newInstance(ArrayList<BookingReference> list, int flag) {
        FragmentOrderToday fragment = new FragmentOrderToday();
        Bundle args = new Bundle();
        args.putSerializable("list", list);
        args.putInt("flag", flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_order_today, container, false);
        rcvOrdersToday= view.findViewById(R.id.rcvOrdersToday);
        AItemOrders adapter= new AItemOrders(getContext(),getList());
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvOrdersToday.setLayoutManager(layoutManager);
        rcvOrdersToday.setAdapter(adapter);
        return view;
    }

    private ArrayList<BookingReference> getList() {
        ArrayList<BookingReference> list = (ArrayList<BookingReference>) getArguments().getSerializable("list");
        ArrayList<BookingReference> listShow = new ArrayList<>();
        int flag = (int) getArguments().getSerializable("flag");
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("bookingreference");
        //mData.
        if(flag==1){
            for (BookingReference b:list){
                if(b.getStatus().equals("Chờ duyệt"))
                    listShow.add(b);
            }
        }
        if(flag==2){
            for (BookingReference b:list){
                if(b.getStatus().equals("Đã duyệt"))
                    listShow.add(b);
            }
        }
        return listShow;
    }
}