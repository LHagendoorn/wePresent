package wepresent.wepresent.mappers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Pair;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jos on 6-Apr-15.
 */
public class PresentationMapper extends Mapper {
    private int userId;
    private int errorCode;
    private String[] PresentationNames;
    private int[] PresentationIds;
    private boolean presSuccesful;

    public PresentationMapper(Activity activity) {
        super(activity);
    }

    public PresentationMapper(Fragment frag) {
        super(frag);
    }

    public void start(int userId) {
        setUserId(userId);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("UserID", getUserId().toString()));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/getPresentationsSet";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject presObject = new JSONObject(result);
            presSuccesful = presObject.getBoolean("Successful");

            if ( isPresSuccesful() ) {
                JSONArray Sessions = presObject.getJSONArray("PresentationsSet");
                PresentationNames = new String[Sessions.length()];
                PresentationIds = new int[Sessions.length()];

                for (int i = 0; i < Sessions.length(); i++){
                    PresentationIds[i] = Sessions.getJSONObject(i).getInt("SetID");
                    PresentationNames[i] = Sessions.getJSONObject(i).getString("Name");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.PRESENTATIONMAPPER;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String[] getPresentationNames() {
        return PresentationNames;
    }

    public int[] getPresentationIds() {
        return PresentationIds;
    }

    public boolean isPresSuccesful() {
        return presSuccesful;
    }
}