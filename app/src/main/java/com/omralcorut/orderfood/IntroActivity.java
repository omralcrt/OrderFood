package com.omralcorut.orderfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

//Use scanning qr code library(ZXing) to read which table
public class IntroActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    public final static String QRCODE = "QRCODE";
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove top bar for fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
    }

    //When click scan button, open scanner camera
    public void scanCode(View v) {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    //Stop camera
    @Override
    protected void onPause() {
        super.onPause();
        //mScannerView.stopCamera();
    }

    //When read qr code, go to main activity
    @Override
    public void handleResult(Result result) {

        //if user scan another QR code, show alert dialog.
        try {
            int tableNumber = Integer.valueOf(result.getText());
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(QRCODE,tableNumber);
            startActivity(intent);
            finish();
        }catch (Exception e) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Scan Result");
            builder1.setMessage("Please scan QR code on the table.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mScannerView.resumeCameraPreview(IntroActivity.this);
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }
}
