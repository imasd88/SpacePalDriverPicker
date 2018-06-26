package java_version.job;

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
import java_version.util.Util;

/**
 * Created by sidhu on 6/3/2018.
 */

public class JobFragment extends BaseFragment implements JobContract.View{


    private static String ARG_ASSIGNMENT="assignment";
    AssignmentItem assignmentItem;
    RecyclerView jobsRecyclerView;
    private JobAdapter jobsListAdapter;
    private List<JobItem> jobs;
    Button btnBundleComplete,btnPrintSticker,btnLoadingBay;
    TextView tvRequestCode,tvCompletionDue,tvDeliveryDate,tvLoadingBay;
    JobContract.Presenter presenter;
    Activity activity;

    public static JobFragment newInstance(AssignmentItem item) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ASSIGNMENT,item);
        JobFragment fragment = new JobFragment();
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
        btnBundleComplete = view.findViewById(R.id.btnBundleComplete);
        btnPrintSticker = view.findViewById(R.id.btnPrintSticker);
        btnLoadingBay = view.findViewById(R.id.btnLoadingBay);

        tvRequestCode = view.findViewById(R.id.tvRequestCode);
        tvCompletionDue = view.findViewById(R.id.tvCompletionDue);
        tvDeliveryDate =view.findViewById(R.id.tvDeliveryDate);
        tvLoadingBay = view.findViewById(R.id.tvLoadingBay);
        setRecyclerView();
        assignmentItem = (AssignmentItem) getArguments().getSerializable(ARG_ASSIGNMENT);
        presenter.getAssignment(assignmentItem.getId());

        if (jobs == null) {
            jobs = new ArrayList<>();
        }

        jobsListAdapter = new JobAdapter(jobs, getActivity());
        jobsRecyclerView.setAdapter(jobsListAdapter);
        showAssignment(assignmentItem);
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (JobActivity)context;

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
            tvDeliveryDate.setText("Delivery Date: "+Util.convertDate(item.getDueDateTimeUtc()));
            tvLoadingBay.setText("Loading Bay: "+item.getBayTitle()!=null?item.getBayTitle():"");
        }
    }

    private void updateBtnStatus(){
        if(allItemsScanned(jobs)){
            btnBundleComplete.setEnabled(true);
            btnPrintSticker.setEnabled(true);
        }else{
            btnBundleComplete.setEnabled(false);
            btnPrintSticker.setEnabled(false);
        }
    }




    @Override
    public void setPresenter(JobContract.Presenter presenter) {
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
    public void onPrintedSticker() {
        btnLoadingBay.setEnabled(true);
    }


    @Override
    public void showMessage(String message) {
       // ToastsKt.toast(getContext(),message);
        showAlert(message,true);
    }

    @Override
    public void showProgressDialog(Boolean isInProgress) {
      /*  if (isInProgress)
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

}
