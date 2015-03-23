package wepresent.wepresent.mappers;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public abstract class Mapper extends AsyncTask<Object, Boolean, String> {
    protected String c_pass = "VecreSAf3CRa3rE";
    private Activity mActivity;
    private boolean isDone;
    private String response;

    /**
     * Constructs a mapper object.
     *
     * @param activity Activity to construct a mapper object
     */
    protected Mapper(Activity activity) {
        if (!(activity instanceof AsyncTaskReport)) {
            throw new IllegalArgumentException("Activity does not implement AsyncTaskReport interface");
        }

        mActivity = activity;
    }

    /**
     * Return the found value of the tag
     *
     * @param sTag
     * @param eElement
     * @return
     */
    public static String getTagValue(String sTag, Element eElement) {
        if (eElement.getElementsByTagName(sTag) == null) {
            return "";
        }

        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();

        Node nValue = (Node) nlList.item(0);
        return (nValue != null && nValue.getNodeValue() != null) ? nValue.getNodeValue() : "";
    }

    /**
     * Create all post data
     *
     * @return List with all post data
     */
    public abstract List<NameValuePair> createPostData();

    /**
     * @return Returns the url for the post action
     */
    public abstract String getUrl();

    /**
     * Process the response, this method get calls when the {@link android.os.AsyncTask} is finished
     *
     * @param result The response from the asynctask
     */
    public abstract void processData(String result);

    /**
     * Returns the mapper sort. This will be used when reporting this class is done
     *
     * @return
     */
    public abstract MapperSort getMapperSort();

    @Override
    protected String doInBackground(Object... params) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(getUrl());
            httppost.addHeader("Accept", "application/xml");
            httppost.addHeader("Content-Type",
                    "application/x-www-form-urlencoded");

            httppost.setEntity(new UrlEncodedFormEntity(createPostData()));
            HttpResponse response = httpclient.execute(httppost);

            return inputStreamToString(
                    response.getEntity().getContent()).toString();

        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.d("ObAtTech", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ObAtTech", e.toString());
        }
        callDone();
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        this.response = response;

        processData(response);
        callDone();
    }

    /**
     * Converts a {@link java.io.InputStream} to a {@link StringBuilder}
     *
     * @param is {@link} InputStream which needs to be converted
     * @return Converted {@link StringBuilder}
     */
    private StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ObAtTech", e.toString());
            callDone();
        }
        return total;
    }

    /**
     * This method calls the activity who constructed this object.
     */
    public void callDone() {
        if (!isDone) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    ((AsyncTaskReport) getActivity()).done(getMapperSort());
                }
            });
        }
        isDone = true;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    public String getResponse() {
        return response;
    }

    public enum MapperSort {
        MAPPER, MAINMAPPER, HINTMAPPER, TASKMAPPER, TASKDONEMAPPER, SESSIONMAPPER
    }
}
