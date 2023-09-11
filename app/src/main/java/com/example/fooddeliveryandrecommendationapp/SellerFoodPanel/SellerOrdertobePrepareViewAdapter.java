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

public class SellerOrdertobePrepareViewAdapter extends RecyclerView.Adapter<SellerOrdertobePrepareViewAdapter.ViewHolder> {

    private Context mcontext;
    private List<SellerWaitingOrders> sellerWaitingOrderslist;

    public SellerOrdertobePrepareViewAdapter(Context context, List<SellerWaitingOrders> sellerWaitingOrderslist) {
        this.sellerWaitingOrderslist = sellerWaitingOrderslist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.seller_ordertobeprepared_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final SellerWaitingOrders sellerWaitingOrders = sellerWaitingOrderslist.get(position);
        holder.dishname.setText(sellerWaitingOrders.getDishName());
        holder.price.setText("Price: PKR " + sellerWaitingOrders.getDishPrice());
        holder.quantity.setText("× " + sellerWaitingOrders.getDishQuantity());
        holder.totalprice.setText("Total: PKR " + sellerWaitingOrders.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return sellerWaitingOrderslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dishname, price, totalprice, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dishname = itemView.findViewById(R.id.Dname);
            price = itemView.findViewById(R.id.Dprice);
            totalprice = itemView.findViewById(R.id.Tprice);
            quantity = itemView.findViewById(R.id.Dqty);
        }
    }
}
