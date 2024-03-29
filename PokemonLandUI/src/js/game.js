const getItems = async function () {
	try {
		const rawResponse = await fetch(SERVER_API_ROUTES.itemRoute, {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
			},
		});

		const content = await rawResponse.json();
		if (content.message == "OK" && content.statusCode.toString() === "200") {
			return JSON.parse(content.data);
		}
	} catch (error) {
		console.log(error);
	}

	return [];
};

const getPokemon = async function () {
	try {
		const rawResponse = await fetch(SERVER_API_ROUTES.pokemonRoute, {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
			},
		});

		const content = await rawResponse.json();
		if (content.message == "OK" && content.statusCode.toString() === "200") {
			return JSON.parse(content.data);
		}
	} catch (error) {
		console.log(error);
	}

	return [];
};

const getOwnedPokemon = async function () {
	try {
		const rawResponse = await fetch(SERVER_API_ROUTES.pokemonRoute, {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
			},
		});

		const content = await rawResponse.json();
		if (content.message == "OK" && content.statusCode.toString() === "200") {
			const pokemon =  JSON.parse(content.data);
            return pokemon.filter(pkm => pkm.status === "OWNED")
		}
	} catch (error) {
		console.log(error);
	}

	return [];
};

const getOwnedItemIds = async (jwtToken) => {
	try {
		const rawResponse = await fetch(SERVER_API_ROUTES.profileRoute, {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
				Authorization: `Bearer ${jwtToken}`,
			},
		});

		const content = await rawResponse.json();
		if (content.message == "OK" && content.statusCode.toString() === "200") {
			const responseData = JSON.parse(content.data);
			return responseData.items;
		}
	} catch (error) {
		console.log(error);
	}

	return [];
};

// Get owned items with full detail
const getUsableItems = (ownedItemIds, items) => {
	return items
		.map((item) => {
			const existed = ownedItemIds.findIndex((i) => i.id === item.id);
			if (existed !== -1) {
				return { ...item, amount: ownedItemIds[existed].amount };
			}
		})
		.filter((i) => i); // Clear undefined values
};

// Find Wild pokemon
const findWildPokemon = async (jwtToken) => {
	try {
		const rawResponse = await fetch(SERVER_API_ROUTES.findPokemonRoute, {
			method: "POST",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
				Authorization: `Bearer ${jwtToken}`,
			},
		});

		const content = await rawResponse.json();
		if (content.message == "OK" && content.statusCode.toString() === "200") {
			const responseData = JSON.parse(content.data);
			return responseData;
		}
	} catch (error) {
		console.log(error);
	}
};

// Find Pokemon By Id
const findPokemonId = async(pokemonId) => {
    try {
		const rawResponse = await fetch(`${SERVER_API_ROUTES.pokemonRoute}/${pokemonId}`, {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
			},
		});

		const content = await rawResponse.json();
		if (content.message == "OK" && content.statusCode.toString() === "200") {
			const responseData = JSON.parse(content.data);
			return responseData;
		}
    } catch (error) {
        return null
    }
}

// Catch wild pokemon
const catchWildPokemon = async(jwtToken, pokemonId, pokeballId) => {
    try {
		const rawResponse = await fetch(SERVER_API_ROUTES.catchPokemonRoute, {
			method: "PUT",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
				Authorization: `Bearer ${jwtToken}`,
			},
            body: JSON.stringify({
                "pokemonId": pokemonId,
                "itemId": pokeballId
            })
		});

		const content = await rawResponse.json();
		if (content.message == "OK" && content.statusCode.toString() === "200") {
			const responseData = JSON.parse(content.data);
			return responseData;
		}
    } catch (error) {
        console.log(error)
    }
}

// Battle with wild pokemon
const battleWithWildPokemon = async (jwtToken, pokemonId, wildPokemonId) => {
    try {
		const rawResponse = await fetch(SERVER_API_ROUTES.battlePokemonRoute, {
			method: "PUT",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
				Authorization: `Bearer ${jwtToken}`,
			},
            body: JSON.stringify({
                "pokemonId": pokemonId,
                "wildPokemonId": wildPokemonId
            })
		});

		const content = await rawResponse.json();
		if (content.message == "OK" && content.statusCode.toString() === "200") {
            console.log(content.data)
			const responseData = JSON.parse(content.data);
			return responseData;
		}
    } catch (error) {
        console.log(error)
    }
}
