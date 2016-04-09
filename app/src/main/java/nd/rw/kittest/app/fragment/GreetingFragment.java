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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.kittest.MainActivity;
import nd.rw.kittest.R;

/**
 * Created by andrew on 25.03.2016.
 */
public class GreetingFragment extends QuestionFragment{

    private static final String TAG = "GreetingFragment";

    private int imgSwitcherResources[] = {R.drawable.gory, R.drawable.kobieta_roza, R.drawable.rsz_stoma_pilka};

    //region QuestionFragment Methods

    @Override
    public View getQuestionView() {
        return null;
    }

    @Override
    public List<View> getViewsForAnimation() {
        return null;
    }

    @Override
    public int getPosition() {
        return 0;
    }

    //endregion QuestionFragment Methods

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
        return view;
    }
}
