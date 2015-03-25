package wepresent.wepresent;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class QuizView extends Activity {

    String Pressed = "";

    boolean MCQuestion = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MCQuestion) {
            setContentView(R.layout.activity_quiz_view);
        }
        else {
            setContentView(R.layout.activity_open_quiz_view);
        }
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_view, menu);
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

    public void pressed(View view){
        Button buttonA = (Button) findViewById(R.id.optionA);
        Button buttonB = (Button) findViewById(R.id.optionB);
        Button buttonC = (Button) findViewById(R.id.optionC);
        if (view.getId() == R.id.optionA){
            buttonA.setBackgroundColor(Color.parseColor("#9FF781"));
            buttonB.setBackgroundColor(Color.parseColor("#ff0f8c07"));
            buttonC.setBackgroundColor(Color.parseColor("#ff0f8c07"));
            Pressed = "A";
        }
        if (view.getId() == R.id.optionB) {
            buttonA.setBackgroundColor(Color.parseColor("#ff0f8c07"));
            buttonB.setBackgroundColor(Color.parseColor("#9FF781"));
            buttonC.setBackgroundColor(Color.parseColor("#ff0f8c07"));
            Pressed = "B";
        }
        if (view.getId() == R.id.optionC){
            buttonA.setBackgroundColor(Color.parseColor("#ff0f8c07"));
            buttonB.setBackgroundColor(Color.parseColor("#ff0f8c07"));
            buttonC.setBackgroundColor(Color.parseColor("#9FF781"));
            Pressed = "C";
        }
    }

    public void submit(View view) {
        Button buttonOK = (Button) findViewById(R.id.optionD);
        Toast toast = Toast.makeText(getApplicationContext(), Pressed, Toast.LENGTH_SHORT);
        toast.show();
    }
}
