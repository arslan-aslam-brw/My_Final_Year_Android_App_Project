package com.example.fooddeliveryandrecommendationapp.SellerFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryandrecommendationapp.R;
import com.example.fooddeliveryandrecommendationapp.ReusableCode.ReusableCodeForAll;
import com.example.fooddeliveryandrecommendationapp.SendNotification.APIService;
import com.example.fooddeliveryandrecommendationapp.SendNotification.Client;
import com.example.fooddeliveryandrecommendationapp.SendNotification.Data;
import com.example.fooddeliveryandrecommendationapp.SendNotification.MyResponse;
import com.example.fooddeliveryandrecommendationapp.SendNotification.NotificationSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerPendingOrdersAdapter extends RecyclerView.Adapter<SellerPendingOrdersAdapter.ViewHolder> {

    private Context context;
    private List<SellerPendingOrders1> sellerPendingOrders1List;
    private APIService apiService;
    String userid, dishid;


    public SellerPendingOrdersAdapter(Context context, List<SellerPendingOrders1> sellerPendingOrders1List) {
        this.sellerPendingOrders1List = sellerPendingOrders1List;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seller_orders, parent, false);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final SellerPendingOrders1 sellerPendingOrders1 = sellerPendingOrders1List.get(position);
        holder.Address.setText(sellerPendingOrders1.getAddress());
        holder.grandtotalprice.setText("GrandTotal: PKR " + sellerPendingOrders1.getGrandTotalPrice());
        final String random = sellerPendingOrders1.getRandomUID();
        holder.Vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Seller_order_dishes.class);
                intent.putExtra("RandomUID", random);
                context.startActivity(intent);
            }
        });

        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SellerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Dishes");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            final SellerPendingOrders sellerPendingOrders = snapshot.getValue(SellerPendingOrders.class);
                            HashMap<String, String> hashMap = new HashMap<>();
                            String sellerid = sellerPendingOrders.getSellerId();
                            String dishid = sellerPendingOrders.getDishId();
                            hashMap.put("SellerId", sellerPendingOrders.getSellerId());
                            hashMap.put("DishId", sellerPendingOrders.getDishId());
                            hashMap.put("DishName", sellerPendingOrders.getDishName());
                            hashMap.put("DishPrice", sellerPendingOrders.getPrice());
                            hashMap.put("DishQuantity", sellerPendingOrders.getDishQuantity());
                            hashMap.put("RandomUID", random);
                            hashMap.put("TotalPrice", sellerPendingOrders.getTotalPrice());
                            hashMap.put("UserId", sellerPendingOrders.getUserId());
                            FirebaseDatabase.getInstance().getReference("SellerPaymentOrders").child(sellerid).child(random).child("Dishes").child(dishid).setValue(hashMap);
                        }
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("SellerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                SellerPendingOrders1 sellerPendingOrders1 = dataSnapshot.getValue(SellerPendingOrders1.class);
                                HashMap<String, String> hashMap1 = new HashMap<>();
                                hashMap1.put("Address", sellerPendingOrders1.getAddress());
                                hashMap1.put("GrandTotalPrice", sellerPendingOrders1.getGrandTotalPrice());
                                hashMap1.put("MobileNumber", sellerPendingOrders1.getMobileNumber());
                                hashMap1.put("Name", sellerPendingOrders1.getName());
                                hashMap1.put("Note", sellerPendingOrders1.getNote());
                                hashMap1.put("RandomUID", random);
                                FirebaseDatabase.getInstance().getReference("SellerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("SellerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Dishes");
                                        Reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    final SellerPendingOrders sellerPendingOrders = snapshot.getValue(SellerPendingOrders.class);
                                                    HashMap<String, String> hashMap2 = new HashMap<>();
                                                    userid = sellerPendingOrders.getUserId();
                                                    dishid = sellerPendingOrders.getDishId();
                                                    hashMap2.put("SellerId", sellerPendingOrders.getSellerId());
                                                    hashMap2.put("DishId", sellerPendingOrders.getDishId());
                                                    hashMap2.put("DishName", sellerPendingOrders.getDishName());
                                                    hashMap2.put("DishPrice", sellerPendingOrders.getPrice());
                                                    hashMap2.put("DishQuantity", sellerPendingOrders.getDishQuantity());
                                                    hashMap2.put("RandomUID", random);
                                                    hashMap2.put("TotalPrice", sellerPendingOrders.getTotalPrice());
                                                    hashMap2.put("UserId", sellerPendingOrders.getUserId());
                                                    FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(userid).child(random).child("Dishes").child(dishid).setValue(hashMap2);
                                                }
                                                DatabaseReference dataa = FirebaseDatabase.getInstance().getReference("SellerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation");
                                                dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        SellerPendingOrders1 sellerPendingOrders1 = dataSnapshot.getValue(SellerPendingOrders1.class);
                                                        HashMap<String, String> hashMap3 = new HashMap<>();
                                                        hashMap3.put("Address", sellerPendingOrders1.getAddress());
                                                        hashMap3.put("GrandTotalPrice", sellerPendingOrders1.getGrandTotalPrice());
                                                        hashMap3.put("MobileNumber", sellerPendingOrders1.getMobileNumber());
                                                        hashMap3.put("Name", sellerPendingOrders1.getName());
                                                        hashMap3.put("Note", sellerPendingOrders1.getNote());
                                                        hashMap3.put("RandomUID", random);
                                                        FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(userid).child(random).child("OtherInformation").setValue(hashMap3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(userid).child(random).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                        FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(userid).child(random).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                FirebaseDatabase.getInstance().getReference("SellerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                                        FirebaseDatabase.getInstance().getReference("SellerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(Void aVoid) {
                                                                                                FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                        String usertoken = dataSnapshot.getValue(String.class);
                                                                                                        sendNotifications(usertoken, "Order Accepted", "Your Order has been Accepted by the Seller, Now make Payment for Order", "Payment");
                                                                                                        ReusableCodeForAll.ShowAlert(context,"","Wait for the Customer to make Payment");

                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                    }
                                                                                                });
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                });

                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("SellerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Dishes");
                Reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            final SellerPendingOrders sellerPendingOrders = snapshot.getValue(SellerPendingOrders.class);
                            userid = sellerPendingOrders.getUserId();
                            dishid = sellerPendingOrders.getDishId();
                        }
                        FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String usertoken = dataSnapshot.getValue(String.class);
                                sendNotifications(usertoken, "Order Rejected", "Your Order has been Rejected by the Seller due to some Circumstances", "Home");
                                FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(userid).child(random).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(userid).child(random).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                FirebaseDatabase.getInstance().getReference("SellerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        FirebaseDatabase.getInstance().getReference("SellerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(userid).child("isOrdered").setValue("false");
                                                            }
                                                        });

                                                    }
                                                });

                                            }
                                        });

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }

    private void sendNotifications(String usertoken, String title, String message, String order) {

        Data data = new Data(title, message, order);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return sellerPendingOrders1List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address, grandtotalprice;
        Button Vieworder, Accept, Reject;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.AD);
            grandtotalprice = itemView.findViewById(R.id.TP);
            Vieworder = itemView.findViewById(R.id.vieww);
            Accept = itemView.findViewById(R.id.accept);
            Reject = itemView.findViewById(R.id.reject);


        }
    }
}