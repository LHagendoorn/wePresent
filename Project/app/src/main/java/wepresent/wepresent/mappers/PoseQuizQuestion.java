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
public class PoseQuizQuestion extends Mapper{
    private String[] SessionNames;
    private int[] SessionIds;
    private int sessionId;
    private boolean getSuccessful;

    public PoseQuizQuestion(Activity activity) {
        super(activity);
    }


    public void start(int sessionId) {

        setSessionId( sessionId );
        execute();
    }


    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("SessionID", Integer.toString(getSessionId())));
        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/getQuizQuestions";
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

            if ( isGetSuccessful() ) {
                JSONArray Sessions = SessionObject.getJSONArray("Questions");
                SessionNames = new String[Sessions.length()];
                SessionIds = new int[Sessions.length()];

                for (int i = 0; i < Sessions.length(); i++){
                    SessionIds[i] = Sessions.getJSONObject(i).getInt("QuestionID");
                    SessionNames[i] = Sessions.getJSONObject(i).getString("Question");
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.SESSIONMAPPER;
    }

    public int getSessionId() { return sessionId; }

    public void setSessionId( int sessionId ) { this.sessionId = sessionId; }

    public int[] getSessionIds() {
        return SessionIds;
    }

    public String[] getSessionNames() {
        return SessionNames;
    }
}
