# Marvel Comics
An app being a solution to an internship recruitment task, that allows to browse comics by Marvel.
The app follows the MVVM architecture with a repository and an offline cache. I decided to complete all the 'nice to have' tasks and surprisingly, they were very easy to do - it was the search function that seemed the hardest to implement.

Home screen
-
<img src="https://github.com/BDemut/marvel_comics/blob/main/1.jpg" alt="list screenshot" width="360" height="640">

Search action - empty
-
<img src="https://github.com/BDemut/marvel_comics/blob/main/2.jpg" alt="list screenshot" width="360" height="640">

Search action - with search term
-
<img src="https://github.com/BDemut/marvel_comics/blob/main/3.jpg" alt="list screenshot" width="360" height="640">

Details screen - collapsed
-
<img src="https://github.com/BDemut/marvel_comics/blob/main/4.jpg" alt="list screenshot" width="360" height="640">

Details screen - expanded
-
<img src="https://github.com/BDemut/marvel_comics/blob/main/5.jpg" alt="list screenshot" width="360" height="640">

# Implementation details
The app opens up with the home screen (ListFragment) which lists 25 comics (as per the API request provided in the Postman collection). Using the bottom bar it's possible to switch to a search action which brings up a custom search view in the app bar and allows the user to search for a comic title. Every time the search action window is updated the comic list refreshes. Caching is implemented in a way that allows the state of the fragment to be entirely reconstructed (even when the app is stopped during the search action).

Clicking on an item initiates navigation to a DetailsFragment which provides more information about the comic and allows the user to view the details on Marvel's website. This screen deviates a little from the prototype - I decided to not include the bottom app bar in the 'details' screen, since I thought it provided a confusing user experience.

The app has a singleton object - 'Repository' that it supplies to ViewModels using dependency injection. The repository manages and provides the views with data. It downloads the data from Marvel API and puts them in a database which it uses as a 'single source of truth'. 

# 3rd party libraries used:
- Material Design
- Retrofit
- Gson (only for parsing lists of 'creators' into strings, to simplify the DB schema)
- Moshi (for parsing the API Json)
- Glide
- Room
- Kotlin Coroutines
- Kotlin Coroutines Adapter
- Hilt
