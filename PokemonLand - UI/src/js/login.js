const loginForm = document.getElementById("login-form");

loginForm.addEventListener("submit", async (e) => {
	e.preventDefault();

	const username = document.getElementById("username");
	const password = document.getElementById("password");

	try {
		const rawResponse = await fetch("http://0.0.0.0:8080/api/login", {
			method: "POST",
			headers: {
				// "Accept": "application/json",
				"Content-Type": "application/json",
			},
			body: JSON.stringify({ username: username.value, password: password.value }),
		});

		const content = await rawResponse.json();

		if (content.message == "OK" && content.token) {
			localStorage.setItem("pkm-jwt", JSON.stringify({ token: content.token }));
		}

		redirectToProfile();
	} catch (error) {
		console.log(error);
	}
});

function main() {
	console.log(window.location.href)
}

main();
