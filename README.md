# Phat's Independent Development Assignment
I decide to follow the [Indigo Card Game](https://hyperskill.org/projects/214?track=18) track and better understand creating a card game. Hopefully, I can build a game based on this tutorial.

# Ideas For The Project

Since I build UI about the pokemon game in the DGL203 (Advanced CSS class), I try to come up with Pokemon ideas so that I can combine them. However, I am still deciding where to use Kotlin to develop the project (Mobile or Web).

| Ideas  | Description |
| ------------- | ------------- |
| Pokemon Card Game  | It will be similar to the Indigo Card Game I am following. Instead of cards, it will be about pokemon and their characteristics. The rule will be adjusted based on their abilities or types.  |
| Pokedex | It works like a Wikipedia but for pokemon. Users can find information about pokemon through this app. (Like this: https://www.pokemon.com/us/pokedex/bulbasaur) |
| Pokemon Game | A simple pokemon game where the player finds and catches them. In addition, the player can boost pokemon's strength by fighting wild pokemon or feeding them. |

# Reflection
## Week 4 [Stage 1 / 5]
The "Indigo Card Game" track has five stages in total, and I will complete the first stage for this week's work. Most of the topics look familiar to me at a glance. Thus, I decide to go through each material and do the practice to recap my understanding of Kotlin. 

- _Where might I face challenges in the assigned work?_

  > I think the only topic of "Work on project" would take me more time because most topics are covered with my understanding of Kotlin. However, I will try my best not to skip any practice in the first stage as much as possible. 

- _Are things progressing as I expected?_ [__Updated__]

  > I thought it would be quick to finish all the topics within one or two hours since I already knew about Kotlin. So I decided not to skip and finish all the topics. In particular, I  skimmed through the lesson (it's called theory in Hyperskill) and did the practice after each lesson. I tried not to skip it until I gave up, so this was why I took so long to finish 42 topics in the first stage. I know I should have skipped some of them, but I feel like I need to finish all of them to ensure I can follow the rest of this track. It was time-consuming, but I learned some cool things along.

- _Is there anything I can, or should, do to make this more successful?_ [__Updated__]

  > By following along with the track, I realize reading comments from others is very helpful. They help me understand more about the task because some of them are not very clear. In addition, I can see solutions from others and learn from their approach. In the next stages, I think I will pay more attention to comments if I get stuck.
  > 
  > For example, I learned a "quicker" way to create a list of integers with the given input. The first line is the size of the list, and the followings are the elements.
  ```kotlin
    // My solution
    val listSize = readln().toInt()
    val inputs = MutableList<Int>(listSize)

    repeat(listSize) {
        inputs.add(readln().toInt())
    }
    
    // Solution from someone in the community.
    val inputs = MutableList<Int>(readln.toInt()) { readln.toInt() }
  ```

- _What did I particularly value in the process and why?_

  > Since most of stage 1 covers the basics about Kotlin, I learned something interesting that I did not acknowledge before: 
  > - I can write underscores in integers more readable. For example: "1000000" can be re-write as "1_000_000" or "1__000_000".
  > - I can use the "const val" identifier to declare a constant variable. Only primitives and strings are allowed. And it must be declared on the top level, outside of any functions.

## Week 5 [Stage 2 + 3 / 5]
This week I plan to finish the second and third stages of the Indigo Card Game track. These two stages focus on working with lists and creating gameplay without rules.
In particular, the second stage is to create a random set of cards and create functions to interact with the list of cards, such as retrieving, subtracting, or adding a new element.
On the other hand, the third stage is to create a card game where the player and computer take turns playing their cards. The game ends when the table has 52 cards, or the player wants to exit. This stage mostly covers materials about looping through arrays and type safety.

- _Where might I face challenges in the assigned work?_

  > Kotlin provides many convenient functions to work with arrays, which look very complicated to me due to their syntax and usage. Thus, I will find it difficult to work with lists using their utility functions to progress modification or looping. In addition, decomposite actions into functions will be challenging since the requirement is different for each stage.

- _Are things progressing as I expected?_ 

  > The material covers wider than I expected. It covers content about programming knowledge, such as algorithms, big o notation, pseudocode, and more. Because I am verified as known by Jetbrain Academy, I decided to skip them and move to the next materials.
Besides working with lists, I was introduced to enum and data classes in Kotlin, null safety, and exception handling. Although I have already used them before, I still find them very helpful by giving me a deeper understanding.

- _Is there anything I can, or should, do to make this more successful?_

  > Unlike the first stage, I try to skip any material that I am verified as known by Jetbrain Academy. It saves me more time on unrelated topics and provides me more time to focus on building the project code. However, I did not implement the project code with an OOP style, like creating classes to handle cards. Therefore, I should create an enum or data class for the next stage if it is possible.

- _What did I particularly value in the process and why?_

  > Besides the material about lists and looping, I also learned more about the when statement, enum class, and exception handling from the track. In addition, while working on the project code, I also learn ways to split a list into small fragments by trying to use these methods: ```mapIndexed```, ```take```, ```subList```. 
  > On the other hand, I try to fulfill each test case for the project code instead of aiming to create a complete project code at the beginning. As a result, I wrote small functions for each feature. Then, I tested them by cases to ensure it was written correctly before going to the next requirement. I learned this technique from the AOC, which is very helpful for debugging. In particular, I find it easier to detect where the bug comes from and what need to refactor. 
