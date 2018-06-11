package java_version.bay_to_shelf.trolly_to_shelf;

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

public class ScanTrollyToShelfFragment extends BaseFragment implements ScanTrollyToShelfContract.View{


    private static String ARG_ASSIGNMENT="assignment";
    AssignmentItem assignmentItem;
    RecyclerView assignmentBundleRecyclerView;
    private ScanTrollyToShelfAdapter assignmentBundleListAdapter;
    private List<JobItem> assignmentBundle;
    Button btnComplete;
    TextView tvRequestCode,tvCompletionDue,tvDeliveryDate,tvLoadingBay,tvZoneQty;
    ScanTrollyToShelfContract.Presenter presenter;

    public static ScanTrollyToShelfFragment newInstance(AssignmentItem item) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ASSIGNMENT,item);
        ScanTrollyToShelfFragment fragment = new ScanTrollyToShelfFragment();
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
        tvZoneQty = view.findViewById(R.id.tvZoneQty);
        setRecyclerView();
        assignmentItem = (AssignmentItem) getArguments().getSerializable(ARG_ASSIGNMENT);
        view.findViewById(R.id.tvScanLoadingBay).setVisibility(View.GONE);
        view.findViewById(R.id.ivScanLoadingBay).setVisibility(View.GONE);
        btnComplete.setText(R.string.pick_complete);
        bindData(assignmentItem);
        if (assignmentBundle == null) {
            assignmentBundle = new ArrayList<>();
        }

        assignmentBundleListAdapter = new ScanTrollyToShelfAdapter(assignmentBundle, getActivity());
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
            tvDeliveryDate.setVisibility(View.GONE);
            tvLoadingBay.setText("Loading Bay: "+item.getBayTitle()!=null?item.getBayTitle():"");
            tvZoneQty.setVisibility(View.VISIBLE);
            tvZoneQty.setText("Number of zones: "+"");
        }
    }

    private void updateBtnStatus(){
        if(allBundleLoaded(assignmentBundle)){
            btnComplete.setEnabled(true);
                   }else{
            btnComplete.setEnabled(false);
        }
    }


    @Override
    public void setPresenter(ScanTrollyToShelfContract.Presenter presenter) {
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
