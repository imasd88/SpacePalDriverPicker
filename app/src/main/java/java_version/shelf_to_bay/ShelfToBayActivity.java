package java_version.shelf_to_bay;

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

import java_version.scanner.QrScannerActivity;
import java_version.shelf_to_bay.shelf_to_trolly.ShelfToTrollyFragment;
import java_version.shelf_to_bay.shelf_to_trolly.ShelfToTrollyPresenter;
import java_version.shelf_to_bay.trolly_to_bay.ScanTrollyToBayFragment;
import java_version.shelf_to_bay.trolly_to_bay.ScanTrollyToBayPresenter;
import java_version.util.PermissionUtil;

/**
 * Created by sidhu on 6/3/2018.
 */
public class ShelfToBayActivity extends BaseActivity implements PermissionUtil.PermissionCallback,ShelfToTrollyFragment.ScanTrollyToBay{


    private static final int REQ_CODE_QR = 0x125;
    Toolbar toolbar;
    private ShelfToTrollyFragment shelfToTrollyFragment;
    private AssignmentItem mAssignment;
    private ShelfToTrollyPresenter jobsPresenter;

    private ScanTrollyToBayFragment scanTrollyToBayFragment;
    private ScanTrollyToBayPresenter scanTrollyToBayPresenter;
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
        loadShelfToTrollyFragment();
    }

    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

    private void loadShelfToTrollyFragment() {

        scanToBayFragLoaded=false;
        shelfToTrollyFragment = shelfToTrollyFragment!=null?shelfToTrollyFragment: ShelfToTrollyFragment.newInstance(mAssignment);
        jobsPresenter =jobsPresenter!=null?jobsPresenter:

                new ShelfToTrollyPresenter(shelfToTrollyFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, shelfToTrollyFragment);
        fragmentTransaction.commit();
    }

    private void loadTrollyToBayFragment(){
        scanTrollyToBayFragment = scanTrollyToBayFragment!=null?scanTrollyToBayFragment: ScanTrollyToBayFragment.newInstance(mAssignment);
        scanTrollyToBayPresenter =scanTrollyToBayPresenter!=null?scanTrollyToBayPresenter:
                new ScanTrollyToBayPresenter(scanTrollyToBayFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, scanTrollyToBayFragment);
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
      //  super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_QR:
                    String response = data.getStringExtra("QR_RESPONSE");
                    if(!scanToBayFragLoaded)
                        jobsPresenter.scanToTrolly(mAssignment.getId(),response);
                    else
                        scanTrollyToBayPresenter.scanTrollyToBay(mAssignment.getNextAssignmentId(),response,mAssignment.getBayId());
                    break;
            }
        }
    }

    @Override
    public void onLoadingBayClick(AssignmentItem mAssignment) {
        this.mAssignment=mAssignment;
        scanToBayFragLoaded=true;
        loadTrollyToBayFragment();
    }
}
