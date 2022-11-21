# Phat's Independent Development Assignment
I decide to follow the [Indigo Card Game](https://hyperskill.org/projects/214?track=18) track and better understand creating a card game. Hopefully, I can build a game based on this tutorial.

Indigo Card Game's Reflection from [Week 4 to 6](https://github.com/nic-dgl-204-fall-2022/phat-independent-development-assignment/tree/main/IndigoCardGame).

# Pokemon Game
## Description
This game explores the pokemon world, where players can nurture and compete with each other. Each player has a collection of pokemon, and they will need different items to nurture them. By exploring the pokemon world, the players can acquire many rewards such as experience points, boosting items, or new pokemon.

## Gameplay
### Trainer (*)
A player is considered a trainer in this game and will receive rewards whenever they level up. Therefore, the more levels, the more benefits they get.

### Pokemon (*)
They are the main objects in this game and play an important role in the trainer's exploration. Each pokemon has many traits, such as strength, agility, magic, and defense. Every time they level up, these traits increase and can be boosted by items. 
Besides the traits above, pokemon also have skills and earn a new skill if they reach a specific level or evolve. 

### Items (*)
Pokeball is used to catch wild pokemon, and if it succeeds, the pokemon will belong to the thrower. Otherwise, it will be gone, and the trainer will have to use another one. 
Mystic items boost pokemon's traits in a certain amount of time. However, some rare items affect permanently.
Consumable items recharge the trainer and pokemon's stamina and have limited usage.

### Shop
This shop sells game items such as Pokeball, Mystic, and Consumable items. In particular, it costs the trainer gold to purchase those items. In addition, trainers can trade their items here and receive gold in return.

### Rewards
When the trainer beats a wild pokemon, they will gain experience points for the pokemon and themself. In addition, they also receive gold and a random item.

### Explore (*)
A trainer might find nearby wild pokemon while using this feature. Whenever a trainer finds a wild pokemon, they can either beat them for rewards or choose to use Pokeball to catch them. If they lose, they will not lose anything, including game resources such as health and mana points.

### Battle 
In a battle, pokemon take turns to cast their skills. The one who has a lower level will become the first one to case their skills; however, it will cost mana points. In addition, the health bar will decrease after the pokemon is hit. Whoever runs out of the health bar will lose the battle.

# What I need to build.
Since the project scope is to create a web game about Pokemon, I need to build both the client side and server side. In particular, I will use Ktor to create an API server that serves the requests from the client. Meanwhile, I will use the code from the [DGL 203 (Advanced CSS) class](https://github.com/phattran2905/phat-dgl-203-semester-project) for the game's UI.

# Sitemap
![semester-project-sitemap-Old drawio](https://user-images.githubusercontent.com/45039354/202970783-517a743f-c577-42bd-828b-357c064508dc.png)

# APIs
I noted [a list of API routes](https://docs.google.com/spreadsheets/d/1JAo7-lMpfWhSyMGu-pp1wE7bL8zKTXrxJQWlhxi9LD8/edit?usp=sharing) the game needs to build. Hopefully, I can build it by the end of the course.


