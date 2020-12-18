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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.task.imager.model.ImageSearchViewModel;

import static com.task.imager.RandomImageFragment.TAG;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
        private CustomViewPager pager;
        private MyFragmentPagerAdapter adapter;
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
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(1);
        model = ViewModelProviders.of(this).get(ImageSearchViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.d(TAG, "MainActivity: onMenuItemActionExpand");
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
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "MainActivity: onQueryTextSubmit");
        pager.setCurrentItem(2);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "MainActivity: onQueryTextChange");
        model.getData().setValue(newText);
        pager.setCurrentItem(2);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_collections) {
            pager.setCurrentItem(0);
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
                    return new CollectionsFragment();
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
}