package ru.loftblog.loftblogmoneytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button firstButtonFragment;
    private Button secondButtonFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstButtonFragment = (Button) findViewById(R.id.button_first);
        secondButtonFragment = (Button) findViewById(R.id.button_second);

        firstButtonFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FirstFragment()).commit();
            }
        });
        secondButtonFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SecondFragment()).commit();
            }
        });
    }
}