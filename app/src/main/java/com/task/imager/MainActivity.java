package com.task.imager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.task.imager.RandomImageFragment.TAG;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private NavController navController;
    private int currentFragmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton floatingActionButton = findViewById(R.id.play_fab);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_RandomImageFragment_to_CollectionsFragment);
            }
        });
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
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Log.d(TAG, "MainActivity: onMenuItemActionCollapse");
                navController.navigate(R.id.action_ImageSearchFragment_to_RandomImageFragment);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "MainActivity: onQueryTextSubmit");
        final Bundle bundle = new Bundle();
        bundle.putString("query", query);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.d(TAG, "MainActivity: NavigationController: onDestinationChanged: " + destination.getLabel());
                currentFragmentID = destination.getId();
            }
        });

        if(currentFragmentID == R.id.RandomImageFragment){
            navController.navigate(R.id.action_RandomImageFragment_to_ImageSearchFragment, bundle);
        } else if(currentFragmentID == R.id.ImageSearchFragment){
            navController.navigate(R.id.action_ImageSearchFragment_to_ImageSearchFragment, bundle);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "MainActivity: onQueryTextChange");
        return false;
    }
}