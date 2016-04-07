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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.kittest.app.AnimatedColor;
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

public class MainActivity
        extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, QuestionFragment.FragmentQuizFinishedResponder {

    //region Fields

    private static final String TAG = "MainActivity";
    private int currentState = 1;
    AnimatedColor animatedColor;

    @Bind(R.id.container)
    public ViewPager mUiViewPager;

    @Bind(R.id.pbQuizz)
    public ProgressBar mUiProgressBar;

    @Bind(R.id.rl_mainQuizz)
    public RelativeLayout relativeLayout;

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

        mUiProgressBar.setMax((pagerAdapter.getCount()-1) * 100);

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

    //region Events, Listeners

    public void startQuestClicked(View view) {
        mUiViewPager.setCurrentItem(1);
    }

    public void onFinishQuizClicked(View view) {
        mUiViewPager.setCurrentItem(10, true);
    }

    //endregion Events, Listeners

    //region OnPageChangeListener methods

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Log.d(TAG, "onPageScrolled() called with: " + "position = [" + position + "], positionOffset = [" + positionOffset + "], positionOffsetPixels = [" + positionOffsetPixels + "]");

        if (position > 0){
            int progressBarPosition = (position + 1) * 100 + (int) (positionOffset * 100);
            mUiProgressBar.setProgress(progressBarPosition);
        }

        if (position == 9) {
            //  continuously change the bg color
            relativeLayout.setBackgroundColor(animatedColor.with(positionOffset));
        }

        if (position == currentState){
            if (positionOffset > 0.05){
                mUiViewPager.setCurrentItem(position, true);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected() called with: " + "position = [" + position + "]");
        QuestionFragment questionFragment = (QuestionFragment) pagerAdapter.getItem(position);
        questionFragment.notifyAboutEntering();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //  empty by design
    }

    //endregion OnPageChangeListener methods

    //region FragmentQuizFinishedResponder methods

    boolean wasFirstCorrect, wasSecondCorrect, wasThirdCorrect, wasFourthCorrect;

    @Override
    public void finished(String fragmentId, String answer) {
        Log.d(TAG, "finished: fragmentId: " + fragmentId);
        Log.d(TAG, "finished: answer: " + answer);

        boolean booleanAnswer = Boolean.parseBoolean(answer);

        switch (fragmentId) {
            case FirstQuestionFragment.ID: {
                wasFirstCorrect = booleanAnswer;
                if (currentState >= 2)
                    return;
                currentState = 2;
                break;
            }
            case SecondQuestionFragment.ID: {
                wasSecondCorrect = booleanAnswer;
                if (currentState >= 3)
                    return;
                currentState = 3;
                break;
            }
            case ThirdQuestionFragment.ID: {
                wasThirdCorrect = booleanAnswer;
                if (currentState >= 4)
                    return;
                currentState = 4;
                break;
            }
            case FourthQuestionFragment.ID: {
                wasThirdCorrect = booleanAnswer;
                if (currentState >= 5)
                    return;
                currentState = 5;
                break;
            }
            case FifthQuestionFragment.ID: {
                wasFourthCorrect = booleanAnswer;
                if (currentState >= 6)
                    return;
                currentState = 6;
                break;
            }
            case SixthQuestionFragment.ID: {
                wasFourthCorrect = booleanAnswer;
                if (currentState >= 7)
                    return;
                currentState = 7;
                break;
            }
            case SeventhQuestionFragment.ID: {
                wasFourthCorrect = booleanAnswer;
                if (currentState >= 8)
                    return;
                currentState = 8;
                break;
            }
            case EighthQuestionFragment.ID: {
                wasFourthCorrect = booleanAnswer;
                if (currentState >= 9)
                    return;
                currentState = 9;
                break;
            }
            case NinthQuestionFragment.ID: {
                wasFourthCorrect = booleanAnswer;
                if (currentState >= 10)
                    return;
                currentState = 10;
                break;
            }

            default: {
                Log.d(TAG, "finished: defaulted. Should not. What the hell.");
            }
        }
    }

    public interface StateFragmentNotifier {
        void notifyFragment();
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
