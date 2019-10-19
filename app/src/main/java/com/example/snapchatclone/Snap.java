package com.example.snapchatclone;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class Snap implements Serializable {
    String caption;
    String photoURL;
    FirebaseUser user;

    public Snap() {

    }

    public Snap(String caption, String photoURL) {
        this.caption = caption;
        this.photoURL = photoURL;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
}
