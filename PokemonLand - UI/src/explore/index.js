const findWildPokemonBtn = document.getElementById("find-wild-pokemon-btn");
const loading = document.getElementById("loading");

const findBlock = document.getElementById('find');
const foundBlock = document.getElementById('found');

findWildPokemonBtn.addEventListener('click', e => {
    e.target.classList.add("disabled");
    loading.classList.add("show");

    setTimeout(() => {
        findBlock.classList.remove("show");
        foundBlock.classList.add("show");
    }, 1000)
})