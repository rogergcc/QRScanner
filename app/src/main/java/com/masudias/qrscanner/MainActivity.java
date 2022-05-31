package com.masudias.qrscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.masudias.qrscanner.util.BarcodeCaptureActivity;
import com.masudias.qrscanner.util.BarcodeCaptureBaseActivity;

public class MainActivity extends AppCompatActivity {

    public static final int RC_BARCODE_CAPTURE = 9001;
    public static final int REQUEST_CODE_PERMISSION = 1001;

    private TextView scanResultTextView;
    private Button scanQRButton,btnScan2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanQRButton = (Button) findViewById(R.id.scan_btn);
        btnScan2 = (Button) findViewById(R.id.btnActivity2);

        scanResultTextView = (TextView) findViewById(R.id.scan_result);

        scanQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScannerIfCameraPermissionAvailable();
            }
        });
        btnScan2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BarcodeCaptureActivity.class);
            startActivity(intent);

        });

    }

    private void openScannerIfCameraPermissionAvailable() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_PERMISSION);
        } else initiateScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initiateScan();
                }
            }
        }
    }

    public void initiateScan() {
        Intent intent = new Intent(MainActivity.this, BarcodeCaptureBaseActivity.class);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CommonStatusCodes.SUCCESS && requestCode == RC_BARCODE_CAPTURE) {
            if (data == null) return;
            Barcode barcode = data.getParcelableExtra(BarcodeCaptureBaseActivity.BarcodeObject);
            final String scanResult = barcode.displayValue;
            if (scanResult == null) return;

            scanResultTextView.setText(scanResult);
        }
    }
}