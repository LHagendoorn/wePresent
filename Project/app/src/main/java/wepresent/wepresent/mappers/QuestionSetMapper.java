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

/**
 * Created by Jos on 6-Apr-15.
 */
public class QuestionSetMapper extends Mapper {
    private int userId;
    private int errorCode;
    private String[] questionNames;
    private int[] questionIds;
    private boolean quesSuccesful;

    public QuestionSetMapper(Activity activity) {
        super(activity);
    }

    public QuestionSetMapper(Fragment frag) {
        super(frag);
    }

    public void start(Integer userId) {
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
        return "http://wepresent.tk/api/getQuizQuestionsSet";
    }

    /**
     *
     * @param result The response from the asynctask
     */
    @Override
    public void processData(String result) {
        try {
            JSONObject presObject = new JSONObject(result);
            quesSuccesful = presObject.getBoolean("Successful");

            if ( isQuesSuccesful() ) {
                JSONArray Sessions = presObject.getJSONArray("QuizQuestionsSet");
                questionNames = new String[Sessions.length()];
                questionIds = new int[Sessions.length()];

                for (int i = 0; i < Sessions.length(); i++){
                    questionIds[i] = Sessions.getJSONObject(i).getInt("SetID");
                    questionNames[i] = Sessions.getJSONObject(i).getString("Name");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public MapperSort getMapperSort() {
        return MapperSort.QUESTIONSETMAPPER;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String[] getQuestionNames() {
        return questionNames;
    }

    public int[] getQuestionIds() {
        return questionIds;
    }

    public boolean isQuesSuccesful() {
        return quesSuccesful;
    }
}