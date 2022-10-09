#include<stdio.h>
#include <math.h>

int inputInteger(char message[], int min, int max) {
	// input	user input -- hopefully a number
	// temp		used to collect garbage characters
	// status	did the user enter a number?
	int input, temp, status;

	printf(message);
	status = scanf("%d", &input);
	while(status!=1 || input < min || input > max){
		while((temp=getchar()) != EOF && temp != '\n');
		printf("Invalid input... please enter a number\n");
		printf(message);
		status = scanf("%d", &input);
	}
	return input;
}

struct coordinate { 
    double x, y;
}; 

// Input coordinate, splitted by space
struct coordinate inputCoordinate(char message[], double min, double max) {
	// input	user input -- hopefully a number
	// temp		used to collect garbage characters
	// status	did the user enter a number?
	double x, y;
	int temp, status;
	
	printf(message);
	status = scanf("%lf %lf", &x, &y);
	while(status==0){
		while((temp=getchar()) != EOF && temp != '\n');
		printf("Invalid input... please enter a number\n");
		printf(message);
		status = scanf("%lf %lf", &x, &y);
	}
	
	struct coordinate coor;
	coor.x = x;
	coor.y = y;
	return coor;
}

int findFarthestPointIndex(int count, double xCoordinates[], double yCoordinates[]) {
	int index = 0;
	double maxDistance = 0;
	for(int i=0; i<count; i++) {		
		// Calculate distance of this point to O(0, 0)
		double distance = xCoordinates[i]*xCoordinates[i] + yCoordinates[i]*yCoordinates[i];
		if(distance > maxDistance) {
			index = i;
			maxDistance = distance;
		}
	}
	return index;
}

main()
{
	int n;
	
	// Input n
	n = inputInteger("Nhap so diem n: ", 1, 200000);
	
	double xCoordinates[n];
	double yCoordinates[n];
	
	// Input coordinates
	for(int i=0; i<n; i++) {
		char message[100];
		sprintf(message, "Nhap toa do diem thu %d: ", (i+1));
		
		struct coordinate coor = inputCoordinate(message, -10000, 10000);
//		printf("%lf %lf\n", coor.x, coor.y);
		xCoordinates[i] = coor.x;
		yCoordinates[i] = coor.y;
	}
	
	// 1. Find circle
	// Find the point which is farthest from the O(0, 0)
	int farthestPointIndex = findFarthestPointIndex(n, xCoordinates, yCoordinates);	
	double circleRadius;
	double circleArea = (xCoordinates[farthestPointIndex]*xCoordinates[farthestPointIndex] + yCoordinates[farthestPointIndex]*yCoordinates[farthestPointIndex]) * M_PI;
	
	// 2. Find rectangle
	int highestXValueIndex = 0;
	int lowestXValueIndex = 0;
	int highestYValueIndex = 0;
	int lowestYValueIndex = 0;
	
	double highestXValue = xCoordinates[0];
	double lowestXValue = xCoordinates[0];
	double highestYValue = yCoordinates[0];
	double lowestYValue = yCoordinates[0];
	
	double rectangleWidth;
	double rectangleHeight;
	double rectangleArea;
	
	for(int i=0; i<n; i++) {
		// Find the point which has highest x value	
		if(xCoordinates[i]>highestXValue) {
			highestXValue = xCoordinates[i];
			highestXValueIndex = i;
		}
		
		// Find the point which has lowest x value	
		if(xCoordinates[i]<lowestXValue) {
			lowestXValue = xCoordinates[i];
			lowestXValueIndex = i;
		}
		
		// Find the point which has highest y value	
		if(yCoordinates[i]>highestYValue) {
			highestYValue = yCoordinates[i];
			highestYValueIndex = i;
		}
		
		// Find the point which has lowest y value	
		if(yCoordinates[i]<lowestYValue) {
			lowestYValue = yCoordinates[i];
			lowestYValueIndex = i;
		}
	}
	
	// Rectangle width equal highest x value minus lowest x value
	rectangleWidth = highestXValue - lowestXValue;
	
	// Rectangle height equal highest y value minus lowest y value
	rectangleHeight = highestYValue - lowestYValue;
	
	// Calculate rectangle area
	rectangleArea = rectangleWidth * rectangleHeight;
	
	printf("Dien tich hinh tron: %.3lf\n", circleArea);	
	printf("Dien tich hinh chu nhat: %.3lf", rectangleArea);
}

