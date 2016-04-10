package nd.rw.kittest.app.fragment;

import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import nd.rw.kittest.R;
import nd.rw.kittest.app.Answer;

public abstract class QuestionFragment extends Fragment {

    //region Fields

    private static final String TAG = "QuestionFragment";

    protected boolean wasNotified;

    public interface FragmentQuizFinishedResponder{
        void finished(int fragmentId, Answer answer);
    }
    protected FragmentQuizFinishedResponder responder;

    //endregion Fields

    //region Fragment methods

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != null && activity instanceof FragmentQuizFinishedResponder) {
            responder = (FragmentQuizFinishedResponder) getActivity();
        }
    }

    //endregion Fragment methods

    //region Methods

    // TODO: 08.04.2016 Rename button names for variables and methods... These are not buttons per se
    //  but rather TextViews which resemble buttons.
    public final void prepareForEntering(){
        Log.d(TAG, "prepareForEntering: wasNotified: " + wasNotified);
        if (wasNotified)
            return;
        wasNotified = true;
        View questionView = this.getQuestionView();
        List<View> buttonsToAnimate = this.getViewsForAnimation();

        animateQuestionView(questionView);
        animateButtons(buttonsToAnimate);
    }

    private static void animateQuestionView(View questionView){
        if (questionView == null) {
            Log.i(TAG, "animateQuestionView: questionView was null");
            return;
        }
        questionView.animate()
                .alpha(1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(1000)
                .start();
    }

    private static void animateButtons(List<View> buttons){
        if (buttons == null) {
            Log.i(TAG, "animateButtons: buttons list was null");
            return;
        }
        int delay = 600;
        int delayValue = 350;
        int duration = 500;
        for (View button :
                buttons) {
            animateSingleButton(button, delay, duration);
            delay += delayValue;
        }
    }

    private static void animateSingleButton(View button, int delay, int duration){
        button.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setStartDelay(delay)
                .setDuration(duration)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();
    }

    protected void pronounceSelectedAnswers(boolean wasSelected, TextView answer){
        if (answer == null) {
            Log.e(TAG, "pronounceSelectedAnswers: answer was null");

            return;
        }
        TransitionDrawable transition = (TransitionDrawable) answer.getBackground();
        if (wasSelected){
            transition.startTransition(0);
            answer.setTextColor(Color.WHITE);
        }
    }

    protected void reanimateAnswers(TextView textView){
        textView.setAlpha(1);
        textView.setScaleX(1);
        textView.setScaleY(1);
    }

    public abstract View getQuestionView();
    public abstract List<View> getViewsForAnimation();
    public abstract int getPosition();

    //region Methods

}
