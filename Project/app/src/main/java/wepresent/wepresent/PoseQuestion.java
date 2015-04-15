package wepresent.wepresent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.PoseQuestionMapper;
import wepresent.wepresent.mappers.PoseQuizQuestion;
import wepresent.wepresent.mappers.SessionMapper;


public class PoseQuestion extends ActionBarActivity implements AsyncTaskReport {
    private PoseQuizQuestion questionMapper;
    private PoseQuestionMapper poseQuestionMapper;
    private int sessionId;
    private int selectedSession;
    private boolean loggedIn;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pose_question);

        // Set title
        setTitle("Pose quiz question");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the shared preferences
        sharedpreferences = getSharedPreferences("appData", Context.MODE_PRIVATE);

        // Get the current session ID
        sessionId = sharedpreferences.getInt("SessionID", 0);

        questionMapper = new PoseQuizQuestion(this);
        questionMapper.start( sessionId );
    }

    public void done(Mapper.MapperSort mapper) {
        String[] sessions;
        if(questionMapper.isGetSuccessful()){
            sessions = questionMapper.getSessionNames();
        } else {
            sessions = new String[]{"No sessions available"};
        }

        ListView listSession = (ListView) findViewById(R.id.questionsList);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sessions);
        listSession.setAdapter(itemsAdapter);
        listSession.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSession = questionMapper.getSessionIds()[position];
                System.out.println("sessionID: " + selectedSession);
            }});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_session, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void poseQuestion(View view) {
        Intent intent = new Intent(this, LauncherHubThing.class);
        if(selectedSession == 0){
            Toast.makeText(getApplicationContext(), "Please select a question", Toast.LENGTH_LONG).show();
        } else {
            poseQuestionMapper = new PoseQuestionMapper( this );
            poseQuestionMapper.start(selectedSession, sessionId);
            Toast.makeText(getApplicationContext(), "Question posed", Toast.LENGTH_LONG).show();
        }
    }

}
