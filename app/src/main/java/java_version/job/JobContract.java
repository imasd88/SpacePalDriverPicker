package java_version.job;

import com.spacepal.internal.app.BasePresenter;
import com.spacepal.internal.app.BaseView;
import com.spacepal.internal.app.model.response.AssignmentItem;
import com.spacepal.internal.app.model.response.JobItem;

import java.util.List;

/**
 * Created by sidhu on 6/3/2018.
 */

public class JobContract {
    interface View extends BaseView<Presenter>

    {
        void showJobs(List<JobItem> jobItemList);
        void showAssignment(AssignmentItem item);
        void onScanResultPushed();
        void onPrintedSticker();
        void showMessage(String message);
        void showProgressDialog( Boolean isInProgress);
        void showOnErrorOnEmpty();
    }


    interface Presenter extends BasePresenter

    {
        void getAssignment(String assignmentId);
        void getJobs(String assignmentId);
        void scanToOrder(String assignmentId,String inventoryId);
        void printSticker(String appointmentId);
    }
}
