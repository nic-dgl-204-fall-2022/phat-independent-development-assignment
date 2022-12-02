// Hide all battle content
function openBattleContentById(elementId) {
	const battleContentElement = document.querySelectorAll(".battle");
	battleContentElement.forEach((element) => {
		if (element.id == elementId) {
			element.classList.add("show");
		} else {
			element.classList.remove("show");
		}
	});
}

// Unselect all pokemon
function unselectAllPokemon() {
	const pokemonElements = document.querySelectorAll(".select__pokemon");
	pokemonElements.forEach((pkm) => pkm.classList.remove("selected"));
}

function showSuccessResult(wildPokemon, selectedPokemon) {
	openBattleContentById("result-success");

	// Wild Pokemon
	const wpkmLevel = document.getElementById("success-wild-level");
	wpkmLevel.textContent = `Lv.${wildPokemon.level}`;
	const wpkmImg = document.getElementById("success-wild-img");
	wpkmImg.src = `../../dist/img/pokemon/${wildPokemon.imgName}`;
	wpkmImg.alt = wildPokemon.name;
	const wpkmPower = document.getElementById("success-wild-power");
	wpkmPower.textContent = wildPokemon.power;
	const wpkmName = document.getElementById("success-wild-name");
	wpkmName.textContent = wildPokemon.name;
	const wpkmHealthBar = document.getElementById("success-wild-health");
	wpkmHealthBar.classList.remove("losing");
	wpkmHealthBar.classList.remove("full");

	// Selected Pokemon
	const pkmLevel = document.getElementById("success-selected-level");
	pkmLevel.textContent = `Lv.${selectedPokemon.level}`;
	const pkmImg = document.getElementById("success-selected-img");
	pkmImg.src = `../../dist/img/pokemon/${selectedPokemon.imgName}`;
	pkmImg.alt = selectedPokemon.name;
	const pkmPower = document.getElementById("success-selected-power");
	pkmPower.textContent = selectedPokemon.power;
	const pkmName = document.getElementById("success-selected-name");
	pkmName.textContent = selectedPokemon.name;
	const pkmHealthBar = document.getElementById("success-selected-health");
	pkmHealthBar.classList.remove("full");
	pkmHealthBar.classList.add("losing");
}

function showFailedResult(wildPokemon, selectedPokemon) {
	openBattleContentById("result-fail");

	// Wild Pokemon
	const wpkmLevel = document.getElementById("fail-wild-level");
	wpkmLevel.textContent = `Lv.${wildPokemon.level}`;
	const wpkmImg = document.getElementById("fail-wild-img");
	wpkmImg.src = `../../dist/img/pokemon/${wildPokemon.imgName}`;
	wpkmImg.alt = wildPokemon.name;
	const wpkmPower = document.getElementById("fail-wild-power");
	wpkmPower.textContent = wildPokemon.power;
	const wpkmName = document.getElementById("fail-wild-name");
	wpkmName.textContent = wildPokemon.name;
	const wpkmHealthBar = document.getElementById("fail-wild-health");
	wpkmHealthBar.classList.remove("full");
	wpkmHealthBar.classList.add("losing");

	// Selected Pokemon
	const pkmLevel = document.getElementById("fail-selected-level");
	pkmLevel.textContent = `Lv.${selectedPokemon.level}`;
	const pkmImg = document.getElementById("fail-selected-img");
	pkmImg.src = `../../dist/img/pokemon/${selectedPokemon.imgName}`;
	pkmImg.alt = selectedPokemon.name;
	const pkmPower = document.getElementById("fail-selected-power");
	pkmPower.textContent = selectedPokemon.power;
	const pkmName = document.getElementById("fail-selected-name");
	pkmName.textContent = selectedPokemon.name;
	const pkmHealthBar = document.getElementById("fail-selected-health");
	pkmHealthBar.classList.remove("losing");
	pkmHealthBar.classList.remove("full");
}

