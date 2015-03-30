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
public class AddQuestionMapper extends Mapper {
    private String question, androidId;
    private Integer errorCode, userId;
    private boolean AddedSuccesful;

    public AddQuestionMapper(Activity activity) {
        super(activity);
    }

    //TODO Allow images to be set and send

    public void start(String question) {
        setQuestion(question);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("Question", getQuestion()));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/addQuestion";
    }
    // TODO Ensure this is set up to accept and add new questions to the list of questions

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
                setUserId(registerObject.getInt("UserID"));
            } else {
                setErrorCode(registerObject.getInt("Errorcode"));
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

    public void setQuestion(String username) {
        this.question = username;
    }

    public boolean isAddedSuccesful() {
        return AddedSuccesful;
    }

    public void setAddedSuccesfull(boolean addedSuccesfull) {
        this.AddedSuccesful = addedSuccesfull;
    }

    public int getErrorCode() { return this.errorCode; }

    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }

    public int getUserId() { return this.userId; }

    public void setUserId(Integer userId) { this.userId = userId; }
}