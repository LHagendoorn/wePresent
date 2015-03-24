package wepresent.wepresent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

public class SlideViewActivity extends MaterialNavigationDrawer {

    private LinearLayout linLayout;
    private ImageView imageView;
    private TextView textView;
    private int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_view);

        linLayout = (LinearLayout) findViewById(R.id.slideViewLayout); // Layout containing stuff

        Bundle extras = getIntent().getExtras(); // Obtain passed variable
        if (extras != null) {
            value = extras.getInt("SlideID");
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = (int) (width * 0.5625);

        imageView = (ImageView) findViewById(R.id.slideViewImage); // TODO obtain proper image and notes for selected slide
        LayoutParams lp = imageView.getLayoutParams();
        lp.width = width;
        lp.height = height;
        imageView.requestLayout(); // Scale to 16:9 format based on size of screen

        textView = (TextView) findViewById(R.id.slideViewNotes);
        textView.setText("Notes of slides #" + Integer.toString(value));
    }

    @Override
    public void init(Bundle bundle) {

    }
}
