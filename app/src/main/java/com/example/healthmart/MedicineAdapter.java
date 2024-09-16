package com.example.healthmart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {
    private List<Medicine> medicineList;
    private Context context;
    private OnAddToCartClickListener listener;

    public interface OnAddToCartClickListener {
        void onAddToCart(Medicine medicine);
    }

    public MedicineAdapter(Context context, List<Medicine> medicineList, OnAddToCartClickListener listener) {
        this.context = context;
        this.medicineList = medicineList != null ? medicineList : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicine, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.medicineName.setText(medicine.getName());
        holder.medicinePrice.setText("₹" + medicine.getPrice());
        // Add to Cart button click listener
        holder.addToCartButton.setOnClickListener(v -> listener.onAddToCart(medicine));
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public void setMedicineList(List<Medicine> medicineList) {
        this.medicineList = medicineList != null ? medicineList : new ArrayList<>();
        notifyDataSetChanged();
    }


    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        TextView medicineName, medicinePrice;
        Button addToCartButton;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.medicineName);
            medicinePrice = itemView.findViewById(R.id.medicinePrice);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
}



