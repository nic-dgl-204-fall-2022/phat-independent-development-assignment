// Remove the class 'selected' for all the Pokeball
function unselectAllPokeball() {
	selectPokeballBtns.forEach((button) => {
		button.classList.remove("selected");
	});
}

function createPokemonStatsCard(pokemon) {
	// Power
	const powerContainer = document.createElement("div");
	powerContainer.className = "card__power catch__card__power";
	const powerIcon = document.createElement("i");
	powerIcon.className = "fa-solid fa-bolt";
	const powerTitle = document.createElement("span");
	powerTitle.textContent = "Power:";
	const powerPoints = document.createElement("span");
	powerPoints.textContent = pokemon.power;
	powerContainer.appendChild(powerIcon);
	powerContainer.appendChild(powerTitle);
	powerContainer.appendChild(powerPoints);

	//Img
	const imgContainer = document.createElement("div");
	imgContainer.className = "card__pokemon_img catch__card__img";
	const pkmImg = document.createElement("img");
	pkmImg.src = `../../dist/img/pokemon/${pokemon.imgName}`;
	pkmImg.alt = pokemon.name;
	imgContainer.appendChild(pkmImg);

	// Level
	const levelContainer = document.createElement("div");
	levelContainer.className = "card__level catch__card_level";
	const levelElement = document.createElement("span");
	levelElement.textContent = `Lv.${pokemon.level}`;
	levelContainer.appendChild(levelElement);

	// Pokemon Status
	const pkmStatusContainer = document.createElement("div");
	pkmStatusContainer.className = "pokemon-status";
	const pkmStatusImg = document.createElement("img");
	pkmStatusImg.src = `../../dist/img/pokemon-status/sniper-gray.png`;
	pkmStatusImg.alt = "Wild Status Background";
	const pkmStatusText = document.createElement("span");
	pkmStatusText.className = "pokemon-status__desc";
	pkmStatusText.textContent = "Wild";
	pkmStatusContainer.appendChild(pkmStatusImg);
	pkmStatusContainer.appendChild(pkmStatusText);

	// Card Content
	const cardContentContainer = document.createElement("div");
	cardContentContainer.className = "card__content catch__card__content";
	cardContentContainer.appendChild(pkmStatusContainer);
	cardContentContainer.appendChild(levelContainer);
	cardContentContainer.appendChild(imgContainer);
	cardContentContainer.appendChild(powerContainer);

	// Card Footer
	const cardFooterContainer = document.createElement("div");
	cardFooterContainer.className = "card__footer catch__card__footer";
	const pkmName = document.createElement("p");
	pkmName.textContent = pokemon.name;
	cardFooterContainer.appendChild(pkmName);

	// Stats Card
	const pokemonStatsCard = document.createElement("div");
	pokemonStatsCard.className = "pokemon-stats__card card catch__card";
	pokemonStatsCard.appendChild(cardContentContainer);
	pokemonStatsCard.appendChild(cardFooterContainer);

	return pokemonStatsCard;
}

function displayWildPokemon(wildPokemon) {
	const wildPkmStatsCard = createPokemonStatsCard(wildPokemon);
	const pkmStatsContainer = document.querySelector("#pokemon-stats");
	// pkmStatsContainer.innerHTML = ``;
	console.log(pkmStatsContainer);
	pkmStatsContainer.appendChild(wildPkmStatsCard);
}

async function main() {
	// Get PokemonID from query params
	const { pokemonId } = new Proxy(new URLSearchParams(window.location.search), {
		get: (searchParams, prop) => searchParams.get(prop),
	});

	const wildPokemon = await findPokemonId(pokemonId);
	displayWildPokemon(wildPokemon);

	const catchBlock = document.getElementById("catch");
	const usePokeballBtn = document.getElementById("use-pokeball-btn");
	const selectPokeballBtns = document.querySelectorAll(".items-list__item.pokeball");
	const pokeballElements = document.querySelectorAll(".pokeball");
	// Results
	const resultSuccessBlock = document.getElementById("result-success");
	const resultFailBlock = document.getElementById("result-fail");

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
		pokeballElements.forEach((pokeball) => {
			if (pokeball.classList.contains("selected")) {
				if (pokeball.id === "pokeball-2") {
					successfulResult = true;
				} else {
					successfulResult = false;
				}
			}
		});

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
}

main();
