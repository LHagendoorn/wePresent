package wepresent.wepresent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

public class SlideViewActivity extends Fragment {

    private LinearLayout linLayout;
    private ImageView imageView;
    private TextView textView;
    private int value = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_slide_view, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_slide_view);

        linLayout = (LinearLayout) getView().findViewById(R.id.slideViewLayout); // Layout containing stuff

        Bundle extras = getActivity().getIntent().getExtras(); // Obtain passed variable
        if (extras != null) {
            value = extras.getInt("SlideID");
        }

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = (int) (width * 0.5625);

        imageView = (ImageView) getView().findViewById(R.id.slideViewImage); // TODO obtain proper image and notes for selected slide
        LayoutParams lp = imageView.getLayoutParams();
        lp.width = width;
        lp.height = height;
        imageView.requestLayout(); // Scale to 16:9 format based on size of screen

        textView = (TextView) getView().findViewById(R.id.slideViewNotes);
        textView.setText("Notes of slides #" + Integer.toString(value));
    }

    
    public void init(Bundle bundle) {

    }
}
