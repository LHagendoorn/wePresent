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
public class RegisterMapper extends Mapper {
    private String username, password, emailaddress, androidId;
    private Integer errorCode, userId;
    private boolean registerSuccesful;

    public RegisterMapper(Activity activity) {
        super(activity);
    }

    public void start(String username, String password, String emailaddress, String androidId) {
        setUsername(username);
        setPassword(password);
        setEmailaddress(emailaddress);
        setAndroidId(androidId);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("Username", getUsername()));
        nameValuePairs.add(new BasicNameValuePair("Password", getPassword()));
        nameValuePairs.add(new BasicNameValuePair("Emailaddress", getEmailaddress()));
        nameValuePairs.add(new BasicNameValuePair("AndroidID", getAndroidId()));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/register";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject registerObject = new JSONObject(result);
            setRegisterSuccesful(registerObject.getBoolean("Successful"));

            if ( isRegisterSuccesful() ) {
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

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public boolean isRegisterSuccesful() {
        return registerSuccesful;
    }

    public void setRegisterSuccesful(boolean registerSuccesful) {
        this.registerSuccesful = registerSuccesful;
    }

    public int getErrorCode() { return this.errorCode; }

    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }

    public int getUserId() { return this.userId; }

    public void setUserId(Integer userId) { this.userId = userId; }
}