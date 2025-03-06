package com.dudeinwood.rollingonmars.domain.usecase

import com.dudeinwood.rollingonmars.domain.model.Grid
import com.dudeinwood.rollingonmars.domain.model.Obstacle
import com.dudeinwood.rollingonmars.domain.model.Rover
import com.dudeinwood.rollingonmars.domain.roverHandler.RoverHandler
import com.dudeinwood.rollingonmars.utils.enums.Direction
import com.dudeinwood.rollingonmars.utils.exceptions.ObstacleDetectedException
import com.dudeinwood.rollingonmars.utils.exceptions.OutOfBoundsException
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class MoveRoverUseCaseTest {

    private lateinit var moveRoverUseCase: MoveRoverUseCase
    private lateinit var roverHandler: RoverHandler
    private lateinit var grid: Grid
    private lateinit var obstacles: List<Obstacle>

    @Before
    fun setUp() {
        roverHandler = mockk()
        moveRoverUseCase = MoveRoverUseCase(roverHandler)
        grid = mockk()
        obstacles = listOf()
    }

    @Test
    fun `test success with correct param`() {

        val commands = "MMRM"
        val expectedRover = Rover(x = 1, y = 2, direction = Direction.E.value)

        every { roverHandler.executeCommands(commands, grid, obstacles) } returns Result.success(expectedRover)
        val result = moveRoverUseCase(commands, grid, obstacles)

        assertTrue(result.isSuccess)
        val rover = result.getOrNull()
        assertNotNull(rover)
        assertEquals(expectedRover.x, rover?.x)
        assertEquals(expectedRover.y, rover?.y)
    }

    @Test
    fun `test invoke should return failure when OutOfBoundsException is thrown`() {
        val commands = "MM"
        val expectedException = OutOfBoundsException("Error: Rover cannot move out of bounds!")

        every { roverHandler.executeCommands(commands, grid, obstacles) } throws expectedException
        val result = moveRoverUseCase(commands, grid, obstacles)

        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `test invoke should return failure when ObstacleDetectedException is thrown`() {
        val commands = "MM"
        val expectedException = ObstacleDetectedException("Error: Obstacle detected!")

        every { roverHandler.executeCommands(commands, grid, obstacles) } throws expectedException
        val result = moveRoverUseCase(commands, grid, obstacles)

        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `test invoke should return failure when Exception is thrown`() {
        val commands = "MM"
        val expectedException = Exception("Error: Obstacle detected!")

        every { roverHandler.executeCommands(commands, grid, obstacles) } throws expectedException
        val result = moveRoverUseCase(commands, grid, obstacles)

        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }
}