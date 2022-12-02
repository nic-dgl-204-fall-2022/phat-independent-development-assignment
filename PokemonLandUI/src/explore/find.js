function createPokemonStatsCard(wildPokemon) {
	// Power
	const powerContainer = document.createElement("div");
	powerContainer.className = "card__power found__card__power";
	const powerIcon = document.createElement("i");
	powerIcon.className = "fa-solid fa-bolt";
	const powerTitle = document.createElement("span");
	powerTitle.textContent = "Power:";
	const powerPoints = document.createElement("span");
	powerPoints.textContent = wildPokemon.power;
	powerContainer.appendChild(powerIcon);
	powerContainer.appendChild(powerTitle);
	powerContainer.appendChild(powerPoints);

	// Img
	const imgContainer = document.createElement("div");
	imgContainer.className = "card__pokemon_img found__card__img";
	const wildPokemonImg = document.createElement("img");
	wildPokemonImg.src = `../../dist/img/pokemon/${wildPokemon.imgName}`;
	wildPokemonImg.alt = wildPokemon.name;
	imgContainer.appendChild(wildPokemonImg);

	// Level
	const levelContainer = document.createElement("div");
	levelContainer.className = "card__level found__card_level";
	const levelElement = document.createElement("span");
	levelElement.textContent = `Lv.${wildPokemon.level}`;
	levelContainer.appendChild(levelElement);

	// Status
	const statusContainer = document.createElement("div");
	statusContainer.className = "pokemon-status";
	if (wildPokemon.status === "WILD") {
		const statusImg = document.createElement("img");
		statusImg.src = "../../dist/img/pokemon-status/sniper-gray.png";
		statusImg.alt = "Wild status background";
		const wildText = document.createElement("span");
		wildText.className = "pokemon-status__desc";
		wildText.textContent = "Wild";
		statusContainer.appendChild(statusImg);
		statusContainer.appendChild(wildText);
	}

	// Card Content
	const cardContentContainer = document.createElement("div");
	cardContentContainer.className = "card__content found__card__content";
	cardContentContainer.appendChild(statusContainer);
	cardContentContainer.appendChild(levelContainer);
	cardContentContainer.appendChild(imgContainer);
	cardContentContainer.appendChild(powerContainer);

	// Card Footer
	const cardFooterContainer = document.createElement("div");
	cardFooterContainer.className = "card__footer found__card__footer";
	const pokemonName = document.createElement("span");
	pokemonName.textContent = wildPokemon.name;
	cardContentContainer.appendChild(pokemonName);

	// Stats Card
	const pkmStatsCard = document.createElement("div");
	pkmStatsCard.className = "pokemon-stats__card card found__card";
	pkmStatsCard.appendChild(cardContentContainer);
	pkmStatsCard.appendChild(cardFooterContainer);

	return pkmStatsCard;
}

function displayWildPokemon(wildPokemon) {
	const pokemonStats = createPokemonStatsCard(wildPokemon);
	const pokemonStatsContainer = document.getElementById("pokemon-stats");
	pokemonStatsContainer.innerHTML = ``;
	pokemonStatsContainer.appendChild(pokemonStats);
    const typeContainer = document.getElementById("pokemon-type")
    typeContainer.innerHTML = ``
	wildPokemon.type.forEach((type) => {
		const typeElement = document.createElement("span");
		typeElement.textContent = type;
		typeContainer.appendChild(typeElement);
	});
}

async function findAndDisplayWildPokemon() {
	const findWildPokemonBtn = document.getElementById("find-wild-pokemon-btn");
	const loading = document.getElementById("loading");
	const findBlock = document.getElementById("find");
	const foundBlock = document.getElementById("found");

	findWildPokemonBtn.classList.add("disabled");
	findWildPokemonBtn.textContent = "Finding...";
	loading.classList.add("show");

	// Delay for showing the loading pokeball
	setTimeout(async () => {
		findBlock.classList.remove("show");

		// Check jwt token
		const loggedIn = isLoggedIn();
		if (!loggedIn) {
			redirectTo(CLIENT_PAGES.loginPage);
            return
		}

		const jwtToken = getJwtToken();
		const wildPokemon = await findWildPokemon(jwtToken);
		displayWildPokemon(wildPokemon);
        document.getElementById("catch-btn").href = `./catch.html?pokemonId=${wildPokemon.id}`
        document.getElementById("battle-btn").href = `./battle.html?pokemonId=${wildPokemon.id}`
		foundBlock.classList.add("show");
        return wildPokemon
	}, 3000);
}

function keepFindingWildPokemon() {
	document.getElementById("found").classList.remove("show");
	document.getElementById("find").classList.add("show");
	findAndDisplayWildPokemon();
}

async function main() {
	const findWildPokemonBtn = document.getElementById("find-wild-pokemon-btn");
	findWildPokemonBtn.addEventListener("click", findAndDisplayWildPokemon);

	const keepFindingBtn = document.getElementById("keep-finding-btn");
	keepFindingBtn.addEventListener("click", keepFindingWildPokemon);
}
main();
