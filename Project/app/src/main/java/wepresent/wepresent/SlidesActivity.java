package wepresent.wepresent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;

import java.io.File;

public class SlidesActivity extends Activity {

    private LinearLayout linLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slides);

        linLayout = (LinearLayout) findViewById(R.id.linearLayout); // Layout where buttons are inserted

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = (int) (width * 0.5625);

        LayoutParams lpView = new LayoutParams(width, height); // 16:9 format based on screen size

        for (int i = 0; i < 4; i++) { // Need yet to be used on an image set. Also requires onclick() events
            ImageButton imageButton = new ImageButton(this);
            imageButton.setImageResource(R.drawable.blapp); // Insert image
            imageButton.setLayoutParams(lpView); // Adjust image button size
            imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE); // Scale of image in button
            linLayout.addView(imageButton, lpView);
        }
    }
}