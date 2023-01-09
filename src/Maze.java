import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Maze
{
    private String img_path;

    private BufferedImage maze_img;
    private MazeNode[][] node_map;

    private MazeNode entrance;
    private MazeNode exit;

    public Maze(String img_path)
    {
        this.img_path = img_path;
    }

    public void loadMaze() throws IOException
    {
        maze_img = ImageIO.read(new File(img_path));
        node_map = new MazeNode[maze_img.getWidth()][maze_img.getHeight()];
    }

    public int createNodes()
    {
        //TODO: Best way to store nodes?? 2D array seems dumb...

        int node_count = 0;
        MazeNode[] above_nodes = new MazeNode[maze_img.getWidth()];

        // find entrance in top layer of maze and add a node to its position
        boolean entrance_found = false;
        for (int x = 0; x < maze_img.getWidth(); x++)
        {
            if (maze_img.getRGB(x, 0) == -1)
            {
                MazeNode node = new MazeNode(x, 0);
                addNode(node);
                above_nodes[x] = node;
                node_count++;
                entrance_found = true;
                entrance = node;
                break;
            }
        }

        if (!entrance_found)
        {
            throw new UnsupportedOperationException("Entrance not found in top of maze");
        }

        // Add nodes to one layer at a time, and make connections in row
        for (int y = 1; y < maze_img.getHeight() - 1; y++)
        {
            // node buffer for connections on same row
            MazeNode west_node = null;

            for (int x = 0; x < maze_img.getWidth() - 1; x++)
            {
                MazeNode node = null;

                // If on path, check previous and next pixel
                if (maze_img.getRGB(x, y) == -1)
                {
                    // ON PATH
                    if (maze_img.getRGB(x + 1, y) == -1)
                    {
                        // (PATH) PATH
                        if (maze_img.getRGB(x - 1, y) == -1)
                        {
                            // PATH (PATH) PATH
                            // create node if path above or below
                            if (maze_img.getRGB(x, y + 1) == -1 || maze_img.getRGB(x, y -1) == -1)
                            {
                                node = new MazeNode(x, y);
                                if (west_node != null)
                                {
                                    node.setWest(west_node);
                                    west_node.setEast(node);
                                }
                                addNode(node);
                                node_count++;
                                west_node = node;
                            }
                        }

                        else
                        {
                            // WALL (PATH) PATH
                            // create node at start of corridor
                            // No west connections possible
                            node = new MazeNode(x, y);
                            addNode(node);
                            node_count++;
                            west_node = node;
                        }
                    }

                    else
                    {
                        // (PATH) WALL
                        if (maze_img.getRGB(x - 1, y) == -1)
                        {
                            // PATH (PATH) WALL
                            // create node at end of corridor
                            node = new MazeNode(x, y);
                            if (west_node != null)
                            {
                                node.setWest(west_node);
                                west_node.setEast(node);
                            }
                            addNode(node);
                            node_count++;
                        }

                        else
                        {
                            // WALL (PATH) WALL
                            // only create node if in dead end
                            if (maze_img.getRGB(x, y - 1) != -1 || maze_img.getRGB(x, y + 1) != -1)
                            {
                                node = new MazeNode(x, y);
                                addNode(node);
                                node_count++;
                            }
                        }
                    }



                }

                else
                {
                    // On wall. Wipe westnode buffer
                    west_node = null;
                }

                // Connect current node to node in row above
                if (node != null)
                {
                    // If clear above current node, we assume a node should be connected above
                    if (maze_img.getRGB(x, y - 1) == -1)
                    {
                        above_nodes[x].setSouth(node);
                        node.setNorth(above_nodes[x]);
                    }

                    // if clear below current node, put it in the above node buffer
                    if (maze_img.getRGB(x, y + 1) == -1)
                    {
                        above_nodes[x] = node;
                    }

                    else
                    {
                        above_nodes[x] = null;
                    }
                }

            }
        }

        // end row
        int height = maze_img.getWidth() - 1;
        for (int x = 0; x < height; x++)
        {
            if (maze_img.getRGB(x, height) == -1)
            {
                MazeNode node = new MazeNode(x, height);
                addNode(node);
                node_count++;

                above_nodes[x].setSouth(node);
                node.setNorth(above_nodes[x]);
                exit = node;
            }
        }
        return node_count;
    }

    public void addNode(MazeNode node)
    {
        node_map[node.getX_pos()][node.getY_pos()] = node;
    }

    public void printNodeMap()
    {
        for (int y = 0; y < node_map[1].length; y++)
        {
            for (int x = 0; x < node_map[0].length; x++)
            {
                String value;
                if (node_map[x][y] != null)
                {
                    value = "1";
                }

                else
                {
                    value = "0";
                }

                System.out.print("|" + value);
            }
            System.out.println();
        }
    }

    public BufferedImage getMaze_img()
    {
        return maze_img;
    }

    public MazeNode[][] getNode_map()
    {
        return node_map;
    }

    public MazeNode getEntrance()
    {
        return entrance;
    }

    public MazeNode getExit()
    {
        return exit;
    }

    public void printNodeConnections()
    {
        for (int y = 0; y < node_map[0].length; y++)
        {
            for (int x = 0; x < node_map[1].length; x++)
            {
                if (node_map[x][y] != null)
                {
                    System.out.println(node_map[x][y].getConnections());
                }
            }
        }
    }
}
