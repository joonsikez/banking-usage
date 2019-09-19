package com.pjs.bankingusage;

import java.util.ArrayList;
import java.util.List;

/**
 * pjsTest.java version 2019, 09. 17
 * <p>
 * Copyright 2019 Tmon Corp. All rights Reserved.
 * Tmon PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class pjsTest {
	public static void main(String[] args) {
		List<Double> rates = new ArrayList<>();
		// 41.1
		rates.add(95.1);
		rates.add(93.9);
		rates.add(67.1);
		rates.add(61.5);
		rates.add(61.9);
		rates.add(58.5);
		rates.add(61.4);
		rates.add(51.2);

		System.out.println(rates.size());

		double minus = 0;
		for (int i = 1; i < rates.size(); i++) {
			minus += rates.get(i) - rates.get(i-1);
		}
		System.out.println(minus);

		double result = minus / rates.size() - 1;
		System.out.println(result);
		double resultRate = Math.round((rates.get(rates.size()-1) + result) * 10) / 10.0;

		System.out.println(resultRate);
	}
}
