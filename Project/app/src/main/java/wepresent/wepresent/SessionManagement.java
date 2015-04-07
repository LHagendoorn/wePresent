package wepresent.wepresent;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.PresentationMapper;
import wepresent.wepresent.mappers.QuestionSetMapper;
import wepresent.wepresent.mappers.UpdateMapper;


public class SessionManagement extends ActionBarActivity implements AsyncTaskReport {
    private PresentationMapper presMap;
    private QuestionSetMapper quesMap;
    private UpdateMapper updMap;
    private int userID;
    private int sessionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = getIntent();
        userID = in.getIntExtra("UserID", 0);
        sessionID = in.getIntExtra("sessionID", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_management);

        presMap = new PresentationMapper(this);
        quesMap = new QuestionSetMapper(this);
        updMap = new UpdateMapper(this);
        presMap.start(userID);
        quesMap.start(userID);
    }


    public void done(Mapper.MapperSort mapper) {
        String[] presentations, questionSets;
        if(presMap.isPresSuccesful()){
            presentations = presMap.getPresentationNames();
        } else {
            presentations = new String[]{"No sessions available"};
        }
        if(quesMap.isQuesSuccesful()){
            questionSets = quesMap.getQuestionNames();
        } else {
            questionSets = new String[]{"No questionsets available"};
        }
        if(presMap.isPresSuccesful() && quesMap.isQuesSuccesful() && !mapper.equals(updMap.getMapperSort())) {
            Spinner listPres = (Spinner) findViewById(R.id.sSpinner);
            Spinner listQues = (Spinner) findViewById(R.id.qSpinner);
            ArrayAdapter<String> presAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, presentations);
            ArrayAdapter<String> quesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, questionSets);
            presAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            quesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listPres.setAdapter(presAdapter);
            listQues.setAdapter(quesAdapter);
        }
        if(mapper.equals(updMap.getMapperSort())){
            Toast.makeText(getApplicationContext(), "Session updated", Toast.LENGTH_LONG).show();
        }
    }

    public void update(){
        int quesID, presID;
        Spinner listPres = (Spinner) findViewById(R.id.sSpinner);
        Spinner listQues = (Spinner) findViewById(R.id.qSpinner);
        quesID = quesMap.getQuestionIds()[Arrays.asList(quesMap.getQuestionNames()).indexOf(listQues.getSelectedItem())];
        presID = presMap.getPresentationIds()[Arrays.asList(presMap.getPresentationNames()).indexOf(listPres.getSelectedItem())];
        updMap.start(sessionID, presID, quesID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_session_management, menu);
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
}
