package com.dudeinwood.rollingonmars.domain.roverHandler

import com.dudeinwood.rollingonmars.domain.model.Grid
import com.dudeinwood.rollingonmars.domain.model.Obstacle
import com.dudeinwood.rollingonmars.domain.model.Rover
import com.dudeinwood.rollingonmars.utils.enums.Direction
import com.dudeinwood.rollingonmars.utils.exceptions.ObstacleDetectedException
import com.dudeinwood.rollingonmars.utils.exceptions.OutOfBoundsException
import io.mockk.*
import org.junit.Test
import org.junit.Before
import org.junit.Assert.*

class RoverHandlerTest {

    private lateinit var roverHandler: RoverHandler

    @Before
    fun setup() {
        roverHandler = RoverHandler()
    }

    @Test
    fun `test moveForward success`() {
        val rover = Rover(x = 0, y = 0, direction = Direction.N.value)
        val grid = mockk<Grid> {
            every { width } returns 5
            every { height } returns 5
        }
        val obstacles = listOf<Obstacle>()
        val updatedRover = roverHandler.moveForward(rover, grid, obstacles)

        assertEquals(0, updatedRover.x)
        assertEquals(1, updatedRover.y)
    }

    @Test
    fun `test executeCommands success`() {
        val commands = "MMRM"
        val grid = mockk<Grid> {
            every { width } returns 5
            every { height } returns 5
        }
        val obstacles = listOf<Obstacle>()

        val result = roverHandler.executeCommands(commands, grid, obstacles)

        assertTrue(result.isSuccess)
        val rover = result.getOrNull()
        assertNotNull(rover)
        assertEquals(1, rover?.x)
        assertEquals(2, rover?.y)
    }

    @Test
    fun `test executeCommands turnLeft`() {
        val commands = "LLLL"
        val grid = mockk<Grid> {
            every { width } returns 5
            every { height } returns 5
        }
        val obstacles = listOf<Obstacle>()

        val result = roverHandler.executeCommands(commands, grid, obstacles)

        assertTrue(result.isSuccess)
        val rover = result.getOrNull()
        assertNotNull(rover)
        assertEquals(Direction.N.value, rover?.direction)
    }

    @Test
    fun `test executeCommands turnRight`() {
        val commands = "RRRR"
        val grid = mockk<Grid> {
            every { width } returns 5
            every { height } returns 5
        }
        val obstacles = listOf<Obstacle>()

        val result = roverHandler.executeCommands(commands, grid, obstacles)

        assertTrue(result.isSuccess)
        val rover = result.getOrNull()
        assertNotNull(rover)
        assertEquals(Direction.N.value, rover?.direction)
    }

    @Test
    fun `test executeCommands invalid command`() {
        val commands = "MMZX"
        val grid = mockk<Grid> {
            every { width } returns 5
            every { height } returns 5
        }
        val obstacles = listOf<Obstacle>()

        val result = roverHandler.executeCommands(commands, grid, obstacles)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `test moveForward OutOfBoundsException`() {
        val rover = Rover(x = 5, y = 5, direction = Direction.N.value)
        val grid = mockk<Grid> {
            every { width } returns 5
            every { height } returns 5
        }
        val obstacles = listOf<Obstacle>()

        val exception = assertThrows(OutOfBoundsException::class.java) {
            roverHandler.moveForward(rover, grid, obstacles)
        }
        assertEquals("Error: Rover cannot move out of bounds!", exception.message)
    }

    @Test
    fun `test moveForward ObstacleDetectedException`() {
        val rover = Rover(x = 0, y = 0, direction = Direction.N.value)
        val grid = mockk<Grid> {
            every { width } returns 5
            every { height } returns 5
        }
        val obstacles = listOf(Obstacle(0, 1))

        val exception = assertThrows(ObstacleDetectedException::class.java) {
            roverHandler.moveForward(rover, grid, obstacles)
        }
        assertEquals("Error: Obstacle detected at (0, 1)!", exception.message)
    }
}

