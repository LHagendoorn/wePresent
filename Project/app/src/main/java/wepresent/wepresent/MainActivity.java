package wepresent.wepresent;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.MainMapper;
import wepresent.wepresent.mappers.Mapper;

public class MainActivity extends Activity implements AsyncTaskReport {

    private MainMapper loginMapper;
    private EditText input_username;
    private EditText input_password;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        loginMapper = new MainMapper(this);
        loginMapper.start(input_username.getText().toString(), input_password.getText().toString(), "");
    }

    public void done(Mapper.MapperSort mapper) {
        if(loginMapper.isLoginsuccesful()) {
            Toast.makeText(getApplicationContext(), "Correcte login gegevens verstuurd", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Niet mogelijk om in te loggen", Toast.LENGTH_LONG).show();
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
        startActivity(intent);
    }}
