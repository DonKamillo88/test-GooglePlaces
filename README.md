# test-GooglePlaces

An application that retrieves data about  places with close proximity to the user and shows the data on the  screen.


Google Places Rest API, GoogleMap, Retrofit, gson

Location permission check, permission request, 

viewPager, tabs, list, jUnit and InstrumentedTest


It is required to to use Google Places Rest API to fetch the data. You need to implement a request that would return bars with close proximity to the device.


Layout

You should implement a tabbed layout. The first tab should contain a list of the bars returned by the rest API. The second tab should contain a google map with pins representing the bars.

Bar List tab

The bar list tab should contain a list of the bars close to the user. Each list item should contain the name of the bar and the estimated distance between the device and the bar. Clicking on a list item should take you to the Google Maps App and show you the location of the bar.


Map Tab

The map tab should show the pins returned by the webservice. Tapping on one of the pins should show info window with the name of the bar and the distance to it.
