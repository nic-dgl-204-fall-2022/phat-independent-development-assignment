// Remove '.active' from the current active one.
function deactiveCurrentTabLink() {
	const activeTab = document.querySelector(".tabs__list__item.active");
	if (activeTab) {
		activeTab.classList.remove("active");
	}
}

// ==== CREATE ELEMENTS ====
function createPokemonCard(pokemon) {
	// Level
	const levelContainer = document.createElement("div");
	levelContainer.className = "card__level";
	const levelElement = document.createElement("span");
	levelElement.textContent = `Lv.${pokemon.level}`;
	levelContainer.appendChild(levelElement);

	// Image
	const imgContainer = document.createElement("div");
	imgContainer.className = "card__pokemon_img";
	const pokemonImg = document.createElement("img");
	pokemonImg.src = `../../dist/img/pokemon/${pokemon.imgName}`;
	pokemonImg.alt = pokemon.name;
	imgContainer.appendChild(pokemonImg);

	// Power
	const powerContainer = document.createElement("div");
	powerContainer.className = "card__power";
	const powerIcon = document.createElement("i");
	powerIcon.className = "fa-solid fa-bolt";
	const powerTitle = document.createElement("span");
	powerTitle.textContent = "Power:";
	const powerPoints = document.createElement("span");
	powerPoints.textContent = pokemon.power;
	powerContainer.appendChild(powerIcon);
	powerContainer.appendChild(powerTitle);
	powerContainer.appendChild(powerPoints);

	// Card Content
	const cardContentElement = document.createElement("div");
	cardContentElement.className = "card__content";
	cardContentElement.appendChild(levelContainer);
	cardContentElement.appendChild(imgContainer);
	cardContentElement.appendChild(powerContainer);

	// Card Footer
	const cardFooterElement = document.createElement("div");
	cardFooterElement.className = "card__footer";
	const pokemonNameElement = document.createElement("p");
	pokemonNameElement.textContent = pokemon.name;
	cardFooterElement.appendChild(pokemonNameElement);

	// Card
	const cardElement = document.createElement("div");
	cardElement.className = "card hoverable";
	cardElement.appendChild(cardContentElement);
	cardElement.appendChild(cardFooterElement);

	// Wrapper
	const tabItemElement = document.createElement("div");
	tabItemElement.classList = "tabs__item";
	tabItemElement.id = pokemon.id;
	tabItemElement.appendChild(cardElement);

	return tabItemElement;
}

function createPokemonStats(pokemon, hasUseItemBtn = false) {
	// Level
	const pkmLevelContainer = document.createElement("div");
	pkmLevelContainer.className = "card__level";
	const pkmLevel = document.createElement("span");
	pkmLevel.textContent = `Lv.${pokemon.level}`;
	pkmLevelContainer.appendChild(pkmLevel);

	//Image
	const pkmImgContainer = document.createElement("div");
	pkmImgContainer.className = "card__pokemon_img";
	const pkmImg = document.createElement("img");
	pkmImg.src = `../../dist/img/pokemon/${pokemon.imgName}`;
	pkmImg.alt = pokemon.name;
	pkmImgContainer.appendChild(pkmImg);

	// Power
	const pkmPowerContainer = document.createElement("div");
	pkmPowerContainer.className = "card__power";
	const pkmPowerIcon = document.createElement("i");
	pkmPowerIcon.className = "fa-solid fa-bolt";
	const pkmPowerTitle = document.createElement("span");
	pkmPowerTitle.textContent = "Power:";
	const pkmPowerPoint = document.createElement("span");
	pkmPowerPoint.textContent = pokemon.power;
	pkmPowerContainer.appendChild(pkmPowerIcon);
	pkmPowerContainer.appendChild(pkmPowerTitle);
	pkmPowerContainer.appendChild(pkmPowerPoint);

	// Card Content
	const statsCardContent = document.createElement("div");
	statsCardContent.className = "card__content";
	statsCardContent.appendChild(pkmLevelContainer);
	statsCardContent.appendChild(pkmImgContainer);
	statsCardContent.appendChild(pkmPowerContainer);

	// Card Footer
	const footerCardContainer = document.createElement("div");
	footerCardContainer.className = "card__footer";
	const pkmName = document.createElement("p");
	pkmName.textContent = pokemon.name;
	footerCardContainer.appendChild(pkmName);

	// Card
	const statsCard = document.createElement("div");
	statsCard.className = "pokemon-stats__card card";
	statsCard.appendChild(statsCardContent);
	statsCard.appendChild(footerCardContainer);

	// Exp
	const expContainer = document.createElement("div");
	expContainer.className = "pokemon-stats__experience";
	const expText = document.createElement("p");
	expText.innerHTML = `<span>${pokemon.expPoints}</span> / ${pokemon.maxExpPoints}`;
	expContainer.appendChild(expText);

	// Use items button
	const useItemContainer = document.createElement("div");
	useItemContainer.className = "pokemon-stats__group-button";
	const useItemBtn = document.createElement("a");
	useItemBtn.id = "open-use-items-modal-btn";
	useItemBtn.className = "pokemon-stats__group-button__button";
	useItemBtn.href = "#";
	useItemBtn.textContent = "Use Items";
	useItemBtn.addEventListener("click", (e) => {
		closeAllModals();
		document.getElementById("use-items-modal")?.classList.add("show");
	});
	useItemContainer.appendChild(useItemBtn);

	// Pokemon Stats Element
	const pkmStatsContainer = document.createElement("div");
	pkmStatsContainer.className = "pokemon-stats";
	pkmStatsContainer.appendChild(statsCard);
	pkmStatsContainer.appendChild(expContainer);
	if (hasUseItemBtn) {
		pkmStatsContainer.appendChild(useItemContainer);
	}

	return pkmStatsContainer;
}

