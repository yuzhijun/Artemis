
package com.winning.artemis;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.winning.artemis_guard.Artemis;
import com.winning.artemis_guard.core.uimock.UiMock;

public class MainActivity extends AppCompatActivity {
    private TextView tvTest;
    private TextView tvTest01;
    private Button btn01;
    private Button btn02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTest = findViewById(R.id.tvTest);
        tvTest01 = findViewById(R.id.tvTest01);
        btn01 = findViewById(R.id.btn01);
        btn02 = findViewById(R.id.btn02);

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

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("内容").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                alertDialog.show();
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"toast 01",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
