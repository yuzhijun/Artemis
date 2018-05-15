
package com.winning.artemis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.winning.artemis_guard.Artemis;
import com.winning.artemis_guard.core.uimock.UiMock;

public class MainActivity extends AppCompatActivity {
    private TextView tvTest;
    private TextView tvTest01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTest = findViewById(R.id.tvTest);
        tvTest01 = findViewById(R.id.tvTest01);

        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

        tvTest01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Artemis.getInstance(getApplication()).stop();
                UiMock.getInstance().mock();
            }
        });
    }
}
