package wepresent.wepresent;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;

public class LauncherHubThing extends MaterialNavigationDrawer implements MaterialSectionListener {

    HomeFragment homeFragment;

    @Override
    public void init(Bundle savedInstanceState) {
        // Create home fragment
        homeFragment = new HomeFragment();
        setupNavigationDrawer();
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

}