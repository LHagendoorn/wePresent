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
public class SingleSlideMapper extends Mapper {
    private String sessionId, slideId, slideUrl, notes;
    private Integer errorCode;
    private boolean slideSuccesful;

    public SingleSlideMapper(Activity activity) {
        super(activity);
    }

    public void start(String sessionId, String slideId) {
        setSessionId(sessionId);
        setSlideId(slideId);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("SlideID", getSlideId()));
        nameValuePairs.add(new BasicNameValuePair("SessionID", getSessionId()));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/getSlide";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject slideObject = new JSONObject(result);
            setSlideSuccesful(slideObject.getBoolean("Successful"));

            if ( isSlideSuccesful() ) {
                setSlideUrl(slideObject.getString("SlideURL"));
                setSlideNotes(slideObject.getString("Notes"));
            } else {
                setErrorCode(slideObject.getInt("Errorcode"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.SINGLESLIDEMAPPER;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSlideUrl() { return slideUrl; }

    public void setSlideUrl(String slideUrl) { this.slideUrl = slideUrl; }

    public String getSlideNotes() { return notes; }

    public void setSlideNotes(String notes) { this.notes = notes; }

    public String getSlideId() {
        return slideId;
    }

    public void setSlideId(String slideId) {
        this.slideId = slideId;
    }

    public boolean isSlideSuccesful() {
        return slideSuccesful;
    }

    public void setSlideSuccesful(boolean slideSuccesful) {
        this.slideSuccesful = slideSuccesful;
    }

    public int getErrorCode() { return this.errorCode; }

    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }
}