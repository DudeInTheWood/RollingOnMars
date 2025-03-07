package com.dudeinwood.rollingonmars.presentation.screen.roverCommand

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dudeinwood.rollingonmars.domain.model.Grid
import com.dudeinwood.rollingonmars.domain.model.Obstacle
import com.dudeinwood.rollingonmars.domain.model.Rover
import com.dudeinwood.rollingonmars.domain.usecase.MoveRoverUseCase
import com.dudeinwood.rollingonmars.utils.exceptions.ObstacleDetectedException
import com.dudeinwood.rollingonmars.utils.exceptions.OutOfBoundsException
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

class RoverViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var roverViewModel: RoverViewModel
    private lateinit var moveRoverUseCase: MoveRoverUseCase
    private lateinit var context: Context

    @Before
    fun setUp() {
        moveRoverUseCase = mockk()
        context = mockk()
        roverViewModel = RoverViewModel(context, moveRoverUseCase)
    }

    @Test
    fun `moveRover success`() = runBlocking {
        val gridSize = "5"
        val commands = "M"
        val obstacles = "[(1,2),(3,4)]"

        roverViewModel.updateGridSize(gridSize)
        roverViewModel.updateCommands(commands)
        roverViewModel.updateObstacles(obstacles)

        val expectedGrid = Grid(5, 5)
        val expectedObstacles = listOf(Obstacle(1, 2), Obstacle(3, 4))
        val mockRover = mockk<Rover>()

        every { moveRoverUseCase.invoke(commands, expectedGrid, expectedObstacles) } returns Result.success(mockRover)
        roverViewModel.moveRover()
        verify { moveRoverUseCase.invoke(commands, expectedGrid, expectedObstacles) }
    }

    @Test
    fun `test invalid command`() = runBlocking {
        val commands = "INVALID"
        val grid = Grid(5, 5)
        val obstacles = listOf<Obstacle>()

        val expectedError = "Invalid input!"

        every { moveRoverUseCase(commands, grid, obstacles) } throws IllegalArgumentException()
        every { roverViewModel.getErrorInvalidInputString() } returns expectedError

        roverViewModel.updateCommands(commands)
        roverViewModel.moveRover()

        assertNull(roverViewModel.roverState.value)
        assertEquals(expectedError, roverViewModel.errorMessage.value)
    }

    @Test
    fun `moveRover should handle ObstacleDetectedException`() = runBlocking {
        val gridSize = "5"
        val commands = "M"
        val obstacles = "[(0,1),(3,4)]"

        roverViewModel.updateGridSize(gridSize)
        roverViewModel.updateCommands(commands)
        roverViewModel.updateObstacles(obstacles)

        val expectedGrid = Grid(5, 5)
        val expectedObstacles = listOf(Obstacle(0, 1), Obstacle(3, 4))
        val mockRover = mockk<Rover>()
        val expectedError = "Obstacle encountered"

        every { moveRoverUseCase.invoke(commands, expectedGrid, expectedObstacles) } returns Result.failure(ObstacleDetectedException(expectedError, mockRover))
        every { roverViewModel.getErrorObstacleString() } returns expectedError

        roverViewModel.moveRover()

        verify { moveRoverUseCase.invoke(commands, expectedGrid, expectedObstacles) }
        assert(roverViewModel.errorMessage.value == expectedError)
        assert(roverViewModel.roverState.value == mockRover)
    }

    @Test
    fun `moveRover should handle OutOfBoundsException vertical`() = runBlocking {
        val gridSize = "5"
        val commands = "MMMMMMMMMMMMM"
        val obstacles = "[(0,1)]"

        roverViewModel.updateGridSize(gridSize)
        roverViewModel.updateCommands(commands)
        roverViewModel.updateObstacles(obstacles)

        val expectedGrid = Grid(5, 5)
        val expectedObstacles = listOf(Obstacle(0, 1))
        val mockRover = mockk<Rover>()
        val expectedError = "Out of bounds"

        every { moveRoverUseCase.invoke(commands, expectedGrid, expectedObstacles) } returns Result.failure(OutOfBoundsException (expectedError, mockRover))
        every { roverViewModel.getErrorOutOfBoundString() } returns expectedError

        roverViewModel.moveRover()

        verify { moveRoverUseCase.invoke(commands, expectedGrid, expectedObstacles) }
        assert(roverViewModel.errorMessage.value == expectedError)
        assert(roverViewModel.roverState.value == mockRover)
    }

    @Test
    fun `moveRover should update savedGridSize`() = runBlocking {
        val gridSize = "5"
        val commands = "M"
        val obstacles = "[(0,1),(3,4)]"

        roverViewModel.updateGridSize(gridSize)
        roverViewModel.updateCommands(commands)
        roverViewModel.updateObstacles(obstacles)

        val expectedGrid = Grid(5, 5)
        val expectedObstacles = listOf(Obstacle(0, 1), Obstacle(3, 4))
        val mockRover = mockk<Rover>()

        every { moveRoverUseCase.invoke(commands.uppercase(), expectedGrid, expectedObstacles) } returns Result.success(mockRover)

        roverViewModel.moveRover()

        assertEquals(5, roverViewModel.savedGridSize)
    }


    @Test
    fun `test parseObstacles should return list of obstacles`() = runBlocking {
        val obstaclesString = "[(1,2),(3,4)]"
        val result = roverViewModel.parseObstacles(obstaclesString)

        assertEquals(2, result.size)
        assertEquals(1, result[0].x)
        assertEquals(2, result[0].y)
        assertEquals(3, result[1].x)
        assertEquals(4, result[1].y)
    }
}
