package com.codepath.apps.tweetter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codepath.apps.tweetter.R;
import com.codepath.apps.tweetter.fragments.SearchTweetsFragment;
import com.codepath.apps.tweetter.fragments.TweetsListFragment;
import com.codepath.apps.tweetter.models.Tweet;
import com.codepath.apps.tweetter.sync.TwitterApp;
import com.codepath.apps.tweetter.sync.TwitterClient;

import org.parceler.Parcels;

import butterknife.ButterKnife;

import static com.codepath.apps.tweetter.activities.TimelineActivity.REQUEST_CODE_DETAILS;
import static com.codepath.apps.tweetter.activities.TimelineActivity.TWEET_POSITION_KEY;
import static com.codepath.apps.tweetter.activities.TimelineActivity.adapter;
import static com.codepath.apps.tweetter.activities.TimelineActivity.vpPager;

public class SearchActivity extends AppCompatActivity implements TweetsListFragment.TweetSelectedListener, TweetsListFragment.LoadingProgressDialog{

    MenuItem miActionProgressItem;
    String searchQuery;
    SearchTweetsFragment searchFragment;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        client = TwitterApp.getRestClient();

        // Fetch the search query entered in the search bar
        searchQuery = getIntent().getStringExtra("search_query");

        // Set up the action bar
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_custom, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.actionbar_title);
        mTitleTextView.setText("Search: " + searchQuery);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        // Inflate the fragment
        searchFragment = SearchTweetsFragment.getInstance(searchQuery);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContainerSearch, searchFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void showProgressBar() {
        if(miActionProgressItem != null) {
            miActionProgressItem.setVisible(true);
        }
    }

    @Override
    public void hideProgressBar() {
        if(miActionProgressItem != null) {
            miActionProgressItem.setVisible(false);
        }
    }

    @Override
    public void onTweetSelected(Tweet tweet, int position) {
        if(position != RecyclerView.NO_POSITION) {
            // Create an intent to the TweetDetailsActivity with the tweet
            Intent i = new Intent(this, TweetDetailsActivity.class);
            i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
            i.putExtra(TWEET_POSITION_KEY, position);
            startActivityForResult(i, REQUEST_CODE_DETAILS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If returning successfully from DetailsActivity
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_DETAILS) {
            // Deserialize the tweet and its position
            Tweet newTweet = Parcels.unwrap(data.getParcelableExtra(Tweet.class.getSimpleName()));
            int position = data.getIntExtra(TWEET_POSITION_KEY, 0);
            // Update the tweet in the search result list and notify the adapter
            TweetsListFragment currentFragment = adapter.getRegisteredFragment(vpPager.getCurrentItem());
            currentFragment.tweets.set(position, newTweet);
            currentFragment.tweetAdapter.notifyItemChanged(position);
            currentFragment.rvTweets.scrollToPosition(position);
        }
    }
}
