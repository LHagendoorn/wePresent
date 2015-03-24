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
public class SessionMapper extends Mapper{
    private String[] SessionNames;
    private Integer[] SessionIds;
    private boolean getSuccesful;

    public SessionMapper(Activity activity) {
        super(activity);
    }


    public void start() {
        execute();
    }


    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("SessionID", "HOIHOI"));
        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/getSessions";
    }

    public boolean isGetSuccesful() {
        return getSuccesful;
    }

    public void setGetSuccesful(boolean getSuccesful) {
        this.getSuccesful = getSuccesful;
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject SessionObject = new JSONObject(result);
            setGetSuccesful(SessionObject.getBoolean("Successful"));

            if ( isGetSuccesful() ) {
                JSONArray Sessions = SessionObject.getJSONArray("ActiveSessions");
                SessionNames = new String[Sessions.length()];
                SessionIds = new Integer[Sessions.length()];

                for (int i = 0; i < Sessions.length(); i++){
                    SessionIds[i] = Sessions.getJSONObject(i).getInt("SessionID");
                    SessionNames[i] = Sessions.getJSONObject(i).getString("name");
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

    public Integer[] getSessionIds() {
        return SessionIds;
    }

    public String[] getSessionNames() {
        return SessionNames;
    }
}
