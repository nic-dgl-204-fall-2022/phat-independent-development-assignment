const signupForm = document.getElementById("signup-form");
const validationElement = document.getElementById("validation-message");
const errorContainerBlock = document.querySelector(".validation__error");
const errorMessage = document.getElementById("error-message");
const successContainerBlock = document.querySelector(".validation__success");
const successMessage = document.getElementById("success-message");

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

signupForm.addEventListener("submit", async (e) => {
	e.preventDefault();
	toggleErrorMessage(false);

	const username = document.getElementById("username");
	const password = document.getElementById("password");
	const confirm = document.getElementById("confirm-password");

	if (username.value.length < 6 || username.value.length > 30) {
		return toggleErrorMessage(true, "Username must contain from 6 to 30 characters.");
	}

	if (username.value.search(/^[a-zA-Z0-9_.-]*$/) === -1) {
		return toggleErrorMessage(true, "Username must contain only numbers and letters.");
	}

	if (password.value !== confirm.value) {
		return toggleErrorMessage(true, "Password does not match Confirm.");
	}

	try {
		const rawResponse = await fetch(SERVER_API_ROUTES.signupRoute, {
			method: "POST",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				username: username.value,
				password: password.value,
				confirm: confirm.value,
			}),
		});

		const content = await rawResponse.json();
		console.log(content);
		if (content.message == "OK" && content.statusCode === "201") {
			toggleSuccessMessage(true, "Successfully registered. Now you can log in.");
		} else {
			toggleErrorMessage(true, content.error);
		}
	} catch (error) {
		toggleErrorMessage(true, "Server error");
	}
});

function main() {
	const loggedIn = isLoggedIn();
	if (loggedIn) {
		redirectTo(CLIENT_PAGES.pokemonPage);
	}
}
main();
