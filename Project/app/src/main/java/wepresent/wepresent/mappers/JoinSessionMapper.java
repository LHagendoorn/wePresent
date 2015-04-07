package wepresent.wepresent.mappers;

import android.app.Activity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jos on 23-3-2015.
 */
public class JoinSessionMapper extends Mapper{
    private String[] SessionNames;
    private int[] SessionIds;
    private String UserID;
    private boolean getSuccessful;

    public JoinSessionMapper(Activity activity) {
        super(activity);
    }

    int sessionID;
    int userID;

    public void start(int sessionId, int userID) {
        this.sessionID = sessionId;
        this.userID = userID;
        execute();
    }


    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("SessionID", sessionID + ""));
        nameValuePairs.add(new BasicNameValuePair("UserID", userID + ""));
        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/joinSession";
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

    public int[] getSessionIds() {
        return SessionIds;
    }

    public String[] getSessionNames() {
        return SessionNames;
    }
}
