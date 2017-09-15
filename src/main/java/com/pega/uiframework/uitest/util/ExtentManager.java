package com.pega.uiframework.uitest.util;
//http://relevantcodes.com/Tools/ExtentReports2/javadoc/index.html?com/relevantcodes/extentreports/ExtentReports.html


import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;
import java.util.Date;

public class ExtentManager {
	private static ExtentReports extent;
	public static ExtentTest test;

	public static ExtentReports getInstance() {
		if (extent == null) {
			Date d = new Date();
			String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".html";
			String reportPath = Constants.REPORTS_PATH + fileName;

			//Initialization of the path for report generation
			//Initialization of the Configuration file which has to be referred to generate the reports

			extent = new ExtentReports (System.getProperty("user.dir") +"/reports/STMExtentReport.html", true, DisplayOrder.NEWEST_FIRST);;
			extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));

			// optional
			extent.addSystemInfo("Selenium Version", "3.0.1").addSystemInfo(
					"Environment", "QA");
		}
		return extent;
	}
}
