package nd.rw.kittest.app.fragment;

import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.kittest.R;

/**
 * Created by andrew on 25.03.2016.
 */
public class SecondQuestionFragment extends QuestionFragment{

    public static final String ID = "SecondFragment";
    private static final String TAG = "SecondFragment";

    @Bind(R.id.tv_question)
    public TextView mUiTvQuestion;

    @Bind(R.id.tv_a)
    public TextView mUiTvAnswerA;
    @Bind(R.id.tv_b)
    public TextView mUiTvAnswerB;
    @Bind(R.id.tv_c)
    public TextView mUiTvAnswerC;

    private boolean wasNotified;

    public static SecondQuestionFragment newInstance() {

        Bundle args = new Bundle();

        SecondQuestionFragment fragment = new SecondQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_2_question, container, false);
        ButterKnife.bind(this, view);

        correctTvAnswer = mUiTvAnswerA;

        mUiTvAnswerA.setOnClickListener(answerListener);
        mUiTvAnswerB.setOnClickListener(answerListener);
        mUiTvAnswerC.setOnClickListener(answerListener);

        if (wasNotified){
            TransitionDrawable transition = (TransitionDrawable) correctTvAnswer.getBackground();
            transition.startTransition(0);
            correctTvAnswer.setTextColor(Color.WHITE);
            mUiTvQuestion.setAlpha(1);
            mUiTvAnswerA.setAlpha(1);
            mUiTvAnswerA.setScaleX(1);
            mUiTvAnswerA.setScaleY(1);
            mUiTvAnswerB.setAlpha(1);
            mUiTvAnswerB.setScaleX(1);
            mUiTvAnswerB.setScaleY(1);
            mUiTvAnswerC.setAlpha(1);
            mUiTvAnswerC.setScaleX(1);
            mUiTvAnswerC.setScaleY(1);
            previouslySelectedAnwer = correctTvAnswer;
        }

        return view;
    }

    private View previouslySelectedAnwer;

    private int transitionTime = 500;
    private TextView correctTvAnswer;
    public View.OnClickListener answerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == previouslySelectedAnwer)
                return;

            TransitionDrawable transition = (TransitionDrawable) v.getBackground();
            transition.startTransition(transitionTime);
            TextView tv = (TextView) v;
            tv.setTextColor(Color.WHITE);
            if (previouslySelectedAnwer != null) {
                transition = (TransitionDrawable) previouslySelectedAnwer.getBackground();
                transition.reverseTransition(transitionTime);
                tv = (TextView) previouslySelectedAnwer;
                tv.setTextColor(Color.BLACK);
            }
            previouslySelectedAnwer = v;

            if (v == correctTvAnswer){
                responder.finished(ID, "true");
            } else {
                responder.finished(ID, "false");
            }

            previouslySelectedAnwer = v;
        }
    };


    @Override
    public void notifyAboutEntering() {
        Log.d(TAG, "notifyAboutEntering: wasNotified?: " + wasNotified);
        if (!wasNotified){
            Log.d(TAG, "notifyFragment: animating");
            mUiTvQuestion.animate()
                    .alpha(1f)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setDuration(1000)
                    .start();
            mUiTvAnswerA.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(600)
                    .setDuration(500)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();
            mUiTvAnswerB.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(900)
                    .setDuration(500)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();
            mUiTvAnswerC.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(1200)
                    .setDuration(500)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();
            wasNotified = true;
        }
    }
}
