package wepresent.wepresent;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.SessionMapper;


public class SessionActivity extends ActionBarActivity {
    private SessionMapper sessionMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        sessionMapper = new SessionMapper(this);
        sessionMapper.start();
    }

    public void done(Mapper.MapperSort mapper) {
        String[] sessions;
        if(sessionMapper.isGetSuccessful()){
            sessions = sessionMapper.getSessionNames();
        } else {
            System.out.println("shitsbrokenlol");
            sessions = new String[]{"shit is kapot yo"};
        }

        ListView listSession = (ListView) findViewById(R.id.sessionList);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sessions);
        listSession.setAdapter(itemsAdapter);
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
}
