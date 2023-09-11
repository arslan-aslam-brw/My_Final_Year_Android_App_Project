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

public class SellerPreparedOrderAdapter extends RecyclerView.Adapter<SellerPreparedOrderAdapter.ViewHolder> {

    private Context context;
    private List<SellerFinalOrders1> sellerFinalOrders1List;

    public SellerPreparedOrderAdapter(Context context, List<SellerFinalOrders1> sellerFinalOrders1List) {
        this.sellerFinalOrders1List = sellerFinalOrders1List;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seller_preparedorder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SellerFinalOrders1 sellerFinalOrders1 = sellerFinalOrders1List.get(position);
        holder.Address.setText(sellerFinalOrders1.getAddress());
        holder.grandtotalprice.setText("Grand Total: PKR " + sellerFinalOrders1.getGrandTotalPrice());
        final String random = sellerFinalOrders1.getRandomUID();
        holder.Vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SellerPreparedOrderView.class);
                intent.putExtra("RandomUID", random);
                context.startActivity(intent);
                ((SellerPreparedOrder) context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return sellerFinalOrders1List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address, grandtotalprice;
        Button Vieworder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.customer_address);
            grandtotalprice = itemView.findViewById(R.id.customer_totalprice);
            Vieworder = itemView.findViewById(R.id.View);
        }
    }
}
