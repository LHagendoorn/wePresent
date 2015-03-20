package wepresent.wepresent;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.RegisterMapper;

public class RegisterActivity extends Activity implements AsyncTaskReport {

    private String errorMessage;

    private RegisterMapper registerMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
    public void register(View view){
        TextView username = (TextView) findViewById(R.id.userName);
        TextView email = (TextView) findViewById(R.id.email);
        TextView password = (TextView) findViewById(R.id.passWord);
        TextView verification = (TextView) findViewById(R.id.verPass);
        String yourUsername = username.getText().toString();
        String yourEmail = email.getText().toString();
        String yourPassword = password.getText().toString();
        String verifyPassword = verification.getText().toString();
        String androidId = "test";

        if (yourUsername.equals("") || yourEmail.equals("") || yourPassword.equals("") || verifyPassword.equals("")){
            showErrorMessage("Please fill in all fields");
        }
        else if(!yourPassword.equals(verifyPassword)){
            showErrorMessage("Your passwords do not match");
        } else {
            registerMapper = new RegisterMapper(this);
            registerMapper.start(yourUsername, yourPassword, yourEmail, androidId);
        }
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

    public void showErrorMessage(String errorMessage) {
        TextView message = (TextView) findViewById(R.id.errorMessage);
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    public void done(Mapper.MapperSort mapper) {
        if(registerMapper.isRegisterSuccesful()) {
            // TODO: Redirect to hub
            int registeredID = registerMapper.getUserId();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            intent.putExtra("ID", registeredID);
        } else {
            // Check what error message occurs
            switch (registerMapper.getErrorCode()) {
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
