package wepresent.wepresent;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.SlidesMapper;

public class SlidesActivity extends Fragment implements AsyncTaskReport {

    private LinearLayout linLayout;
    private SlidesMapper slidesMapper;
    private Integer sessionId;
    private ArrayList<Map<String, String>> slides;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_slides);

        // TODO: Get session ID from previous class
        sessionId = 1;

        slidesMapper = new SlidesMapper(this);
        slidesMapper.start(sessionId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_slides, container, false);
    }

    public void done(Mapper.MapperSort mapper) {
        if(slidesMapper.isSlidesSuccesful()) {
            slides = slidesMapper.getSlides();
            displaySlides();
            Toast.makeText(getActivity().getApplicationContext(), "Slides retrieved", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Slides not available for this session", Toast.LENGTH_LONG).show();
        }
    }

    private void displaySlides() {
        //TODO Misschien niet het geheugen van de telefoon gebruiken om de slides te cachen, but then again; I DON'T CARE
        // Get where the images should go
        linLayout = (LinearLayout) getView().findViewById(R.id.linearLayout);

        //TODO Zorg ervoor dat de hele breedte van het scherm gebruikt wordt, wellicht ook nog een padding?
        WindowManager wm = (WindowManager) getView().getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = (int) (width * 0.5625);
        LayoutParams lpView = new LayoutParams(width, height);

        // For each slide
        for ( Map<String, String> slide : slides ) {
            //Create the image loader
            ImageLoader.ImageCache imageCache = new BitmapLruCache();
            ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(getView().getContext()), imageCache);

            //Set the image
            NetworkImageView image = new NetworkImageView(getView().getContext());
            image.setImageUrl(slide.get("SlideURL"), imageLoader);
            image.setFitsSystemWindows(true);
            image.setId(Integer.parseInt(slide.get("id")));

            // Add it to the view
            linLayout.addView(image, lpView);

            //TODO start een fragment ipv een activity, zodat je tabladen blijven
            // Add a listener
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Go to the slideView activity
                    Intent intent = new Intent(getActivity(), SlideView.class);
                    intent.putExtra("SlideID", v.getId());
                    startActivity(intent);
                }
            });
        }
    }

    public static Bundle createBundle(String s) {
        Bundle bundle = new Bundle();
        //bundle.putString( EXTRA_TITLE, title );
        return bundle;
    }
}
