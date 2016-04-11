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
public class FifthQuestionFragment
        extends QuestionFragment {

    //region Fields

    public static final String ID = "FifthQuestionFragment";
    private static final String TAG = "FifthQuestionFragment";

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


    //endregion Fields

    //region Methods

    public static FifthQuestionFragment newInstance() {
        Bundle args = new Bundle();

        FifthQuestionFragment fragment = new FifthQuestionFragment();

        fragment.setArguments(args);
        return fragment;
    }

    //endregion Methods

    //region Fragment methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_5_question, container, false);
        ButterKnife.bind(this, view);

        mUiTvAnswerA.setOnClickListener(answerListener);
        mUiTvAnswerB.setOnClickListener(answerListener);
        mUiTvAnswerC.setOnClickListener(answerListener);
        mUiTvAnswerD.setOnClickListener(answerListener);
        if (wasNotified){
            pronounceSelectedAnswers(isASelected, mUiTvAnswerA);
            pronounceSelectedAnswers(isBSelected, mUiTvAnswerB);
            pronounceSelectedAnswers(isCSelected, mUiTvAnswerC);
            pronounceSelectedAnswers(isDSelected, mUiTvAnswerD);
            mUiTvQuestion.setAlpha(1);
            reanimateAnswers(mUiTvAnswerA);
            reanimateAnswers(mUiTvAnswerB);
            reanimateAnswers(mUiTvAnswerC);
            reanimateAnswers(mUiTvAnswerD);
        }


        return view;
    }

    private int transitionTime = 500;
    private boolean isASelected;
    private boolean isBSelected;
    private boolean isCSelected;
    private boolean isDSelected;

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

        return answer;
    }

    //endregion Fragment methods

    //region Question Methods

    @Override
    public void resetFragment() {
        super.resetFragment();
        isASelected = false;
        isBSelected = false;
        isCSelected = false;
        isDSelected = false;
        mUiTvQuestion.setAlpha(0);
    }

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
        return viewList;
    }

    @Override
    public int getPosition() {
        return 5;
    }

    //endregion Question Methods

}
