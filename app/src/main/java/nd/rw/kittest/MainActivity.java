package nd.rw.kittest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.kittest.app.AnimatedColor;
import nd.rw.kittest.app.Answer;
import nd.rw.kittest.app.AnswerBundle;
import nd.rw.kittest.app.AnswerService;
import nd.rw.kittest.app.fragment.EighthQuestionFragment;
import nd.rw.kittest.app.fragment.FifthQuestionFragment;
import nd.rw.kittest.app.fragment.FirstQuestionFragment;
import nd.rw.kittest.app.fragment.FourthQuestionFragment;
import nd.rw.kittest.app.fragment.GreetingFragment;
import nd.rw.kittest.app.fragment.NinthQuestionFragment;
import nd.rw.kittest.app.fragment.QuestionFragment;
import nd.rw.kittest.app.fragment.SecondQuestionFragment;
import nd.rw.kittest.app.fragment.SeventhQuestionFragment;
import nd.rw.kittest.app.fragment.SixthQuestionFragment;
import nd.rw.kittest.app.fragment.SummingUpFragment;
import nd.rw.kittest.app.fragment.ThirdQuestionFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity
        extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, QuestionFragment.FragmentQuizFinishedResponder {

    //region Fields

    private static final String TAG = "MainActivity";
    private int fragmentPositionPagerCantGoBeyond = 1;
    AnimatedColor animatedColor;

    @Bind(R.id.container)
    public ViewPager mUiViewPager;

    @Bind(R.id.pbQuizz)
    public ProgressBar mUiProgressBar;

    @Bind(R.id.btn_reset)
    public Button mUiBtnReset;

    @Bind(R.id.rl_mainQuizz)
    public RelativeLayout mUiRlMainQuiz;

    public SectionsPagerAdapter pagerAdapter;

    private GreetingFragment greetingFragment;
    private FirstQuestionFragment firstQuestionFragment;
    private SecondQuestionFragment secondQuestionFragment;
    private ThirdQuestionFragment thirdQuestionFragment;
    private FourthQuestionFragment fourthQuestionFragment;
    private FifthQuestionFragment fifthQuestionFragment;
    private SixthQuestionFragment sixthQuestionFragment;
    private SeventhQuestionFragment seventhQuestionFragment;
    private EighthQuestionFragment eighthQuestionFragment;
    private NinthQuestionFragment ninthQuestionFragment;
    private SummingUpFragment summingUpFragment;

    private AnswerBundle answerBundle;
    private AnswerService answerService;

    public static final String localServerAddress = "http://192.168.1.110:3000";
    public static final String remoteServerAddress = "http://46.101.112.125:3000";

    //endregion Fields

    //region AppCompatActivity Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mUiViewPager.setAdapter(pagerAdapter);
        mUiViewPager.addOnPageChangeListener(this);

        mUiProgressBar.setMax((pagerAdapter.getCount() - 1) * 100);

        greetingFragment = GreetingFragment.newInstance();
        firstQuestionFragment = FirstQuestionFragment.newInstance();
        secondQuestionFragment = SecondQuestionFragment.newInstance();
        thirdQuestionFragment = ThirdQuestionFragment.newInstance();
        fourthQuestionFragment = FourthQuestionFragment.newInstance();
        fifthQuestionFragment = FifthQuestionFragment.newInstance();
        sixthQuestionFragment = SixthQuestionFragment.newInstance();
        seventhQuestionFragment = SeventhQuestionFragment.newInstance();
        eighthQuestionFragment = EighthQuestionFragment.newInstance();
        ninthQuestionFragment = NinthQuestionFragment.newInstance();
        summingUpFragment = SummingUpFragment.newInstance();

        answerBundle = new AnswerBundle();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteServerAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        answerService = retrofit.create(AnswerService.class);
        animatedColor = new AnimatedColor(Color.parseColor("#84BD00"), Color.WHITE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion AppCompatActivity Methods

    //region Public Methods

    //endregion Public Methods

    //region Private Methods

    private void reset(){
        mUiBtnReset.setVisibility(View.INVISIBLE);
        //  navigate to greeting fragment
        mUiViewPager.setCurrentItem(0, false);
        //  reset fragments
        firstQuestionFragment.resetFragment();
        secondQuestionFragment.resetFragment();
        thirdQuestionFragment.resetFragment();
        fourthQuestionFragment.resetFragment();
        fifthQuestionFragment.resetFragment();
        sixthQuestionFragment.resetFragment();
        seventhQuestionFragment.resetFragment();
        eighthQuestionFragment.resetFragment();
        ninthQuestionFragment.resetFragment();
        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mUiViewPager.setAdapter(pagerAdapter);
        //  reset bundle
        answerBundle.reset();

        fragmentPositionPagerCantGoBeyond = 1;
    }

    private void putAnswerBundle(){
        Call<AnswerBundle> call = answerService.putAnswerBundle(answerBundle);
        call.enqueue(new Callback<AnswerBundle>() {
            @Override
            public void onResponse(Call<AnswerBundle> call, Response<AnswerBundle> response) {
                Log.d(TAG, "onResponse: response: " + response.message());
            }

            @Override
            public void onFailure(Call<AnswerBundle> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    //endregion Private Methods

    //region Events, Listeners

    public void startQuestClicked(View view) {
        mUiViewPager.setCurrentItem(1);
    }

    public void onFinishQuizClicked(View view) {
        mUiViewPager.setCurrentItem(10, true);
    }

    public void onResetClicked(View view) {
        reset();
    }

    //endregion Events, Listeners

    //region OnPageChangeListener methods

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Log.d(TAG, "onPageScrolled() called with: " + "position = [" + position + "], positionOffset = [" + positionOffset + "], positionOffsetPixels = [" + positionOffsetPixels + "]");

        int progressBarPosition = (position + 1) * 100 + (int) (positionOffset * 100);
        mUiProgressBar.setProgress(progressBarPosition);

        if (position == fragmentPositionPagerCantGoBeyond){
            if (positionOffset > 0.05){
                mUiViewPager.setCurrentItem(position, true);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected() called with: " + "position = [" + position + "]");
        QuestionFragment questionFragment = (QuestionFragment) pagerAdapter.getItem(position);
        questionFragment.prepareForEntering();
        //  on navigating to summing up page we should fire up the retrofit and post the result
        //  TODO iterate over fragments and put them in mode which prevents entering answers if we're on last page?
        if (position == 10){
            this.mUiBtnReset.setVisibility(View.VISIBLE);
            this.putAnswerBundle();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //  empty by design
    }

    //endregion OnPageChangeListener methods

    //region FragmentQuizFinishedResponder methods

    private void setFragmentPositionPagerCantGoBeyond(int fragmentState){
        if(fragmentPositionPagerCantGoBeyond > fragmentState)
            return;
        fragmentPositionPagerCantGoBeyond = fragmentState+1;
    }

    @Override
    public void finished(int fragmentPositionInPager, Answer answer) {
        Log.d(TAG, "finished: fragmentPositionInPager: " + fragmentPositionInPager);
        Log.d(TAG, "finished: answer id : " + answer.answerNumber + ", answer answer: " + answer.answers);
        Log.d(TAG, "finished: answerBundle size: " + answerBundle.answers.length);
        setFragmentPositionPagerCantGoBeyond(fragmentPositionInPager);
        answerBundle.answers[(fragmentPositionInPager - 1)] =  answer;
    }

    //endregion FragmentQuizFinishedResponder methods

    //region SectionsPagerAdapter class

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return greetingFragment;
                case 1:
                    return firstQuestionFragment;
                case 2:
                    return secondQuestionFragment;
                case 3:
                    return thirdQuestionFragment;
                case 4:
                    return fourthQuestionFragment;
                case 5:
                    return fifthQuestionFragment;
                case 6:
                    return sixthQuestionFragment;
                case 7:
                    return seventhQuestionFragment;
                case 8:
                    return eighthQuestionFragment;
                case 9:
                    return ninthQuestionFragment;
                case 10:
                    return summingUpFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 11;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }

    //endregion SectionsPagerAdapter class

}
