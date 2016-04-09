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

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.kittest.R;
import nd.rw.kittest.app.Answer;

/**
 * Created by andrew on 25.03.2016.
 */
public class SecondQuestionFragment extends QuestionFragment{

    //region Fields

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

    //endregion Fields

    //region Fragment Methods

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

        mUiTvAnswerA.setOnClickListener(answerListener);
        mUiTvAnswerB.setOnClickListener(answerListener);
        mUiTvAnswerC.setOnClickListener(answerListener);

        if (wasNotified) {
            mUiTvQuestion.setAlpha(1);
            TextView tvToPronounce = null;
            switch(currentlySelectedAnswerNumber){
                case 1:
                    tvToPronounce = mUiTvAnswerA;
                    break;
                case 2:
                    tvToPronounce = mUiTvAnswerB;
                    break;
                case 3:
                    tvToPronounce = mUiTvAnswerC;
                    break;
            }
            pronounceSelectedAnswers(true, tvToPronounce);
            reanimateAnswers(mUiTvAnswerA);
            reanimateAnswers(mUiTvAnswerB);
            reanimateAnswers(mUiTvAnswerC);
            previouslySelectedAnwer = tvToPronounce;
        }
        return view;
    }

    //endregion Fragment Methods

    private void pronounceSelectedAnswers(boolean wasSelected, TextView answer){
        if (answer == null) {
            Log.e(TAG, "pronounceSelectedAnswers: answer was null");

            return;
        }
        TransitionDrawable transition = (TransitionDrawable) answer.getBackground();
        if (wasSelected){
            transition.reverseTransition(0);
            answer.setTextColor(Color.WHITE);
        } else {
            transition.startTransition(0);
            answer.setTextColor(Color.BLACK);
        }
    }

    private void reanimateAnswers(TextView textView){
        textView.setAlpha(1);
        textView.setScaleX(1);
        textView.setScaleY(1);
    }

    private int currentlySelectedAnswerNumber;
    private View previouslySelectedAnwer;

    private int transitionTime = 500;

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
            String answer;
            if (v == mUiTvAnswerA) {
                answer = "a";
                currentlySelectedAnswerNumber = 1;
            } else if (v == mUiTvAnswerB) {
                answer = "b";
                currentlySelectedAnswerNumber = 2;
            } else if (v == mUiTvAnswerC) {
                answer = "c";
                currentlySelectedAnswerNumber = 3;
            } else {
                answer = "ERROR";
            }

            responder.finished(getPosition(), new Answer(getPosition(), answer));

            previouslySelectedAnwer = v;
        }
    };

    //region Question Methods

    @Override
    public View getQuestionView() {
        return mUiTvQuestion;
    }

    @Override
    public List<View> getViewsForAnimation() {
        List<View> viewList = new LinkedList<>();
        viewList.add(mUiTvAnswerA);
        viewList.add(mUiTvAnswerB);
        viewList.add(mUiTvAnswerC);
        return viewList;
    }

    @Override
    public int getPosition() {
        return 2;
    }

    //endregion Question Methods
}
