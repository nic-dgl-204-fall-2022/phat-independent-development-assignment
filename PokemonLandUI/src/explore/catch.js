// Remove the class 'selected' for all the Pokeball
function unselectAllPokeball() {
	const selectPokeballBtns = document.querySelectorAll(".items-list__item.pokeball");
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
	pkmStatsContainer.innerHTML = ``;
	pkmStatsContainer.appendChild(wildPkmStatsCard);
}

function createPkbItem(pokeball) {
	// Select box
	const selectBoxContainer = document.createElement("div");
	selectBoxContainer.className = "items-list__item__select-box";
	const selectIcon = document.createElement("i");
	selectIcon.className = "fa-solid fa-check";
	selectBoxContainer.appendChild(selectIcon);

	// Img Holder
	const imgContainer = document.createElement("div");
	imgContainer.className = "items-list__item__img-holder";
	const pkmImg = document.createElement("img");
	pkmImg.src = `../../dist/img/items/${pokeball.imgName}`;
	pkmImg.alt = pokeball.name;
	imgContainer.appendChild(pkmImg);

	// Details
	const detailContainer = document.createElement("div");
	detailContainer.className = "items-list__item__details";
	const detailName = document.createElement("p");
	detailName.className = "items-list__item__details__name";
	detailName.textContent = pokeball.name;
	const detailAffect = document.createElement("p");
	detailAffect.className = "items-list__item__details__affect";
	for (const [key, value] of Object.entries(pokeball.affect)) {
		detailAffect.innerHTML = `${key}: <span>${value}%</span>`;
	}
	const pkmAmount = document.createElement("p");
	pkmAmount.className = "items-list__item__details__quantity";
	pkmAmount.innerHTML = `Amount: <span>${pokeball.amount}</span>`;
	detailContainer.appendChild(detailName);
	detailContainer.appendChild(detailAffect);
	detailContainer.appendChild(pkmAmount);

	// Button wrapper
	const buttonWrapper = document.createElement("button");
	buttonWrapper.id = pokeball.id;
	buttonWrapper.className = "items-list__item pokeball";
	buttonWrapper.appendChild(selectBoxContainer);
	buttonWrapper.appendChild(imgContainer);
	buttonWrapper.appendChild(detailContainer);
	buttonWrapper.addEventListener("click", () => {
		unselectAllPokeball();
		buttonWrapper.classList.add("selected");
	});

	return buttonWrapper;
}

function displayPokeballItems(pokeballItems) {
	const pokemonList = document.getElementById("pokeball-list");
	pokemonList.innerHTML = ``;
	pokeballItems.forEach((pkbItem) => {
		const pkbButtonItem = createPkbItem(pkbItem);
		pokemonList.appendChild(pkbButtonItem);
	});
}

async function main() {
	// Check jwt token
	const loggedIn = isLoggedIn();
	if (!loggedIn) {
		redirectTo(CLIENT_PAGES.loginPage);
		return;
	}

	// Get PokemonID from query params
	const { pokemonId } = new Proxy(new URLSearchParams(window.location.search), {
		get: (searchParams, prop) => searchParams.get(prop),
	});

	const jwtToken = getJwtToken();
	const wildPokemon = await findPokemonId(pokemonId);
	displayWildPokemon(wildPokemon);

	const itemList = await getItems();
	const ownedItems = await getOwnedItemIds(jwtToken);
	const usableItems = await getUsableItems(ownedItems, itemList);
	const pokeballItems = usableItems.filter((item) => item.type === "Pokeball");
	displayPokeballItems(pokeballItems);

	const catchBlock = document.getElementById("catch");
	const usePokeballBtn = document.getElementById("use-pokeball-btn");
	const pokeballElements = document.querySelectorAll(".pokeball");

	// Results
	const resultSuccessBlock = document.getElementById("result-success");
	const resultFailBlock = document.getElementById("result-fail");

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
