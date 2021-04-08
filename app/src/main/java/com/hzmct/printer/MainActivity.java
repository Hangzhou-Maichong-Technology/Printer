package com.hzmct.printer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lvrenyang.io.Pos;
import com.lvrenyang.io.base.COMIO;
import com.lvrenyang.io.base.IOCallBack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Pos pos = new Pos();
    COMIO comio = new COMIO();
    ExecutorService execTask = Executors.newCachedThreadPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initListener();
    }

    private void initData() {
        pos.Set(comio);
        comio.SetCallBack(new IOCallBack() {
            @Override
            public void OnOpen() {
                Log.i(TAG, "Print is connected");
            }

            @Override
            public void OnOpenFailed() {
                Log.e(TAG, "Print open failed");
                Toast.makeText(MainActivity.this, "连接打印机失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnClose() {
                Log.i(TAG, "Print is close");
            }
        });

        execTask.execute(new Runnable() {
            @Override
            public void run() {
                comio.Open("/dev/ttyS3", 9600, 0);
            }
        });
    }

    private void initListener() {
        findViewById(R.id.btn_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        PrintUtil.printText(pos, 384, false, false, 1);
                    }
                });
            }
        });

        findViewById(R.id.btn_qrcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        PrintUtil.printQrCode(pos, 384, true, false, 1);
                    }
                });
            }
        });

        findViewById(R.id.btn_ticket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        PrintUtil.printTicket(pos, 384, true, true, 1);
                    }
                });
            }
        });
    }
}
