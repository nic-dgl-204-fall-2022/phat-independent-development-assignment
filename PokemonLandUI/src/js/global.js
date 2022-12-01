const SERVER_API_ORIGIN_URL = "http://0.0.0.0:8080/api/";
const SERVER_API_ROUTES = {
	loginRoute: `${SERVER_API_ORIGIN_URL}/login`,
};

const CLIENT_ORIGIN_URL = window.location.origin + "/src";
const CLIENT_PAGES = {
	loginPage: `${CLIENT_ORIGIN_URL}/login.html`,
	signupPage: `${CLIENT_ORIGIN_URL}/signup.html`,
	profilePage: `${CLIENT_ORIGIN_URL}/profile.html`,
	pokemonPage: `${CLIENT_ORIGIN_URL}/pokemon/index.html`,
};
const JWT_STORAGE_NAME = "pkm-jwt";

// Auth Guard
function isLoggedIn() {
	const jwtToken = localStorage.getItem(JWT_STORAGE_NAME);
	if (jwtToken && JSON.parse(jwtToken).token != null) {
		return true;
	}

	return false;
}

// Get JWT TOken
function getJwtToken() {
	if (!isLoggedIn) {
		return null;
	}
	const jwtToken = JSON.parse(localStorage.getItem(JWT_STORAGE_NAME));
	return jwtToken.token;
}

// Set Jwt Token
function setJwtToken(token) {
	localStorage.setItem(JWT_STORAGE_NAME, JSON.stringify({ token }));
}

// Redirect to
function redirectTo(page) {
	window.location.replace(page);
}

function isProtectedURL(currentUrl) {
	return Object.values(CLIENT_PAGES).findIndex((url) => url == currentUrl) != -1;
}

// ===== Handle Modals =====
const modals = document.querySelectorAll(".modal");
const closeModalButtons = document.querySelectorAll(".modal__header__close-btn");

// Close all modals
function closeAllModals() {
	modals.forEach((modal) => {
		modal.classList.remove("show");
	});
}

closeModalButtons.forEach((button) => {
	button.addEventListener("click", closeAllModals);
});