function createRewardItemElement(item) {
	const itemContainer = document.createElement("div");
	itemContainer.className = "rewards__list__items__item";

	const itemDetailContainer = document.createElement("div");
	itemDetailContainer.className = "rewards__item__left";
	const itemImg = document.createElement("img");
	itemImg.src = `../../dist/img/items/${item.imgName}`;
	const itemName = document.createElement("span");
	itemName.textContent = item.name;
	itemDetailContainer.appendChild(itemImg);
	itemDetailContainer.appendChild(itemName);

	const amount = document.createElement("span");
	amount.className = "rewards__item__amount";
	amount.textContent = `x${item.amount}`;

	itemContainer.appendChild(itemDetailContainer);
	itemContainer.appendChild(amount);
	return itemContainer;
}

async function showRewards(winner, rewards) {
	// Show winner pkm info
	const pkmLevel = document.getElementById("winner-pkm-level");
	pkmLevel.textContent = `Lv.${winner.level}`;
	const pkmImg = document.getElementById("winner-pkm-img");
	pkmImg.src = `../../dist/img/pokemon/${winner.imgName}`;
	pkmImg.alt = winner.name;
	const pkmPower = document.getElementById("winner-pkm-power");
	pkmPower.textContent = winner.power;
	const pkmName = document.getElementById("winner-pkm-name");
	pkmName.textContent = winner.name;

	// Show earned EXP
	const trainerExp = document.getElementById("reward-trainer-exp");
	trainerExp.textContent = `+${rewards.earnedExp} EXP`;
	// Pokemon EXP
	const pokemonExp = document.getElementById("reward-pkm-exp");
	pokemonExp.textContent = `+${rewards.earnedExp} EXP`;

	// Show reward items
	const itemList = await getItems();
	const rewardItems = itemList
		.map((item) => {
			const existed = rewards.earnedItems.findIndex((i) => i.id == item.id);
			if (existed !== -1) {
				return { ...item, amount: rewards.earnedItems[existed].amount };
			}
		})
		.filter((i) => i);

	const itemListContainer = document.getElementById("reward-items");
	itemListContainer.innerHTML = ``;
	rewardItems.forEach((item) => {
		const itemElement = createRewardItemElement(item);
		itemListContainer.appendChild(itemElement);
	});

	openBattleContentById("rewards");
}

function displayHealth(ownedWon = false) {
	if (ownedWon) {
		setTimeout(() => {
			document.getElementById("in-battle-wild-health").classList.remove("full");
			document.getElementById("in-battle-wild-health").classList.add("losing");
		}, 1000);
		setTimeout(() => {
			document.getElementById("in-battle-wild-health").classList.remove("losing");
		}, 2000);
	} else {
		setTimeout(() => {
			document.getElementById("in-battle-selected-health").classList.remove("full");
			document.getElementById("in-battle-selected-health").classList.add("losing");
		}, 1000);
		setTimeout(() => {
			document.getElementById("in-battle-selected-health").classList.remove("losing");
		}, 2000);
	}
}

async function battle(jwtToken, selectedPokemon, wildPokemon) {
	const battleElement = document.getElementById("in-battle");
	if (selectedPokemon) {
		openBattleContentById(battleElement.id);

		// Result
		const result = await battleWithWildPokemon(jwtToken, selectedPokemon.id, wildPokemon.id);

		console.log(result);
		displayHealth(result.won);
		// Delays 3 seconds and show the result
		setTimeout(() => {
			if (result.won) {
				showSuccessResult(wildPokemon, selectedPokemon);
				const showRewardsBtn = document.getElementById("show-rewards-btn");
				showRewardsBtn.addEventListener("click", () =>
					showRewards(selectedPokemon, result)
				);
			} else {
				showFailedResult(wildPokemon, selectedPokemon);
			}
		}, 3000);
	}
}

