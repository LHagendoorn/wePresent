package wepresent.wepresent.mappers;

import android.app.Activity;
import android.support.v4.app.Fragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnswerQuiz extends Mapper {
    private Integer errorCode, userId, questionId;
    private String answer;
    private boolean upvoteSuccesful, secondtime;

    public AnswerQuiz(Activity activity) {
        super(activity);
    }

    public AnswerQuiz(Fragment frag) {
        super(frag);
    }

    public void start(Integer userID, Integer questionID, String answer) {
        setUserID(userID);
        setQuestionID(questionID);
        setAnswer(answer);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("UserID", Integer.toString(getUserID())));
        nameValuePairs.add(new BasicNameValuePair("QuizQuestionID", Integer.toString(getQuestionID())));
        nameValuePairs.add(new BasicNameValuePair("Answer", getAnswer()));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/answerQuizQuestion";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject upvoteObject = new JSONObject(result);
            setSuccesful(upvoteObject.getBoolean("Successful"));

            if ( !isSuccesful() ) {
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

    public boolean isSuccesful() {
        return upvoteSuccesful;
    }

    public void setSuccesful(boolean succesful) {
        this.upvoteSuccesful = succesful;
    }

    public int getErrorCode() { return this.errorCode; }

    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }

    public int getUserID() { return this.userId; }

    public void setUserID(Integer userId) { this.userId = userId; }

    public int getQuestionID() { return this.questionId; }

    public void setQuestionID(Integer questionId) { this.questionId = questionId; }

    public void setAnswer(String answer) {this.answer = answer;}

    public String getAnswer() {return this.answer;}
}