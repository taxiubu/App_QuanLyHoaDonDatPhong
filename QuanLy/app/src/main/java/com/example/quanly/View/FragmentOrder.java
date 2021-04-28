package com.example.quanly.View;

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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOrder extends Fragment {
    RecyclerView rcvNewOrders;
    public static FragmentOrder newInstance() {
        FragmentOrder fragment = new FragmentOrder();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_order, container, false);
        rcvNewOrders= view.findViewById(R.id.rcvNewOrders);
        AItemOrders adapter= new AItemOrders(getContext(),getList());
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvNewOrders.setLayoutManager(layoutManager);
        rcvNewOrders.setAdapter(adapter);
        return view;
    }
    public ArrayList<BookingReference> getList(){
        ArrayList<BookingReference> list= new ArrayList<>();
        list.add(new BookingReference("123456789","","","","","","","Chờ duyệt"));
        list.add(new BookingReference("123456790","","","","","","","Đã hủy"));
        list.add(new BookingReference("123456791","","","","","","","Chờ duyệt"));
        list.add(new BookingReference("123456792","","","","","","","Chờ duyệt"));
        list.add(new BookingReference("123456793","","","","","","","Đã thanh toán"));
        return list;
    }
}