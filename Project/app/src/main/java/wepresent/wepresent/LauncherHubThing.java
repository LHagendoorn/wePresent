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
    boolean loggedIn;
    int userID;
    int sessionID;
    @Override
    public void init(Bundle savedInstanceState) {
        // Create home fragment
        Intent in = getIntent();
        String tab = in.getStringExtra("Tab");
        loggedIn = in.getBooleanExtra("LoggedIn",false);
        //loggedIn = true;

        Bundle sessBundle = new Bundle();

        // Determine for what tab it is
        switch (tab) {
            case "slides":
                sessionID = in.getIntExtra("SessionID",0);
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
        sessionID = in.getIntExtra("SessionID",0);
        sessBundle.putInt("SessionID", sessionID);
        userID = in.getIntExtra("UserID",0);
        sessBundle.putInt("UserID", userID);
        System.out.println("Dit is nu de userID: " + userID);

        homeFragment = new HomeFragment();
        homeFragment.setArguments(sessBundle);
        setupNavigationDrawer();
        setDrawerHeaderImage(R.drawable.menuthing);
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
            "Hub",
            homeFragment
        );
        this.addSection(section);

        Intent in = new Intent(this, SessionActivity.class);
        in.putExtra("LoggedIn", true);
        in.putExtra("UserID", userID);
        section = newSection(
                "Switch Session",
                in
        );
        this.addSection(section);

        in = new Intent(this, MainActivity.class);
        in.putExtra("Leaved", true);
        section = newSection(
                "Leave Session",
                in
        );

        if (loggedIn){
            Intent in = new Intent(this, MainActivity.class);
            in.putExtra("LoggedOut", true);
            section = newSection(
                    "Logout",
                    in
            );
            in = new Intent(this, SessionManagement.class);
            in.putExtra("UserdID", userID);
            in.putExtra("SessionID", 0);
            this.addBottomSection(section);
            section = newSection(
                    "Start new session",
                    in
            );

            in = new Intent(this, SessionManagement.class);
            in.putExtra("UserdID", userID);
            in.putExtra("SessionID", sessionID);
            this.addSection(section);
            section = newSection(
                    "Manage Session",
                    in
            );

            this.addSection(section);
            section = newSection(
                    "Pose Quiz Question",
                    homeFragment
            );
            this.addSection(section);
        } else {
            section = newSection(
                    "Login",
                    new Intent(this, MainActivity.class)
            );
            this.addBottomSection(section);
        }
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