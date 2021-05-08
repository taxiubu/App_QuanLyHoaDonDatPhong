package com.example.quanly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly.IOnClickItemOrders;
import com.example.quanly.Model.BookingReference;
import com.example.quanly.R;

import java.util.ArrayList;

public class AItemOrders extends RecyclerView.Adapter<AItemOrders.ViewHoder>{
    private Context context;
    private ArrayList<BookingReference> list;
    IOnClickItemOrders iOnClickItemOrders;
    public AItemOrders(Context context, ArrayList<BookingReference> list) {
        this.context = context;
        this.list = list;
    }

    public void setiOnClickItemOrders(IOnClickItemOrders iOnClickItemOrders) {
        this.iOnClickItemOrders = iOnClickItemOrders;
    }

    @NonNull
    @Override
    public AItemOrders.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_bookingreference, parent, false);
        return new AItemOrders.ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AItemOrders.ViewHoder holder, int position) {
        BookingReference b=list.get(position);
        holder.tvOrderID.setText(b.getId());
        holder.tvStatus.setText(b.getStatus());
        switch (b.getStatus()){
            case "Chờ duyệt":
                holder.imStatus.setBackgroundResource(R.drawable.ic_baseline_access_time_24);
                break;
            case "Đã duyệt":
                holder.imStatus.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
                break;
            case "Đã hủy":
                holder.imStatus.setBackgroundResource(R.drawable.ic_baseline_do_not_disturb_alt_24);
                break;
            case "Đã thanh toán":
                holder.imStatus.setBackgroundResource(R.drawable.ic_outline_monetization_on_24);
                break;
            default:
                break;
        }
        if(position==0)
            holder.line.setVisibility(View.GONE);
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickItemOrders.onClickItem(b);
            }
        });
    }
    public void filterList(ArrayList<BookingReference> filterList) {
        list= filterList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvOrderID,tvStatus,tvMoney;
        ImageView imStatus;
        View line;
        RelativeLayout layoutItem;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvOrderID=itemView.findViewById(R.id.tvOrderId);
            tvStatus=itemView.findViewById(R.id.tvStatus);
            imStatus=itemView.findViewById(R.id.imStatus);
            line=itemView.findViewById(R.id.line);
            layoutItem=itemView.findViewById(R.id.layoutItem);
        }
    }
}
