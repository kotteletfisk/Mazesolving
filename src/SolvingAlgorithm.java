public interface SolvingAlgorithm
{
    MazeNode[] solve(Maze maze);

    int getTotal_visited();
}
