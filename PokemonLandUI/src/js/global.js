const SERVER_API_ORIGIN_URL = "http://0.0.0.0:8080/api";
const SERVER_API_ROUTES = {
	loginRoute: `${SERVER_API_ORIGIN_URL}/login`,
	signupRoute: `${SERVER_API_ORIGIN_URL}/signup`,
	profileRoute: `${SERVER_API_ORIGIN_URL}/profile`,
	itemRoute: `${SERVER_API_ORIGIN_URL}/item`,
	useItemRoute: `${SERVER_API_ORIGIN_URL}/item/use`,
	pokemonRoute: `${SERVER_API_ORIGIN_URL}/pokemon`,
	findPokemonRoute: `${SERVER_API_ORIGIN_URL}/explore/find`,
};
const CLIENT_ORIGIN_URL = window.location.origin + "/src";
const CLIENT_PAGES = {
	loginPage: `${CLIENT_ORIGIN_URL}/login.html`,
	signupPage: `${CLIENT_ORIGIN_URL}/signup.html`,
	profilePage: `${CLIENT_ORIGIN_URL}/profile.html`,
	pokemonPage: `${CLIENT_ORIGIN_URL}/pokemon.html`,
	findPkmPage: `${CLIENT_ORIGIN_URL}/explore/find.html`,
	catchPkmPage: `${CLIENT_ORIGIN_URL}/explore/catch.html`,
	battlePkmPage: `${CLIENT_ORIGIN_URL}/explore/battle.html`,
};
const JWT_STORAGE_NAME = "pkm-jwt";
let currentScroll = 0

// Check if user already logged in
function isLoggedIn() {
	const jwtToken = localStorage.getItem(JWT_STORAGE_NAME);
	if (jwtToken && JSON.parse(jwtToken).token != null) {
		return true;
	} else {
		const sessionJwt = sessionStorage.getItem(JWT_STORAGE_NAME);
		if (sessionJwt && JSON.parse(sessionJwt).token != null) {
			return true;
		}
	}

	return false;
}

// Get JWT TOken
function getJwtToken() {
	if (!isLoggedIn) {
		return null;
	}

	const jwtToken = localStorage.getItem(JWT_STORAGE_NAME);
	if (jwtToken && JSON.parse(jwtToken).token != null) {
		return JSON.parse(jwtToken).token;
	} else {
		const sessionJwt = sessionStorage.getItem(JWT_STORAGE_NAME);
		if (sessionJwt && JSON.parse(sessionJwt).token != null) {
			return JSON.parse(sessionJwt).token;
		}
	}
}

// Set Jwt Token
function setJwtToken(token, saveInLocalStorage = false) {
	if (saveInLocalStorage) {
		localStorage.setItem(JWT_STORAGE_NAME, JSON.stringify({ token }));
	} else {
		sessionStorage.setItem(JWT_STORAGE_NAME, JSON.stringify({ token }));
	}
}
// Remove Jwt Token
function removeStorages() {
    localStorage.clear()
    sessionStorage.clear()
}

// Redirect to
function redirectTo(page) {
	window.location.replace(page);
}

function isProtectedURL(currentUrl) {
	return Object.values(CLIENT_PAGES).findIndex((url) => url == currentUrl) != -1;
}

// ===== Show / Hide Validation Messages =====
function toggleErrorMessage(show = false, errorMsg = "") {
	if (show) {
		validationElement.classList.add("show");
		errorContainerBlock.classList.add("show");
		errorMessage.textContent = errorMsg;
	} else {
		validationElement.classList.remove("show");
		errorContainerBlock.classList.remove("show");
		errorMessage.textContent = "";
	}

	successContainerBlock.classList.remove("show");
	successMessage.textContent = "";
}

function toggleSuccessMessage(show = false, successMsg = "") {
	if (show) {
		validationElement.classList.add("show");
		successContainerBlock.classList.add("show");
		successMessage.textContent = successMsg;
	} else {
		validationElement.classList.remove("show");
		successContainerBlock.classList.remove("show");
		successMessage.textContent = "";
	}

	errorContainerBlock.classList.remove("show");
	errorMessage.textContent = "";
}

// ===== Handle Modals =====
const modals = document.querySelectorAll(".modal");
const closeModalButtons = document.querySelectorAll(".modal__header__close-btn");

// Close all modals
function closeAllModals() {
	modals.forEach((modal) => {
		modal.classList.remove("show");
	});
    // Scroll back to previous coordinates
    window.scrollTo({ top: currentScroll, behavior: 'smooth' });
}
closeModalButtons.forEach((button) => {
	button.addEventListener("click", closeAllModals);
});
