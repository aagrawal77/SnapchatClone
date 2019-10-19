package com.example.snapchatclone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewSnapActivity extends AppCompatActivity {

    Button btnNew;
    Button btnSubmit;
    EditText caption;
    Uri photoUri;
    String photoByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_snap);

        btnNew = findViewById(R.id.newSnap);
        btnSubmit = findViewById(R.id.submit);
        caption = findViewById(R.id.caption);

        btnSubmit.setVisibility(View.GONE);
        btnSubmit.setClickable(false);
        caption.setClickable(false);
        caption.setVisibility(View.GONE);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickPhoto(view);
            }
        });
    }

    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, 1046);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            photoUri = data.getData();
            // Load the selected image into a preview
            photoByte = photoUri.toString();
            btnSubmit.setVisibility(View.VISIBLE);
            btnSubmit.setClickable(true);
            caption.setVisibility((View.VISIBLE));
            caption.setClickable(true);

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snap newSnap = new Snap(caption.getText().toString(), photoByte);
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("snaps").child(((Integer) newSnap.hashCode()).toString()).setValue(newSnap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(NewSnapActivity.this, "success!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });


        }
    }




}
