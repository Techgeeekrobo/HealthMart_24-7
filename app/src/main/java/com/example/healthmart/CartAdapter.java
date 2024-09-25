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

    public CartAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the cart_item.xml layout
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        // Get the current CartItem
        CartItem cartItem = cartItemList.get(position);

        // Bind the CartItem data to the views
        holder.medicineNameTextView.setText(cartItem.getName());
        holder.medicinePriceTextView.setText("Price: $" + cartItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView medicineNameTextView, medicinePriceTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineNameTextView = itemView.findViewById(R.id.textViewCartMedicineName);
            medicinePriceTextView = itemView.findViewById(R.id.textViewCartMedicinePrice);
        }
    }
}
