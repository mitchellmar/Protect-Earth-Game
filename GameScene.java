/* GameScene
    - Has GamePanel so it can call repaint
    - Has list of GameItems, add/remove
    - Asks each GameItem to draw :)
    - Has keyPressed 
    - Can startTimer, calls onTimer.

    - GameSceneTitle/GameSceneEnd
        ○ Has GameItemTitles e.g. for "PROTECT EARTH BY TYPING WORDS ON ASTEROIDS" or "YOU WON! THANK YOU FOR PROTECTING EARTH!" 
        ○ keyPress will setActive the GameSceneGame
    - GameSceneGame
        ○ Has GameWordPicker
        ○ Starts timer
        ○ Creates game items for words, earth, score, healthbar etc.
        ○ Pick words from GameWordPicker and create GameItemWords
        ○ Update score in GameItemScore , typed text in GameItemTyped etc.
 */
