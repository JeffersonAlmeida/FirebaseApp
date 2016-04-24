package com.example.jefferson.firebaseapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.quente)
    Button quente;
    @Bind(R.id.frio)
    Button frio;
    @Bind(R.id.tempo)
    TextView tempo;

    Firebase myFirebaseRef;

    private static final String CHILD = "tempo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Firebase.setAndroidContext(this);

        myFirebaseRef = new Firebase("https://scorching-heat-4329.firebaseio.com/" + CHILD);
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tempo = (String) dataSnapshot.getValue();
                updateUI(tempo);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @OnClick({R.id.quente, R.id.frio})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quente:
                taQuente();
                break;
            case R.id.frio:
                taFrio();
                break;
        }
    }

    private void taQuente() {
        applyChange("Quente");
    }

    private void applyChange(String text) {
        updateUI(text);
        updateFirebase(text);
    }

    private void updateFirebase(String text) {
        myFirebaseRef.setValue(text);
    }

    private void updateUI(String text) {
        configureText(text);
    }

    private void configureText(String t) {
        tempo.setText(t);
        int c = t.equals("Quente") ?
                getResources().getColor(R.color.Color_21) :
                getResources().getColor(R.color.Color_2);

        tempo.setTextColor(c);
        doAnimationIn();
    }

    private void doAnimationOut() {
        YoYo.with(Techniques.FlipOutX)
                .duration(700)
                .playOn(this.tempo);

    }

    private void doAnimationIn() {
        YoYo.with(Techniques.FadeInUp) // FadeInUp
                .duration(700)
                .playOn(this.tempo);

    }

    private void taFrio() {
        applyChange("Frio");
    }
}
