package java_version.bay_to_shelf.bay_to_trolly;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.spacepal.internal.app.R;
import com.spacepal.internal.app.model.response.AssignmentItem;
import com.spacepal.internal.app.model.response.JobItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import java_version.BaseFragment;
import java_version.bay_to_shelf.BayToShelfActivity;
import java_version.util.Util;

/**
 * Created by sidhu on 6/3/2018.
 */

public class BayToTrollyFragment extends BaseFragment implements BayToTrollyContract.View{


    private static String ARG_ASSIGNMENT="assignment";
    AssignmentItem assignmentItem;
    RecyclerView jobsRecyclerView;
    private BayToTrollyAdapter jobsListAdapter;
    private List<JobItem> jobs;
    Button btnLoadComplete;
    TextView tvRequestCode,tvCompletionDue,tvLoadingBay,tvNumberOfZone,tvPacking;
    BayToTrollyContract.Presenter presenter;
    Activity activity;

    public static BayToTrollyFragment newInstance(AssignmentItem item) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ASSIGNMENT,item);
        BayToTrollyFragment fragment = new BayToTrollyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getID() {
        return R.layout.fragment_assignment_jobs;
    }

    @Override
    public void initUI(@NotNull View view) {
        jobsRecyclerView=  view.findViewById(R.id.rvJobItems);
        btnLoadComplete = view.findViewById(R.id.btnBundleComplete);
        tvRequestCode = view.findViewById(R.id.tvRequestCode);
        tvCompletionDue = view.findViewById(R.id.tvCompletionDue);
        tvLoadingBay = view.findViewById(R.id.tvLoadingBay);
        tvNumberOfZone = view.findViewById(R.id.tvZoneQty);
        tvPacking = view.findViewById(R.id.tvPacking);
        tvPacking.setVisibility(View.VISIBLE);
        tvNumberOfZone.setVisibility(View.VISIBLE);
        view.findViewById(R.id.tvDeliveryDate).setVisibility(View.GONE);
        view.findViewById(R.id.btnPrintSticker).setVisibility(View.GONE);
        view.findViewById(R.id.btnLoadingBay).setVisibility(View.GONE);
        setRecyclerView();
        assignmentItem = (AssignmentItem) getArguments().getSerializable(ARG_ASSIGNMENT);
        presenter.getAssignment(assignmentItem.getId());

        if (jobs == null) {
            jobs = new ArrayList<>();
        }

        jobsListAdapter = new BayToTrollyAdapter(jobs, getActivity());
        jobsRecyclerView.setAdapter(jobsListAdapter);
        btnLoadComplete.setText(getString(R.string.load_complete));


    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (BayToShelfActivity)context;

    }
    private void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        jobsRecyclerView.setLayoutManager(layoutManager);
    }

    private void bindData(AssignmentItem item){
        if(item!=null)
        {
            tvRequestCode.setText("Request #"+ Util.getReference(item.getId()));
            tvCompletionDue.setText("Completion Due: "+ Util.convertDate(item.getDueDateTimeUtc()));
            tvLoadingBay.setText("Loading Bay: "+item.getBayTitle()!=null?item.getBayTitle():"");
            tvNumberOfZone.setText("");
        }
    }

    private void updateBtnStatus(){
        if(allItemsScanned(jobs)){
            btnLoadComplete.setEnabled(true);
        }else{
            btnLoadComplete.setEnabled(false);
        }
    }


    public void onLoadCompleted(View view){
        ((ScanBayToTrolly)activity).onLoadingCompleteClick(assignmentItem);
    }

    @Override
    public void setPresenter(BayToTrollyContract.Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void showJobs(List<JobItem> jobItemList) {
        jobs = jobItemList;
        jobsListAdapter.updateItems(jobs);
        updateBtnStatus();
    }

    @Override
    public void showAssignment(AssignmentItem item) {
        assignmentItem=item;
        bindData(assignmentItem);
        presenter.getJobs(assignmentItem.getId());
        updateBtnStatus();
    }

    @Override
    public void onScanResultPushed() {

        presenter.getJobs(assignmentItem.getId());
    }


    @Override
    public void showMessage(String message) {
        showAlert(message,true);
    }

    @Override
    public void showProgressDialog(Boolean isInProgress) {
       /* if (isInProgress)
            showProgress();
        else
            hideProgress();*/
    }

    @Override
    public void showOnErrorOnEmpty() {

    }

    private boolean allItemsScanned(List<JobItem> jobs){
        if(jobs.isEmpty())
            return false;
        for (JobItem item:jobs){
            if(item.getCompletedDateTimeUtc()==null)
                return false;
        }
        return true;
    }

    public interface ScanBayToTrolly{
         void onLoadingCompleteClick(AssignmentItem item);
    }
}
