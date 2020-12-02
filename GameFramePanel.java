/*GameFrame/GamePanel
    - Similar to TileFrame, TilePanel
    - GamePanel 
        ○ has GameScene (e.g. Title, Game, End)
        ○ You can setActive new GameSceneXXX(), starts with GameSceneTitle.
        ○ paintComponent asks active GameScene to draw.
        ○ Has GameKeyListener similar to PacManKeyListener
            § Calls active GameScene's keyPressed event
*/

