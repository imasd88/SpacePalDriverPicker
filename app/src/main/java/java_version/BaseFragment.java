package java_version;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spacepal.internal.app.BaseActivity;
import com.spacepal.internal.app.Constant;

import butterknife.ButterKnife;

/**
 * Created by sidhu on 4/24/2018.
 */

public abstract class BaseFragment extends Fragment implements Constant {
    public BaseActivity mBaseActivity;
    public View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity)
            mBaseActivity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getID(), container, false);
        ButterKnife.bind(this,view);
        this.view = view;
        initUI(view);
        return view;
    }

    public abstract int getID();

    public abstract void initUI(View view);

    public View getView(){
        return this.view;
    }

    public void showProgress() {

        mBaseActivity.showProgress();
    }

    public void hideProgress() {
        mBaseActivity.hideProgress();
    }


    public void showAlert(String message, boolean alert) {
        try {
            mBaseActivity.showAlertDialog(message, alert,mBaseActivity);
        } catch (Exception e) {
            Log.e("Dialog Exception", e.getMessage());
        }
    }



}
