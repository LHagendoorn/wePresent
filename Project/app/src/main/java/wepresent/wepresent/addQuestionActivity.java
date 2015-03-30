package wepresent.wepresent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import wepresent.wepresent.mappers.AddQuestionMapper;
import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.RegisterMapper;

public class addQuestionActivity extends Activity implements AsyncTaskReport {

    private String errorMessage, androidId;
    private ImageButton imageButton;

    private AddQuestionMapper addQuestionMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        imageButton = (ImageButton) findViewById(R.id.addQuestionImage);

        // Get the unique device ID
        Bundle intentInfo = getIntent().getExtras();

        if(intentInfo.getString( "AndroidID" ) != null) {
            androidId = intentInfo.getString( "AndroidID" );
        } else {

        }

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image to add"), 1);
            }
        });
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageButton.setImageURI(data.getData());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Moves message to other text unit
     */
    public void addQuestion(View view){
        TextView question = (TextView) findViewById(R.id.question);
        ImageButton image = (ImageButton) findViewById(R.id.addQuestionImage);
        String yourQuestion = question.getText().toString();
        //TODO add image and allow it to be send
        if (yourQuestion.equals("")){
            showErrorMessage("Please fill in a question");
        } else {
            addQuestionMapper = new AddQuestionMapper(this);
            addQuestionMapper.start(yourQuestion);
        }
    }

    public void showErrorMessage(String errorMessage) {
        TextView message = (TextView) findViewById(R.id.addQuestionError);
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    public void done(Mapper.MapperSort mapper) {
        if(addQuestionMapper.isAddedSuccesful()) {
            //TODO go back to questions
        } else {
            // Check what error message occurs
            switch (addQuestionMapper.getErrorCode()) {
                case 1:
                    this.errorMessage = "This username is already in use";
                    break;
                case 2:
                    this.errorMessage = "This emailaddress is already in use";
                    break;
                case 3:
                    this.errorMessage = "There was an internal error, please try again later";
                    break;
                default:
                    this.errorMessage = "Unknown error";
                    break;
            }

            // Show the error message
            showErrorMessage(this.errorMessage);
        }
    }
}
