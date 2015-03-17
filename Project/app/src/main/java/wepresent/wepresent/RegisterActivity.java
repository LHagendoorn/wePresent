package wepresent.wepresent;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends ActionBarActivity {

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
    public void moveMessage(View view){
        TextView message = (TextView) findViewById(R.id.errorMessage);
        TextView username = (TextView) findViewById(R.id.userName);
        TextView email = (TextView) findViewById(R.id.email);
        TextView password = (TextView) findViewById(R.id.passWord);
        TextView verification = (TextView) findViewById(R.id.verPass);
        String yourUsername = username.getText().toString();
        String yourEmail = email.getText().toString();
        String yourPassword = password.getText().toString();
        String verifyPassword = verification.getText().toString();

        if(!yourPassword.equals(verifyPassword)){
            message.setText("Passwords do not match");
        }
        /**
         * retrieve list with userNames []
         * for (int i = 0; i<userNames.length; i++){
         *  if yourUsername.equals(userNames[i]){
         *      message.setText("user name already in use!"
         *      }
         *      }
         *
         * if (false){
         * message.setText("wrong email address")
         * }
         */
    }
}
