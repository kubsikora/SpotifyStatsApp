	var redirect_uri = "http://127.0.0.1/index.html";
	 

	var client_id = "";
	var client_secret = "";

	var access_token = null;
	var refresh_token = null;


	const AUTHORIZE = "https://accounts.spotify.com/authorize"
	const TOKEN = "https://accounts.spotify.com/api/token";

	function onPageLoad(){
		client_id = localStorage.getItem("client_id");
		client_secret = localStorage.getItem("client_secret");
		if ( window.location.search.length > 0 ){
			handleRedirect();
		}
		else{
			access_token = localStorage.getItem("access_token");
			if ( access_token == null ){
				document.getElementById("tokenSection").style.display = 'block';  
			}
		}
	}

	function handleRedirect(){
		let code = getCode();
		fetchAccessToken( code );
		window.history.pushState("", "", redirect_uri);
	}

	function getCode(){
		let code = null;
		const queryString = window.location.search;
		if ( queryString.length > 0 ){
			const urlParams = new URLSearchParams(queryString);
			code = urlParams.get('code')
		}
		return code;
	}
	/* 
	*	In function requestAuthorization you need to set your 
	*	client_id and client_secret from spotify dev webside 
	*/
	function requestAuthorization(){
		client_id = "your client_id";
		client_secret = "your client_secret";
		localStorage.setItem("client_id", client_id);
		localStorage.setItem("client_secret", client_secret);

		let url = AUTHORIZE;
		url += "?client_id=" + client_id;
		url += "&response_type=code";
		url += "&redirect_uri=" + encodeURI(redirect_uri);
		url += "&show_dialog=true";
		url += "&scope=user-read-private user-read-email user-top-read user-modify-playback-state user-read-playback-position user-library-read streaming user-read-playback-state user-read-recently-played playlist-read-private";
		window.location.href = url;
	}

	function fetchAccessToken( code ){
		let body = "grant_type=authorization_code";
		body += "&code=" + code; 
		body += "&redirect_uri=" + encodeURI(redirect_uri);
		body += "&client_id=" + client_id;
		body += "&client_secret=" + client_secret;
		callAuthorizationApi(body);
	}

	function refreshAccessToken(){
		refresh_token = localStorage.getItem("refresh_token");
		let body = "grant_type=refresh_token";
		body += "&refresh_token=" + refresh_token;
		body += "&client_id=" + client_id;
		callAuthorizationApi(body);
	}

	function callAuthorizationApi(body){
		let xhr = new XMLHttpRequest();
		xhr.open("POST", TOKEN, true);
		xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		xhr.setRequestHeader('Authorization', 'Basic ' + btoa(client_id + ":" + client_secret));
		xhr.send(body);
		xhr.onload = handleAuthorizationResponse;
	}

	function handleAuthorizationResponse() {
		if (this.status === 200) {
			var data = JSON.parse(this.responseText);
			console.log(data);

			if (data.access_token !== undefined) {
				access_token = data.access_token;
				localStorage.setItem("access_token", access_token);

				if (data.refresh_token !== undefined) {
					refresh_token = data.refresh_token;
					localStorage.setItem("refresh_token", refresh_token);
				}
			}

			onPageLoad();
		} else {
			console.log(this.responseText);
			/*alert(this.responseText);*/
		}
	}

				
	document.addEventListener("DOMContentLoaded", function() {
		setTimeout(showContainer, 2000);
	});

	function showContainer() {
		document.querySelector(".container").style.display = "block";
	}

	function handleAuthorizationResponse2() {
		access_token = localStorage.getItem("access_token");
		fetch("@@@" + access_token);
		/*alert(access_token);*/
	}