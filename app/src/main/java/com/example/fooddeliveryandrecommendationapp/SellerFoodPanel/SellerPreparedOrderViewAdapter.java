package com.example.fooddeliveryandrecommendationapp.SellerFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryandrecommendationapp.R;
import java.util.List;

public class SellerPreparedOrderViewAdapter extends RecyclerView.Adapter<SellerPreparedOrderViewAdapter.ViewHolder> {

    private Context mcontext;
    private List<SellerFinalOrders> sellerFinalOrderslist;

    public SellerPreparedOrderViewAdapter(Context context, List<SellerFinalOrders> sellerFinalOrderslist) {
        this.sellerFinalOrderslist = sellerFinalOrderslist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.seller_preparedorderview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final SellerFinalOrders sellerFinalOrders = sellerFinalOrderslist.get(position);
        holder.dishname.setText(sellerFinalOrders.getDishName());
        holder.price.setText("Price: PKR " + sellerFinalOrders.getDishPrice());
        holder.quantity.setText("× " + sellerFinalOrders.getDishQuantity());
        holder.totalprice.setText("Total: PKR " + sellerFinalOrders.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return sellerFinalOrderslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dishname, price, totalprice, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dishname = itemView.findViewById(R.id.Cdishname);
            price = itemView.findViewById(R.id.Cdishprice);
            totalprice = itemView.findViewById(R.id.Ctotalprice);
            quantity = itemView.findViewById(R.id.Cdishqty);
        }
    }
}
