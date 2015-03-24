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
 * Created by Chris on 16-Mar-15.
 */
public class SlidesMapper extends Mapper {
    private Integer sessionId;
    private Integer errorCode;
    private ArrayList<Map<String, String>> slides;
    private boolean slidesSuccesful;
    private JSONArray slidesRetrieved;

    public SlidesMapper(Activity activity) {
        super(activity);
    }

    public SlidesMapper(Fragment frag) {
        super(frag);
    }

    public void start(Integer sessionId) {
        setSessionId(sessionId);
        execute();
    }

    @Override
    public List<NameValuePair> createPostData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("SessionID", getSessionId().toString()));

        return nameValuePairs;
    }

    @Override
    public String getUrl() {
        return "http://wepresent.tk/api/getSlides";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject slidesObject = new JSONObject(result);
            setSlidesSuccesful(slidesObject.getBoolean("Successful"));

            if ( isSlidesSuccesful() ) {
                // For each slide, add it to the slides
                slidesRetrieved = slidesObject.getJSONArray("Slides");
                processSlides();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void processSlides() {
        Integer tmpSlideId;
        String tmpSlideUrl;
        Integer arrayLength = slidesRetrieved.length();
        JSONObject tmpSlide;
        slides = new ArrayList<>();
        Map<String, String> slideInfo = new HashMap<>();

        for ( int i = 0; i < arrayLength; i++ ) {
            try {
                // Clear the slideInfo
                slideInfo.clear();

                // Split it in parts
                tmpSlide = slidesRetrieved.getJSONObject(i);

                // Get the slide info
                tmpSlideId = tmpSlide.getInt("id");
                tmpSlideUrl = tmpSlide.getString("SlideURL");

                // Add it to the slideInfo array
                slideInfo.put("id", tmpSlideId.toString());
                slideInfo.put("SlideURL", tmpSlideUrl);

                // Add it to the ArrayList<ID, SlideURL>
                slides.add(slideInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.SLIDESMAPPER;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isSlidesSuccesful() {
        return slidesSuccesful;
    }

    public void setSlidesSuccesful(boolean slidesSuccesful) {
        this.slidesSuccesful = slidesSuccesful;
    }

    public ArrayList<Map<String, String>> getSlides() {
        return slides;
    }

}