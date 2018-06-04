package java_version.scanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;
import com.spacepal.internal.app.BaseActivity;
import com.spacepal.internal.app.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by sidhu on 6/4/2018.
 */

public class QrScannerActivity extends BaseActivity implements DecodeCallback {
    private CodeScanner mCodeScanner;
    @Override
    public int getId() {
        return R.layout.activity_scanner;
    }

    @Override
    public void created(@Nullable Bundle savedInstanceState) {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        mCodeScanner.setScanMode(ScanMode.SINGLE);
        mCodeScanner.setDecodeCallback(this);
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    @Override
    public void onDecoded(@NonNull Result result) {
        runOnUiThread(() -> {
            Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}
