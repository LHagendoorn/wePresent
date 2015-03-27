package wepresent.wepresent;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QuestionView extends Fragment {

    // We get the ListView component from the layout
    ////ListView lv;// = (ListView) getView().findViewById(R.id.questionList);


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_question_view2, container, false);
        ////lv = (ListView) v.findViewById(R.id.questionList);
        initList();
        LinearLayout ll = (LinearLayout) v.findViewById(R.id.linearLayout);

        //TODO Server communication with a mapper, functionality.
        for (int i =0; i<5; i++){
            RelativeLayout rv = new RelativeLayout(v.getContext());
            ll.addView(rv);

            ToggleButton tb = new ToggleButton(v.getContext());
            rv.addView(tb);
            tb.setText(Integer.toString(i));
            tb.setTextOff(Integer.toString(i));
            tb.setTextOn(Integer.toString(i));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tb.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_END);
            tb.setLayoutParams(params);

            TextView tv = new TextView(v.getContext());
            tv.setText("Question " + Integer.toString(i) +  " goes here");
            rv.addView(tv);
            params = (RelativeLayout.LayoutParams) tv.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
            params.addRule(RelativeLayout.START_OF, tb.getId());
            params.addRule(RelativeLayout.CENTER_IN_PARENT, 2);
            tv.setLayoutParams(params);

        }




        // This is a simple adapter that accepts as parameter
        // Context
        // Data list
        // The row layout that is used during the row creation
        // The keys used to retrieve the data
        // The View id used to show the data. The key number and the view id must match
        ////SimpleAdapter simpleAdpt = new SimpleAdapter(getActivity(), planetsList, android.R.layout.simple_list_item_1, new String[] {"planet"}, new int[] {android.R.id.text1});

        ////lv.setAdapter(simpleAdpt);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_question_view);
        }

/*    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_question_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    // The data to show
    List<Map<String, String>> planetsList = new ArrayList<Map<String,String>>();

    private void initList() {
        // We populate the planets

        planetsList.add(createPlanet("planet", "Mercury"));
        planetsList.add(createPlanet("planet", "Venus"));
        planetsList.add(createPlanet("planet", "Mars"));
        planetsList.add(createPlanet("planet", "Jupiter"));
        planetsList.add(createPlanet("planet", "Saturn"));
        planetsList.add(createPlanet("planet", "Uranus"));
        planetsList.add(createPlanet("planet", "Neptune"));


    }

    private HashMap<String, String> createPlanet(String key, String name) {
        HashMap<String, String> planet = new HashMap<String, String>();
        planet.put(key, name);

        return planet;
    }


}
