//package com.example.froggerdroid;
//
//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import com.example.froggerdroid.data.Controls;
//import com.example.froggerdroid.game.Configurables;
//import com.example.froggerdroid.game.GameConfig;
//import com.example.froggerdroid.game.GameLogic;
//import com.example.froggerdroid.game.InputController;
//
//import org.junit.Before;
//import org.junit.Test;
//
//public class TestSprint2 {
//
//            GameLogic gameLogic;
//
//            @Before
//            public void setup() {
//                gameLogic = GameLogic.getInstance();
//            }
//
//            //Evan's Tests
//            @Test
//            public void testEasy() {
//                GameConfig.setDifficulty(Configurables.Difficulty.EASY);
//                assertEquals(GameScreen.getDifficultyString(), "Easy");
//            }
//
//            @Test
//            public void testNormal() {
//
//                GameConfig.setDifficulty(Configurables.Difficulty.NORMAL);
//                assertEquals(GameScreen.getDifficultyString(), "Normal");
//            }
//
//        //Owen's Tests
//        @Test
//        public void testHard () {
//            GameConfig.setDifficulty(Configurables.Difficulty.HARD);
//            assertEquals(GameScreen.getDifficultyString(), "Hard");
//        }
//
//        @Test
//        public void testLivesEasy () {
//            GameConfig.setDifficulty(Configurables.Difficulty.EASY);
//            assertEquals(GameScreen.getStartingLives(), 3);
//        }
//
//        //Nate's Tests
//        @Test
//        public void testLivesNormal () {
//            GameConfig.setDifficulty(Configurables.Difficulty.NORMAL);
//            assertEquals(GameScreen.getStartingLives(), 2);
//        }
//
//        @Test
//        public void testLivesHard () {
//            GameConfig.setDifficulty(Configurables.Difficulty.HARD);
//            assertEquals(GameScreen.getStartingLives(), 1);
//        }
//        //Daniel's Tests
//        @Test
//        public void testWhitespaceInput () {
//            boolean test = DummyConfigScreen.isValidName("       ");
//            assertFalse(test);
//        }
//
//        @Test
//        public void testNoInput () {
//            boolean test = DummyConfigScreen.isValidName("");
//            assertFalse(test);
//        }
//
//        // Donald's tests
//        @Test
//        public void testMovement () {
//            // up
//            InputController.QUEUE_MOVEMENTS.add(Controls.MOVE.UP);
//            gameLogic.processMovement();
//            assertTrue(InputController.QUEUE_MOVEMENTS.isEmpty());
//            assertArrayEquals(gameLogic.getPlayer().getPos(), new int[]{0, 1});
//            // right
//            InputController.QUEUE_MOVEMENTS.add(Controls.MOVE.RIGHT);
//            gameLogic.processMovement();
//            assertTrue(InputController.QUEUE_MOVEMENTS.isEmpty());
//            assertArrayEquals(gameLogic.getPlayer().getPos(), new int[]{1, 1});
//            // down
//            InputController.QUEUE_MOVEMENTS.add(Controls.MOVE.DOWN);
//            gameLogic.processMovement();
//            assertTrue(InputController.QUEUE_MOVEMENTS.isEmpty());
//            assertArrayEquals(gameLogic.getPlayer().getPos(), new int[]{1, 0});
//            // left
//            InputController.QUEUE_MOVEMENTS.add(Controls.MOVE.LEFT);
//            gameLogic.processMovement();
//            assertTrue(InputController.QUEUE_MOVEMENTS.isEmpty());
//            assertArrayEquals(gameLogic.getPlayer().getPos(), new int[]{0, 0});
//        }
//
//        @Test
//        public void testBoundsDetection () {
//            // try to move the player into to left edge
//            gameLogic.getPlayer().setPos(0, 0);
//            assertArrayEquals(gameLogic.getPlayer().getPos(), new int[]{0, 0});
//            InputController.QUEUE_MOVEMENTS.add(Controls.MOVE.LEFT);
//            gameLogic.processMovement();
//            assertTrue(InputController.QUEUE_MOVEMENTS.isEmpty());
//            assertArrayEquals(gameLogic.getPlayer().getPos(), new int[]{0, 0});
//            // try to move the player into to bottom edge
//            gameLogic.getPlayer().setPos(0, 0);
//            assertArrayEquals(gameLogic.getPlayer().getPos(), new int[]{0, 0});
//            InputController.QUEUE_MOVEMENTS.add(Controls.MOVE.DOWN);
//            gameLogic.processMovement();
//            assertTrue(InputController.QUEUE_MOVEMENTS.isEmpty());
//            assertArrayEquals(gameLogic.getPlayer().getPos(), new int[]{0, 0});
//        }
//    }