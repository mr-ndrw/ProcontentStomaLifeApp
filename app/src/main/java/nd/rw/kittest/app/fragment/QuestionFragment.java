package nd.rw.kittest.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import nd.rw.kittest.R;
import nd.rw.kittest.app.Answer;

public abstract class QuestionFragment extends Fragment {

    private static final String TAG = "QuestionFragment";
    protected FragmentQuizFinishedResponder responder;

    public interface FragmentQuizFinishedResponder{
        void finished(int fragmentId, Answer answer);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            responder = (FragmentQuizFinishedResponder) getActivity();
        }
    }

    public abstract void notifyAboutEntering();

    public abstract int getPosition();

}
