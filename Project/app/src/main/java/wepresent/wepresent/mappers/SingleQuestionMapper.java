package wepresent.wepresent.mappers;

import android.app.Activity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 16-Mar-15.
 */
public class SingleQuestionMapper extends Mapper {
    private String questionTitle, questionDesc, questionURL;
    private Integer errorCode, sessionId, questionId, userId;
    private boolean questionSuccesful;

    public SingleQuestionMapper(Activity activity) {
        super(activity);
    }

    public void start(Integer sessionId, Integer questionId, Integer userId) {
        setSessionId(sessionId);
        setQuestionId(questionId);
        setUserId(userId);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("QuestionID", getQuestionId().toString()));
        nameValuePairs.add(new BasicNameValuePair("SessionID", getSessionId().toString()));
        nameValuePairs.add(new BasicNameValuePair("UserID", getUserId().toString()));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/getQuestion";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject slideObject = new JSONObject(result);
            setQuestionSuccesful(slideObject.getBoolean("Successful"));

            if ( isQuestionSuccesful() ) {
                setQuestionTitle(slideObject.getString("Title"));
                setQuestionDesc(slideObject.getString("Question"));
                setQuestionURL(slideObject.getString("PhotoURL"));
            } else {
                setErrorCode(slideObject.getInt("Errorcode"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.SINGLEQUESTIONMAPPER;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getQuestionTitle() { return questionTitle; }

    public void setQuestionTitle(String questionTitle) { this.questionTitle = questionTitle; }

    public String getQuestionDesc() { return questionDesc; }

    public void setQuestionDesc(String notes) { this.questionDesc = notes; }

    public String getQuestionURL() {return questionURL; }

    public void setQuestionURL(String questionRUL) { this.questionURL = questionRUL; }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isQuestionSuccesful() {
        return questionSuccesful;
    }

    public void setQuestionSuccesful(boolean questionSuccesful) {
        this.questionSuccesful = questionSuccesful;
    }

    public int getErrorCode() { return this.errorCode; }

    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }
}