package com.task.imager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.task.imager.Custom.CustomViewPager;
import com.task.imager.Fragment.CollectionListFragment;
import com.task.imager.Fragment.RandomImageFragment;
import com.task.imager.Fragment.SearchImageFragment;
import com.task.imager.model.ImageSearchViewModel;

import static com.task.imager.Fragment.RandomImageFragment.TAG;

public class MainActivity extends AppCompatActivity{
    private CustomViewPager pager;
    private ImageSearchViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(1);
        pager.setOffscreenPageLimit(3);
        model = ViewModelProviders.of(this).get(ImageSearchViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "MainActivity: onQueryTextSubmit");
                pager.setCurrentItem(2);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "MainActivity: onQueryTextChange: query: " + newText);
                model.getData().setValue(newText);
                pager.setCurrentItem(2);
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.d(TAG, "MainActivity: onMenuItemActionExpand: query: " + model.getData().getValue());
                pager.setCurrentItem(2);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Log.d(TAG, "MainActivity: onMenuItemActionCollapse");
                pager.setCurrentItem(1);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_collections:
                pager.setCurrentItem(0);
                return true;
            case R.id.action_search:
                pager.setCurrentItem(2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return CollectionListFragment.newInstance();
                case 2:
                    return SearchImageFragment.newInstance();
                default:
                    return new RandomImageFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "MainActivity: onBackPressed");
        switch (pager.getCurrentItem()){
            case 0:
                try {
                    Log.d(TAG, "MainActivity: onBackPressed: getSupportFragmentManager().getFragments().get(1): " + getSupportFragmentManager().getFragments().get(1).getClass());
                    if (getSupportFragmentManager().getFragments().get(1).getChildFragmentManager().getFragments().size() != 0)
                        getSupportFragmentManager().getFragments().get(1).getChildFragmentManager().popBackStack();
                    else
                        pager.setCurrentItem(1);
                } catch (IllegalStateException e){
                    Log.d(TAG, "MainActivity: onBackPressed: IllegalStateException");
                    super.onBackPressed();
                }
                break;
            case 2:
                pager.setCurrentItem(1);
                break;
            default:
                super.onBackPressed();
        }
    }
}