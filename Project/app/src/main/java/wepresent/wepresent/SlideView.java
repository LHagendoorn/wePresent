package wepresent.wepresent;

import android.app.Activity;
import android.os.Bundle;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import wepresent.wepresent.MainActivity;

/**
 * Created by Chris on 24-Mar-15.
 */
public class SlideView extends Activity {
    HomeFragment homeFragment;
    @Override
    public void init(Bundle bundle) {
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
}