function displayInBattleInfo(wildPokemon, selectedPokemon) {
	// Wild Pokemon
	const wpkmLevel = document.getElementById("in-battle-wild-level");
	wpkmLevel.textContent = `Lv.${wildPokemon.level}`;
	const wpkmImg = document.getElementById("in-battle-wild-img");
	wpkmImg.src = `../../dist/img/pokemon/${wildPokemon.imgName}`;
	wpkmImg.alt = wildPokemon.name;
	const wpkmPower = document.getElementById("in-battle-wild-power");
	wpkmPower.textContent = wildPokemon.power;
	const wpkmName = document.getElementById("in-battle-wild-name");
	wpkmName.textContent = wildPokemon.name;

	// Selected Pokemon
	const pkmLevel = document.getElementById("in-battle-selected-level");
	pkmLevel.textContent = `Lv.${selectedPokemon.level}`;
	const pkmImg = document.getElementById("in-battle-selected-img");
	pkmImg.src = `../../dist/img/pokemon/${selectedPokemon.imgName}`;
	pkmImg.alt = selectedPokemon.name;
	const pkmPower = document.getElementById("in-battle-selected-power");
	pkmPower.textContent = selectedPokemon.power;
	const pkmName = document.getElementById("in-battle-selected-name");
	pkmName.textContent = selectedPokemon.name;
}

function createPokemonOption(pokemon) {
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
	pkmImg.src = `../../dist/img/pokemon/${pokemon.imgName}`;
	pkmImg.alt = pokemon.name;
	imgContainer.appendChild(pkmImg);

	// Details
	const detailContainer = document.createElement("div");
	detailContainer.className = "items-list__item__details";
	const detailName = document.createElement("p");
	detailName.className = "items-list__item__details__name";
	detailName.textContent = pokemon.name;
	const detailPowerContainer = document.createElement("p");
	detailPowerContainer.className = "card__power select__pokemon__power";
	const detailPowerIcon = document.createElement("i");
	detailPowerIcon.className = "fa-solid fa-bolt";
	const detailPowerTitle = document.createElement("span");
	detailPowerTitle.textContent = "Power";
	const detailPowerPoints = document.createElement("span");
	detailPowerPoints.textContent = pokemon.power;
	detailPowerContainer.appendChild(detailPowerIcon);
	detailPowerContainer.appendChild(detailPowerTitle);
	detailPowerContainer.appendChild(detailPowerPoints);

	detailContainer.appendChild(detailName);
	detailContainer.appendChild(detailPowerContainer);

	// Button wrapper
	const buttonWrapper = document.createElement("button");
	buttonWrapper.id = pokemon.id;
	buttonWrapper.className = "items-list__item select__pokemon";
	buttonWrapper.appendChild(selectBoxContainer);
	buttonWrapper.appendChild(imgContainer);
	buttonWrapper.appendChild(detailContainer);
	buttonWrapper.addEventListener("click", () => {
		unselectAllPokemon();
		buttonWrapper.classList.add("selected");
	});

	return buttonWrapper;
}

function displayOwnedPokemon(pokemonList) {
	const pokemonListContainer = document.getElementById("pokemon-list");
	pokemonListContainer.innerHTML = ``;
	pokemonList.forEach((pokemon) => {
		const pokemonOption = createPokemonOption(pokemon);
		pokemonListContainer.appendChild(pokemonOption);
	});
}

function displayWildPokemonInfo(wildPokemon) {
	const wpkmLevel = document.getElementById("wild-pkm-level");
	wpkmLevel.textContent = `Lv.${wildPokemon.level}`;
	const wpkmImg = document.getElementById("wild-pkm-img");
	wpkmImg.src = `../../dist/img/pokemon/${wildPokemon.imgName}`;
	wpkmImg.alt = wildPokemon.name;
	const wpkmPower = document.getElementById("wild-pkm-power");
	wpkmPower.textContent = wildPokemon.power;
	const wpkmName = document.getElementById("wild-pkm-name");
	wpkmName.textContent = wildPokemon.name;
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
	// Redirect if it is not a wild Pokemon
	if (wildPokemon?.status !== "WILD") {
		return redirectTo(CLIENT_PAGES.pokemonPage);
	}
	displayWildPokemonInfo(wildPokemon);

	const ownedPokemonList = await getOwnedPokemon();
	displayOwnedPokemon(ownedPokemonList);

	const battleBtn = document.getElementById("battle-btn");
	battleBtn.addEventListener("click", async () => {
		const selectedPkmElement = document.querySelector(".select__pokemon.selected");
		const selectedPokemon = ownedPokemonList.find((pkm) => pkm.id === selectedPkmElement.id);
		displayInBattleInfo(wildPokemon, selectedPokemon);
		await battle(jwtToken, selectedPokemon, wildPokemon);
	});
}

main();
