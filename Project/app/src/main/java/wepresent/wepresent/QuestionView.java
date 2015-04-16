package wepresent.wepresent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Map;

import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;
import wepresent.wepresent.mappers.QuestionsMapper;
import wepresent.wepresent.mappers.UpVoteMapper;


public class QuestionView extends Fragment implements AsyncTaskReport {

    private LinearLayout linLayout;
    private QuestionsMapper questionsMapper;
    private UpVoteMapper upvoteMapper;
    private int sessionId, userId;
    private ArrayList<Map<String, String>> questions;
    private Button button;
    private int[] upvotes;
    private int sesID, useID;
    private boolean refresh = true;
    SharedPreferences sharedpreferences;

    // We get the ListView component from the layout
    ////ListView lv;// = (ListView) getView().findViewById(R.id.questionList);


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        questions = new ArrayList<Map<String, String>>();
        return inflater.inflate(R.layout.activity_question_view2, container, false);
    }

    public void done(Mapper.MapperSort mapper) {
        if (questionsMapper.isQuestionsSuccesful()) {
            questions = new ArrayList<Map<String, String>>();
            if (questionsMapper.getQuestions() != null) {
                questions = questionsMapper.getQuestions();
            }
            upvotes = questionsMapper.getUpvotes();
            if (refresh == true) {
                refresh = false;
                //TODO This function has to be called to draw the entire question list.
                displayQuestions();
            }
        } else {
            if (sessionId>0) {
                Toast.makeText(getActivity().getApplicationContext(), "Questions not available for this session", Toast.LENGTH_LONG).show();
            }
        }
        button = (Button) getView().findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to the slideView activity
                Intent intent = new Intent(getActivity(), addQuestionActivity.class);
                intent.putExtra("UserId", userId);
                intent.putExtra("SessionId", sessionId);
                startActivity(intent);
            }
        });
    }

    public void onResume(){
        sharedpreferences = this.getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);
        refresh = true;
        super.onResume();
            sessionId = sharedpreferences.getInt("SessionID", 0);
            userId = sharedpreferences.getInt("UserID", 0);

            questionsMapper = new QuestionsMapper(this);
            questionsMapper.start(sessionId, userId);
    }

    public void upVote(Integer questionId) {
        userId = sharedpreferences.getInt("UserID", 0);

        upvoteMapper = new UpVoteMapper(this);
        upvoteMapper.start(userId, questionId);
    }

    private void displayQuestions() {
        linLayout = (LinearLayout) getView().findViewById(R.id.linearLayout);
        linLayout.removeAllViews();
        if (questions.size()>0){
            for (final Map<String, String> question : questions ) {

                RelativeLayout rv = new RelativeLayout(getView().getContext());
                linLayout.addView(rv);

                final ToggleButton tb = new ToggleButton(getView().getContext());
                rv.addView(tb);
                tb.setText(question.get("upvotes"));
                final int questionID = Integer.parseInt(question.get("QuestionID"));
                tb.setTextOff(question.get("upvotes"));
                tb.setTextOn(Integer.toString(Integer.parseInt(question.get("upvotes")) + 1));
                for (int i = 0; i < upvotes.length; i++) {
                    if (questionID == upvotes[i]) {
                        tb.setTextOn(question.get("upvotes"));
                        tb.setTextOff(Integer.toString(Integer.parseInt(question.get("upvotes")) - 1));
                        tb.setChecked(true);
                        break;
                    }
                }
                tb.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        upVote(questionID);
                    }
                });
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tb.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_END);
                tb.setLayoutParams(params);
                TextView tv = new TextView(getView().getContext());
                tv.setText(question.get("Title"));
                rv.addView(tv);
                params = (RelativeLayout.LayoutParams) tv.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_START);
                params.addRule(RelativeLayout.START_OF, tb.getId());
                params.addRule(RelativeLayout.CENTER_IN_PARENT, 2);
                tv.setLayoutParams(params);
                rv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setBackgroundColor(Color.GRAY);
                        Intent intent = new Intent(getActivity(), SingleQuestionView.class);
                        intent.putExtra("SessionID", sessionId);
                        intent.putExtra("QuestionID", questionID);
                        intent.putExtra("UserID", userId);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    public static Bundle createBundle(String s) {
        Bundle bundle = new Bundle();
        //bundle.putString( EXTRA_TITLE, title );
        return bundle;
    }

}
