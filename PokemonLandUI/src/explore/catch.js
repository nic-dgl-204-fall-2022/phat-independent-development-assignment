const catchBlock = document.getElementById("catch");
const usePokeballBtn = document.getElementById("use-pokeball-btn");
const selectPokeballBtns = document.querySelectorAll(".items-list__item.pokeball");
const pokeballElements = document.querySelectorAll(".pokeball");

// Results
const resultSuccessBlock = document.getElementById("result-success")
const resultFailBlock = document.getElementById("result-fail");

// Remove the class 'selected' for all the Pokeball
function unselectAllPokeball () {
    selectPokeballBtns.forEach((button) => {
        button.classList.remove("selected");
    })
}

// Add class 'selected' for a Pokeball
selectPokeballBtns.forEach((button) => {
	button.addEventListener("click", () => {
        unselectAllPokeball();
		button.classList.add("selected");
	});
});

// Show the result
usePokeballBtn.addEventListener("click", () => {
    catchBlock.classList.remove("show");

    let successfulResult = false;
    // Display the success result if the first Pokeball is selected.
    pokeballElements.forEach(pokeball => {
        if (pokeball.classList.contains("selected")) {
            if (pokeball.id === 'pokeball-2') {
                successfulResult = true
            } else {
                successfulResult = false
            }
        }
    })

    if (successfulResult) {
        resultSuccessBlock.classList.add("show");

        resultFailBlock.classList.remove("show");
        catchBlock.classList.remove("show");

    } else {
        resultFailBlock.classList.add("show");

        resultSuccessBlock.classList.remove("show");
        catchBlock.classList.remove("show");
    }
});
