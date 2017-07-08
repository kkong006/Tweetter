# Project 4 - *Tweetter*

**Tweetter** is an android app that allows a user to view home and mentions timelines, view user profiles with user timelines, as well as compose and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **25** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **sign in to Twitter** using OAuth login process
* [X] User can **view the tweets from their home timeline**
* [X] RecyclerView is used to display listings of any tweets
* [X] User is displayed the username, name, and body for each tweet
* [X] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
* [X] User can **compose and post a new tweet**
* [X] User can click a "Compose" icon in the App Bar on the top right
* [X] User can then enter a new tweet from a second activity and then post this to twitter
* [X] User is taken back to home timeline with new tweet visible in timeline
* [X] Newly created tweet should be manually inserted into the timeline and not rely on a full refresh

The following **optional** features are implemented:

* [X] While composing a tweet, user can see a character counter with characters remaining for tweet out of 140
* [X] User can **pull down to refresh tweets** in either timeline.
* [X] Improve the user interface and theme the app to feel twitter branded with colors and styles
* [X] User can **search for tweets matching a particular query** and see results.
* [X] When a network request is sent, user sees an [indeterminate progress indicator](http://guides.codepath.com/android/Handling-ProgressBars#progress-within-actionbar)
* [X] User can **"reply" to any tweet on their home timeline**
* [X] The user that wrote the original tweet is automatically "@" replied in compose
* [X] User can click on a tweet to be **taken to a "detail view"** of that tweet
* [X] User can take favorite (and unfavorite) or retweet actions on a tweet
* [X] User can see embedded image media within the tweet item in list or detail view.
* [X] Compose activity is replaced with a modal compose overlay.
* [X] User can **click a link within a tweet body** on tweet details view. The click will launch the web browser with relevant page opened.
* [X] Used Parcelable instead of Serializable leveraging the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler) when passing data between activities.
* [ ] Replaced all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate.
* [ ] User can view following / followers list through the profile of a user
* [X] Apply the popular Butterknife annotation library to reduce view boilerplate.
* [ ] Implement collapse scrolling effects on the Twitter profile view using `CoordinatorLayout`.
* [ ] User can **open the twitter app offline and see last loaded tweets**. Persisted in SQLite tweets are refreshed on every application launch. While "live data" is displayed when app can get it from Twitter API, it is also saved for use in an offline mode.

The following **additional** features are implemented:

* [X] User can view more tweets as they scroll with [infinite pagination](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView). Number of tweets is unlimited.
* [X] Replace compose button with a Floating Action Button.

## Video Walkthrough

# Required Functionality
<img src='https://github.com/kkong006/Tweetter/blob/master/WalkthroughRequiredFunctionality.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

# Optional Features
<img src='https://github.com/kkong006/Tweetter/blob/master/WalkthroughOptionalFunctionality.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

# Additional Features
<img src='https://github.com/kkong006/Tweetter/blob/master/WalkthroughAdditionalFunctionality.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Debugging was the most difficult aspect of the project. This project introduced the concept of fragments, tab layout, and coordinator layout.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright [2017] [Karen Kong]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
