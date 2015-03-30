package wepresent.wepresent.mappers;

import android.app.Activity;
import android.support.v4.app.Fragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QuestionsMapper extends Mapper {
    private Integer sessionId;
    private Integer errorCode;
    private ArrayList<Map<String, String>> questions;
    private boolean questionsSuccesful;
    private JSONArray questionsRetrieved;

    public QuestionsMapper(Activity activity) {
        super(activity);
    }

    public QuestionsMapper(Fragment frag) {
        super(frag);
    }

    public void start(Integer sessionId) {
        setSessionId(sessionId);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("SessionID", getSessionId().toString()));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/getQuestions";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject questionsObject = new JSONObject(result);
            setQuestionsSuccesful(questionsObject.getBoolean("Successful"));

            if ( isQuestionsSuccesful() ) {
                // For each slide, add it to the slides
                questionsRetrieved = questionsObject.getJSONArray("Questions");
                processQuestions();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void processQuestions() {
        Integer tmpQuestionId;
        String tmpQuestionTitle;
        Integer arrayLength = questionsRetrieved.length();
        JSONObject tmpQuestion;
        questions = new ArrayList<>();

        for ( int i = 0; i < arrayLength; i++ ) {
            try {
                // Clear the slideInfo
                Map<String, String> questionInfo = new HashMap<>();

                // Split it in parts
                tmpQuestion = questionsRetrieved.getJSONObject(i);

                // Get the slide info
                tmpQuestionId = tmpQuestion.getInt("id");
                tmpQuestionTitle = tmpQuestion.getString("question");

                // Add it to the slideInfo array
                questionInfo.put("id", tmpQuestionId.toString());
                questionInfo.put("question", tmpQuestionTitle);

                // Add it to the ArrayList<ID, SlideURL>
                questions.add(questionInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.QUESTIONSMAPPER;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isQuestionsSuccesful() {
        return questionsSuccesful;
    }

    public void setQuestionsSuccesful(boolean questionsSuccesful) {
        this.questionsSuccesful = questionsSuccesful;
    }

    public ArrayList<Map<String, String>> getQuestions() {
        return questions;
    }

}