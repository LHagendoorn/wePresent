package wepresent.wepresent.mappers;

import android.app.Activity;
import android.support.v4.app.Fragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UpdateMapper extends Mapper {
    private int sessionId;
    private int errorCode;
    private int userID;
    private int presentationID;
    private int quesSetID;
    private boolean sendSuccesful;
    private JSONArray questionsRetrieved;
    private String title;


    public UpdateMapper(Activity activity) {
        super(activity);
    }

    public UpdateMapper(Fragment frag) {
        super(frag);
    }

    public void start(Integer sessionId, Integer presID, Integer quesID, String title, int userID) {
        setSessionId(sessionId);
        setPresentationID(presID);
        setQuesSetID(quesID);
        setTitle(title);
        setUserID(userID);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("SessionID", getSessionId() + ""));
        nameValuePairs.add(new BasicNameValuePair("UserID", getUserID() + ""));
        nameValuePairs.add(new BasicNameValuePair("PresentationID", getPresentationID() + ""));
        nameValuePairs.add(new BasicNameValuePair("QuizID", getQuesSetID() + ""));
        nameValuePairs.add(new BasicNameValuePair("Title", getTitle()));

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
        System.out.println(result);
        try {
            JSONObject questionsObject = new JSONObject(result);
            sendSuccesful = questionsObject.getBoolean("Successful");
            System.out.println("CAN ANYBODY HEAR ME? " + this.sessionId);
            if (this.sessionId==0){
                System.out.println("IS IT PRINTING THIS?");
                this.sessionId = questionsObject.getInt("SessionID");
                System.out.println("in: " + questionsObject.getInt("SessionID") + " - uit: " + getSessionId());
            }
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

    public void setSessionId(int sessionId) {
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
    public String getTitle() {
        return title;
    }
}