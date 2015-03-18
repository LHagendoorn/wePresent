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

public class SlideViewActivity extends Activity  {

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
            value = extras.getInt("SlideNumber");
        }

        imageView = (ImageView) findViewById(R.id.slideViewImage); // TODO obtain proper image and notes for selected slide

        textView = (TextView) findViewById(R.id.slideViewNotes);
        textView.setText("Notes of slides #" + Integer.toString(value));
    }
}
