package nd.rw.kittest.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
public class GreetingFragment extends QuestionFragment{

    private static final String TAG = "GreetingFragment";

    @Bind(R.id.tv_greeting_Text)
    public TextView mUiTvGreeting;

    @Bind(R.id.btn_startQuizz)
    public Button mUiBtnStartQuiz;

//    @Bind(R.id.imageSwitcher1)
    public ImageSwitcher imageSwitcher;

    private int imgSwitcherResources[] = {R.drawable.gory, R.drawable.kobieta_roza, R.drawable.rsz_stoma_pilka};

    @Override
    public void notifyAboutEntering() {

    }

    public static GreetingFragment newInstance() {

        Bundle args = new Bundle();

        GreetingFragment fragment = new GreetingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_greetings, container, false);
        ButterKnife.bind(this, view);
        //initializeImageSwitcher();
        return view;
    }

    private void initializeImageSwitcher(){
        Animation inAnimation = new AnimationUtils().loadAnimation(getContext(), android.R.anim.fade_in);
        Animation outAnimation = new AnimationUtils().loadAnimation(getContext(), android.R.anim.fade_out);

        imageSwitcher.setInAnimation(inAnimation);
        imageSwitcher.setOutAnimation(outAnimation);

        if (imageSwitcher == null) {
            return;
        }

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView iv = new ImageView(getContext());
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setLayoutParams(new ImageSwitcher.LayoutParams(
                                    ImageSwitcher.LayoutParams.MATCH_PARENT,
                                    ImageSwitcher.LayoutParams.MATCH_PARENT));
                return iv;
            }
        });
        imageSwitcher.setImageResource(R.drawable.gory);
        imageSwitcher.postDelayed(new Runnable() {
            int i = 0, size = imgSwitcherResources.length;
            @Override
            public void run() {
                i++;
                if (i == size){
                    i = 0;
                }
                imageSwitcher.setImageResource(imgSwitcherResources[i]);
                imageSwitcher.postDelayed(this, 6000);
            }
        }, 6000);
    }

}
