package wepresent.wepresent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by Ruud on 11-3-2015.
 */
public class HomeFragment extends Fragment implements MaterialTabListener {
    /** Main View. */
    View rootView;

    /** Tabs */
    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter pagerAdapter;
    SlidesActivity slidesAct;
    QuestionView questAct;

    /** FAB */
    //AddFloatingActionButton addButton;

    /** Number of pages */
    final private int NUM_PAGES = 3;

    /** Tabhost titles. */
    final String[] tabTitles = {
            "Slides",
            "Questions",
            "Quiz"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set root view
        rootView = inflater.inflate(R.layout.home_fragment, container, false);

        // Add action button
        //addButton = (AddFloatingActionButton)rootView.findViewById(R.id.add_button);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTabs();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Setup tabs
     */
    private void setupTabs() {
        // Set TabHost views
        tabHost = (MaterialTabHost)rootView.findViewById(R.id.tabHost);
        pager = (ViewPager)rootView.findViewById(R.id.pager);

        // Set the adapter
        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });

        // Add the tabs
        for (int i = 0; i < NUM_PAGES; ++ i) {
            MaterialTab tab = tabHost.newTab()
                    .setText(pagerAdapter.getPageTitle(i))
                    .setTabListener(this);
            tab.getView().findViewById(R.id.reveal).setVisibility(View.GONE);
            tabHost.addTab(tab);
        }
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        System.out.println("materialTab.getPostition() " + materialTab.getPosition());
        pager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    /**
     * The adapter that fills the tabhost
     */
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        /**
         * Constructor
         * @param fm the fragment manager
         */
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Returns the fragment of the position
         * @param num item position
         * @return the fragment at position {@code num}
         */
        public Fragment getItem(int num) {
            Fragment frag = null;
            System.out.println("WELL THIS HAPPENED: " + num);
            switch(num){
                case 0:
                    //System.out.print("slidesAct ID: " + slidesAct.getId());
                    /*if (slidesAct.getId() == null) {
                        slidesAct = new SlidesActivity();
                        slidesInit = true;
                    } else {
                        frag = slidesAct;
                    }*/
                break;
                case 1:
//                    if (questInit == false) {
//                        questAct = new QuestionView();
//                        questInit = true;
//                    } else {
//                        frag = questAct;
//                    }
                break;
                case 2:
//                    frag = new QuestionView();
                break;
            }
            return new SlidesActivity();
        }

        /**
         * Returns the amount of pages
         * @return amount of pages
         */
        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        /**
         * Get the tab title of that position
         * @param position the position in the tabhost
         * @return the title of {@code position}
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    }
}
