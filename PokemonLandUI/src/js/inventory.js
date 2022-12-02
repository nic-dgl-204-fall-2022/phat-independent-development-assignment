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
const itemModal = document.getElementById("item-modal");

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
		// Trigger Open Item Modal
		pkbItem.addEventListener("click", (e) => {
			itemModal.classList.add("show");
			displayItemInModal(pokeball);
		});
		pokeballItemsContainer.appendChild(pkbItem);
	});
}

function addMysticItems(mysticItems) {
	const mysticItemsContainer = document.getElementById("mystic-items");
	mysticItems.forEach((mystic) => {
		const mysticItem = createItemElement(mystic);
		// Trigger Open Item Modal
		mysticItem.addEventListener("click", (e) => {
			itemModal.classList.add("show");
			displayItemInModal(mystic);
		});
		mysticItemsContainer.appendChild(mysticItem);
	});
}

function addConsumableItems(consumableItems) {
	const consumableItemsContainer = document.getElementById("consumable-items");
	consumableItems.forEach((consumable) => {
		const consumableItem = createItemElement(consumable);
		// Trigger Open Item Modal
		consumableItem.addEventListener("click", (e) => {
			itemModal.classList.add("show");
			displayItemInModal(consumable);
		});
		consumableItemsContainer.appendChild(consumableItem);
	});
}