async function main() {
	// ==== Pokemon ====
	let pokemonIdList = [];
	// ==== Item ====
	let itemIdList = [];

	const loggedIn = isLoggedIn();
	if (!loggedIn) {
		return redirectTo(CLIENT_PAGES.loginPage);
	}

	const jwtToken = getJwtToken();
	const pokemonList = await getPokemon();
	const ownedPokemon = pokemonList.filter((pkm) => pkm.status === "OWNED");
	const wildPokemon = pokemonList.filter((pkm) => pkm.status === "WILD");

	const ownedPokemonContainer = document.getElementById("owned-pokemon");
	ownedPokemon.forEach((pokemon) => {
		const pokemonCardElement = createPokemonCard(pokemon);
		ownedPokemonContainer.appendChild(pokemonCardElement);
	});

	const wildPokemonContainer = document.getElementById("wild-pokemon");
	wildPokemon.forEach((pokemon) => {
		const pokemonCardElement = createPokemonCard(pokemon);
		wildPokemonContainer.appendChild(pokemonCardElement);
	});

	// ====== Event Handlers ======
	// Tabs
	const ownedTabLink = document.getElementById("tab-owned-link");
	const wildTabLink = document.getElementById("tab-wild-link");
	const ownedTab = document.getElementById("owned-pokemon");
	const wildTab = document.getElementById("wild-pokemon");
	const pokemonAmount = document.getElementById("amount");

	// Show the total number of Pokemon
	if (wildTabLink.parentNode.classList.contains("active")) {
		pokemonAmount.textContent = wildPokemon.length;
	}
	if (ownedTabLink.parentNode.classList.contains("active")) {
		pokemonAmount.textContent = ownedPokemon.length;
	}

	// Open wild tab
	wildTabLink.addEventListener("click", () => {
		wildTab.classList.add("show");
		ownedTab.classList.remove("show");
		deactiveCurrentTabLink();
		wildTabLink.parentNode.classList.add("active");
		pokemonAmount.textContent = wildPokemon.length;
	});

	// Open owned tab
	ownedTabLink.addEventListener("click", () => {
		ownedTab.classList.add("show");
		wildTab.classList.remove("show");

		deactiveCurrentTabLink();
		ownedTabLink.parentNode.classList.add("active");
		pokemonAmount.textContent = ownedPokemon.length;
	});

	// ==== Modals ====
	// Pokemon modal
	const backToPokemonModalButton = document.getElementById("back-to-pokemon-modal-btn");
	const pokemonCards = document.querySelectorAll(".card.hoverable");
	const pokemonModal = document.getElementById("pokemon-modal");

	// Handle back buttons
	backToPokemonModalButton.addEventListener("click", (e) => {
		closeAllModals();
		pokemonModal.classList.add("show");
	});

	// Open Pokemon Modal
	pokemonCards.forEach((element) => {
		element.addEventListener("click", () => {
			pokemonModal.classList.add("show");
			const pokemonId = element.parentNode.id;
			const pokemon = pokemonList.find((pkm) => pkm.id === pokemonId);

			if (pokemon === -1) {
				return;
			}

			const pkmStatsContainer = document.querySelectorAll(".pokemon-stats-container");
			// Clear previous pokemon stats
			pkmStatsContainer.forEach((e) => (e.innerHTML = ""));
			// Add pokemon stats to modals
			pkmStatsContainer.forEach((e) => {
				const hasUseItemBtn = e.id === "pokemon-modal-stats";
				const pkmStatsCard = createPokemonStats(pokemon, hasUseItemBtn);

				e.appendChild(pkmStatsCard);
			});
		});
	});

	// Use items Modal
	// const useItemsModal = document.getElementById("use-items-modal");
	// const openUseItemsModalButton = document.getElementById("open-use-items-modal-btn");
	// openUseItemsModalButton.addEventListener("click", (e) => {
	// 	closeAllModals();
	// 	useItemsModal.classList.add("show");
	// });

	// Show content for items
	const openConsumableBtn = document.getElementById("open-consumable-items");
	const openMysticBtn = document.getElementById("open-mystic-items");
	const consumableItemsContainer = document.getElementById("consumable-items");
	const mysticItemsContainer = document.getElementById("mystic-items");

	openConsumableBtn.addEventListener("click", (e) => {
		consumableItemsContainer.classList.add("show");
		openConsumableBtn.parentElement.classList.add("active");

		mysticItemsContainer.classList.remove("show");
		openMysticBtn.parentElement.classList.remove("active");
	});

	openMysticBtn.addEventListener("click", (e) => {
		mysticItemsContainer.classList.add("show");
		openMysticBtn.parentElement.classList.add("active");

		consumableItemsContainer.classList.remove("show");
		openConsumableBtn.parentElement.classList.remove("active");
	});

	// Select items
	const itemInputs = document.querySelectorAll("input.item-amount");

	itemInputs.forEach((itemInput) => {
		itemInput.addEventListener("change", (e) => {
			const { value, id } = e.target;
			const containerId = `item-${id.split("-amount")[0]}`;
			const containerElement = document.getElementById(containerId);

			if (value > 0) {
				containerElement.classList.add("selected");
			} else {
				containerElement.classList.remove("selected");
			}
		});
	});
}

main();
