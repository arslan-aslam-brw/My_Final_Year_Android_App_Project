package com.example.fooddeliveryandrecommendationapp.SellerFoodPanel;

public class FoodSupplyDetails {

    public String Dishes,Quantity,Price,Description,ImageURL,RandomUID, SellerId;

    public FoodSupplyDetails(String dishes, String quantity, String price, String description, String imageURL,String randomUID,String sellerId) {
        Dishes = dishes;
        Quantity = quantity;
        Price = price;
        Description = description;
        ImageURL = imageURL;
        RandomUID=randomUID;
        SellerId = sellerId;
    }

}
