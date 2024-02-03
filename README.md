
# Spotify Stats App

Spotify Stats Desktop App is a Java-based desktop application designed to fetch user statistics from the Spotify API. App is made fully with a though about polish language users. The app provides a user-friendly interface for easy access to various features related to data analysis of music playback.


## Introduction



[![Watch the video](https://i.imgur.com/VPuMVsy.png)](https://youtu.be/TRpGMfRgeSQ?si=DYo8npRlwMkdbyJ8)

The application is designed to operate within a single window. The Auth0 authorization process takes place in a web browser window automatically triggered when the user clicks "Log in."

In case of any issues, users can click "Coś poszło nie tak?" providing them with the option to connect their accounts by copying the token directly from the Spotify developer website.

The application dynamically fetches all data in real-time from the Spotify API, ensuring that no information is stored locally on the client's PC. This deliberate choice prioritizes data security and privacy. Because of that, the time required to display certain albums or statistics may be extended.

[![Watch the video](https://i.imgur.com/ZtAhRDw.png)](https://youtu.be/TRpGMfRgeSQ?si=DYo8npRlwMkdbyJ8)

The color scheme of the project draws inspiration from the desktop version of the Spotify app, utilizing predominantly three colors (excluding black and white) for simplification.
| Color             | Hex                                                                |
| ----------------- | ------------------------------------------------------------------ |
| Main window bg color | ![#4f4e4e](https://via.placeholder.com/10/4f4e4e?text=+) #4f4e4e |
| Container bg color | ![#1f1f1f](https://via.placeholder.com/10/1f1f1f?text=+) #1f1f1f |
| Contrast color | ![#009d3c](https://via.placeholder.com/10/009d3c?text=+) #009d3c |


## How it works
The main app is entirely built in Java, with a slight contribution from HTML and JS, which is responsible exclusively for Auth0 authorization. The website is hosted on a basic Java localhost server built within the application. Upon successful login to the Spotify account, JS generates a token on the server and directly provides it to the Main class. If the token provided by JS is incorrect, the app won't allow the user to proceed. The Main class utilizes the token to fetch data directly from the server and presents it to the user. It's noteworthy that the token is available for only one hour using this approach. 
## Functionality


The application is primarily designed for examining your top songs, artists, and albums over various time periods. However, it goes beyond that, offering insights such as your favorite genre, identifying the artist with the greatest influence on your top songs, and revealing the preferred music era. Additionally, the application provides a comprehensive track list for each album, displays the popularity ("posłuch") of every song, artist, and album, and suggests three songs similar to your chosen one.
## Organization
[Website](website) -  provides you with full HTML and JS code used for    autoryzation and redirecting to spotify website.

[Src](src) - complete Java source code of the App.

[Images](images) - all images essential for proper functioning of App.


## Related

The complete walkthrough of the app functionality is available on
[Youtube](https://youtu.be/TRpGMfRgeSQ?si=DYo8npRlwMkdbyJ8)
.

