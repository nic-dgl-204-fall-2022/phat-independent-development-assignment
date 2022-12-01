function redirectToProfile() {
	window.location.replace("http://localhost:5500/src/auth/profile.html");
}

function isLoggedIn() {
	if (jwtToken) {
		redirectToProfile();
	}
}

function getJwtToken() {
	return localStorage.getItem("pkm-jwt");
}
