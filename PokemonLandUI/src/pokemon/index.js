const backToPokemonModalButton = document.getElementById("back-to-pokemon-modal-btn");
const pokemonCards = document.querySelectorAll(".card.hoverable");
const pokemonModal = document.getElementById("pokemon-modal");

// Tabs
const ownedTabLink = document.getElementById("tab-owned-link");
const wildTabLink = document.getElementById("tab-wild-link");
const ownedTab = document.getElementById("owned-pokemon")
const wildTab = document.getElementById("wild-pokemon")

// Remove '.active' from the current active one.
function deactiveCurrentTabLink() {
	const activeTab = document.querySelector(".tabs__list__item.active");
	if (activeTab) {
		activeTab.classList.remove("active");
	}
}

// Open wild tab
wildTabLink.addEventListener('click', () => {
    wildTab.classList.add("show")
    ownedTab.classList.remove("show")
    deactiveCurrentTabLink()
    wildTabLink.parentNode.classList.add("active")
})

// Open owned tab
ownedTabLink.addEventListener('click', () => {
    ownedTab.classList.add("show")
    wildTab.classList.remove("show")
console.log(ownedTab)
    deactiveCurrentTabLink()
    ownedTabLink.parentNode.classList.add("active")
})

// Handle back buttons
backToPokemonModalButton.addEventListener('click', e => {
    closeAllModals();
    pokemonModal.classList.add("show");
})

// Open Pokemon Modal
pokemonCards.forEach((element) => {
	element.addEventListener("click", (e) => {
        pokemonModal.classList.add("show");
	});
});

// Open Use items Modal
const openUseItemsModalButton = document.getElementById("open-use-items-modal-btn")
const useItemsModal = document.getElementById("use-items-modal");
openUseItemsModalButton.addEventListener('click', e=> {
    closeAllModals();
    useItemsModal.classList.add("show");
})

// Show content for items
const openConsumableBtn = document.getElementById("open-consumable-items");
const openMysticBtn = document.getElementById("open-mystic-items");
const consumableItemsContainer = document.getElementById("consumable-items");
const mysticItemsContainer = document.getElementById("mystic-items");

openConsumableBtn.addEventListener('click', e => {
    consumableItemsContainer.classList.add("show");
    openConsumableBtn.parentElement.classList.add("active");
    
    mysticItemsContainer.classList.remove("show");
    openMysticBtn.parentElement.classList.remove("active");
})

openMysticBtn.addEventListener('click', e => {
    mysticItemsContainer.classList.add("show");
    openMysticBtn.parentElement.classList.add("active");

    consumableItemsContainer.classList.remove("show");
    openConsumableBtn.parentElement.classList.remove("active");
})

// Select items
const itemInputs = document.querySelectorAll("input.item-amount");

itemInputs.forEach(itemInput => {
    itemInput.addEventListener('change', e => {
        const {value, id} = e.target;
        const containerId = `item-${id.split("-amount")[0]}`;
        const containerElement = document.getElementById(containerId);

        if (value > 0) {
            containerElement.classList.add("selected");
        } else {
            containerElement.classList.remove("selected");
        }
    })
})