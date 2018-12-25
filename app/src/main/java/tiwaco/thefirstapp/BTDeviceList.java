package tiwaco.thefirstapp;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BTDeviceList extends ListActivity {
    static public final int REQUEST_CONNECT_BT = 0x2300;

    static private final int REQUEST_ENABLE_BT = 0x1000;

    static private BluetoothAdapter mBluetoothAdapter = null;

    static private ArrayAdapter<String> mArrayAdapter = null;

    static private ArrayAdapter<BluetoothDevice> btDevices = null;
    ActionBar bar;

    private static final UUID SPP_UUID = UUID.fromString("0001101-0000-1000-8000-00805F9B34FB");
//            UUID
//            .fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");


    static private BluetoothSocket mbtSocket = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            if (initDevicesList() != 0) {
                this.finish();
                return;
            }

        } catch (Exception ex) {
            this.finish();
            return;
        }

        IntentFilter btIntentFilter = new IntentFilter(
                BluetoothDevice.ACTION_FOUND);
        btIntentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        btIntentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        btIntentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);

        registerReceiver(mBTReceiver, btIntentFilter);
    }

    public static BluetoothSocket getSocket() {
        return mbtSocket;
    }

    private void flushData() {
        try {
            if (mbtSocket != null) {
                mbtSocket.close();
                mbtSocket = null;
            }

            if (mBluetoothAdapter != null) {
                mBluetoothAdapter.cancelDiscovery();
            }

            if (btDevices != null) {
                btDevices.clear();
                btDevices = null;
            }

            if (mArrayAdapter != null) {
                mArrayAdapter.clear();
                mArrayAdapter.notifyDataSetChanged();
                mArrayAdapter.notifyDataSetInvalidated();
                mArrayAdapter = null;
            }

            finalize();

        } catch (Exception ex) {
        } catch (Throwable e) {
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        //Có vao trong này
        Log.e("KIEM tra ket noi", "co");

        // Register for broadcasts when a device is discovered and discovery has finished
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);


        registerReceiver(mBTReceiver, filter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(mBTReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private int initDevicesList() {

        flushData();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),
                    "Không hổ trợ bluetooth!!", Toast.LENGTH_LONG).show();
            return -1;
        }

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listbluetooth_item);
        //   android.R.layout.simple_list_item_1);

        setListAdapter(mArrayAdapter);

        Intent enableBtIntent = new Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE);
        try {
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } catch (Exception ex) {
            return -2;
        }

        Toast.makeText(getApplicationContext(),
                "Đang tiến hành lấy tất cả các thiết bị Bluetooth ", Toast.LENGTH_SHORT)
                .show();

        return 0;

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent intent) {
        super.onActivityResult(reqCode, resultCode, intent);

        switch (reqCode) {
            case REQUEST_ENABLE_BT:

                if (resultCode == RESULT_OK) {
                    Set<BluetoothDevice> btDeviceList = mBluetoothAdapter
                            .getBondedDevices();
                    try {
                        if (btDeviceList.size() > 0) {

                            for (BluetoothDevice device : btDeviceList) {
                                if (btDeviceList.contains(device) == false) {
                                    Log.e(" btDevices.add(device)", "add onActivityResult " + device.getName());
                                    btDevices.add(device);
                                    mArrayAdapter.add(device.getName() + "\n"
                                            + device.getAddress());
                                    mArrayAdapter.notifyDataSetInvalidated();
                                }
                            }
                        }
                    } catch (Exception ex) {
                    }
                }

                break;
        }

        mBluetoothAdapter.startDiscovery();

    }



    private final BroadcastReceiver mBTReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();


            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                try {
//                    if (btDevices == null) {
//                        btDevices = new ArrayAdapter<BluetoothDevice>(
//                                getApplicationContext(), android.R.id.text1);
//                    }
//
//                    if (btDevices.getPosition(device) < 0) {
//
//                        if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
//                            btDevices.add(device);
//                            mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//                            Log.e(" btDevices.add(device)", "add broahcast" + device.getName() );
//                            mArrayAdapter.notifyDataSetInvalidated();
//                        }
//
//
//                        btDevices.add(device);
//                        mArrayAdapter.add(device.getName() + "\n"
//                                + device.getAddress() + "\n" );
//                        mArrayAdapter.notifyDataSetChanged();
//                        Log.e(" btDevices.add(device)", "add broahcast" + device.getName());
                    if (btDevices == null) {
                        btDevices = new ArrayAdapter<BluetoothDevice>(
                                getApplicationContext(), android.R.id.text1);
                    }

                    if (btDevices.getPosition(device) < 0) {
                        btDevices.add(device);
                        mArrayAdapter.add(device.getName() + "\n"
                                + device.getAddress() + "\n" );
                        mArrayAdapter.notifyDataSetInvalidated();
                    }

                } catch (Exception ex) {
// ex.fillInStackTrace();
                }
            }


            if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                if (mbtSocket != null) {
                    try {
                        mbtSocket.getOutputStream().close();
                        mbtSocket.close();
                        mbtSocket = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                if (mbtSocket != null) {
                    try {
                        mbtSocket.getOutputStream().close();
                        mbtSocket.close();
                        mbtSocket = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };


    @Override
    protected void onListItemClick(ListView l, View v, final int position,
                                   long id) {
        super.onListItemClick(l, v, position, id);

        if (mBluetoothAdapter == null) {
            return;
        }

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        Toast.makeText(
                getApplicationContext(),
                "Đang kết nối với máy in " + btDevices.getItem(position).getName() + ","
        + btDevices.getItem(position).getAddress(),
                Toast.LENGTH_SHORT).show();

        Thread connectThread = new Thread(new Runnable() {

            @Override
            public void run() {
                UUID uuid = null;
                Log.e("vitri", String.valueOf(position));
//                boolean gotuuid = btDevices.getItem(position)
//                        .fetchUuidsWithSdp();

                try {
                    uuid = btDevices.getItem(position).getUuids()[0]
                            .getUuid();
                    Log.e("uuid device", uuid.toString());
                } catch (Exception e) {
                    uuid = SPP_UUID;
                    Log.e("uuid SPP_UUID", uuid.toString());
                }
                try {
//                    if (!btDevices.isEmpty()) {
//                        BluetoothDevice device = btDevices.remove(position);
//                        boolean result = device.fetchUuidsWithSdp();
//                    }


                    mbtSocket = btDevices.getItem(position).createRfcommSocketToServiceRecord(uuid);


                    //Bien.socketTest = mbtSocket;
                    mbtSocket.connect();

                } catch (IOException ex) {
//                    Log.e("loi ket noi thiet bi", ex.toString());
//                    runOnUiThread(socketErrorRunnable);
//                    try {
//                        if (mbtSocket != null) {
//                            mbtSocket.close();
//                        }
//                    } catch (IOException e) {
//// e.printStackTrace();
//                    }
//                    mbtSocket = null;
//                    return;
                    try {
                        Log.e("", "trying fallback..." + ex);
                        if (Build.VERSION.SDK_INT >= 10) {
                            try {
                                final Method m = btDevices.getItem(position).getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});
                                mbtSocket = (BluetoothSocket) m.invoke(btDevices.getItem(position), uuid);
                                mbtSocket.connect();
                            } catch (Exception e) {
                                Log.e("", "Could not create Insecure RFComm Connection", e);
                            }
                        } else {
                            mbtSocket = (BluetoothSocket) btDevices.getItem(position).getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(btDevices.getItem(position), 1);
                            mbtSocket.connect();
                        }


                        Log.e("", "Connected");
                    } catch (Exception e2) {
                        runOnUiThread(socketErrorRunnable);
                        Log.e("", "Couldn't establish Bluetooth connection!" + e2);
                    }

                } finally {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            finish();

                        }
                    });
                }
            }
        });

        connectThread.start();
    }

    private Runnable socketErrorRunnable = new Runnable() {

        @Override
        public void run() {
            Toast.makeText(getApplicationContext(),
                    "Không thể tạo kết nối với thiết bị", Toast.LENGTH_SHORT).show();
            mBluetoothAdapter.startDiscovery();

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, Menu.FIRST, Menu.NONE, "Refresh Scanning");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case Menu.FIRST:
                initDevicesList();
                break;
        }

        return true;
    }

}
