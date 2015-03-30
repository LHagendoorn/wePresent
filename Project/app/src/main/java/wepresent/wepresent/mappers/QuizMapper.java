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
public class QuizMapper extends Mapper {
    private String answer;
    private int errorCode, userId;
    private boolean quizSuccesful;

    public QuizMapper(Activity activity) {
        super(activity);
    }

    public void start(String answer, int userId) {
        setAnswer(answer);
        setUserId(userId);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("Answer", getAnswer()));
        nameValuePairs.add(new BasicNameValuePair("UserID", getUserId().toString()));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/sendQuizAnswer";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject quizObject = new JSONObject(result);
            setQuizSuccesful(quizObject.getBoolean("Successful"));

            if ( !isQuizSuccesful() ) {
                setErrorCode(quizObject.getInt("Errorcode"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.QUIZMAPPER;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isQuizSuccesful() {
        return quizSuccesful;
    }

    public void setQuizSuccesful(boolean quizSuccesful) {
        this.quizSuccesful = quizSuccesful;
    }

    public int getErrorCode() { return this.errorCode; }

    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }

}