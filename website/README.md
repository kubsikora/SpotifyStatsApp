
# Website


[Index.html](Index.html) is the main Page where the user is sent to from the app; from here, we connect them with the Spotify authorization website. To go successfully through authorization, you have to provide, in the indicated place in [app.js](app.js), your client ID and client secret from the Spotify Developer Website. Without that, the only way you can see your stats is by manually copying the token from the website. After successful authorization, the Server redirects us to [index2.html](index2.html), from where [app.js](app.js) will send the fetched token back to [Server](./SpotifyStatsApp/src/Server).

