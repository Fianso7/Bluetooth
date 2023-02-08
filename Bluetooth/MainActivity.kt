package com.example.bluthoothexercice

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var CODE_ENABLE = 1
    private var CODE_DISCOVERABLE = 2
    lateinit var statusBluetooth:TextView
    lateinit var paired:TextView
    lateinit var on_btn:Button
    lateinit var off_btn:Button
    lateinit var discoverable_btn:Button
    lateinit var paired_btn:Button
    lateinit var bluetooth_img:ImageView
    lateinit var bluetoothAdapter: BluetoothAdapter
    @SuppressLint("MissingInflatedId", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        on_btn = findViewById(R.id.on_bluetooth)
        off_btn = findViewById(R.id.off_bluetooth)
        discoverable_btn = findViewById(R.id.discoverable_btn)
        paired_btn = findViewById(R.id.paired_btn)
        bluetooth_img = findViewById(R.id.bluetooth_image)
        statusBluetooth = findViewById(R.id.status_bluetooth)
        paired = findViewById(R.id.paired)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()


        if(bluetoothAdapter == null)
            statusBluetooth.text = "Bluetooth is not avilable"
        else
            statusBluetooth.text = "Bluetooth is avilable"

        if(bluetoothAdapter.isEnabled)
            bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_audio_24)
        else
            bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24)

        on_btn.setOnClickListener{
           if (bluetoothAdapter.isEnabled){
               Toast.makeText(this,"Bluetooth is already ON",Toast.LENGTH_LONG).show()
           }else{
               var intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
               startActivityForResult(intent,CODE_ENABLE)
           }
        }

        off_btn.setOnClickListener {
            if (!bluetoothAdapter.isEnabled){
                Toast.makeText(this,"Bluetooth is already OFF",Toast.LENGTH_LONG).show()
            }else{
                bluetoothAdapter.disable()
                bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24)
                Toast.makeText(this,"Bluetooth turned off",Toast.LENGTH_LONG).show()


            }
        }

        discoverable_btn.setOnClickListener {
            if (!bluetoothAdapter.isDiscovering){
                Toast.makeText(this,"making your device discoverable",Toast.LENGTH_LONG).show()
                var intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                startActivityForResult(intent,CODE_DISCOVERABLE)

            }
        }

        paired_btn.setOnClickListener {
            if (bluetoothAdapter.isEnabled){
                paired.text = "Paired Devices"
                val devices = bluetoothAdapter.bondedDevices
                for (devices in devices){
                    val deviceName = devices.name
                    val devceAdress = devices
                    paired.append("\nDevices: $deviceName , $devices")
                }
            }else{
                Toast.makeText(this,"turn on bluetooth first",Toast.LENGTH_LONG).show()

            }
        }

    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        when(resultCode){
            CODE_ENABLE->
                if (resultCode == Activity.RESULT_OK){
                    bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_audio_24)
                    Toast.makeText(this,"bluetooth is on",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"cold not on bluetooth",Toast.LENGTH_LONG).show()
                }
        }
    }
}