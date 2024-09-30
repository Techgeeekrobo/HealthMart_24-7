package com.example.healthmart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItemList;

    // Constructor to initialize the context and the cart item list
    public CartAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for individual cart items
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        // Get the current CartItem from the list
        CartItem cartItem = cartItemList.get(position);

        // Safeguard against null values in the cart item
        if (cartItem != null) {
            // Safeguard against null values for name
            if (cartItem.getName() != null) {
                holder.medicineNameTextView.setText(cartItem.getName());
            } else {
                holder.medicineNameTextView.setText("Unknown Medicine");
            }

            // Check if price is a valid number, and format it
            double price = cartItem.getPrice(); // Assuming price is a double, no need for null check

            // Format the price and display it
            if (price > 0) {
                holder.medicinePriceTextView.setText("Price: " + String.format("%.2f", price));
            } else {
                holder.medicinePriceTextView.setText("Price: N/A");
            }
        }
    }

    @Override
    public int getItemCount() {
        // Return the size of the cart item list, or 0 if it's null
        return (cartItemList != null) ? cartItemList.size() : 0;
    }

    // ViewHolder class to bind views for individual cart items
    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView medicineNameTextView, medicinePriceTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the TextViews
            medicineNameTextView = itemView.findViewById(R.id.textViewCartMedicineName);
            medicinePriceTextView = itemView.findViewById(R.id.textViewCartMedicinePrice);
        }
    }

    // A method to update the cart list and refresh the adapter
    public void updateCartList(List<CartItem> newCartItemList) {
        this.cartItemList = newCartItemList;
        notifyDataSetChanged(); // Notify adapter that the data has changed
    }
}
