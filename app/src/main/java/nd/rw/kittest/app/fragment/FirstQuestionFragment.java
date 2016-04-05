package nd.rw.kittest.app.fragment;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.kittest.MainActivity;
import nd.rw.kittest.R;
import nd.rw.kittest.app.model.FirstQuestionModel;

/**
 * Created by andrew on 25.03.2016.
 */
public class FirstQuestionFragment
        extends QuestionFragment
        implements RadioGroup.OnCheckedChangeListener, MainActivity.StateFragmentNotifier {

    public static final String ID = "FirstFragment";
    private static final String TAG = "FirstFragment";
    private FirstQuestionModel model;
    private Button mUiBackButton, mUiNextButton;


    @Bind(R.id.tv_question)
    public TextView mUiTvQuestion;

    @Bind(R.id.tv_a)
    public TextView mUiTvAnswerA;
    @Bind(R.id.tv_b)
    public TextView mUiTvAnswerB;
    @Bind(R.id.tv_c)
    public TextView mUiTvAnswerC;


    public boolean isASelected, isBSelected, isCSelected;

    public boolean wasNotified = false;

    //region Methods

    public static FirstQuestionFragment newInstance(FirstQuestionModel model) {

        Bundle args = new Bundle();

        FirstQuestionFragment fragment = new FirstQuestionFragment();
        if (model != null) {
            args.putInt("selectedAnswer", model.selectedAnswers);
        }

        fragment.setArguments(args);
        return fragment;
    }

    //endregion Methods

    //region RadioGroup.OnCheckedChangeListener

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.d(TAG, "onCheckedChanged: checkedId: " + checkedId);
        responder.finished(ID, getAnswer(checkedId));
    }

    //endregion RadioGroup.OnCheckedChangeListener

    //region Fragment methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_first_question, container, false);
        ButterKnife.bind(this, view);

        mUiTvAnswerA.setOnClickListener(answerListener);
        mUiTvAnswerB.setOnClickListener(answerListener);
        mUiTvAnswerC.setOnClickListener(answerListener);

        //mUiRbAnswers.setOnCheckedChangeListener(this);
        return view;
    }

    private View previouslySelectedAnwer;

    private int correctAnswer = 1;

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
            previouslySelectedAnwer = v;
        }
    };

    @Override
    public void notifyFragment() {
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

    //endregion Fragment methods

}
