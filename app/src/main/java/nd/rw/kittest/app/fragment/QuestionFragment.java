package nd.rw.kittest.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import nd.rw.kittest.R;

public abstract class QuestionFragment extends Fragment {

    private static final String TAG = "QuestionFragment";
    protected FragmentQuizFinishedResponder responder;

    public interface FragmentQuizFinishedResponder{
        void finished(String fragmentId, String answer);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            responder = (FragmentQuizFinishedResponder) getActivity();
        }
    }

    protected String getAnswer(int uiId){
        switch(uiId){
            case 1:
                return "a";
            case 2:
                return "b";
            case 3:
                return "c";
            default:{
                return "e";
            }
        }
    }

    public abstract void notifyAboutEntering();

}
