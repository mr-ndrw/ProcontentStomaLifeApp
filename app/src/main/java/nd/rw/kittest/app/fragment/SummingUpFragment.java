package nd.rw.kittest.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nd.rw.kittest.R;

/**
 * Created by andrew on 25.03.2016.
 */
public class SummingUpFragment extends QuestionFragment {

    public static SummingUpFragment newInstance() {

        Bundle args = new Bundle();

        SummingUpFragment fragment = new SummingUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_summing_up, container, false);

        return view;
    }

    @Override
    public void notifyAboutEntering() {

    }

    @Override
    public int getPosition() {
        return 10;
    }
}
