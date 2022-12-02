const backToPokemonModalButton = document.getElementById("back-to-pokemon-modal-btn");
const pokemonCards = document.querySelectorAll(".card.hoverable");
const pokemonModal = document.getElementById("pokemon-modal");

// Tabs
const ownedTabLink = document.getElementById("tab-owned-link");
const wildTabLink = document.getElementById("tab-wild-link");
const ownedTab = document.getElementById("owned-pokemon");
const wildTab = document.getElementById("wild-pokemon");

// Remove '.active' from the current active one.
function deactiveCurrentTabLink() {
	const activeTab = document.querySelector(".tabs__list__item.active");
	if (activeTab) {
		activeTab.classList.remove("active");
	}
}

// Open wild tab
wildTabLink.addEventListener("click", () => {
	wildTab.classList.add("show");
	ownedTab.classList.remove("show");
	deactiveCurrentTabLink();
	wildTabLink.parentNode.classList.add("active");
});

// Open owned tab
ownedTabLink.addEventListener("click", () => {
	ownedTab.classList.add("show");
	wildTab.classList.remove("show");

	deactiveCurrentTabLink();
	ownedTabLink.parentNode.classList.add("active");
});

// Handle back buttons
backToPokemonModalButton.addEventListener("click", (e) => {
	closeAllModals();
	pokemonModal.classList.add("show");
});

// Open Pokemon Modal
pokemonCards.forEach((element) => {
	element.addEventListener("click", (e) => {
		pokemonModal.classList.add("show");
	});
});

// Open Use items Modal
const openUseItemsModalButton = document.getElementById("open-use-items-modal-btn");
const useItemsModal = document.getElementById("use-items-modal");
openUseItemsModalButton.addEventListener("click", (e) => {
	closeAllModals();
	useItemsModal.classList.add("show");
});

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
	// pokemonImg.src = `../../dist/img/${pokemon.imgName}`;
	pokemonImg.src = `../../dist/img/charmander.svg`;
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

    return tabItemElement
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
}

main();
