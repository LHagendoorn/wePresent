package wepresent.wepresent.mappers;

import android.app.Activity;
import android.support.v4.app.Fragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 16-Mar-15.
 */
public class AddQuestionMapper extends Mapper {
    private String question, encodedimage, title;
    private Integer userId, questionId, sessionId;
    private boolean AddedSuccesful;

    public AddQuestionMapper(Activity activity) {
        super(activity);
    }

    public AddQuestionMapper(Fragment frag) {
        super(frag);
    }

    public void start(String title, String question, Integer userID, Integer sessionID, String image) {
        setTitle(title);
        setUserId(userID);
        setQuestion(question);
        setSessionId(sessionID);
        setImage(image);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("Title", getTitle()));
        nameValuePairs.add(new BasicNameValuePair("UserID", Integer.toString(getUserId())));
        nameValuePairs.add(new BasicNameValuePair("Question", getQuestion()));
        nameValuePairs.add(new BasicNameValuePair("SessionID", Integer.toString(getSessionId())));
        nameValuePairs.add(new BasicNameValuePair("Photo", getImage()));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/createQuestion";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject registerObject = new JSONObject(result);
            setAddedSuccesfull(registerObject.getBoolean("Successful"));

            if ( isAddedSuccesful() ) {
                setQuestionID(registerObject.getInt("QuestionID"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.ADDQUESTIONMAPPER;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAddedSuccesful() {
        return AddedSuccesful;
    }

    public void setAddedSuccesfull(boolean addedSuccesfull) {
        this.AddedSuccesful = addedSuccesfull;
    }

    public int getUserId() { return this.userId; }

    public void setUserId(Integer userId) { this.userId = userId; }

    public int getSessionId() { return this.sessionId; }

    public void setSessionId(Integer sessionId) { this.sessionId = sessionId; }

    public void setQuestionID(Integer questionID) {this.questionId = questionID;}

    public int getQuestionId() {return this.questionId;}

    public void setImage(String image) {
        this.encodedimage = image;
    }

    public String getImage() {return this.encodedimage;}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {return this.title;}

}