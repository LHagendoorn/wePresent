package wepresent.wepresent;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.QuestionsMapper;


public class QuestionView extends Fragment implements AsyncTaskReport {

    private LinearLayout linLayout;
    private QuestionsMapper questionsMapper;
    private int sessionId;
    private ArrayList<Map<String, String>> questions;

    // We get the ListView component from the layout
    ////ListView lv;// = (ListView) getView().findViewById(R.id.questionList);


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_question_view2, container, false);
    }

    public void done(Mapper.MapperSort mapper) {
        if (questionsMapper.isQuestionsSuccesful()) {
            questions = questionsMapper.getQuestions();
            displayQuestions();
            Toast.makeText(getActivity().getApplicationContext(), "Questions retrieved", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Questions not available for this session", Toast.LENGTH_LONG).show();
        }
    }

    public void onResume(){
        super.onResume();
        if(questionsMapper == null) {
            Bundle b = getArguments();
            sessionId = b.getInt("SessionID");


            questionsMapper = new QuestionsMapper(this);
            questionsMapper.start(sessionId);
        }
    }

    //TODO Add question button
    //TODO View Question Activity
    //TODO Additional functionalities like amount of votes

    private void displayQuestions() {
        linLayout = (LinearLayout) getView().findViewById(R.id.linearLayout);

        for ( Map<String, String> question : questions ) {

            RelativeLayout rv = new RelativeLayout(getView().getContext());
            linLayout.addView(rv);

            ToggleButton tb = new ToggleButton(getView().getContext());
            rv.addView(tb);
            //tb.setText(Integer.toString(i));
            //tb.setTextOff(Integer.toString(i));
            //tb.setTextOn(Integer.toString(i));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tb.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_END);
            tb.setLayoutParams(params);

            TextView tv = new TextView(getView().getContext());
            tv.setText(question.get("question"));
            rv.addView(tv);
            params = (RelativeLayout.LayoutParams) tv.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
            params.addRule(RelativeLayout.START_OF, tb.getId());
            params.addRule(RelativeLayout.CENTER_IN_PARENT, 2);
            tv.setLayoutParams(params);

        }
    }


}
