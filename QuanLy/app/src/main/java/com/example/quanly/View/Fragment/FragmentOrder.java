package com.example.quanly.View.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.quanly.Adapter.AItemOrders;
import com.example.quanly.IOnClickItemOrders;
import com.example.quanly.Model.BookingReference;
import com.example.quanly.R;
import com.example.quanly.View.MainActivity2;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOrder extends Fragment {
    RecyclerView rcvNewOrders;
    ArrayList<BookingReference> list;
    AItemOrders adapter;
    ProgressBar progressBar;
    EditText etSearch;
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
        list= new ArrayList<>();
        etSearch= view.findViewById(R.id.etSearch);
        progressBar= view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        adapter= new AItemOrders(getContext(),list);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvNewOrders.setLayoutManager(layoutManager);
        rcvNewOrders.setAdapter(adapter);
        adapter.setiOnClickItemOrders(new IOnClickItemOrders() {
            @Override
            public void onClickItem(BookingReference bookingReference) {
                Intent i= new Intent(getContext(), MainActivity2.class);
                i.putExtra("bookingReference",bookingReference);
                startActivity(i);
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;
    }
    private void getList(){
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("bookingreference");
        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot e, @Nullable String previousChildName) {
                if(e.exists()){
                    String dateIn=e.getValue(BookingReference.class).getDateIn();
                    String dateOut=e.getValue(BookingReference.class).getDateOut();
                    String email=e.getValue(BookingReference.class).getEmail();
                    String phone=e.getValue(BookingReference.class).getPhone();
                    String nameResidence=e.getValue(BookingReference.class).getNameResidence();
                    String countRoom=e.getValue(BookingReference.class).getCountRoom();
                    String countPeople=e.getValue(BookingReference.class).getCountPeople();
                    String status=e.getValue(BookingReference.class).getStatus();
                    String id=e.getKey();
                    list.add(new BookingReference(id,dateIn,dateOut,email,phone,nameResidence,countRoom,countPeople,status));
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void filter(String text){
        ArrayList<BookingReference> filterList= new ArrayList<>();
        for (BookingReference item : list) {
            if (item.getId().contains((text))) {
                filterList.add(item);
            }
        }
        adapter.filterList(filterList);
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        getList();
    }
}