package com.example.healthmart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class DoctorAdapter extends ArrayAdapter<Map<String, Object>> {

    public DoctorAdapter(Context context, List<Map<String, Object>> doctorDataList) {
        super(context, 0, doctorDataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the doctor data for this position
        Map<String, Object> doctorData = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.doctor_list_item, parent, false);
        }

        // Lookup view for data population
        TextView nameTextView = convertView.findViewById(R.id.doctor_name);
        TextView contactTextView = convertView.findViewById(R.id.doctor_contact);
        TextView addressTextView = convertView.findViewById(R.id.doctor_address);
        TextView feesTextView = convertView.findViewById(R.id.doctor_fees);

        // Populate the data into the template view using the doctor object
        nameTextView.setText("Name: " + doctorData.get("name"));
        contactTextView.setText("Contact: " + doctorData.get("contact"));
        addressTextView.setText("Address: " + doctorData.get("address"));
        feesTextView.setText("Fees: $" + doctorData.get("consultancyFees"));

        // Return the completed view to render on screen
        return convertView;
    }
}
