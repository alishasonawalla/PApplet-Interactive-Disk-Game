/**
 * This class creates a game that generates 4 disks with various point values.
 *The disks move at various speeds in different directions.
 * The user wins points if he/she clicks on the disks within a given range.
 * 
 * @author Alisha Sonawalla
 * @version 03/15/2017
 */

import processing.core.PApplet;

@SuppressWarnings("serial")
public class diskGame extends PApplet {
	// Initialize timer
	int timer;

	// Game length in milliseconds
	int gameDuration = 10 * 1000;

	// Keep track of ball's x and y coordinates
	// Here we initialize the starting position
	float x = 300;
	float y = 250;

	// Keep track of current score, start with 0 points
	static int score = 0;

	// Horizontal speed - start with a slow speed like 2
	float xSpeed = 2;

	// Canvas size
	final int canvasWidth = 500;
	final int canvasHeight = 500;

	// Shape size
	final int shapeWidth = 90;
	final int shapeHeight = 90;

	// It's hard to click a precise position, to make it easier,
	// require the click to be somewhere on the shape
	int targetRange = Math.round((min(shapeWidth, shapeHeight)) / 2);

	Disk[] d = { new Disk(51, 102, 0, 20, 100, 2, 10), new Disk(0, 0, 102, 100, 200, 4, 20),
			new Disk(102, 0, 51, 300, 300, 6, 50), new Disk(255, 204, 51, canvasWidth, 400, 8, 100) };

  
    /**
     * This method sets up runs one time at the beginning of the program.
     * It creates a canvas of a specified width and height
     */
    
	public void setup() {
		System.out.println("targetRange: " + targetRange);
		size(canvasWidth, canvasHeight);
		smooth();

		// Set time now
		timer = millis() + gameDuration;
	}

    
    /**
     * This method is in an infinite loop running 60 times per second.
     * It erases the background so that the previous position of the disks is not visible.
     */
	
	public void draw() {
		// Erase the background, if you don't, the previous shape will
		// still be displayed
		eraseBackground();

		for (int i = 0; i < d.length; i++) {
			// Move the shape, i.e. calculate the next x and y position
			// where the shape will be drawn.
			d[i].calcCoords();

			// Draw the shape
			d[i].drawShape();

			// pointValue=(i*10)+10;

			// Display point value on the shape
			d[i].displayPointValue();

		}

		// Display player's score
		fill(100, 100, 100);
		text("SCORE: " + score, 250, 480);

		if (millis() >= timer) {
			// Clear the canvas
			background(0, 0, 51);

			// Output the final score
			textAlign(CENTER);
			textSize(35);
			fill(255, 255, 255);
			text("Your score is: " + score, 250, 250);

			// Let the user click when finished reading score
			textAlign(CENTER);
			textSize(12);
			fill(255, 255, 255);
			text("Click to exit", 250, 400);

			if (this.mousePressed) {
				// Exit
				System.exit(0);
			}
		}
	}

    /**
     * This method is called when the mouse is pressed.
     * mousePressed is a PApplet method that you can override.
     */

    @Override
	public void mousePressed() {
		// Draw a circle wherever the mouse is
		int mouseWidth = 20;
		int mouseHeight = 20;
		fill(0, 255, 0);
		ellipse(this.mouseX, this.mouseY, mouseWidth, mouseHeight);

		for (int i = 0; i < d.length; i++) {
			// Check whether the click occurred within range of the shape
			if (((this.mouseX < d[i].x + targetRange) && (this.mouseX > d[i].x - targetRange))
					&& ((this.mouseY < d[i].y + targetRange) && (this.mouseY > d[i].y - targetRange))) {
				score += d[i].pointValue;
			}
		}
	}
    
    /**
     * This method clears the background
     */
	public void eraseBackground() {
		background(255);
	}

    
  
    
    
    /**
     * This class creates disks that are used in the game.
     * It contains several methods and data fields to calculate the disk coordinates,
     * draw the disk, and display points
     */
	class Disk {

		int pointValue;
		float x;
		float y;
		float xSpeed;
		float red;
		float green;
		float blue;

        //Create a disk constructor that takes in color value, speed, and point value
		Disk(float red, float green, float blue, float x, float y, float xSpeed, int pointValue) {
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.x = x;
			this.y = y;
			this.xSpeed = xSpeed;
			this.pointValue = pointValue;
		}

        
        /**
         * This method calculates the coordinates to allow control of its direction
         */
		public void calcCoords() {
			// Compute the x position where the shape will be drawn
			this.x += this.xSpeed;

			// If the x position is off the canvas, reverse direction of movement
			if (this.x > canvasWidth) {
				System.out.println("<===  Change direction, go left because x = " + x);
				this.xSpeed = -1 * this.xSpeed;
			}

			// If the x position is off the canvas, reverse direction of movement
			if (this.x < 0) {
				System.out.println("     ===> Change direction, go right because x = " + x + "\n");
				this.xSpeed = -1 * this.xSpeed;
			}
		}

        /**
         * This method draws the disk at the specified location in the specified color
         */
		public void drawShape() {
			// Select color, then draw the shape at computed x, y location
			fill(this.red, this.green, this.blue);
			ellipse(this.x, this.y, shapeWidth, shapeHeight);
		}

        /**
         * This method is used to display the points on the disk and align it
         */
		public void displayPointValue() {
			// Draw the text at computed x, y location
			textAlign(CENTER);
			fill(255, 255, 255);
			text(pointValue, x, y);
		}
	}
}
