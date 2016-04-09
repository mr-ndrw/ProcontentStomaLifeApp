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
public class SeventhQuestionFragment
        extends QuestionFragment {

    //region Fields

    public static final String ID = "SeventhQuestionFragment";
    private static final String TAG = "SeventhQuestionFragment";

    @Bind(R.id.tv_question)
    public TextView mUiTvQuestion;

    @Bind(R.id.tv_a)
    public TextView mUiTvAnswerA;
    @Bind(R.id.tv_b)
    public TextView mUiTvAnswerB;
    @Bind(R.id.tv_c)
    public TextView mUiTvAnswerC;
    @Bind(R.id.tv_d)
    public TextView mUiTvAnswerD;
    @Bind(R.id.tv_e)
    public TextView mUiTvAnswerE;

    //endregion Fields

    //region Fragment methods

    public static SeventhQuestionFragment newInstance() {
        Bundle args = new Bundle();

        SeventhQuestionFragment fragment = new SeventhQuestionFragment();

        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_7_question, container, false);
        ButterKnife.bind(this, view);

        mUiTvAnswerA.setOnClickListener(answerListener);
        mUiTvAnswerB.setOnClickListener(answerListener);
        mUiTvAnswerC.setOnClickListener(answerListener);
        mUiTvAnswerD.setOnClickListener(answerListener);
        mUiTvAnswerE.setOnClickListener(answerListener);

        if (wasNotified){
            pronounceSelectedAnswers(isASelected, mUiTvAnswerA);
            pronounceSelectedAnswers(isBSelected, mUiTvAnswerB);
            pronounceSelectedAnswers(isCSelected, mUiTvAnswerC);
            pronounceSelectedAnswers(isDSelected, mUiTvAnswerD);
            pronounceSelectedAnswers(isESelected, mUiTvAnswerE);
            mUiTvQuestion.setAlpha(1);
            reanimateAnswers(mUiTvAnswerA);
            reanimateAnswers(mUiTvAnswerB);
            reanimateAnswers(mUiTvAnswerC);
            reanimateAnswers(mUiTvAnswerD);
            reanimateAnswers(mUiTvAnswerE);
        }
        return view;
    }

    private void pronounceSelectedAnswers(boolean wasSelected, TextView answer){
        TransitionDrawable transition = (TransitionDrawable) answer.getBackground();
        if (wasSelected) {
            transition.startTransition(0);
            answer.setTextColor(Color.WHITE);
        }
    }

    private void reanimateAnswers(TextView textView){
        textView.setAlpha(1);
        textView.setScaleX(1);
        textView.setScaleY(1);
    }

    private int transitionTime = 500;
    private boolean isASelected;
    private boolean isBSelected;
    private boolean isCSelected;
    private boolean isDSelected;
    private boolean isESelected;

    public View.OnClickListener answerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            TransitionDrawable transition = (TransitionDrawable) v.getBackground();
            if (tv.getCurrentTextColor() == Color.WHITE) {
                tv.setTextColor(Color.BLACK);
                transition.reverseTransition(transitionTime);
            } else {
                tv.setTextColor(Color.WHITE);
                transition.startTransition(transitionTime);
            }

            if (tv == mUiTvAnswerA) {
                isASelected = !isASelected;
            } else if (tv == mUiTvAnswerB) {
                isBSelected = !isBSelected;
            } else if (tv == mUiTvAnswerC) {
                isCSelected = !isCSelected;
            } else if (tv == mUiTvAnswerD) {
                isDSelected = !isDSelected;
            } else if (tv == mUiTvAnswerE) {
                isESelected = !isESelected;
            }
            String answer = buildAnswer();

            responder.finished(getPosition(), new Answer(getPosition(), answer));

        }
    };

    private String buildAnswer(){
        String answer = "";
        answer += isASelected ? "a" : "";
        answer += isBSelected ? "b" : "";
        answer += isCSelected ? "c" : "";
        answer += isDSelected ? "d" : "";
        answer += isESelected ? "e" : "";
        return answer;
    }
    //endregion Fragment methods

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
        viewList.add(mUiTvAnswerD);
        viewList.add(mUiTvAnswerE);
        return viewList;
    }

    @Override
    public int getPosition() {
        return 7;
    }

    //endregion Question Methods

}
