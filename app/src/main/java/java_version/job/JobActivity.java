package java_version.job;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.spacepal.internal.app.BaseActivity;
import com.spacepal.internal.app.R;
import com.spacepal.internal.app.model.response.AssignmentItem;

import org.jetbrains.annotations.Nullable;

import java_version.job.scan_bundle_to_bay.ScanBundleToBayFragment;
import java_version.job.scan_bundle_to_bay.ScanBundleToBayPresenter;
import java_version.scanner.QrScannerActivity;
import java_version.util.PermissionUtil;

/**
 * Created by sidhu on 6/3/2018.
 */
public class JobActivity extends BaseActivity implements PermissionUtil.PermissionCallback{


    private static final int REQ_CODE_QR = 0x125;
    Toolbar toolbar;
    private JobFragment jobFragment;
    private AssignmentItem mAssignment;
    private JobPresenter jobsPresenter;

    private ScanBundleToBayFragment scanBundleToBayFragment;
    private ScanBundleToBayPresenter scanBundleToBayPresenter;
    private boolean scanToBayFragLoaded=false;

    @Override
    public int getId() {
        return R.layout.activity_job;
    }

    @Override
    public void created(@Nullable Bundle savedInstanceState) {
        mAssignment = (AssignmentItem) getIntent().getSerializableExtra("Assignment");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mAssignment.getRole() + "-" + mAssignment.getUserFullName());
        loadJobFragment();
    }

    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

    private void loadJobFragment() {

        scanToBayFragLoaded=false;
        jobFragment = jobFragment!=null?jobFragment: JobFragment.newInstance(mAssignment);
        jobsPresenter =jobsPresenter!=null?jobsPresenter:

                new JobPresenter(jobFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, jobFragment);
        fragmentTransaction.commit();
    }

    private void loadScanBundleToBayFragment(){
        scanBundleToBayFragment = scanBundleToBayFragment!=null?scanBundleToBayFragment: ScanBundleToBayFragment.newInstance(mAssignment);
        scanBundleToBayPresenter =scanBundleToBayPresenter!=null?scanBundleToBayPresenter:
                new ScanBundleToBayPresenter(scanBundleToBayFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, scanBundleToBayFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_qr_code, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_scan: {

                checkPermission();
            }
            break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void checkPermission(){
        if(PermissionUtil.isCameraPermissionGranted(this)){
            startActivityForResult(new Intent(this, QrScannerActivity.class),REQ_CODE_QR);
        }else{
            PermissionUtil.requestCameraPermission(this,this);

        }
    }

    @Override
    public void onPermissionGranted() {
        startActivityForResult(new Intent(this, QrScannerActivity.class), REQ_CODE_QR);
    }

    @Override
    public void onPermissionDenied() {
        showSettingsDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     //   super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_QR:
                    String response = data.getStringExtra("QR_RESPONSE");
                    if(!scanToBayFragLoaded)
                        jobsPresenter.scanToOrder(mAssignment.getId(),response);
                    else
                        scanBundleToBayPresenter.scanToBay(mAssignment.getNextAssignmentId(),mAssignment.getBayId());
                    break;
            }
        }
    }


    // This is being called from xml
    public void printSticker(View view){
        jobsPresenter.printSticker(mAssignment.getId());
    }

    public void onLoadingBayClick(View view){
        scanToBayFragLoaded=true;
        loadScanBundleToBayFragment();
    }
}
