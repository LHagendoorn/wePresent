package wepresent.wepresent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// Google Cloud Messaging
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
// End Google Cloud Messaging

import java.util.UUID;

import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.MainMapper;
import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.SessionMapper;

public class MainActivity extends Activity implements AsyncTaskReport {
    //TODO Check for uniqueDeviceId, otherwise ABORT, ABORT!!!
    private MainMapper loginMapper;
    private SessionMapper sessionMapper;
    private EditText input_username;
    private EditText input_password;
    private Button loginButton;
    private String[] sessions;
    public int selectedSession;

    // Google Cloud Messaging
    private GCMClientManager pushClientManager;
    String PROJECT_NUMBER = "422250236441";

    String uniqueDeviceId;
    // End Google Cloud Messaging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_main);

        // Google Cloud Messaging
        pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                uniqueDeviceId = registrationId;
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });
        // End Google Cloud Messaging

        sessionMapper = new SessionMapper(this);
        sessionMapper.start();

        input_username = (EditText) findViewById(R.id.userName);
        input_password = (EditText) findViewById(R.id.passWord);

        loginButton = (Button) findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedLogin();
            }

        });
        }

    private void proceedLogin() {
        if(input_username.getText().toString().equals("") || input_password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Please enter a username and password", Toast.LENGTH_LONG).show();
        } else {
            loginMapper = new MainMapper(this);
            loginMapper.start(input_username.getText().toString(), input_password.getText().toString(), uniqueDeviceId);
        }
    }


    public void done(Mapper.MapperSort mapper) {
        if(!mapper.equals(sessionMapper.getMapperSort())) {
            if (loginMapper.isLoginsuccesful()) {
                Toast.makeText(getApplicationContext(), "Correct login data send", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Cannot login", Toast.LENGTH_LONG).show();
            }
        } else {
            if(sessionMapper.isGetSuccessful()){
                sessions = sessionMapper.getSessionNames();
            } else {
                System.out.println("shitsbrokenlol");
                sessions = new String[]{"shit is kapot yo"};
            }

            final ListView listSession = (ListView) findViewById(R.id.sessionList);
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sessions);
            listSession.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    TextView textView = (TextView) view.findViewById(R.id.list_content);
//                    String text = textView.getText().toString();
                    selectedSession = sessionMapper.getSessionIds()[position];
                    System.out.println("sessionID: " + selectedSession);
                }});
            listSession.setAdapter(itemsAdapter);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void gotoRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("AndroidID", uniqueDeviceId);
        startActivity(intent);
    }

    public void gotoSlides(View view) {
        Intent intent = new Intent(this, LauncherHubThing.class);
        ListView listSession = (ListView) findViewById(R.id.sessionList);
        if(selectedSession == 0){
            Toast.makeText(getApplicationContext(), "Please select a session", Toast.LENGTH_LONG).show();
        } else {
            intent.putExtra("SessionID", selectedSession);
            intent.putExtra("Tab", "slides");
            intent.putExtra("AndroidID", uniqueDeviceId);
            System.out.println("SessionID = " + intent.getIntExtra("SessionID", 0));
            startActivity(intent);
        }
    }

}
