<h1>A Random 2D Tiles world generation algorithm</h1>
<h2>Algorithm</h2>

used a [BSP Dungeon generation algorithm](https://www.roguebasin.com/index.php?title=Basic_BSP_Dungeon_generation) with some tweaks and edits,
It is used for room generation using random sizes for partitions a random number of partitions and a random way of partitioning,
then a hybrid Dijkstra algorithm is used to traverse the grid from random points on the wall of every room to connect it with another room without leaving any room disconnected,
it also used some functions to check validation and the type of the tile that the algorithm traversed into, it forces every room to be connected so that there are no isolated rooms.

***Here are the steps for this Dijkstra-based algorithm***

1. using ```GraphUG``` class, initialize with candidate cells using ```rooms``` map of the list of points of borders for each room and set the cost of floor and nothing tiles with 1 and walls with 2

2. choose a random start point from a random room

3. run randomized Dijkstra and crave a corridor using ```reconstructPath``` method between two rooms only if a new room is found

4. repeat Dijkstra on all of the grid points until all rooms are connected.

------------------------------------------------------------------------
<h2>Classes used in the algorithm</h2>

- ```GraphUG```: a class for the graph that takes a TETile[][] grid array and optionally a long seed to connect rooms in the grid with corridors, uses a hybrid Dijkstra algorithm.

- ```BSPNode```: every object from this class represents a partition from the partitions in the grid  

- ```Dungeon```: is the creator of the partitions and rooms in the grid, uses GridDrawer class methods to display what have it done on the screen  

- ```GridDrawer```: have all the methods to draw on the grid, **(e.g drawing a vertical line of Mountain Tiles)**

- ```Point```: a class represents a point in the grid

- ```Room```: a class represents a room in a partition

- ```World```: a class for inputting and outputting the grid and initializing it

- ```Main```: for running the algorithm
  <h3>Other classes</h3>
  
  - ```TileRenderer```
  
  - ```Tileset```
    
  - ```FileUtils```
    
  - ```RandomUtils```
    
  - ```TETile```

 --------------------------------------------------------------
