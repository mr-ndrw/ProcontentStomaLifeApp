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
import nd.rw.kittest.app.fragment.FirstQuestionFragment;
import nd.rw.kittest.app.fragment.FourthQuestionFragment;
import nd.rw.kittest.app.fragment.GreetingFragment;
import nd.rw.kittest.app.fragment.QuestionFragment;
import nd.rw.kittest.app.fragment.SecondQuestionFragment;
import nd.rw.kittest.app.fragment.SummingUpFragment;
import nd.rw.kittest.app.fragment.ThirdQuestionFragment;
import nd.rw.kittest.app.model.FirstQuestionModel;

public class MainActivity
        extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, QuestionFragment.FragmentQuizFinishedResponder {

    public void startQuestClicked(View view) {
        mUiViewPager.setCurrentItem(1);
    }

    public void onFinishQuizClicked(View view) {
        mUiViewPager.setCurrentItem(6, true);
    }

    public interface StateFragmentNotifier{
        void notifyFragment();
    }

    //region State enum

    private enum State{
        Greeting, FirstQuestion, SecondQuestion, ThirdQuestion, FourthQuestion, SummingUp
    }

    //endregion State enum

    //region Fields

    private static final int MIN_STATE = 0;
    public static final int MAX_STATE = 5;
    private static final String TAG = "MainActivity";
    private int currentState = 1;


    @Bind(R.id.container)
    public ViewPager mUiViewPager;

    @Bind(R.id.pbQuizz)
    public ProgressBar mUiProgressBar;

    @Bind(R.id.rl_mainQuizz)
    public RelativeLayout relativeLayout;

    public SectionsPagerAdapter pagerAdapter;

    private GreetingFragment greetingsFragment;
    private FirstQuestionFragment firstQuestionFragment;
    private SecondQuestionFragment secondQuestionFragment;
    private ThirdQuestionFragment thirdQuestionFragment;
    private FourthQuestionFragment fourthQuestionFragment;
    private SummingUpFragment summingUpFragment;

    private FirstQuestionModel firstModel, secondModel, thirdModel, fourthModel;

    //endregion Fields

    //region AppCompatActivity Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ButterKnife.bind(this);

        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mUiViewPager.setAdapter(pagerAdapter);
        mUiViewPager.addOnPageChangeListener(this);
        firstModel = secondModel = thirdModel = fourthModel = new FirstQuestionModel();
        firstModel.selectedAnswers = 1;

        greetingsFragment = GreetingFragment.newInstance();
        firstQuestionFragment = FirstQuestionFragment.newInstance(firstModel);
        secondQuestionFragment= SecondQuestionFragment.newInstance();
        thirdQuestionFragment= ThirdQuestionFragment.newInstance();
        fourthQuestionFragment = FourthQuestionFragment.newInstance();
        summingUpFragment = SummingUpFragment.newInstance();

        mUiProgressBar.setMax(500);

        animatedColor = new AnimatedColor(Color.parseColor("#84BD00"), Color.WHITE);
    }

    AnimatedColor animatedColor;

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

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        super.onStart();

        greetingsFragment.notifyFragment();
    }

    //endregion AppCompatActivity Methods

    //region Private Methods

    //endregion Private Methods

    //region OnPageChangeListener methods


    final float[] from = new float[3], to =   new float[3];
    float[] hsv = new float[3];

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled() called with: " + "position = [" + position + "], positionOffset = [" + positionOffset + "], positionOffsetPixels = [" + positionOffsetPixels + "]");

        int progressBarPositon = position * 100 + (int)(positionOffset * 100);
        mUiProgressBar.setProgress(progressBarPositon);


        if(position == 4){


            relativeLayout.setBackgroundColor(animatedColor.with(positionOffset));
        }


//        if (position == currentState){
//            if (positionOffset > 0.05){
//                mUiViewPager.setCurrentItem(position, true);
//            }
//        }
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected() called with: " + "position = [" + position + "]");
        switch(position){
            //// TODO: 30.03.2016 shorten this shit, abstract notifyFragment method into QuestionFragment
            case 1: {
                ((FirstQuestionFragment)pagerAdapter.getItem(1)).notifyFragment();
                break;
            }
            case 2: {
                ((SecondQuestionFragment)pagerAdapter.getItem(2)).notifyFragment();
                break;
            }
            case 3: {
                ((ThirdQuestionFragment)pagerAdapter.getItem(3)).notifyFragment();
                break;
            }
            case 4: {
                ((FourthQuestionFragment)pagerAdapter.getItem(4)).notifyFragment();
                break;
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    //endregion OnPageChangeListener methods

    //region FragmentQuizFinishedResponder methods

    @Override
    public void finished(String fragmentId, String answer) {
        Log.d(TAG, "finished: fragmentId: " + fragmentId);
        Log.d(TAG, "finished: answer: " + answer);
        switch(fragmentId){
            case FirstQuestionFragment.ID:{
                firstQuestionFragment.notifyFragment();
                currentState = 2;
                break;
            }
            case SecondQuestionFragment.ID:{
                secondQuestionFragment.notifyFragment();
                currentState = 3;
                break;
            }
            case ThirdQuestionFragment.ID:{
                thirdQuestionFragment.notifyFragment();
                currentState = 4;
                break;
            }
            case FourthQuestionFragment.ID:{
                fourthQuestionFragment.notifyFragment();
                currentState = 5;
                break;
            }
            default:{
                Log.d(TAG, "finished: defaulted. Should not. What the hell.");
            }
        }
    }


    //endregion FragmentQuizFinishedResponder methods

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return GreetingFragment.newInstance();
                case 1:
                    return MainActivity.this.firstQuestionFragment;
                case 2:
                    return MainActivity.this.secondQuestionFragment;
                case 3:
                    return MainActivity.this.thirdQuestionFragment;
                case 4:
                    return MainActivity.this.fourthQuestionFragment;
                case 5:
                    return SummingUpFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

}
