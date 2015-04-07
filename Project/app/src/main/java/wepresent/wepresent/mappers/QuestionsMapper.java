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
    private Integer sessionId, userId;
    private Integer errorCode;
    private ArrayList<Map<String, String>> questions;
    private int[] upvotes;
    private boolean questionsSuccesful;
    private JSONArray questionsRetrieved, upvotesRetrieved;

    public QuestionsMapper(Activity activity) {
        super(activity);
    }

    public QuestionsMapper(Fragment frag) {
        super(frag);
    }

    public void start(Integer sessionId, Integer userId) {
        setSessionId(sessionId);
        setUserId(userId);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        System.out.println("The session ID I'm sending: " + getSessionId().toString());
        nameValuePairs.add(new BasicNameValuePair("SessionID", getSessionId().toString()));
        nameValuePairs.add(new BasicNameValuePair("UserID", getUserId().toString()));

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
                upvotesRetrieved = questionsObject.getJSONArray("Upvotes");
                processQuestions();
                processUpvotes();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void processQuestions() {
        Integer tmpQuestionId, tmpQuestionUpvotes;
        String tmpQuestionTitle, tmpQuestionquestion;
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
                tmpQuestionquestion = tmpQuestion.getString("question");
                tmpQuestionId = tmpQuestion.getInt("QuestionID");
                tmpQuestionTitle = tmpQuestion.getString("Title");
                tmpQuestionUpvotes = tmpQuestion.getInt("upvotes");

                // Add it to the slideInfo array
                questionInfo.put("QuestionID", tmpQuestionId.toString());
                questionInfo.put("question", tmpQuestionquestion);
                questionInfo.put("upvotes", tmpQuestionUpvotes.toString());
                questionInfo.put("Title", tmpQuestionTitle);

                // Add it to the ArrayList<ID, SlideURL>
                questions.add(questionInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void processUpvotes() {
        Integer arrayLength = upvotesRetrieved.length();
        System.out.println("Length is: " + arrayLength);
        String tmpQuestion;
        upvotes = new int[arrayLength];

        for ( int i = 0; i < arrayLength; i++ ) {
            try {

                // Split it in parts
                tmpQuestion = upvotesRetrieved.getString(i);
                upvotes[i] = Integer.parseInt(tmpQuestion.toString());

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

    public void setUserId(Integer userID) {this.userId = userID; }

    public Integer getUserId() {return userId; }

    public boolean isQuestionsSuccesful() {
        return questionsSuccesful;
    }

    public void setQuestionsSuccesful(boolean questionsSuccesful) {
        this.questionsSuccesful = questionsSuccesful;
    }

    public ArrayList<Map<String, String>> getQuestions() {
        return questions;
    }

    public int[] getUpvotes() { return upvotes; }

}