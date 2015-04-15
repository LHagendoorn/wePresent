package wepresent.wepresent;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

// Google Cloud Messaging

// End Google Cloud Messaging

import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.JoinSessionMapper;
import wepresent.wepresent.mappers.LeaveSessionMapper;
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
    private int selectedSession;
    private boolean onStartUpLogin = true;
    private int UserID;
    private JoinSessionMapper joinSessionMapper;
    private LeaveSessionMapper leaveSessionMapper;
    SharedPreferences sharedpreferences;

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

        // Get the shared preferences
        sharedpreferences = getSharedPreferences("appData", Context.MODE_PRIVATE);

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

        System.out.println("Altijd leuk om te weten: " + uniqueDeviceId);

        loginMapper = new MainMapper(this);
        loginMapper.start(null,null,uniqueDeviceId);
        sessionMapper = new SessionMapper(this);
        sessionMapper.start();

        input_username = (EditText) findViewById(R.id.title);
        input_password = (EditText) findViewById(R.id.passWord);

        loginButton = (Button) findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedLogin();
            }

        });

        Intent in = getIntent();
        boolean leaved = in.getBooleanExtra("Leaved", false);
        if(leaved){
            leaveSessionMapper = new LeaveSessionMapper(this);
            leaveSessionMapper.start(UserID);
        }
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
        System.out.println(mapper);
        if(mapper.equals(loginMapper.getMapperSort())) {
            if (loginMapper.isLoginsuccesful() && !onStartUpLogin) {
                UserID = loginMapper.getUserID();

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("UserID", UserID);
                editor.putString("AndroidID", uniqueDeviceId);
                editor.putBoolean("LoggedIn", true);
                editor.putInt("SessionID", 0);
                editor.commit();
                //Intent out = new Intent(this, SessionActivity.class);
                //out.putExtra("LoggedIn", true);
                //out.putExtra("UserID", loginMapper.getUsername());
                Intent out = new Intent(this, LauncherHubThing.class);
                out.putExtra("Tab", "slides");
                startActivity(out);
            } else if (onStartUpLogin) {
                UserID = loginMapper.getUserID();
                System.out.println("Chris maakte veel lawaai en toen schrok ik en dit is mijn userID:" + loginMapper.getUserID());
                onStartUpLogin = false;
            } else {
                Toast.makeText(getApplicationContext(), "Cannot login", Toast.LENGTH_LONG).show();
            }
        } else if (mapper.equals(sessionMapper.getMapperSort())) {
            if(sessionMapper.isGetSuccessful()){
                sessions = sessionMapper.getSessionNames();
            } else {
                sessions = new String[]{"No Sessions available"};
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

        }// else if (mapper.equals(log)) {
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
        if(selectedSession == 0){
//            intent = new Intent(this, LauncherHubThing.class);
//            intent.putExtra("Tab", "quiz");
//            intent.putExtra("Question", "Dit is een vraag?");
//            intent.putExtra("Type", "open");
//            intent.putExtra("SessionID", "1");
//            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Please select a session", Toast.LENGTH_LONG).show();
        } else {
            joinSessionMapper = new JoinSessionMapper(this);
            joinSessionMapper.start(selectedSession, UserID);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("UserID", UserID);
            editor.putString("AndroidID", uniqueDeviceId);
            editor.putInt("SessionID", selectedSession);
            editor.putBoolean("LoggedIn", false);
            editor.commit();

            intent.putExtra("Tab", "slides");
            startActivity(intent);
        }
    }

}
