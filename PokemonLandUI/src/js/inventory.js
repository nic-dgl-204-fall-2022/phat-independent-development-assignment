const pokeballTabLink = document.getElementById("tab-pokeball-link");
const mysticTabLink = document.getElementById("tab-mystic-link");
const consumableTabLink = document.getElementById("tab-consumable-link");
// Tabs
const pokeballTab = document.getElementById("pokeball-items");
const mysticTab = document.getElementById("mystic-items");
const consumableTab = document.getElementById("consumable-items");
// Cards
const pokeballCards = document.querySelectorAll(".hoverable.pokeball-item");
const mysticCards = document.querySelectorAll(".hoverable.mystic-item");
const consumableCards = document.querySelectorAll(".hoverable.consumable-item");

// Modal
const pokeballModal = document.getElementById("pokeball-modal");
const mysticItemModal = document.getElementById("mystic-item-modal");
const consumableItemModal = document.getElementById("consumable-item-modal");
const choosePokemonModal = document.getElementById("choose-pokemon-modal");

// Open Choose Pokemon Modal Buttons
const openPokemonModalBtns = document.querySelectorAll(".open-pokemon-list-modal-btn");

// Back Buttons
const backToMysticButton = document.getElementById("back-to-mystic-modal-btn");

// Pokemon buttons
const selectPokemonBtns = document.querySelectorAll(".items-list__item");

function closeAllTabs() {
	pokeballTab.classList.remove("show");
	mysticTab.classList.remove("show");
	consumableTab.classList.remove("show");

	const activeTab = document.querySelector(".tabs__list__item.active");
	if (activeTab) {
		activeTab.classList.remove("active");
	}
}

function unselectAllPokemon() {
	selectPokemonBtns.forEach((button) => {
		button.classList.remove("selected");
	});
}

// Tab links
pokeballTabLink.addEventListener("click", (e) => {
	closeAllTabs();
	pokeballTab.classList.add("show");
	e.target.parentNode.classList.add("active");
});
mysticTabLink.addEventListener("click", (e) => {
	closeAllTabs();
	mysticTab.classList.add("show");
	e.target.parentNode.classList.add("active");
});
consumableTabLink.addEventListener("click", (e) => {
	closeAllTabs();
	consumableTab.classList.add("show");
	e.target.parentNode.classList.add("active");
});

// Modal
// Open Pokeball Modal
pokeballCards.forEach((element) => {
	element.addEventListener("click", (e) => {
		pokeballModal.classList.add("show");
	});
});

// Open Mystic Item Modal
mysticCards.forEach((element) => {
	element.addEventListener("click", (e) => {
		mysticItemModal.classList.add("show");
		// Open Consumable Item Modal
		consumableCards.forEach((element) => {
			element.addEventListener("click", (e) => {
				consumableItemModal.classList.add("show");
			});
		});

		// Open Choose Pokemon Modal
		openPokemonModalBtns.forEach((button) => {
			button.addEventListener("click", () => {
				closeAllModals();
				choosePokemonModal.classList.add("show");
			});
		});

		// Select Pokemon to use item
		selectPokemonBtns.forEach((button) => {
			button.addEventListener("click", (e) => {
				e.preventDefault();
				unselectAllPokemon();
				button.classList.add("selected");
			});
		});

		// Back buttons
		// Back to Mystic modal
		backToMysticButton.addEventListener("click", (e) => {
			closeAllModals();
			mysticItemModal.classList.add("show");
		});
	});
});

function createItemElement(pokeball) {
	// Image
	const itemImgContainer = document.createElement("div");
	itemImgContainer.className = "card__item-img";
	const itemImg = document.createElement("img");
	itemImg.src = `../../dist/img/items/${pokeball.imgName}`;
	itemImg.alt = pokeball.name;
	itemImgContainer.appendChild(itemImg);
	//Amount
	const itemAmountContainer = document.createElement("div");
	itemAmountContainer.className = "card__item-amount";
	const itemAmount = document.createElement("p");
	itemAmount.innerHTML = `Amount <span>${pokeball.amount}</span>`;
	itemAmountContainer.appendChild(itemAmount);
	// Card content
	const cardContent = document.createElement("div");
	cardContent.className = "card__content";
	cardContent.appendChild(itemImgContainer);
	cardContent.appendChild(itemAmountContainer);
	// Card Footer
	const cardFooter = document.createElement("div");
	cardFooter.className = "card__footer";
	const pokeballName = document.createElement("p");
	pokeballName.textContent = pokeball.name;
	cardFooter.appendChild(pokeballName);
	// Card Container
	const cardContainer = document.createElement("div");
	cardContainer.className = "card hoverable pokeball-item";
	cardContainer.appendChild(cardContent);
	cardContainer.appendChild(cardFooter);
	// Container
	const itemContainer = document.createElement("div");
	itemContainer.className = "tabs__item";
	itemContainer.appendChild(cardContainer);

	return itemContainer;
}

function addPokeBallItems(pokeballItems) {
	const pokeballItemsContainer = document.getElementById("pokeball-items");
	pokeballItems.forEach((pokeball) => {
		const pkbItem = createItemElement(pokeball);
		pokeballItemsContainer.appendChild(pkbItem);
	});
}

function addMysticItems(mysticItems) {
	const mysticItemsContainer = document.getElementById("mystic-items");
	mysticItems.forEach((mystic) => {
		const mysticItem = createItemElement(mystic);
		mysticItemsContainer.appendChild(mysticItem);
	});
}

function addConsumableItems(consumableItems) {
	const consumableItemsContainer = document.getElementById("consumable-items");
	consumableItems.forEach((consumable) => {
		const consumableItem = createItemElement(consumable);
		consumableItemsContainer.appendChild(consumableItem);
	});
}

async function main() {
	// Check jwt token
	const loggedIn = isLoggedIn();
	if (!loggedIn) {
		return redirectTo(CLIENT_PAGES.loginPage);
	}

	const jwtToken = getJwtToken();
	const ownedItemIds = await getOwnedItemIds(jwtToken);
	const itemList = await getItems();
	const usableItems = await getUsableItems(ownedItemIds, itemList);
	const pokeballItems = usableItems.filter((i) => i.type === "Pokeball");
	addPokeBallItems(pokeballItems);
	const mysticItems = usableItems.filter((i) => i.type === "MysticItems");
	addMysticItems(mysticItems);
	const consumableItems = usableItems.filter((i) => i.type === "ConsumableItems");
	addConsumableItems(consumableItems);
}
main();
