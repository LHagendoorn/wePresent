package wepresent.wepresent;

import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.SingleSlideMapper;

public class SlideViewActivity extends ActionBarActivity implements AsyncTaskReport {

    private LinearLayout linLayout;
    private ImageView imageView;
    private TextView textView;
    private SingleSlideMapper slideMapper;
    private int value = 0;
    private int sessionId, slideId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_view);

        // Set title
        setTitle("Slide notes");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the extras
        Bundle intentInfo = getIntent().getExtras();
        sessionId = intentInfo.getInt("SessionID");
        slideId = intentInfo.getInt("SlideID");

        // Start the mapper
        slideMapper = new SingleSlideMapper(this);
        slideMapper.start(sessionId, slideId);
    }

    public void done(Mapper.MapperSort mapper) {

        if(slideMapper.isSlideSuccesful()){
            // Get the slide info
            String slideUrl = slideMapper.getSlideUrl();
            String slideNotes = slideMapper.getSlideNotes();

            // Create the image loader
            ImageLoader.ImageCache imageCache = new BitmapLruCache();
            ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(this), imageCache);

            NetworkImageView image = (NetworkImageView) findViewById(R.id.slideViewImage);
            image.setImageUrl(slideUrl, imageLoader);

            //System.out.println(slideNotes);
            if (slideNotes == null || slideNotes.equals("null") || slideNotes.equals(null)){
                slideNotes = "";
            }

            TextView text = (TextView) findViewById(R.id.slideViewNotes);
            text.setText(slideNotes);
        } else {
            System.out.println("shitsbrokenlol");
        }
    }

    @Override
      public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
