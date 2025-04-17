package com.brandmap.backend.services;

import com.brandmap.backend.model.Brand;
import com.brandmap.backend.model.Opinion;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class MindMapService {

    private static DatabaseReference database;

    static {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("resources/firebase-config.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://<firebase-config>.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
            database = FirebaseDatabase.getInstance().getReference();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Opinion submitOpinion(Long brandId, String opinionText) {
        String summary = "This is a fake summary of the opinion- will have AI integration:) right now this is just a placeholder...."; 
        String[] keywords = {"positive", "reliable"};

        Opinion opinion = new Opinion(brandId, opinionText, summary, keywords);
        database.child("brands").child(String.valueOf(brandId)).child("opinions").push().setValueAsync(opinion);

        return opinion;
    }

    public List<Brand> getAllBrands() {
        final List<Brand> brands = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        
        database.child("brands").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot brandSnapshot : dataSnapshot.getChildren()) {
                    Brand brand = brandSnapshot.getValue(Brand.class);
                    brands.add(brand);
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return brands;
    }
}
