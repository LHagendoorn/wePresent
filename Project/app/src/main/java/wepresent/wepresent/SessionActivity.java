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
import android.widget.Toast;

import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.SessionMapper;


public class SessionActivity extends ActionBarActivity implements AsyncTaskReport {
    private SessionMapper sessionMapper;
    private String uniqueDeviceId;
    private int selectedSession;
    private boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent in = getIntent();
        uniqueDeviceId = in.getStringExtra("AndroidID");
        loggedIn = in.getBooleanExtra("LoggedIn", false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        // Set title
        setTitle("Switch Session");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionMapper = new SessionMapper(this);
        sessionMapper.start();
    }

    public void done(Mapper.MapperSort mapper) {
        String[] sessions;
        if(sessionMapper.isGetSuccessful()){
            sessions = sessionMapper.getSessionNames();
        } else {
            sessions = new String[]{"No sessions available"};
        }

        ListView listSession = (ListView) findViewById(R.id.sessionList);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sessions);
        listSession.setAdapter(itemsAdapter);
        listSession.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSession = sessionMapper.getSessionIds()[position];
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void gotoSlides(View view) {
        Intent intent = new Intent(this, LauncherHubThing.class);
        if(selectedSession == 0){
            Toast.makeText(getApplicationContext(), "Please select a session", Toast.LENGTH_LONG).show();
        } else {
            intent.putExtra("SessionID", selectedSession);
            intent.putExtra("Tab", "slides");
            intent.putExtra("AndroidID", uniqueDeviceId);
            intent.putExtra("LoggedIn", loggedIn);
            System.out.println("SessionID = " + intent.getIntExtra("SessionID", 0));
            startActivity(intent);
        }
    }
}
