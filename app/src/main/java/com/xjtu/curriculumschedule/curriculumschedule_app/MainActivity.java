package com.xjtu.curriculumschedule.curriculumschedule_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button kcBtn= (Button) findViewById(R.id.kc_Btn);
        kcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kcI=new Intent(MainActivity.this,Kc_mainActivity.class);
                startActivity(kcI);
            }
        });
    }
}
