package java_version.shelf_to_bay.trolly_to_bay;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.spacepal.internal.app.R;
import com.spacepal.internal.app.model.response.AssignmentItem;
import com.spacepal.internal.app.model.response.JobItem;

import org.jetbrains.anko.ToastsKt;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import java_version.BaseFragment;
import java_version.util.Util;

/**
 * Created by sidhu on 6/3/2018.
 */

public class ScanTrollyToBayFragment extends BaseFragment implements ScanTrollyToBayContract.View{


    private static String ARG_ASSIGNMENT="assignment";
    AssignmentItem assignmentItem;
    RecyclerView assignmentBundleRecyclerView;
    private ScanTrollyToBayAdapter assignmentBundleListAdapter;
    private List<JobItem> assignmentBundle;
    Button btnComplete;
    TextView tvRequestCode,tvCompletionDue,tvDeliveryDate,tvLoadingBay,tvLoadingBayConfirm;
    ScanTrollyToBayContract.Presenter presenter;

    public static ScanTrollyToBayFragment newInstance(AssignmentItem item) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ASSIGNMENT,item);
        ScanTrollyToBayFragment fragment = new ScanTrollyToBayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getID() {
        return R.layout.fragment_place_in_bay;
    }

    @Override
    public void initUI(@NotNull View view) {
        assignmentBundleRecyclerView=  view.findViewById(R.id.rvJobItems);
        btnComplete = view.findViewById(R.id.btnComplete);

        tvRequestCode = view.findViewById(R.id.tvRequestCode);
        tvCompletionDue = view.findViewById(R.id.tvCompletionDue);
        tvDeliveryDate =view.findViewById(R.id.tvDeliveryDate);
        tvLoadingBay = view.findViewById(R.id.tvLoadingBay);
        tvLoadingBayConfirm = view.findViewById(R.id.tvLoadingBayConfirm);
        setRecyclerView();
        assignmentItem = (AssignmentItem) getArguments().getSerializable(ARG_ASSIGNMENT);
        bindData(assignmentItem);
        if (assignmentBundle == null) {
            assignmentBundle = new ArrayList<>();
        }

        assignmentBundleListAdapter = new ScanTrollyToBayAdapter(assignmentBundle, getActivity());
        assignmentBundleRecyclerView.setAdapter(assignmentBundleListAdapter);
        presenter.getJobs(assignmentItem.getId());
        updateBtnStatus();
    }

    private void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        assignmentBundleRecyclerView.setLayoutManager(layoutManager);
    }

    private void bindData(AssignmentItem item){
        if(item!=null)
        {
            tvRequestCode.setText("Request #"+ Util.getReference(item.getId()));
            tvCompletionDue.setText("Completion Due: "+ Util.convertDate(item.getDueDateTimeUtc()));
            tvDeliveryDate.setText("Delivery Date: "+Util.convertDate(item.getDueDateTimeUtc()));
            tvLoadingBay.setText("Loading Bay: "+item.getBayTitle()!=null?item.getBayTitle():"");
        }
    }

    private void updateBtnStatus(){
        if(allBundleLoaded(assignmentBundle)){
            btnComplete.setEnabled(true);
            tvLoadingBayConfirm.setVisibility(View.VISIBLE);
            tvLoadingBayConfirm.setText("Loading Bay "+assignmentItem.getBayTitle()!=null?assignmentItem.getBayTitle():""+" confirmed");
        }else{
            btnComplete.setEnabled(false);
        }
    }


    @Override
    public void setPresenter(ScanTrollyToBayContract.Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void showAssignmentBundle(List<JobItem> jobItemList) {
        assignmentBundle = jobItemList;
        assignmentBundleListAdapter.updateItems(assignmentBundle);
        updateBtnStatus();
    }

    @Override
    public void onScanResultPushed() {
        presenter.getJobs(assignmentItem.getId());
    }



    @Override
    public void showMessage(String message) {
        ToastsKt.toast(getContext(),message);
    }

    @Override
    public void showProgressDialog(Boolean isInProgress) {

    }

    @Override
    public void showOnErrorOnEmpty() {

    }

    private boolean allBundleLoaded(List<JobItem> assignmentBundle){
        if(assignmentBundle.isEmpty())
            return false;
        for (JobItem item:assignmentBundle){
            if(item.getCompletedDateTimeUtc()==null)
                return false;
        }
        return true;
    }
}
