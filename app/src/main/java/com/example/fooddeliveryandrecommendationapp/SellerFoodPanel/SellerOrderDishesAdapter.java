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

public class SellerOrderDishesAdapter extends RecyclerView.Adapter<SellerOrderDishesAdapter.ViewHolder> {


    private Context mcontext;
    private List<SellerPendingOrders> sellerPendingOrderslist;

    public SellerOrderDishesAdapter(Context context, List<SellerPendingOrders> sellerPendingOrderslist) {
        this.sellerPendingOrderslist = sellerPendingOrderslist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.seller_order_dishes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final SellerPendingOrders sellerPendingOrders = sellerPendingOrderslist.get(position);
        holder.dishname.setText(sellerPendingOrders.getDishName());
        holder.price.setText("Price: PKR " + sellerPendingOrders.getPrice());
        holder.quantity.setText("× " + sellerPendingOrders.getDishQuantity());
        holder.totalprice.setText("Total: PKR " + sellerPendingOrders.getTotalPrice());


    }

    @Override
    public int getItemCount() {
        return sellerPendingOrderslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dishname, price, totalprice, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dishname = itemView.findViewById(R.id.DN);
            price = itemView.findViewById(R.id.PR);
            totalprice = itemView.findViewById(R.id.TR);
            quantity = itemView.findViewById(R.id.QY);
        }
    }
}
