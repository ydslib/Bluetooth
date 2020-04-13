package com.yds.bluetooth.source;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yds.bluetooth.R;

public class MainActivity extends AppCompatActivity {
    private Button mOpenBluetooth;
    private Button mOpenBluetoothByIntent;
    private Button mCloseBluetooth;
    private Button mDiscoveryBluetooth;
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initEvent() {
        mOpenBluetooth.setOnClickListener(mOnClickListener);
        mCloseBluetooth.setOnClickListener(mOnClickListener);
        mOpenBluetoothByIntent.setOnClickListener(mOnClickListener);
        mDiscoveryBluetooth.setOnClickListener(mOnClickListener);

        IntentFilter filter = new IntentFilter();
//        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);

        registerReceiver(myReciever, filter);
    }

    private void initView() {
        mOpenBluetooth = findViewById(R.id.open_bluetooth);
        mOpenBluetoothByIntent = findViewById(R.id.open_bluetooth_by_intent);
        mCloseBluetooth = findViewById(R.id.close_bluetooth);
        mDiscoveryBluetooth = findViewById(R.id.discovery_bluetooth);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.open_bluetooth:
                    mBluetoothAdapter.enable();
                    break;
                case R.id.close_bluetooth:
                    mBluetoothAdapter.disable();
                case R.id.open_bluetooth_by_intent:
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, 0x01);
                    break;
                case R.id.discovery_bluetooth:
                    if (mBluetoothAdapter.isDiscovering()) {
                        mBluetoothAdapter.cancelDiscovery();
                    }
                    mBluetoothAdapter.startDiscovery();
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    BroadcastReceiver myReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
                System.out.println("Bluetooth discovery started");
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                System.out.println("Bluetooth discovery finished");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReciever);
    }
}
