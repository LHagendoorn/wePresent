package wepresent.wepresent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * I think this is needed for the menu...
 */
public class HomeListFragment extends Fragment {

    /** The views. */
    private View rootView;
    private RecyclerView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Define the rootview
        rootView = inflater.inflate(R.layout.home_list_fragment, container, false);

        // RecyclerView setup
        list = (RecyclerView) rootView.findViewById(R.id.items_list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()
                .getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);

        return rootView;
    }
}
