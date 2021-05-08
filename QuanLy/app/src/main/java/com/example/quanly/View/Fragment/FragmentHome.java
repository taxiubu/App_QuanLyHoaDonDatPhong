 package com.example.quanly.View.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quanly.Model.BookingReference;
import com.example.quanly.R;
import com.example.quanly.View.MainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

 public class FragmentHome extends Fragment{
    RelativeLayout btPending,btCheck;
    TextView tvNewOrders,tvPendingOrders,tvCancelOrders,tvPaidOrders,tvConfirmOrder,tvCountAllOrder;
    ArrayList<BookingReference> list;
    MainActivity mainActivity;
    int count=0;
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

        btCheck=view.findViewById(R.id.waitForPay);
        btPending=view.findViewById(R.id.pending);
        tvNewOrders=view.findViewById(R.id.tvNewOrders);
        tvPendingOrders=view.findViewById(R.id.tvPendingOrder);
        tvCancelOrders=view.findViewById(R.id.tvCancelOrders);
        tvPaidOrders=view.findViewById(R.id.tvPaidOrders);
        tvConfirmOrder=view.findViewById(R.id.tvConfirmOrder);
        tvCountAllOrder=view.findViewById(R.id.tvCountAllOrder);
        getCurrentBookingReference();

        return view;
    }
    public void getCurrentBookingReference(){
        list= new ArrayList<>();
        //get day current
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String time= day+"-"+month+"-"+year;
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("bookingreference");
        mData.orderByChild("dateIn").equalTo(time).addValueEventListener(new ValueEventListener() {
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
                        String countPeople=e.getValue(BookingReference.class).getCountPeople();
                        String status=e.getValue(BookingReference.class).getStatus();
                        String id=e.getKey();
                        list.add(new BookingReference(id,dateIn,dateOut,email,phone,nameResidence,countRoom,countPeople,status));
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
                countConfirm++;
            if(e.getStatus().equals("Đã duyệt"))
                countPending++;
            if(e.getStatus().equals("Đã hủy"))
                countCancel++;
            if(e.getStatus().equals("Đã thanh toán"))
                countPaid++;
        }
        tvNewOrders.setText(String.valueOf(list.size()));
        tvPendingOrders.setText(String.valueOf(countPending));
        tvCancelOrders.setText(String.valueOf(countCancel));
        tvPaidOrders.setText(String.valueOf(countPaid));
        tvConfirmOrder.setText(String.valueOf(countConfirm));

    }
     private void countAllOrder(){
         DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("bookingreference");
         mData.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(@NonNull DataSnapshot e, @Nullable String previousChildName) {
                 if(e.exists()){
                     count++;
                     tvCountAllOrder.setText(String.valueOf(count));
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

     @Override
     public void onResume() {
         super.onResume();
         countAllOrder();
     }
 }