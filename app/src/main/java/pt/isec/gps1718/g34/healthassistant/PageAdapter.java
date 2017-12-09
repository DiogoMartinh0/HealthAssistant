package pt.isec.gps1718.g34.healthassistant;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ListView;


public class PageAdapter extends FragmentStatePagerAdapter {

    int nTabs;

    public PageAdapter(FragmentManager fm, int nTabs) {
        super(fm);
        this.nTabs = nTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new PrescriptionsFragment();
            case 1:
                return new AppointmentsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nTabs;
    }
}
