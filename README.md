# Phat's Independent Development Assignment
I decide to follow the [Indigo Card Game](https://hyperskill.org/projects/214?track=18) track and better understand creating a card game. Hopefully, I can build a game based on this tutorial.

Indigo Card Game's Reflection from [Week 4 to 6](https://github.com/nic-dgl-204-fall-2022/phat-independent-development-assignment/tree/main/IndigoCardGame).

# Pokemon Game
## Description
This game explores the pokemon world, where players can nurture and compete with each other. Each player has a collection of pokemon, and they will need different items to nurture them. By exploring the pokemon world, the players can acquire many rewards such as experience points, boosting items, or new pokemon.

# What I need to build.
I will create this Pokemon game as a web game. Thus, I am going to create an API server with Kotlin and implement the [DGL 203 (Advanced CSS) class](https://github.com/phattran2905/phat-dgl-203-semester-project) for the game's UI. 
In particular, I will use the [Ktor](https://ktor.io/docs/intellij-idea.html) framework to create the server and use [Kmongo](https://litote.org/kmongo/) to connect it to MongoDB.

In Ktor, there are plugins that I use for the project:
- CORS (for the client's side to fetch data)
- Routing 
- Security (for authentication with JWT token)
- Serialization

In addtion, I also need some dependencies such as ```BCrypt, EnvConfig, UUID```.

# APIs
I noted [a list of API routes](https://docs.google.com/spreadsheets/d/1JAo7-lMpfWhSyMGu-pp1wE7bL8zKTXrxJQWlhxi9LD8/edit?usp=sharing) the game needs to build. Hopefully, I can build it by the end of the course.

# Features
### Authentication
A player is considered a trainer in this game and will receive rewards whenever they level up. Therefore, the more levels, the more benefits they get.

At first, I aimed to create a game for multiple players, so I provided this authentication procedure. However, I changed to create a single game player. Although the game has its authentication system, all wild Pokemon will belong to any user logged in.

### Pokemon
There are two types of Pokemon in this game: the wild ones and the ones you own. You can see what Pokemon you have owned and what wild Pokemon you found. You can boost their strength by leveling up or with items.

### Items
This game has three types of items: Pokeball, Mystic Items, and Consumable Items.
Whenever you find a wild Pokemon, you need Pokeball to catch it. You can catch Pokemon at the first use, depending on the capture rate. Don't worry if you missed it. You can find and try to catch them again on the Pokemon page.
Mystic Items increase the power of Pokemon, while Consumable Items increase their experience points. You can receive these items if you win the battle.

### Shop (*)
The shop will not be available now since I don't have time to work on it.

### Explore
You can find wild Pokemon here, and you can choose to catch them or get in a battle with them.
#### Catch
The capture rate will be stacked so. Eventually, you can catch wild Pokemon as long as the rate turns ```100```.
#### Battle
For the battle, you only win the Pokemon if you have the higher power score. You will receive additional experience points for both trainer and Pokemon. Moreover, three random items will hand to you after the fight.

# Installation
## 1. Server
It locates in the folder ```PokemonLand```. To start the server, please run the ```main/kotlin/com.example/Application.kt```.

## 2. UI
For the client side, start a live server with the extension __Live Server__ in _Visual Studio Code_ in the ```PokemonLandUI``` folder. The root file should be ```PokemonLandUI/src/index.html```.

# Reflection
Although the final version of the project lacks some initial features as I planned, I think I am happy with what I have done for this project so far. Since I am familiar with a dynamic language like Javascript, I struggled to use Kotlin at the beginning of the course. However, after spending time coding Kotlin, I made some progress with Kotlin. Eventually, I created a Pokemon game as I wanted. 

My first frustration is coping with Kotlin and Java's ecosystems. Fortunately, I figured out the way to install dependencies and get in-depth with web development with Kotlin and Ktor. As a result, I can implement the necessary libraries to build the game's functionality and learn more about the helpfulness of the type-safe system. I never bother about this in Javascript because it is a dynamic-type language. Although it requires extra steps to set up different classes for the work, it helps my code easier to manage and reminds me what data type I am working with. Now I know why more and more people recommend using Typescript. In addition, thanks to the IntelliJ CE, I had a fun time developing this project with its convenient tools. I also learned more about Kotlin coding conventions with its suggestion. I feel like I improved my Kotlin skill just by using it. 

I had a fun time with Kotlin in this course and recognized that I would get in-depth in some programming languages and would hate it. However, my feeling about Kotlin at the beginning of the course is now different. I appreciate some advanced functions provided by Kotlin; they save me a lot of work that I can't have in Javascript. My initial mistake was to bring my Javascript coding convention into Kotlin and impose that Kotlin has a similar syntax. Instead, I should see the difference between them and focus on what technique I use in programming rather than how I can find this Javascript function in Kotlin.
