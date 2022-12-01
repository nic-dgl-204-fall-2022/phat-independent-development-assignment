const logoutBtn = document.getElementById("logout-btn");

logoutBtn.addEventListener('click', () => {
    setJwtToken(null)
    redirectTo(CLIENT_PAGES.loginPage)
})

function main() {

}

main() 