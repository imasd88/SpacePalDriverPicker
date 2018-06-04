package java_version.job;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.spacepal.internal.app.R;
import com.spacepal.internal.app.model.response.JobItem;

import java.util.List;

import java_version.util.Util;

/**
 * Created by sidhu on 6/3/2018.
 */

public class JobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<JobItem> jobsList;
    private Context context;


    public JobAdapter(List<JobItem> jobs, Context context) {
        this.jobsList = jobs;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_view_job, parent, false);
            return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

            JobItem job =  jobsList.get(position);

            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tvTitle.setText(job.getProductTitle());
            viewHolder.tvRef.setText("Ref: "+ Util.getReference(job.getAssignmentId()));
            viewHolder.cbJob.setChecked(job.getInventoryId()!=null?true:false);

    }


    @Override
    public int getItemCount() {
        return  jobsList.size();

    }

    public JobItem getItem(int position) {
        return jobsList.get(position);
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);

    }

    public void updateItems(List<JobItem> jobs) {
        jobsList.clear();
        jobsList.addAll(jobs);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
         TextView tvRef;
        CheckBox cbJob;

        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.tvTitle = itemView.findViewById(R.id.tvJobTitle);
            tvRef =  itemView.findViewById(R.id.tvJobReference);
            cbJob = itemView.findViewById(R.id.cbJob);
        }
    }


}
