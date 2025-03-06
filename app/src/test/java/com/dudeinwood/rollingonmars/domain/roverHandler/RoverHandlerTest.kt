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
        val commands = "MMRMLM"
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
        assertEquals(3, rover?.y)
    }

    @Test
    fun `test executeCommands turnAround`() {
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
    fun `test turnLeft` () {
        assertEquals(Direction.W.value, roverHandler.turnLeft(Direction.N))
        assertEquals(Direction.S.value, roverHandler.turnLeft(Direction.W))
        assertEquals(Direction.E.value, roverHandler.turnLeft(Direction.S))
        assertEquals(Direction.N.value, roverHandler.turnLeft(Direction.E))
    }

    @Test
    fun `test turnRight` () {
        assertEquals(Direction.E.value, roverHandler.turnRight(Direction.N))
        assertEquals(Direction.S.value, roverHandler.turnRight(Direction.E))
        assertEquals(Direction.W.value, roverHandler.turnRight(Direction.S))
        assertEquals(Direction.N.value, roverHandler.turnRight(Direction.W))
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
    fun `test moveForward OutOfBoundsException head N`() {
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
    fun `test moveForward OutOfBoundsException head E`() {
        val rover = Rover(x = 5, y = 5, direction = Direction.E.value)
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
    fun `test moveForward OutOfBoundsException head W`() {
        val rover = Rover(x = 0, y = 0, direction = Direction.W.value)
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
    fun `test moveForward OutOfBoundsException head S`() {
        val rover = Rover(x = 0, y = 0, direction = Direction.S.value)
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

    @Test
    fun `test moveForward ObstacleDetectedException at start`() {
        val rover = Rover(x = 0, y = 0, direction = Direction.N.value)
        val grid = mockk<Grid> {
            every { width } returns 5
            every { height } returns 5
        }
        val obstacles = listOf(Obstacle(0, 0))

        val exception = assertThrows(ObstacleDetectedException::class.java) {
            roverHandler.moveForward(rover, grid, obstacles)
        }
        assertEquals("Error: Obstacle detected at (0, 1)!", exception.message)
    }
}

