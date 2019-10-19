package com.example.snapchatclone;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    SnapAdapter adapter;
    ArrayList<Snap> snaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        Query ref = mDatabase.child("snaps");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                snaps.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Snap snap = ds.getValue(Snap.class);
                    snaps.add(snap);
                }
                // have to do the recyclerview stuff here because its an async task
                // that needs to be completed before the adapter can be created

                final RecyclerView recList = (RecyclerView) findViewById(R.id.recyclerView);
                recList.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(ListActivity.this);
                llm.setReverseLayout(true);
                llm.setStackFromEnd(true);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recList.setLayoutManager(llm);
                adapter = new SnapAdapter(getApplicationContext(), snaps);
                recList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.d("XYZ", ((Integer) snaps.size()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("XYZ", "error");
            }
        });
    }
}
