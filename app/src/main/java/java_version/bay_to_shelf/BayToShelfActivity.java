package java_version.bay_to_shelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.spacepal.internal.app.BaseActivity;
import com.spacepal.internal.app.R;
import com.spacepal.internal.app.model.response.AssignmentItem;

import org.jetbrains.annotations.Nullable;

import java_version.bay_to_shelf.bay_to_trolly.BayToTrollyFragment;
import java_version.bay_to_shelf.bay_to_trolly.BayToTrollyPresenter;
import java_version.bay_to_shelf.trolly_to_shelf.ScanTrollyToShelfFragment;
import java_version.bay_to_shelf.trolly_to_shelf.ScanTrollyToShelfPresenter;
import java_version.scanner.QrScannerActivity;
import java_version.util.PermissionUtil;

/**
 * Created by sidhu on 6/3/2018.
 */
public class BayToShelfActivity extends BaseActivity implements PermissionUtil.PermissionCallback,BayToTrollyFragment.ScanBayToTrolly{


    private static final int REQ_CODE_QR = 0x125;
    Toolbar toolbar;
    private BayToTrollyFragment bayToTrollyFragment;
    private AssignmentItem mAssignment;
    private BayToTrollyPresenter jobsPresenter;

    private ScanTrollyToShelfFragment scanTrollyToShelfFragment;
    private ScanTrollyToShelfPresenter scanTrollyToShelfPresenter;
    private boolean trollyToShelfFragLoaded=false;

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
        loadBayToTrollyFragment();
    }

    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

    private void loadBayToTrollyFragment() {

        trollyToShelfFragLoaded=false;
        bayToTrollyFragment = bayToTrollyFragment!=null?bayToTrollyFragment: BayToTrollyFragment.newInstance(mAssignment);
        jobsPresenter =jobsPresenter!=null?jobsPresenter:

                new BayToTrollyPresenter(bayToTrollyFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, bayToTrollyFragment);
        fragmentTransaction.commit();
    }

    private void loadTrollyToShelfFragment(){
        scanTrollyToShelfFragment = scanTrollyToShelfFragment!=null?scanTrollyToShelfFragment: ScanTrollyToShelfFragment.newInstance(mAssignment);
        scanTrollyToShelfPresenter =scanTrollyToShelfPresenter!=null?scanTrollyToShelfPresenter:
                new ScanTrollyToShelfPresenter(scanTrollyToShelfFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, scanTrollyToShelfFragment);
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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_QR:
                    String response = data.getStringExtra("QR_RESPONSE");
                    if(!trollyToShelfFragLoaded)
                        jobsPresenter.scanToTrolly(mAssignment.getId(),response);
                    else
                        scanTrollyToShelfPresenter.scanTrollyToShelf(mAssignment.getNextAssignmentId(),response,mAssignment.getBayId());
                    break;
            }
        }
    }

    @Override
    public void onLoadingCompleteClick(AssignmentItem mAssignment) {
        this.mAssignment=mAssignment;
        trollyToShelfFragLoaded=true;
        loadTrollyToShelfFragment();
    }
}
