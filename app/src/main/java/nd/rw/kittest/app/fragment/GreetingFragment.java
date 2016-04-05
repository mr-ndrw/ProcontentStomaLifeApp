package nd.rw.kittest.app.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.kittest.MainActivity;
import nd.rw.kittest.R;

/**
 * Created by andrew on 25.03.2016.
 */
public class GreetingFragment extends Fragment implements MainActivity.StateFragmentNotifier {

    private static final String TAG = "GreetingFragment";
    private GreetingFragmentStart fragmentStart;

    //@Bind(R.id.iv_stomalife_logo)
    public ImageView mUiIvStomaLifeLogo;

    @Bind(R.id.tv_greeting_Text)
    public TextView mUiTvGreeting;

    @Bind(R.id.btn_startQuizz)
    public Button mUiBtnStartQuiz;

    @Bind(R.id.imageSwitcher1)
    public ImageSwitcher imageSwitcher;

    private int imagesId [] = {R.drawable.gory, R.drawable.kobieta_roza, R.drawable.rsz_stoma_pilka};


    @Override
    public void notifyFragment() {
//        animateLogo();
//        animateText();
//        animateButton();
    }

    private void animateLogo(){
        mUiIvStomaLifeLogo.animate()
                .alpha(1f)
//                .scaleX(1f)
//                .scaleY(1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(100)
                .setDuration(400)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) { }

                    @Override
                    public void onAnimationEnd(Animator animation) { }

                    @Override
                    public void onAnimationCancel(Animator animation) { }

                    @Override
                    public void onAnimationRepeat(Animator animation) { }
                })
                .start();
    }

    private void animateText(){
        mUiTvGreeting.animate()
                .alpha(1f)
//                .scaleX(1f)
//                .scaleY(1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(100)
                .setDuration(400)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) { }

                    @Override
                    public void onAnimationEnd(Animator animation) { }

                    @Override
                    public void onAnimationCancel(Animator animation) { }

                    @Override
                    public void onAnimationRepeat(Animator animation) { }
                })
                .start();
    }

    private void animateButton(){
        mUiBtnStartQuiz.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(1000)
                .setDuration(400)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) { }

                    @Override
                    public void onAnimationEnd(Animator animation) { }

                    @Override
                    public void onAnimationCancel(Animator animation) { }

                    @Override
                    public void onAnimationRepeat(Animator animation) { }
                })
                .start();
    }

    public interface GreetingFragmentStart{
        void start();
    }

    public static GreetingFragment newInstance() {

        Bundle args = new Bundle();

        GreetingFragment fragment = new GreetingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != null) {
//            fragmentStart = (GreetingFragmentStart) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_greetings, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView: " + (R.drawable.gory));

        Animation inAnimation = new AnimationUtils().loadAnimation(getContext(), android.R.anim.fade_in);
        Animation outAnimation = new AnimationUtils().loadAnimation(getContext(), android.R.anim.fade_out);

        imageSwitcher.setInAnimation(inAnimation);
        imageSwitcher.setOutAnimation(outAnimation);

        if (imageSwitcher != null) {
            imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    ImageView iv = new ImageView(getContext());
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    iv.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.MATCH_PARENT,ImageSwitcher.LayoutParams.MATCH_PARENT));
                    return iv;
                }
            });
            imageSwitcher.setImageResource(R.drawable.gory);
            imageSwitcher.postDelayed(new Runnable() {

                int i = 0;
                int size = 3;
                @Override
                public void run() {
                    i++;
                    if (i == size){
                        i = 0;
                    }
                    imageSwitcher.setImageResource(imagesId[i]);
                    imageSwitcher.postDelayed(this, 6000);
                }
            }, 6000);
        }
        return view;
    }
}
