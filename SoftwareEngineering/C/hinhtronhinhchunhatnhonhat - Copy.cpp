#include<stdio.h>
#include <math.h>

// Nhap va validate integer
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

// Nhap toa do x, y ngan cach boi dau cach
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

// Tim index cua diem xa nhat den goc toa do O(0, 0)
int findFarthestPointIndex(int count, double xCoordinates[], double yCoordinates[]) {
	int index = 0;
	double maxDistance = 0;
	for(int i=0; i<count; i++) {		
		// Tinh khoang cach tu diem hien tai den goc toa do O(0, 0)
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
	
	// Nhap cac toa do
	for(int i=0; i<n; i++) {
		char message[100];
		sprintf(message, "Nhap toa do diem thu %d: ", (i+1));
		
		struct coordinate coor = inputCoordinate(message, -10000, 10000);
//		printf("%lf %lf\n", coor.x, coor.y);
		xCoordinates[i] = coor.x;
		yCoordinates[i] = coor.y;
	}
	
	// 1. Tim cac tham so hinh tron
	// Tim diem xa nhat den goc toa do O(0, 0)
	int farthestPointIndex = findFarthestPointIndex(n, xCoordinates, yCoordinates);	
	double circleRadius;
	double circleArea = (xCoordinates[farthestPointIndex]*xCoordinates[farthestPointIndex] + yCoordinates[farthestPointIndex]*yCoordinates[farthestPointIndex]) * M_PI;
	
	// 2. Tim cac tham so cua hinh chu nhat
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
		// Tim diem co gia tri x lon nhat
		if(xCoordinates[i]>highestXValue) {
			highestXValue = xCoordinates[i];
			highestXValueIndex = i;
		}
		
		// Tim diem co gia tri x nho nhat
		if(xCoordinates[i]<lowestXValue) {
			lowestXValue = xCoordinates[i];
			lowestXValueIndex = i;
		}
		
		// Tim diem co gia tri y lon nhat
		if(yCoordinates[i]>highestYValue) {
			highestYValue = yCoordinates[i];
			highestYValueIndex = i;
		}
		
		// Tim diem co gia tri y nho nhat
		if(yCoordinates[i]<lowestYValue) {
			lowestYValue = yCoordinates[i];
			lowestYValueIndex = i;
		}
	}
	
	// Chieu rong hinh chu nhat bang gia tri x lon nhat tru gia tri x nho nhat
	rectangleWidth = highestXValue - lowestXValue;
	
	// Chieu cao hinh chu nhat bang gia tri y lon nhat tru gia tri y nho nhat
	rectangleHeight = highestYValue - lowestYValue;
	
	// Tinh dien tich hinh chu nhat
	rectangleArea = rectangleWidth * rectangleHeight;
	
	printf("Dien tich hinh tron: %.3lf\n", circleArea);	
	printf("Dien tich hinh chu nhat: %.3lf", rectangleArea);
}

