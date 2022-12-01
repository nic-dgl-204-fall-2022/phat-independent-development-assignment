const battleContentElement = document.querySelectorAll(".battle");
const inBattleElement = document.getElementById("in-battle");

// Hide all battle content
function openBattleContentById(elementId) {
	battleContentElement.forEach((element) => {
		if (element.id == elementId) {
			element.classList.add("show");
		} else {
			element.classList.remove("show");
		}
	});
}

// Select Pokemon
const pokemonElements = document.querySelectorAll(".select__pokemon");

// Unselect all pokemon
function unselectAllPokemon() {
	pokemonElements.forEach((pkm) => pkm.classList.remove("selected"));
}

pokemonElements.forEach((pkm) => {
	pkm.addEventListener("click", () => {
		unselectAllPokemon();
		pkm.classList.add("selected");
	});
});


const battleBtn = document.getElementById("battle-btn");
const resultSuccessElement = document.getElementById('result-success');
const resultFailElement = document.getElementById('result-fail');

battleBtn.addEventListener("click", () => {
	const selectedPokemon = document.querySelector(".select__pokemon.selected");
	if (selectedPokemon) {
		openBattleContentById(inBattleElement.id);

        // Delays 3 seconds and show the result
        setTimeout(() => {
            inBattleElement.classList.remove("show");
            // Show won result
            resultSuccessElement.classList.add("show");

            // Show lost result
            resultFailElement.classList.add("show");
        }, 3000)
	}
});

const showRewardsBtn = document.getElementById("show-rewards-btn");
const rewardsElement = document.getElementById('rewards');
showRewardsBtn.addEventListener('click', () => {
    openBattleContentById(rewardsElement.id);
})
