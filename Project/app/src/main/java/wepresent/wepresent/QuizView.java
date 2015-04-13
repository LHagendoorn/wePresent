package wepresent.wepresent;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;


public class QuizView extends Fragment {

    String Pressed = "";
    private String question, button1, button2, button3;
    LayoutInflater saveItForLater;
    ViewGroup vasthouder;
    boolean MCQuestion = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int viewID;
        RelativeLayout layout;
        saveItForLater = inflater;
        vasthouder = container;
        // Check if there is a bundle attached - otherwise show image
        if (getArguments() == null) {
            // Show just the image view
            viewID = R.layout.activity_quiz_view_empty;
            layout = (RelativeLayout) inflater.inflate(viewID, container, false);
        } else {
            Bundle b = getArguments();
            question = b.getString("Question");
            button1 = b.getString("Button1");
            button2 = b.getString("Button2");
            button3 = b.getString("Button3");

            // Determine if it is a multiple choice question
            if (b.getString("Type").equals("multiplechoice")) {
                MCQuestion = true;
            }

            if (MCQuestion) {
                viewID = R.layout.activity_quiz_view;
            } else {
                viewID = R.layout.activity_open_quiz_view;
            }
            layout = (RelativeLayout) inflater.inflate(viewID, container, false);

            // Set the question text
            TextView questionView = (TextView) layout.findViewById(R.id.questionText);
            questionView.setText(question);

            if (MCQuestion) {
                Button optionA = (Button) layout.findViewById(R.id.optionA);
                optionA.setText(button1);
                optionA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pressed(v);
                    }
                });

                Button optionB = (Button) layout.findViewById(R.id.optionB);
                optionB.setText(button2);
                optionB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pressed(v);
                    }
                });

                Button optionC = (Button) layout.findViewById(R.id.optionC);
                optionC.setText(button3);
                optionC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pressed(v);
                    }
                });

                Button optionD = (Button) layout.findViewById(R.id.optionD);
                optionD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submit(v);
                    }
                });
            } else {
                final Button submitOpen = (Button) layout.findViewById(R.id.submitOpen);
                submitOpen.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        submit(v);
                    }
                });
            }
        }
        return layout;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_quiz_view, menu);
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
        Button buttonA = (Button) getView().findViewById(R.id.optionA);
        Button buttonB = (Button) getView().findViewById(R.id.optionB);
        Button buttonC = (Button) getView().findViewById(R.id.optionC);
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
        //Button buttonOK = (Button) getView().findViewById(R.id.optionD);
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), Pressed, Toast.LENGTH_SHORT);
        toast.show();

        ((ViewGroup) this.getView().getParent()).la;

        this.getLayoutInflater(new Bundle()).inflate(R.layout.activity_quiz_view_empty, (ViewGroup) this.getView());

    }
}
