package wepresent.wepresent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;
import wepresent.wepresent.mappers.AsyncTaskReport;
import wepresent.wepresent.mappers.Mapper;

public class LauncherHubThing extends MaterialNavigationDrawer implements MaterialSectionListener {

    HomeFragment homeFragment;

    @Override
    public void init(Bundle savedInstanceState) {
        // Create home fragment
        Intent in = getIntent();
        String tab = in.getStringExtra("Tab");
        Bundle sessBundle = new Bundle();

        // Determine for what tab it is
        switch (tab) {
            case "slides":
                int sessionID = in.getIntExtra("SessionID",0);
                sessBundle.putInt("SessionID", sessionID);
                sessBundle.putString("Tab", "slides");
                break;
            case "quiz":
                String question = in.getStringExtra("Question");
                String type = in.getStringExtra("Type");

                // Determine if multiple choice
                if( type.equals("multiplechoice") ) {
                    String button1 = in.getStringExtra("Button1");
                    String button2 = in.getStringExtra("Button2");
                    String button3 = in.getStringExtra("Button3");
                    sessBundle.putString("Button1", button1);
                    sessBundle.putString("Button2", button2);
                    sessBundle.putString("Button3", button3);
                }

                // Add it to the bundle
                sessBundle.putString("Question", question);
                sessBundle.putString("Type", type);
                sessBundle.putString("Tab", tab);
                break;
        }

        homeFragment = new HomeFragment();
        homeFragment.setArguments(sessBundle);
        setupNavigationDrawer();
        setDrawerHeaderImage(R.drawable.notification_icon);
    }

    /**
     * Sets up the items for the navigation drawer
     */
    private void setupNavigationDrawer() {
        // Options
        this.disableLearningPattern();
        this.allowArrowAnimation();
        // Back pattern
        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_TO_FIRST);


        // The sections
        MaterialSection section = newSection(
                "Home",
                homeFragment
        );

        this.addSection(section);

        // Bottom
        section = newSection(
                "Settings",
                this
        );

        this.addBottomSection(section);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    /*@Override
    public void done(Mapper.MapperSort mapper) {

    }*/
}