package ru.itis.vkr2023.concurentgame.model;

/**
 * Состояния игры:
 * - создана
 * - начат очередной этап
 * - очередной этап завершен
 * - игра окончена
 */
public enum GameStatus {
    created,
    stagestarted,
    stageover,
    gameover
}
