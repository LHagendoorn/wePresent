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
import wepresent.wepresent.mappers.SingleQuestionMapper;
import wepresent.wepresent.mappers.SingleSlideMapper;

public class SingleQuestionView extends ActionBarActivity implements AsyncTaskReport {

    private LinearLayout linLayout;
    private ImageView imageView;
    private TextView titleView, questionView;
    private SingleQuestionMapper questionMapper;
    private int sessionId, questionId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_question);

        // Set title
        setTitle("Question description");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the extras
        Bundle intentInfo = getIntent().getExtras();
        sessionId = intentInfo.getInt("SessionID");
        questionId = intentInfo.getInt("QuestionID");
        userId = intentInfo.getInt("UserID");

        // Start the mapper
        questionMapper = new SingleQuestionMapper(this);
        questionMapper.start(sessionId, questionId, userId);
    }

    public void done(Mapper.MapperSort mapper) {

        if(questionMapper.isQuestionSuccesful()){
            // Get the slide info
            String questionTitle = questionMapper.getQuestionTitle();
            String questionDesc = questionMapper.getQuestionDesc();
            String questionURL = questionMapper.getQuestionURL();

            // Create the image loader
            ImageLoader.ImageCache imageCache = new BitmapLruCache();
            ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(this), imageCache);

            NetworkImageView image = (NetworkImageView) findViewById(R.id.questionImage);
            image.setImageUrl(questionURL, imageLoader);

            titleView = (TextView) findViewById(R.id.titleText);
            titleView.setText(questionTitle);

            questionView = (TextView) findViewById(R.id.questionText);
            questionView.setText(questionDesc);
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
