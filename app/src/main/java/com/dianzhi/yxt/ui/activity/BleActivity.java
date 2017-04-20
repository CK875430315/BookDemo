package com.dianzhi.yxt.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dianzhi.yxt.R;
import com.dianzhi.yxt.base.BaseActivity;
import com.dianzhi.yxt.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CK on 2017/4/15.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BleActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothGatt bluetoothGatt;
    private static Handler handler=new Handler();
    private List<BluetoothDevice> datas=new ArrayList<>();
    private boolean mScanning=true;
    public static final UUID UUID1 = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID2 = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID3 = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    @BindView(R.id.list_ble)
    ListView list_ble;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        needImmersive=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble,true);
        ButterKnife.bind(this);
        bluetoothManager= (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter=bluetoothManager.getAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            //open ble
            bluetoothAdapter.enable();
        }
        //需要参数 BluetoothAdapter.LeScanCallback(返回的扫描结果)
        bluetoothAdapter.startLeScan(mLeScanCallback);
        mScanning=true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScanning=false;
                bluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        },10000);
        list_ble.setOnItemClickListener(this);
    }
    private BluetoothAdapter.LeScanCallback mLeScanCallback=new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (device != null && device.getName() != null) {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           if (!datas.contains(device)) {
                           datas.add(device);
                           }
                           list_ble.setAdapter(new ArrayAdapter<>(BleActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,datas));
                       }
                   });
            } else {
                ToastUtils.showToast(BleActivity.this,"没有获取到设备信息");
            }
        }
    };
   private List<BluetoothGattService> serviceList=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mScanning) {
            bluetoothAdapter.stopLeScan(mLeScanCallback);
            mScanning=false;
        }
        //通过蓝牙设备地址 获取远程设备 开始连接
        BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(datas.get(position).getAddress());
        bluetoothGatt= remoteDevice.connectGatt(this, false, bluetoothGattCallback);
    }
    private BluetoothGattCallback bluetoothGattCallback=new BluetoothGattCallback() {
        /**
         * 蓝牙连接状态改变后调用 此回调 (断开，连接)
         * @param gatt
         * @param status
         * @param newState
         */
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Message message=Message.obtain();
                message.obj="success";
                handler.sendMessage(message);
                //连接成功后去发现该连接的设备的服务
                bluetoothGatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {////连接失败 或者连接断开都会调用此方法
                Message message=Message.obtain();
                message.obj="failed";
                handler.sendMessage(message);

            }

        }
        /**
         * 连接成功后发现设备服务后调用此方法
         * @param gatt
         * @param status
         */
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {//发现该设备的服务
                //拿到该服务 1,通过UUID拿到指定的服务  2,可以拿到该设备上所有服务的集合
                serviceList.clear();
                serviceList= bluetoothGatt.getServices();
//                for (int i = 0; i < serviceList.size(); i++) {
//                    Log.e("ssss", "serviceList: "+serviceList.get(i).getUuid()+"\n");
//                    List<BluetoothGattCharacteristic> characteristics = serviceList.get(i).getCharacteristics();
//                    for (int j = 0; j < characteristics.size(); j++) {
//                        Log.e("ssss", "characteristics: "+characteristics.get(j).getUuid()+"\n");
//                    }
//                }
                readyData();
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(BleActivity.this,"未发现该设备的服务");
                    }
                });
            }
        }
        /**
         * Characteristic数据发送后调用此方法
         * @param gatt
         * @param characteristic
         * @param status
         */
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {//写入成功
            ToastUtils.showToast(BleActivity.this,"写入成功");

            } else if (status == BluetoothGatt.GATT_FAILURE) {//写入失败
                ToastUtils.showToast(BleActivity.this,"写入失败");

            } else if (status == BluetoothGatt.GATT_WRITE_NOT_PERMITTED) {// 没有写入的权限
                ToastUtils.showToast(BleActivity.this,"没有写入的权限");
            }
        }
    };

    private void readyData() {
        //1.准备数据
        byte[] data = new byte[6];
        data[0] = 0x55;
        data[1] = (byte) 0xAA;
        data[2] = 0x00;
        data[3] = 0x03;
        data[4] = 0x02;
        data[5] = (byte) 0xFB;

        //2.通过指定的UUID拿到设备中的服务也可使用在发现服务回调中保存的服务
        BluetoothGattService bluetoothGattService = serviceList.get(0);

        //3.通过指定的UUID拿到设备中的服务中的characteristic，也可以使用在发现服务回调中通过遍历服务中信息保存的Characteristic
        BluetoothGattCharacteristic gattCharacteristic = bluetoothGattService.getCharacteristic(UUID1);

        //4.将byte数据设置到特征Characteristic中去
        BluetoothGattCharacteristic bluetoothGattCharacteristic = serviceList.get(0).getCharacteristics().get(0);
        serviceList.get(0).getCharacteristics().get(0).setValue(data);

        //5.将设置好的特征发送出去
        bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
    }
}
