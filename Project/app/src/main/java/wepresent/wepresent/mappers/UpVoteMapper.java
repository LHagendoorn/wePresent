package wepresent.wepresent.mappers;

import android.app.Activity;
import android.support.v4.app.Fragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpVoteMapper extends Mapper {
    private Integer errorCode, userId, questionId;
    private boolean upvoteSuccesful, secondtime;

    public UpVoteMapper(Activity activity) {
        super(activity);
    }

    public UpVoteMapper(Fragment frag) {
        super(frag);
    }

    public void start(Integer userID, Integer questionID) {
        setUserID(userID);
        setQuestionID(questionID);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("UserID", Integer.toString(getUserID())));
        nameValuePairs.add(new BasicNameValuePair("QuestionID", Integer.toString(getQuestionID())));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/upvoteQuestion";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject upvoteObject = new JSONObject(result);
            setUpvoteSuccesful(upvoteObject.getBoolean("Successful"));

            if ( isUpvoteSuccesful() ) {
                setSecondTime(upvoteObject.getBoolean("secondTime"));
            } else {
                setErrorCode(upvoteObject.getInt("Errorcode"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.UPVOTEMAPPER;
    }

    public boolean isUpvoteSuccesful() {
        return upvoteSuccesful;
    }

    public void setUpvoteSuccesful(boolean upvoteSuccesful) {
        this.upvoteSuccesful = upvoteSuccesful;
    }

    public int getErrorCode() { return this.errorCode; }

    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }

    public int getUserID() { return this.userId; }

    public void setUserID(Integer userId) { this.userId = userId; }

    public int getQuestionID() { return this.questionId; }

    public void setQuestionID(Integer questionId) { this.questionId = questionId; }

    public void setSecondTime(Boolean secondTime) {this.secondtime = secondTime;}

    public boolean getSecondTime() {return this.secondtime;}
}