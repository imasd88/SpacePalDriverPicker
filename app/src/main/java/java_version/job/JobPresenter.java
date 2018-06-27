package java_version.job;

import android.util.Log;

import com.spacepal.internal.app.Constant;
import com.spacepal.internal.app.SpacePalApplication;
import com.spacepal.internal.app.model.response.APIError;
import com.spacepal.internal.app.model.response.AssignmentItem;
import com.spacepal.internal.app.model.response.JobsResponse;

import java_version.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sidhu on 6/3/2018.
 */

public class JobPresenter implements JobContract.Presenter,Constant {

    private JobContract.View view;
    private static final String TAG = "ScanTrollyToShelfPresenter";

    public JobPresenter(JobContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void getAssignment(String assignmentId) {
        view.showProgressDialog(true);
        
        Call<AssignmentItem>  call = SpacePalApplication.Companion.getInstance().getApi().getAssignment(assignmentId);
        call.enqueue(new Callback<AssignmentItem>() {
            @Override
            public void onResponse(Call<AssignmentItem> call, Response<AssignmentItem> response) {
                if (response.code() == 200) {
                    view.showAssignment(response.body());
                } else {
                    APIError error = Util.parseError(response);
                    view.showMessage(response.code()+" - "+error.getErrorMsg());
                }
                view.showProgressDialog(false);
            }

            @Override
            public void onFailure(Call<AssignmentItem> call, Throwable t) {
                view.showMessage( "FAIL...");
                view.showProgressDialog(false);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getJobs(String assignmentId) {
        view.showProgressDialog(true);

        Call<JobsResponse>  call = SpacePalApplication.Companion.getInstance().getApi().getJobs(assignmentId);
        call.enqueue(new Callback<JobsResponse>() {
            @Override
            public void onResponse(Call<JobsResponse> call, Response<JobsResponse> response) {
                if (response.code() == 200) {
                    view.showJobs(response.body().getJob());
                } else {
                    APIError error = Util.parseError(response);
                    view.showMessage(response.code()+" - "+error.getErrorMsg());
                }
                view.showProgressDialog(false);
            }

            @Override
            public void onFailure(Call<JobsResponse> call, Throwable t) {
                view.showMessage( "FAIL...");
                view.showProgressDialog(false);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void scanToOrder(String assignmentId,String inventoryId) {
        view.showProgressDialog(true);
        Call<Void>  call = SpacePalApplication.Companion.getInstance().getApi().scanToOrder(assignmentId,inventoryId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view.showProgressDialog(false);
                if (response.code() == 200) {
                    view.onScanResultPushed();
                } else {
                    view.showMessage(response.code()+" - "+String.valueOf(response.raw()));
                    Log.e("error", String.valueOf(response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.showProgressDialog(false);
                view.showMessage( "FAIL...");
                t.printStackTrace();
            }
        });

    }

    @Override
    public void printSticker(String appointmentId) {
        view.showProgressDialog(true);
        Call<Void>  call = SpacePalApplication.Companion.getInstance().getApi().printSticker(appointmentId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view.showProgressDialog(false);
                if (response.code() == 200) {
                    view.onPrintedSticker();
                } else {
                    APIError error = Util.parseError(response);
                    view.showMessage(response.code()+" - "+error.getErrorMsg());
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.showProgressDialog(false);
                view.showMessage( "FAIL...");
                t.printStackTrace();
            }
        });
    }
}
