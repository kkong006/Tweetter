package com.codepath.apps.tweetter.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.tweetter.R;
import com.codepath.apps.tweetter.TwitterApp;
import com.codepath.apps.tweetter.TwitterClient;
import com.codepath.apps.tweetter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kkong on 7/7/17.
 */

public class ComposeFragment extends DialogFragment {

    private EditText mTweetText;
    private Tweet tweet;
    final private int maxLength = 140;
    private TwitterClient client;

    public interface ComposeDialogListener {
        public void onFinishComposeDialog(Tweet tweet);
    }

    // Empty constructor required for DialogFragment
    public ComposeFragment() {
    }

    public static ComposeFragment newInstance() {
        ComposeFragment composeFragment = new ComposeFragment();
        return composeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        client = TwitterApp.getRestClient();

        mTweetText = (EditText) view.findViewById(R.id.etNewTweet);
        Button btTweetCompose = (Button) view.findViewById(R.id.btSubmitNewTweet);

        final TextView tvCharacterCount = (TextView) view.findViewById(R.id.tvCharacterCount);
//        EditText etNewTweet = (EditText) view.findViewById(R.id.etNewTweet);
        mTweetText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCharacterCount.setText(String.valueOf(maxLength - s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Set the title
        getDialog().setTitle("Compose Tweet");

        // Show soft keyboard automatically and request focus to field
        mTweetText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btTweetCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Submitting tweet", Toast.LENGTH_SHORT).show();
                if (v.getId() == R.id.btSubmitNewTweet) {
                    Toast.makeText(getActivity(), "Submitting tweet", Toast.LENGTH_SHORT).show();
                    String newTweetText = mTweetText.getText().toString();
                    client.sendTweet(newTweetText, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                // Get the tweet
                                tweet = Tweet.fromJSON(response);
                                // Make the intent
//                            Intent i = new Intent(ComposeActivity.this, TimelineActivity.class);
//                            i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
//                            setResult(RESULT_OK, i);
//                            finish();
                                ComposeDialogListener listener = (ComposeDialogListener) getActivity();
                                listener.onFinishComposeDialog(tweet);
                                // Close the dialog and return back to the parent activity
                                dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dismiss();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    tweet = Tweet.fromJSON(response.getJSONObject(i));
//                                Intent intent = new Intent(ComposeActivity.this, TimelineActivity.class);
//                                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
//                                setResult(RESULT_OK, intent);
//                                finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
//                            Toast.makeText(ComposeActivity.this, "Failed to submit tweet", Toast.LENGTH_SHORT).show();
                            }
                            dismiss();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            super.onSuccess(statusCode, headers, responseString);
                            dismiss();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            throwable.printStackTrace();
                            Toast.makeText(getActivity(), "Failed to submit tweet", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            throwable.printStackTrace();
                            Toast.makeText(getActivity(), "Failed to submit tweet", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            throwable.printStackTrace();
                            Toast.makeText(getActivity(), "Failed to submit tweet", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    });
                }

            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        Toast.makeText(getActivity(), "Submitting tweet", Toast.LENGTH_SHORT).show();
//        if(v.getId() == R.id.btSubmitNewTweet) {
//            Toast.makeText(getActivity(), "Submitting tweet", Toast.LENGTH_SHORT).show();
//            EditText etNewTweet = (EditText) v.findViewById(R.id.etNewTweet);
//            String newTweetText = etNewTweet.getText().toString();
//            client.sendTweet(newTweetText, new JsonHttpResponseHandler() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    super.onSuccess(statusCode, headers, response);
//                    try {
//                        // Get the tweet
//                        tweet = Tweet.fromJSON(response);
//                        // Make the intent
////                            Intent i = new Intent(ComposeActivity.this, TimelineActivity.class);
////                            i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
////                            setResult(RESULT_OK, i);
////                            finish();
//                        ComposeDialogListener listener = (ComposeDialogListener) getActivity();
//                        listener.onFinishComposeDialog(tweet);
//                        // Close the dialog and return back to the parent activity
//                        dismiss();
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    dismiss();
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                    super.onSuccess(statusCode, headers, response);
//                    try {
//                        for(int i = 0;  i < response.length(); i++) {
//                            tweet = Tweet.fromJSON(response.getJSONObject(i));
////                                Intent intent = new Intent(ComposeActivity.this, TimelineActivity.class);
////                                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
////                                setResult(RESULT_OK, intent);
////                                finish();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
////                            Toast.makeText(ComposeActivity.this, "Failed to submit tweet", Toast.LENGTH_SHORT).show();
//                    }
//                    dismiss();
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                    super.onSuccess(statusCode, headers, responseString);
//                    dismiss();
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    throwable.printStackTrace();
//                    Toast.makeText(getActivity(), "Failed to submit tweet", Toast.LENGTH_SHORT).show();
//                    dismiss();
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                    throwable.printStackTrace();
//                    Toast.makeText(getActivity(), "Failed to submit tweet", Toast.LENGTH_SHORT).show();
//                    dismiss();
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                    throwable.printStackTrace();
//                    Toast.makeText(getActivity(), "Failed to submit tweet", Toast.LENGTH_SHORT).show();
//                    dismiss();
//                }
//            });
//        }
//    }

//    @Override
//    public void onClick(View v) {
////        Toast.makeText(getActivity(), v.getId() + "", Toast.LENGTH_SHORT).show();
//        Log.e("COMPOSE FRAGMENT", v.getId() + "");
//    }
}
