package wepresent.wepresent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import wepresent.wepresent.mappers.AddQuestionMapper;
import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;

public class addQuestionActivity extends ActionBarActivity implements AsyncTaskReport {

    private String errorMessage;
    private ImageButton imageButton;
    private Button addquestionButton;
    private Integer userId, sessionId = null;

    private static final int SELECT_PICTURE = 1;

    private Uri currImageURI;
    private boolean imageUsed = false;

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

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");

                Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                String pickTitle = "Select or take a new photo";
                Intent chooseIntent = Intent.createChooser(photoPickerIntent, pickTitle);
                chooseIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { photoIntent});

                startActivityForResult(chooseIntent, SELECT_PICTURE);
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
                System.out.println(data.getData());
                System.out.println(data.getExtras());
                if (data.getData() == null) {
                    imageButton.setBackground(null);
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imageButton.setImageBitmap(photo);
                    imageUsed = true;
                } else {
                    imageButton.setBackground(null);
                    currImageURI = data.getData();
                    imageButton.setImageURI(currImageURI);
                    imageUsed = true;
                }
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
        if (yourTitle.equals("") || yourQuestion.equals("")){
            showErrorMessage("Please fill in a title and question");
        } else {

            if (imageUsed == true) {
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
