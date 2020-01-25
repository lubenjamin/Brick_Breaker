#Game Design
##Benjamin Lu (bll32)

###Design Goals

One of the main project goals is designing the basic components and classes
of the game in a way that allowed for easy addition of features. In order
to achieve this goal abstraction and inheritance were implemented. By implementing
these design concepts the process of adding new cheat keys, power ups, and
levels incredibly efficient. Additional features I wanted to make 
easier to incorporate was level customization and highscore recorder.
While level customization was achieved, due to time constraint the highscore
feature was not implemented. However, the frame work exists to easily
implement a highscore feature. A fun feature added at the very end
was background music paired with sound effects when the bouncer collides
with the paddle or breaks a brick.

###High Level Design

The fundamental class for Brickbreaker was the element class. All aspects
of the game build off the existence of elements. Elements include the 
ten different type of bricks, variants of the paddle, bouncer, and power
ups. Elements fundamentally inherit the ImageView class given that all elements
were to be represented using gif files located in the resources folder. While elements
shared the properties of an ImageView object, each had their own functions
and properties. Bricks and the paddle both contained an hp parameter to keep
track if a brick was destroyed and if the player lost all lives. While not implemented
due to time constraints, the multiple powerups would have their respective functions
when collected stored inside their respective class rather than having the 
functions be activated in the main BrickBreaker class. Even though there were 10 types
of bricks the bricks only contained one class. Within the brick class contained
a method that created the correct brick dependent on the value read from
the level text file. Additionally the brick class sends to the main 
program an arraylist of all possible bricks allowing for bricks to 
dynamically change appearance when hit. 

With elements established, levels then could be created. Similar to bricks,
there is only one level class given that the process of constructing the level
is consistent amongst all levels. The only differing factor is the text file
being read. Within the level class is a method that takes in the game elements, 
given that we wanted the element objects to be consistent amongst all levels, 
and the text file. The method then reads through the text file and creates
the correct bricks and positions based off locations within the text file. 
Additional random coordinates are generated to randomly choose which bricks
would contain power ups. Additionally an arraylist of bricks within the level
is generated and given to the main class to keep track of whether the player
had beaten the level. Screens such as the title and win screen
were all extensions of the level class given that they were effectively
levels but with text rather than game elements. 

With the element and level classes the game could run. The main brickbreaker
class contained the setting up of the game, the logic for element interaction and
collision, and event handlers for paddle control and cheat code inputs.  

###Assumptions 

An assumption made was that all level text files would contain the same
amount of rows and columns. The level setup would break if the text file 
had any extra columns or rows. It is also assumed that there are only three
brick levels in the game. In its current state levels and the win screen
are triggered dependent on a count variable. Likewise, if additional levels
were to be added the cases where the levels and win scree are activated
would have to be adjusted. Another assumption was that there only existed
three different power ups. This was simply due to the fact there were only
three gifs available to make power ups. However this is not a huge constraint
given that more gifs could be added to make more power ups.

### New Feature

One of the features I intended to add was a highscore record feature.
The feature would record the score of players whether they beat the game 
or died and at the end/win screen the player with the highest score would be displayed.
In order to implement this feature I would give the paddle class a name and change
the score variable from being a global instance variable to being a parameter
of the paddle class and an instance
high score paddle of type paddle would be initiated. With this adjustment the each instance of the 
game paddle is acting like a "player". In the brickbreaker class, when
it is detected that the paddle has lost all lives, the score is then compared
to the existing highscore and if the current paddle score is higher, the
highscore paddle is then replaced with the current paddle. When displaying
the result on the end/win screen, the name and score parameter of the highscore
paddle can be called to be displayed as text on the screen.

Another feature I wanted to add was the capability of adding hidden levels
that would be activated via a cheat key. This would be easy to add in that
all that would need to be done is create the hidden level text file with the
correct dimensions, then add to the key event handler within the main
game class to make and display the hidden level when the corresponding key
is pressed. 

