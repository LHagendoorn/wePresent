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


public class UpdateMapper extends Mapper {
    private Integer sessionId;
    private Integer errorCode;
    private Integer userID;
    private Integer presentationID;
    private Integer quesSetID;
    private boolean sendSuccesful;
    private JSONArray questionsRetrieved;
    private String title;


    public UpdateMapper(Activity activity) {
        super(activity);
    }

    public UpdateMapper(Fragment frag) {
        super(frag);
    }

    public void start(Integer sessionId, Integer presID, Integer quesID, String title) {
        setSessionId(sessionId);
        setPresentationID(presID);
        setQuesSetID(quesID);
        setTitle(title);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("SessionID", getSessionId().toString()));
        nameValuePairs.add(new BasicNameValuePair("UserID", getUserID().toString()));
        nameValuePairs.add(new BasicNameValuePair("PresentationID", getPresentationID().toString()));
        nameValuePairs.add(new BasicNameValuePair("QuizID", getQuesSetID().toString()));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/updateSession";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject questionsObject = new JSONObject(result);
            sendSuccesful = questionsObject.getBoolean("Successful");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.UPDATEMAPPER;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getPresentationID() {
        return presentationID;
    }

    public void setPresentationID(Integer presentationID) {
        this.presentationID = presentationID;
    }

    public Integer getQuesSetID() {
        return quesSetID;
    }

    public void setQuesSetID(Integer quesSetID) {
        this.quesSetID = quesSetID;
    }

    public boolean isSendSuccesful() {
        return sendSuccesful;
    }

    public void setSendSuccesful(boolean sendSuccesful) {
        this.sendSuccesful = sendSuccesful;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}