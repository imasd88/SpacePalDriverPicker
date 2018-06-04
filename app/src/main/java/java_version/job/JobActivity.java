package java_version.job;

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
import com.spacepal.internal.app.ui.profile.ProfileActivity;

import org.jetbrains.annotations.Nullable;

import java_version.scanner.QrScannerActivity;
import java_version.util.PermissionUtil;

/**
 * Created by sidhu on 6/3/2018.
 */

public class JobActivity extends BaseActivity implements PermissionUtil.PermissionCallback{

    Toolbar toolbar;
    private JobFragment jobFragment;
    private AssignmentItem mAssignment;
    private JobPresenter jobsPresenter;

    @Override
    public int getId() {
        return R.layout.activity_job;
    }

    @Override
    public void created(@Nullable Bundle savedInstanceState) {
        mAssignment=(AssignmentItem) getIntent().getSerializableExtra("Assignment");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mAssignment.getRole() +"-"+ mAssignment.getUserFullName());
        loadJobFragment();
    }

    public boolean onSupportNavigateUp()  {
        this.finish();
        return true;
    }
    private void loadJobFragment() {
        jobFragment = jobFragment!=null?jobFragment: JobFragment.newInstance(mAssignment);
        jobsPresenter =jobsPresenter!=null?jobsPresenter:
                new JobPresenter(jobFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, jobFragment);
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
            case R.id.action_scan : {

                checkPermission();
            }
            break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void checkPermission(){
        if(PermissionUtil.isCameraPermissionGranted(this)){
            startActivity(new Intent(this, ProfileActivity.class));
        }else{
            PermissionUtil.requestCameraPermission(this,this);
        }
    }

    @Override
    public void onPermissionGranted() {
        startActivity(new Intent(this, QrScannerActivity.class));
    }

    @Override
    public void onPermissionDenied() {
    showSettingsDialog();
    }
}