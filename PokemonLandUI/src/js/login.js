const loginForm = document.getElementById("login-form");

loginForm.addEventListener("submit", async (e) => {
	e.preventDefault();

	const username = document.getElementById("username");
	const password = document.getElementById("password");

	try {
		const rawResponse = await fetch(SERVER_API_ROUTES.loginRoute, {
			method: "POST",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
			},
			body: JSON.stringify({ username: username.value, password: password.value }),
		});

		const content = await rawResponse.json();
		if (content.message == "OK" && content.token) {
			setJwtToken(content.token)
		}

		return redirectTo(CLIENT_PAGES.profilePage)
	} catch (error) {
		console.log(error);
	}
});

function main () {
    const loggedIn = isLoggedIn()
    if (loggedIn) {
        redirectTo(CLIENT_PAGES.pokemonPage)
    }
}
main()