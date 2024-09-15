// FirebaseHelper.java
package com.example.healthmart;

import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseHelper {

    // Firestore instance
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Helper method to create a doctor's data map
    public Map<String, Object> createDoctor(String name, String contactNumber, String address, double consultancyFees, String type) {
        Map<String, Object> doctor = new HashMap<>();
        doctor.put("name", name);
        doctor.put("contactNumber", contactNumber);
        doctor.put("address", address);
        doctor.put("consultancyFees", consultancyFees);
        doctor.put("type", type);
        return doctor;
    }

    // Method to add all doctors to Firestore
    public void addDoctors(List<Map<String, Object>> doctors) {
        for (Map<String, Object> doctor : doctors) {
            db.collection("doctors")
                    .add(doctor)
                    .addOnSuccessListener(documentReference -> {
                        Log.d("Firebase", "Doctor added with ID: " + documentReference.getId());
                    })
                    .addOnFailureListener(e -> {
                        Log.w("Firebase", "Error adding doctor", e);
                    });
        }
    }
}
