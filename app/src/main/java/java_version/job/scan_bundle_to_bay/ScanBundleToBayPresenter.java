package java_version.job.scan_bundle_to_bay;

import com.spacepal.internal.app.Constant;
import com.spacepal.internal.app.SpacePalApplication;
import com.spacepal.internal.app.model.response.APIError;
import com.spacepal.internal.app.model.response.JobsResponse;

import java_version.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sidhu on 6/3/2018.
 */

public class ScanBundleToBayPresenter implements ScanBundleToBayContract.Presenter,Constant {

    private ScanBundleToBayContract.View view;
    private static final String TAG = "ScanTrollyToShelfPresenter";

    public ScanBundleToBayPresenter(ScanBundleToBayContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void getJobs(String assignmentId) {
        view.showProgressDialog(true);

        Call<JobsResponse> call = SpacePalApplication.Companion.getInstance().getApi().getJobs(assignmentId);
        call.enqueue(new Callback<JobsResponse>() {
            @Override
            public void onResponse(Call<JobsResponse> call, Response<JobsResponse> response) {
                view.showProgressDialog(false);
                if (response.code() == 200) {
                    view.showAssignmentBundle(response.body().getJob());
                } else {
                    APIError error = Util.parseError(response);
                    view.showMessage(response.code()+" - "+error.getError());
                }

            }

            @Override
            public void onFailure(Call<JobsResponse> call, Throwable t) {
                view.showMessage("FAIL...");
                view.showProgressDialog(false);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void scanToBay(String assignmentId,String bayId) {
        view.showProgressDialog(true);
        Call<Void> call = SpacePalApplication.Companion.getInstance().getApi().scanToBay(assignmentId,bayId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view.showProgressDialog(false);
                if (response.code() == 200) {
                    view.onScanResultPushed();
                } else {
                    APIError error = Util.parseError(response);
                    view.showMessage(response.code()+" - "+error.getError());
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.showProgressDialog(false);
                view.showMessage("FAIL...");
                t.printStackTrace();
            }
        });

    }
}
