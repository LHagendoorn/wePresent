package wepresent.wepresent;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import wepresent.wepresent.mappers.AddQuestionMapper;
import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.RegisterMapper;

public class addQuestionActivity extends ActionBarActivity implements AsyncTaskReport {

    private String errorMessage;
    private ImageButton imageButton;
    private Button addquestionButton;
    private Integer userId, sessionId = null;

    private Uri currImageURI;

    private AddQuestionMapper addQuestionMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        setTitle("Add new question");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageButton = (ImageButton) findViewById(R.id.addQuestionImage);
        addquestionButton = (Button) findViewById(R.id.button);

        Bundle intentInfo = getIntent().getExtras();
        userId = intentInfo.getInt("UserId");
        sessionId = intentInfo.getInt("SessionId");
        System.out.println(userId);

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image to add"), 1);
            }
        });

        addquestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestion(v);
            }
        });
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageButton.setBackground(null);
                currImageURI = data.getData();
                imageButton.setImageURI(currImageURI);
            }
        }
    }

    /**
     * Moves message to other text unit
     */
    public void addQuestion(View view){
        TextView title = (TextView) findViewById(R.id.title);
        TextView question = (TextView) findViewById(R.id.question);
        ImageButton image = (ImageButton) findViewById(R.id.addQuestionImage);
        String yourQuestion = question.getText().toString();
        String yourTitle = title.getText().toString();
        String encodedImage = null;
        if (yourQuestion.equals("")){
            showErrorMessage("Please fill in a question");
        } else {

            if (currImageURI != null) {
                Bitmap bm = ((BitmapDrawable)imageButton.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            }

            addQuestionMapper = new AddQuestionMapper(this);
            addQuestionMapper.start(yourTitle, yourQuestion, userId, sessionId, encodedImage);
        }
    }

    public void showErrorMessage(String errorMessage) {
        TextView message = (TextView) findViewById(R.id.addQuestionError);
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    public void done(Mapper.MapperSort mapper) {
        if(addQuestionMapper.isAddedSuccesful()) {
            System.out.println("New question made");
            onBackPressed();
        } else {
                    this.errorMessage = "Unknown error";

            // Show the error message
            showErrorMessage(this.errorMessage);
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