function displayItemInModal(item) {
	// Buy Button
	const leftColBuyBtnContainer = document.createElement("div");
	leftColBuyBtnContainer.className = "left-col__group-button";
	const buyButton = document.createElement("a");
	buyButton.href = "./shop.html";
	buyButton.className = "left-col__group-button__button";
	buyButton.textContent = "Buy";
	leftColBuyBtnContainer.appendChild(buyButton);

	// Amount
	const leftColAmount = document.createElement("div");
	leftColAmount.className = "left-col__amount";
	const itemAmount = document.createElement("p");
	itemAmount.innerHTML = `Amount <span>${item.amount}</span>`;
	leftColAmount.appendChild(itemAmount);

	// Card Footer
	const cardFooterContainer = document.createElement("div");
	cardFooterContainer.className = "card__footer";
	const itemName = document.createElement("p");
	itemName.textContent = item.name;
	cardFooterContainer.appendChild(itemName);

	// Image
	const itemImgContainer = document.createElement("div");
	itemImgContainer.classList = "card__item-img";
	const itemImg = document.createElement("img");
	itemImg.src = `../../dist/img/items/${item.imgName}`;
	itemImg.alt = item.name;
	itemImgContainer.appendChild(itemImg);

	// Card Content
	const leftCardContent = document.createElement("div");
	leftCardContent.className = "card__content";
	leftCardContent.appendChild(itemImgContainer);
	leftCardContent.appendChild(cardFooterContainer);

	// Left Column Card
	const leftColCard = document.createElement("div");
	leftColCard.className = "left-col__card card";
	leftColCard.appendChild(leftCardContent);
	leftColCard.appendChild(cardFooterContainer);

	// Left Column Container
	const leftColContainer = document.createElement("div");
	leftColContainer.className = "left-col";
	leftColContainer.appendChild(leftColCard);
	leftColContainer.appendChild(leftColAmount);
	leftColContainer.appendChild(leftColBuyBtnContainer);

	// Right Column Header
	const rightColH2 = document.createElement("h2");
	rightColH2.textContent = "Description";
	const rightColText = document.createElement("p");
	rightColText.textContent = item.description;
	// Right Column Affect
	const rightColAffectContainer = document.createElement("div");
	rightColAffectContainer.className = "right-col__affect";
	const rightColAffectTitle = document.createElement("h3");
	rightColAffectTitle.textContent = "Affect";
	const rightColAffectValue = document.createElement("p");
	const affectKey = Object.keys(item.affect)[0];
	rightColAffectValue.innerHTML = `<i class="fa-solid fa-angles-up"></i>${affectKey} <span>+${item.affect[affectKey]}</span>`;
	rightColAffectContainer.appendChild(rightColAffectTitle);
	rightColAffectContainer.appendChild(rightColAffectValue);

	// Right Column content
	const rightColContent = document.createElement("div");
	rightColContent.className = "right-col__content";
	rightColContent.appendChild(rightColH2);
	rightColContent.appendChild(rightColText);
	rightColContent.appendChild(rightColAffectContainer);

	// Right Column
	const rightColContainer = document.createElement("div");
	rightColContainer.className = "right-col";
	rightColContainer.appendChild(rightColContent);

	// Wrapper
	const wrapperElement = document.getElementById("item-modal-content");
	wrapperElement.innerHTML = ``;
	wrapperElement.appendChild(leftColContainer);
	wrapperElement.appendChild(rightColContainer);
	//     <div class="left-col">
	//     <!-- Card-->
	//     <div class="left-col__card card">
	//       <div class="card__content">
	//         <div class="card__item-img">
	//           <img src="../../dist/img/standard-pokeball-640.png" alt="Pikachu">
	//         </div>

	//         <div class="card__item-amount"></div>
	//       </div>

	//       <div class="card__footer">
	//         <p>Standard Pokeball</p>
	//       </div>
	//     </div><!-- End Card-->

	//     <!-- Card  -->
	//     <div class="left-col__amount">
	//       <p>Amount <span>5</span></p>
	//     </div>

	//     <div class="left-col__group-button">
	//       <a class="left-col__group-button__button" href="./shop.html">Buy</a>
	//     </div>
	//   </div>

	//   <!-- Pokeball Info -->
	//   <div class="right-col">
	//     <div class="right-col__content">
	//       <h2>Description</h2>
	//       <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Laboriosam, ut expedita, animi nobis
	//         quibusdam sit, iste delectus suscipit excepturi dolorum cupiditate neque non a perspiciatis ducimus unde
	//         voluptatum minus tenetur assumenda illum ad labore autem hic? Dolore sapiente placeat, blanditiis
	//         possimus sit consectetur laudantium nemo modi excepturi est quasi minus explicabo, magnam quam
	//         perferendis facilis atque magni praesentium facere? Facere blanditiis eveniet hic deserunt aperiam
	//         temporibus eos, explicabo laborum assumenda? Veniam quia neque est itaque excepturi. Illo, earum dolorem
	//         architecto esse voluptate, ullam commodi laboriosam ipsam ea labore dolores nihil error, enim maxime
	//         magnam voluptas voluptatum numquam odit laudantium quam.</p>
	//       <div class="right-col__affect">
	//         <h3>Affect</h3>
	//         <p>
	//           <i class="fa-solid fa-angles-up"></i>
	//           Capture Rate <span>+50</span>
	//         </p>
	//       </div>
	//     </div>
	//   </div><!-- End Pokemon Info -->
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
    document.getElementById("item-amount").textContent = pokeballItems.length
	const mysticItems = usableItems.filter((i) => i.type === "MysticItems");
	addMysticItems(mysticItems);
	const consumableItems = usableItems.filter((i) => i.type === "ConsumableItems");
	addConsumableItems(consumableItems);

	// Tab links
	pokeballTabLink.addEventListener("click", (e) => {
		closeAllTabs();
		pokeballTab.classList.add("show");
		e.target.parentNode.classList.add("active");
        document.getElementById("item-amount").textContent = pokeballItems.length
	});
	mysticTabLink.addEventListener("click", (e) => {
		closeAllTabs();
		mysticTab.classList.add("show");
		e.target.parentNode.classList.add("active");
        document.getElementById("item-amount").textContent = mysticItems.length
	});
	consumableTabLink.addEventListener("click", (e) => {
		closeAllTabs();
		consumableTab.classList.add("show");
		e.target.parentNode.classList.add("active");
        document.getElementById("item-amount").textContent = consumableItems.length
	});
}
main();
