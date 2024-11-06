public class GameProperties {
    // Controls how far the character moves in one step. | Default 50
    public static final int CHARACTER_STEP = 50;
    
    // Controls screen width, making sure it fits the proper grid size | Default 15
    public static final int GRID_WIDTH = 15;
    
    // Controls screen heights, making sure it fits the proper grid size | Default 21
    public static final int GRID_HEIGHT = 15;
    
    public static final int TOOL_BAR = 39;
    
    public static final int SCREEN_WIDTH = CHARACTER_STEP * GRID_WIDTH;
    
    // Adding 28 to the height because the tool bar adds to the total height
    public static final int SCREEN_HEIGHT = CHARACTER_STEP * GRID_HEIGHT + TOOL_BAR;
}
