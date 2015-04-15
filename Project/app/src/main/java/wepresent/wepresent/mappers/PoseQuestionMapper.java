package wepresent.wepresent.mappers;

import android.app.Activity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zjosti on 23-3-2015.
 */
public class PoseQuestionMapper extends Mapper{
    private int questionId, sessionId;
    private boolean getSuccessful;

    public PoseQuestionMapper(Activity activity) {
        super(activity);
    }

    int userID;

    public void start(int questionId, int sessionId) {
        this.questionId = questionId;
        this.sessionId = sessionId;
        execute();
    }


    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("QuizQuestionID", Integer.toString(questionId)));
        nameValuePairs.add(new BasicNameValuePair("SessionID", Integer.toString(sessionId)));
        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/askQuizQuestion";
    }

    public boolean isGetSuccessful() {
        return getSuccessful;
    }

    public void setGetSuccessful(boolean getSuccessful) {
        this.getSuccessful = getSuccessful;
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject SessionObject = new JSONObject(result);
            setGetSuccessful(SessionObject.getBoolean("Successful"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.SESSIONMAPPER;
    }

}
