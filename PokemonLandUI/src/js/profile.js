const logoutBtn = document.getElementById("logout-btn");
logoutBtn.addEventListener("click", () => {
	removeStorages();
	redirectTo(CLIENT_PAGES.loginPage);
});

const profileForm = document.getElementById("profile-form");
profileForm.addEventListener("submit", (e) => {
	e.preventDefault();
});

const levelElement = document.getElementById("level");
const usernameElement = document.getElementById("username");
const expElement = document.getElementById("exp");
const maxExpElement = document.getElementById("max-exp");
const coinsElement = document.getElementById("coins");
const nameInputElement = document.getElementById("name");
const emailInputElement = document.getElementById("email");
const phoneInputElement = document.getElementById("phone");

async function main() {
	const loggedIn = isLoggedIn();
	if (!loggedIn) {
		return redirectTo(CLIENT_PAGES.loginPage);
	}

	const jwtToken = getJwtToken();

	try {
		const rawResponse = await fetch(SERVER_API_ROUTES.profileRoute, {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
				"Authorization": `Bearer ${jwtToken}`,
			},
		});

		const content = await rawResponse.json();
		if (content.message == "OK" && content.statusCode.toString() === "200") {
			const responseData = content.data;
			levelElement.textContent = `Lv.${responseData.level}`;
			usernameElement.textContent = responseData.username;
			expElement.textContent = responseData.expPoints;
			maxExpElement.textContent = responseData.maxExpPoints;
			coinsElement.textContent = responseData.coins;
			nameInputElement.value = responseData.name;
			emailInputElement.value = responseData.email;
			phone.value = responseData.phone;
		}
        
	} catch (error) {
		console.log(error);
	}
}
main();