package java_version.shelf_to_bay.shelf_to_trolly;

import android.util.Log;

import com.spacepal.internal.app.Constant;
import com.spacepal.internal.app.model.response.APIError;
import com.spacepal.internal.app.model.response.AssignmentItem;
import com.spacepal.internal.app.model.response.JobsResponse;
import com.spacepal.internal.app.source.RetrofitHelper;

import java_version.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sidhu on 6/3/2018.
 */

public class ShelfToTrollyPresenter implements ShelfToTrollyContract.Presenter,Constant {

    private ShelfToTrollyContract.View view;
    private static final String TAG = "BayToTrollyPresenter";

    public ShelfToTrollyPresenter(ShelfToTrollyContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void getAssignment(String assignmentId) {
        view.showProgressDialog(true);

        Call<AssignmentItem>  call = RetrofitHelper.Companion.getInstance().getApi().getAssignment(assignmentId);
        call.enqueue(new Callback<AssignmentItem>() {
            @Override
            public void onResponse(Call<AssignmentItem> call, Response<AssignmentItem> response) {
                if (response.code() == 200) {
                    view.showAssignment(response.body());
                } else {
                    APIError error = Util.parseError(response);
                    view.showMessage(error.getError());
                    view.showOnErrorOnEmpty();
                }
                view.showProgressDialog(false);
            }

            @Override
            public void onFailure(Call<AssignmentItem> call, Throwable t) {
                view.showMessage( "FAIL...");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getJobs(String assignmentId) {
        view.showProgressDialog(true);

        Call<JobsResponse>  call = RetrofitHelper.Companion.getInstance().getApi().getJobs(assignmentId);
        call.enqueue(new Callback<JobsResponse>() {
            @Override
            public void onResponse(Call<JobsResponse> call, Response<JobsResponse> response) {
                if (response.code() == 200) {
                    view.showJobs(response.body().getJob());
                } else {
                    APIError error = Util.parseError(response);
                    view.showMessage(error.getError());
                    view.showOnErrorOnEmpty();
                }
                view.showProgressDialog(false);
            }

            @Override
            public void onFailure(Call<JobsResponse> call, Throwable t) {
                view.showMessage( "FAIL...");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void scanToTrolly(String assignmentId,String inventoryId) {
        view.showProgressDialog(true);
        Call<Void>  call = RetrofitHelper.Companion.getInstance().getApi().scanToTrolly(assignmentId,inventoryId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view.showProgressDialog(false);
                if (response.code() == 200) {
                    view.onScanResultPushed();
                } else {
                    view.showMessage( String.valueOf(response.raw()));
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


}
