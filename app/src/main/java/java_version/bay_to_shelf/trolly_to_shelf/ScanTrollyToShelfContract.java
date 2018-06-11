package java_version.bay_to_shelf.trolly_to_shelf;

import com.spacepal.internal.app.BasePresenter;
import com.spacepal.internal.app.BaseView;
import com.spacepal.internal.app.model.response.JobItem;

import java.util.List;

/**
 * Created by sidhu on 6/3/2018.
 */

public class ScanTrollyToShelfContract {
    interface View extends BaseView<Presenter>

    {
        void showAssignmentBundle(List<JobItem> jobItemList);
        void onScanResultPushed();
        void showMessage(String message);
        void showProgressDialog(Boolean isInProgress);
        void showOnErrorOnEmpty();
    }


    interface Presenter extends BasePresenter

    {
        void getJobs(String nextAssignmentId);
        void scanTrollyToShelf(String assignmentId, String inventoryId, String shelfId);
    }
}
