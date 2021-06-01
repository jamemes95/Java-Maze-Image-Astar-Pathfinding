# Java-Maze-Image-Astar-Pathfinding
Basic A* Pathfinding implementation in a Maze Image

#Purpose of Project
This is a project I did in my free time to see how I would implement A* pathfinding if given only a maze image

# Instructions
1. Put MazeSolver.java and Listitem.java in same folder
2. Add Maze Image to directory (use png format). I recommend using keesiemeijer's maze generator (Linked Below) to keep the colors consistent with my tests and avoid errors.
3. Use CMD to compile and run the MazeSolver.java. You can also use the VS Code Debugger to run the program.
4. The program will spam "calculating path now.." until the process is finished.
5. After processing, the console will print all the coordinates for the path generated. Also, 2 files will be generated. 
    -solvedImg.png -> Shows the path generated from start point to end point (represented by a red line)
    -closedListIMG.png -> shows all the pixels reachable by the algorithm
    
Using Keesiemeijer's Maze Generator:
I only changed columns and rows to 10 and kept everything else as the default values provided
    
# Credits and Link for Resource Materials
1. keesiemeijer.github.io/maze-generator -> Used the maze images generated here as basis for the pixel detection
2. geeksforgeeks.org/a-search-algorithm -> Used the pseudocode provided as basis
3. stackoverflow.com/questions/23104778/create-list-with-two-items-e-g-liststring-integer -> used StephaneM's answer to implement the ListItem.java
4. tutorialspoint.com/how-to-get-pixels-rgb-values-of-an-image-using-java-opencv-library -> learned about RGB values for Java here
