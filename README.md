game
====

This project implements the game of Breakout.

Name: Benjamin Lu (bll32)

### Timeline

Start Date: 1/12/2020

Finish Date: 1/19/2020

Hours Spent: 30 

### Resources Used
Javafx documentation for built in classes (ImageView, Scene, Stage)

Brickbreaker guide for level design inspiration

Various stackoverflow posts


### Running the Program

####Main class: 

game_bll32/src/breakout/BrickBreaker.java

####Data files needed: 

game_bll32/resources

gif files for all game elements (paddle, bricks, 
ball, game title, and powerups)

####Key/Mouse inputs: 

Space bar used to start
 the game from the title screen
 
Left and right arrow keys to move paddle

####Cheat keys: 
R - reset the ball and paddle position of the current level

1,2,3 - jump and start level 1, 2, or 3

F - double the speed that 
the paddle moves

####Known Bugs:

When the ball hits a bricks vertical surface the collision interaction
is incorrect

When the ball hits the paddle vertical surface the ball has a near horizontal
response

When the ball hits the intersection of two bricks, regardless of the bricks hp,
both bricks instantly break

####Extra credit: 

Advanced paddle physics - ball will bounce off the paddle at various
angles dependent on where on the paddle is hit

Replayability - whether its from the game over screen or win screen, the player
can replay the game as many times as they would like

Theme music plays while playing the game and sounds play when hitting
a brick, wall, or paddle

Keep track of global score and amount of bricks destroyed and displayed 
at the end

Change the appearance of bricks dependent on how much health is left
in the brick

Powerups include increasing life, reducing all brick health to 1, 
and increasing the paddle size

### Notes/Assumptions

It is assumed the player is willing to face defeat multiple times,
part of the fun of games is discovering how certain functions and elements work,
likewise levels are designed to be quite difficult and power-ups are not
explained in the rules and are rather for the player to discovery what they do
themselves. 

### Impressions

At first the project was very intimidating. However, the process
of development was very enjoyable and educational. For me, the 
most valuable concept from this project was abstraction and inheritance.
Being able to create classes that extend from others helps create a solid
foundation which makes implementing higher level features very efficient. 
For the future it would be more helpful to have a little more direction
when it comes to abstraction, however there definitely is value in 
having to figure it out without assistance. The resources given in 
lab_bounce is definitely sufficient to complete the project. In terms of 
time frame, I believe the given time is sufficient for finishing.  
