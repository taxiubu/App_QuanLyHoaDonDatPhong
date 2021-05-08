package com.example.quanly.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanly.Model.BookingReference;
import com.example.quanly.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FragmentDetailOrder extends Fragment {
    BookingReference bookingReference;
    TextView tvOrderId,tvNameResidence,tvEmailUser,tvPhoneUser,tvStatus;
    Button btConfirm, btCancel,btPaid;
    ImageView btBack;
    EditText etDateIn, etDateOut, etCountRoom, etCountPeople;
    DatabaseReference mData;
    public static FragmentDetailOrder newInstance(BookingReference b) {
        FragmentDetailOrder fragment = new FragmentDetailOrder();
        Bundle args = new Bundle();
        args.putSerializable("bookingReference", b);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_detail_order, container, false);
        tvOrderId= view.findViewById(R.id.tvOrderId);
        tvNameResidence= view.findViewById(R.id.tvNameResidence);
        tvEmailUser= view.findViewById(R.id.tvEmailUser);
        tvPhoneUser= view.findViewById(R.id.tvPhoneUser);
        etDateIn = view.findViewById(R.id.tvDateIn);
        etDateOut = view.findViewById(R.id.tvDateOut);
        etCountRoom = view.findViewById(R.id.tvCountRoom);
        etCountPeople = view.findViewById(R.id.tvCountPeople);
        tvStatus= view.findViewById(R.id.tvStatus);
        btBack= view.findViewById(R.id.btBack);
        btConfirm= view.findViewById(R.id.btConfirm);
        btPaid= view.findViewById(R.id.btPaid);
        btCancel = view.findViewById(R.id.btCancel);
        bookingReference=(BookingReference)getArguments().getSerializable("bookingReference");
        mData= FirebaseDatabase.getInstance().getReference("bookingreference");
        setTextView();
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child(bookingReference.getId()).child("status").setValue("Đã duyệt");
                upload();
                Toast.makeText(getContext(),"Duyệt thành công",Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
        btPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child(bookingReference.getId()).child("status").setValue("Đã thanh toán");
                upload();
                Toast.makeText(getContext(),"Thanh toán thành công",Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child(bookingReference.getId()).child("status").setValue("Đã hủy");
                upload();
                Toast.makeText(getContext(),"Hủy hóa đơn thành công",Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
        return view;
    }

    private void setTextView(){
        tvOrderId.setText(bookingReference.getId());
        tvNameResidence.setText(bookingReference.getNameResidence());
        tvEmailUser.setText(bookingReference.getEmail());
        tvPhoneUser.setText(bookingReference.getPhone());
        etDateIn.setText(bookingReference.getDateIn());
        etDateOut.setText(bookingReference.getDateOut());
        etCountRoom.setText(bookingReference.getCountRoom());
        etCountPeople.setText(bookingReference.getCountPeople());
        tvStatus.setText(bookingReference.getStatus());
    }
    private void upload(){
        mData.child(bookingReference.getId()).child("countPeople").setValue(etCountPeople.getText().toString());
        mData.child(bookingReference.getId()).child("countRoom").setValue(etCountRoom.getText().toString());
        mData.child(bookingReference.getId()).child("dateIn").setValue(etDateIn.getText().toString());
        mData.child(bookingReference.getId()).child("dateOut").setValue(etDateOut.getText().toString());
    }
}