package wepresent.wepresent;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.SlidesMapper;

public class SlidesActivity extends Activity implements AsyncTaskReport {

    private LinearLayout linLayout;
    private SlidesMapper slidesMapper;
    private Integer sessionId;
    private ArrayList<Pair> slides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slides);

        // TODO: Get session ID from previous class
        sessionId = 1;

        slidesMapper = new SlidesMapper(this);
        slidesMapper.start(sessionId);
    }

    public void done(Mapper.MapperSort mapper) {
        if(slidesMapper.isSlidesSuccesful()) {
            slides = slidesMapper.getSlides();
            Toast.makeText(getApplicationContext(), "Slides retrieved", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Slides not available for this session", Toast.LENGTH_LONG).show();
        }
    }

    private void displaySlides() {
        linLayout = (LinearLayout) findViewById(R.id.linearLayout); // Layout where buttons are inserted

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = (int) (width * 0.5625);

        LayoutParams lpView = new LayoutParams(width, height); // 16:9 format based on screen size


        for (int i = 0; i < 4; i++) { // TODO yet to be used on an image set. Also requires server communication
            ImageButton imageButton = new ImageButton(this);
            imageButton.setId(i);
            imageButton.setImageResource(R.drawable.test); // Insert image (simple 16:9 image for now)
            imageButton.setLayoutParams(lpView); // Adjust image button size
            imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE); // Scale of image in button
            linLayout.addView(imageButton, lpView);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // On click event, brings to slide view activity
                    Intent intent = new Intent(getApplicationContext(), SlideViewActivity.class);
                    System.out.println(v.getId());
                    intent.putExtra("SlideNumber",v.getId()); // Send extra variable
                    startActivity(intent);
                }
            });
        }
    }
}
