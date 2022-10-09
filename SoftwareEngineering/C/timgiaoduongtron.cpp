#include<stdio.h>
#include <math.h>

main()
{
	double a, b, x0, y0, r;
	double delta, x, y, distance, x1, y1, x2, y2;
	
	// Input a
	printf("Nhap he so a cua phuong trinh duong thang y = ax + b: ");
	scanf("%lf", &a);
	
	// Input b
	printf("Nhap he so b cua phuong trinh duong thang y = ax + b: ");
	scanf("%lf", &b);
	
	// Input x0
	printf("Nhap he so x0 cua phuong trinh duong tron (x - x0)^2 + (y - y0)^2 = r^2: ");
	scanf("%lf", &x0);
	
	// Input y0
	printf("Nhap he so y0 cua phuong trinh duong tron (x - x0)^2 + (y - y0)^2 = r^2: ");
	scanf("%lf", &y0);
	
	// Input r
	printf("Nhap he so r cua phuong trinh duong tron (x - x0)^2 + (y - y0)^2 = r^2: ");
	scanf("%lf", &r);
	
	// TODO: Validate input
	
	// Giai phuong trinh bac 2 giao giua duong thang va duong tron (a^2 + 1)*x^2 + (2ab - 2ay0 - 2x0)*x + x0^2 + b^2 - 2by0 + y0^2 - r^2 = 0
	// a = (a*a + 1)
	// b = (2*a*b - 2*a*y0 - 2*x0)
	// c = (x0*x0 + b*b - 2*b*y0 + y0*y0 - r*r)
	// Tinh delta
	delta = (2*a*b - 2*a*y0 - 2*x0)*(2*a*b - 2*a*y0 - 2*x0) - 4*(a*a + 1)*(x0*x0 + b*b - 2*b*y0 + y0*y0 - r*r);
		
	if (delta < 0) {
		// neu delta < 0, 2 duong khong cat nhau
		printf("Hai duong khong cat nhau");
	} else if (delta == 0) {
		// neu delta = 0, 2 duong tiep xuc tai (x, y)
		x = -1 * (2*a*b - 2*a*y0 - 2*x0) / (2 * (a*a + 1));
		y = a * x + b;
		printf("Hai duong tiep xuc tai (%.3lf, %.3lf)", x, y);
	} else {
		// neu delta > 0, 2 duong cat nhau tai 2 diem (x1, y1), (x2, y2)
		x1 = (-1 * (2*a*b - 2*a*y0 - 2*x0) - sqrt(delta)) / (2 * (a*a + 1));
		y1 = a * x1 + b;
		
		x2 = (-1 * (2*a*b - 2*a*y0 - 2*x0) + sqrt(delta)) / (2 * (a*a + 1));
		y2 = a * x2 + b;
		
		printf("Hai duong cat nhau tai hai diem phan biet (%.3lf, %.3lf) va (%.3lf, %.3lf)", x1, y1, x2, y2);
	}
}

