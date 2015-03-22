package wepresent.wepresent;

import android.app.Activity;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends Activity implements AsyncTaskReport {

    private MainMapper loginMapper;
    private EditText input_username;
    private EditText input_password;
    private String androidId;
    private Button loginButton;

    // Google Cloud Messaging
    private GCMClientManager pushClientManager;
    String PROJECT_NUMBER = "422250236441";

    String uniqueDeviceId;
    // End Google Cloud Messaging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Google Cloud Messaging
        pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
           @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
               Toast.makeText(MainActivity.this, registrationId, Toast.LENGTH_SHORT).show();
               uniqueDeviceId = registrationId;
           }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });
        // End Google Cloud Messaging

        input_username = (EditText) findViewById(R.id.userName);
        input_password = (EditText) findViewById(R.id.passWord);
        androidId = getUniquePsuedoID();

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
            loginMapper.start(input_username.getText().toString(), input_password.getText().toString(), "");
        }
    }

    public void done(Mapper.MapperSort mapper) {
        if(loginMapper.isLoginsuccesful()) {
            Toast.makeText(getApplicationContext(), "Correct login data send", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cannot login", Toast.LENGTH_LONG).show();
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
    }

    /**
     * Return pseudo unique ID
     * @return ID
     */
    public static String getUniquePsuedoID() {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // String needs to be initialized
            serial = "serial"; // some value
        }

        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }


}
