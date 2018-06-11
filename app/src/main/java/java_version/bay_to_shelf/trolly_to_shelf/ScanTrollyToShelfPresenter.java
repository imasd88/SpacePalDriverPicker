package java_version.bay_to_shelf.trolly_to_shelf;

import com.spacepal.internal.app.Constant;
import com.spacepal.internal.app.model.response.APIError;
import com.spacepal.internal.app.model.response.JobsResponse;
import com.spacepal.internal.app.source.RetrofitHelper;

import java_version.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sidhu on 6/3/2018.
 */

public class ScanTrollyToShelfPresenter implements ScanTrollyToShelfContract.Presenter,Constant {

    private ScanTrollyToShelfContract.View view;
    private static final String TAG = "ScanTrollyToShelfPresenter";

    public ScanTrollyToShelfPresenter(ScanTrollyToShelfContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void getJobs(String assignmentId) {
        view.showProgressDialog(true);

        Call<JobsResponse> call = RetrofitHelper.Companion.getInstance().getApi().getJobs(assignmentId);
        call.enqueue(new Callback<JobsResponse>() {
            @Override
            public void onResponse(Call<JobsResponse> call, Response<JobsResponse> response) {
                if (response.code() == 200) {
                    view.showAssignmentBundle(response.body().getJob());
                } else {
                    APIError error = Util.parseError(response);
                    view.showMessage(error.getError());
                    view.showOnErrorOnEmpty();
                }
                view.showProgressDialog(false);
            }

            @Override
            public void onFailure(Call<JobsResponse> call, Throwable t) {
                view.showMessage("FAIL...");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void scanTrollyToShelf(String assignmentId,String inventoryId,String shelfId) {
        view.showProgressDialog(true);
        Call<Void> call = RetrofitHelper.Companion.getInstance().getApi().scanTrollyToShelf(assignmentId,inventoryId,shelfId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view.showProgressDialog(false);
                if (response.code() == 200) {
                    view.onScanResultPushed();
                } else {
                    APIError error = Util.parseError(response);
                    view.showMessage(error.getError());
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
