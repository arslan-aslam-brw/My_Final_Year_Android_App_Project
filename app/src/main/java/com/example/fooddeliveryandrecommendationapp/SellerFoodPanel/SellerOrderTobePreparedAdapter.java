package com.example.fooddeliveryandrecommendationapp.SellerFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryandrecommendationapp.R;
import java.util.List;

public class SellerOrderTobePreparedAdapter extends RecyclerView.Adapter<SellerOrderTobePreparedAdapter.ViewHolder> {

    private Context context;
    private List<SellerWaitingOrders1> sellerWaitingOrders1List;

    public SellerOrderTobePreparedAdapter(Context context, List<SellerWaitingOrders1> sellerWaitingOrders1List) {
        this.sellerWaitingOrders1List = sellerWaitingOrders1List;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seller_ordertobeprepared, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SellerWaitingOrders1 sellerWaitingOrders1 = sellerWaitingOrders1List.get(position);
        holder.Address.setText(sellerWaitingOrders1.getAddress());
        holder.grandtotalprice.setText("Grand Total: PKR " + sellerWaitingOrders1.getGrandTotalPrice());
        final String random = sellerWaitingOrders1.getRandomUID();
        holder.Vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SellerOrdertobePrepareView.class);
                intent.putExtra("RandomUID", random);
                context.startActivity(intent);
                ((SellerOrderTobePrepared) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sellerWaitingOrders1List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address, grandtotalprice;
        Button Vieworder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.cust_address);
            grandtotalprice = itemView.findViewById(R.id.Grandtotalprice);
            Vieworder = itemView.findViewById(R.id.View_order);
        }
    }
}
