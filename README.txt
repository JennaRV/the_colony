Welcome to Realm Hopper!

In this game, you can create your own map based on the parameter this program requires. This program uses json,
but it is not hard to use.

If you open rooms.json, you will see a total of 6 rooms, which are all separated by curly brackets. Here is
what each variable means.

room_num -> contains the room number that will be used to uniquely identify the room you are currently in.

room_name -> contains the name of the room you are in.

desc -> gives a brief description of the room

visit -> gives a boolean value to indicate whether a person has visited a room.

nav_tab -> shows what rooms share a connection. A number bigger than 0 indicates what room is connected to the
room you are in. 0 indicates there are no rooms connected. The four values from left to right indicates whether a room
is north, east, south, or west.


If you open the items.json, it'll display the parameters needed to create an item object.

room_num -> links the specified item to a designated room.

item_id -> determines what kind of object will be made: a physical item or a consumable item.

name -> shows the name of the item.

description -> shows the description of the item.

stats -> shows item stats.


If you open the puzzles.json, it'll display the parameters needed to make a puzzle object.

room_ID -> links the puzzle to a designated room

puzzleQ -> shows the puzzle question.

puzzleA -> shows the correct puzzle answer

isSolved -> a boolean to indicate if a person correctly solved the puzzle

numAttempts -> shows the number of attempts a player has to solve the puzzle.


If you open the monsters.json, it'll display the parameters needed to make a monster object.

roomID -> shows what room the monster is in.

name -> displays name of monster.

desc -> displays monster description.

isDead -> state of the monster: dead or alive. It's a boolean that's false by default until the monster is defeated.

HP -> total monster hit points.

DEF -> total monster defense.

ATK -> total monster attack.


For controls, the first input will ask you for your name, so type your name. The rest will be directional inputs: "n",
"s","e","w", which represent north, south, east, and west respectively. Pressing "i" would open the inventory to see what
items the player currently has. Once the player is in their inventory, they can choose to drop an item by typing "drop" +
the item name, or they can explore the item by typing "explore" and the item name. Players can also equip and unequip an
 item using a similar command format as explore and drop. If the item is a consumable item, players can consume the item.
 These items can be used only once and they disappear after they're consumed. Typing "explore" would allow the player
to view any items that are in the room. Typing pickup would allow the user to pick up an item from the room assuming it
exists. Pressing any other letter will exit the game. Case does not matter. Players can view all commands using the "commands"
input after they enter their name in the game.

To solve puzzles, players must enter a room with a puzzle. There will be a question they must answer within a certain
number of tries. Failure to solve the puzzle won't remove the puzzle from the room. Attempts get reset everytime the player
revisits the room with the puzzle.

To fight monsters, players must enter a room with a monster. A player must examine the monster first before being prompted
to either attack it or ignore it. Ignoring it would avoid combat and remove the monster from the room. Attacking it would
initiate a turn based combat between the player and the monster. If the player wins, the monster will be removed from the room.
If the player loses, they can restart or simply exit the game.



Here's the default map:

 _____
|  2  |
|_____|_____ _____ _____
|  1  |  3  |  4  |  5  |
|_____|_____|_____|_____|
            |  6  |
            |_____|
