package wepresent.wepresent.mappers;

import android.app.Activity;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 16-Mar-15.
 */
public class MainMapper extends Mapper {
    private String username, password;
    private boolean loginsuccesful;

    public MainMapper(Activity activity) {
        super(activity);
    }

    public void start(String username, String password, String androidId) {
        setUsername(username);
        setPassword(password);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("Username", getUsername()));
        nameValuePairs.add(new BasicNameValuePair("Password", getPassword()));
        nameValuePairs.add(new BasicNameValuePair("AndroidID", "TEST"));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/login";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject loginObject = new JSONObject(result);
            setLoginsuccesful(loginObject.getBoolean("Successful"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.MAINMAPPER;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoginsuccesful() {
        return loginsuccesful;
    }

    public void setLoginsuccesful(boolean loginsuccesful) {
        this.loginsuccesful = loginsuccesful;
    }
}