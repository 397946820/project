package com.it.ocs.amazon.service.impl;

public class MyCount {

	public static void main(String[] args) {
		
		System.out.println(getMyBoal(2, 18));
		System.out.println(getMyBoal(2, 18) * 5 + getMyBoal(1, 18));
	}
	

	private static double getResultPrice(double startPrice, int count) {
		double result = 0.00;
		int i = 1;
		int j = 1;
		for (; i <= count; i++) {
			if (i % 10 == 1) {
				j = 1;
			}
			result += startPrice + (j - 1) * 300;
			j++;
		}
		return result;
	}

	/**
	 * 抓鬼收入
	 * 
	 * @param hour
	 * @return
	 */
	private static double getMyBoal(int hour, int qty) {
		double result = 0.0;
		for (int i = 1; i <= hour; i++) {
			double cardPrice = 12357.00;
			int cardCount = 6;
			if (i == 1) {
				cardCount = 7;
			}

			double boal = getResultPrice(6900, qty) * 5;
			result += boal - cardCount * cardPrice * 5 - qty * 10000;
		}

		return result;
	}
}
