package com.pega.uiframework.uitest.util;

import com.pega.uiframework.utils.Xls_Reader;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;

public class DataUtil {
    public static String filename = System.getProperty("user.dir") + "/config/Data.xlsx";
    public String path;
    public FileInputStream fis = null;
    public FileOutputStream fileOut = null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;

    public static Object[][] getData(Xls_Reader xls, String testCaseName) {
        String sheetName = Constants.TESTDATA_SHEET;
        // reads data for only testCaseName

        int testStartRowNum = 1;
        System.out.println(xls.getCellData(sheetName, 0, testStartRowNum));
        while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) {
            testStartRowNum++;
        }
        System.out.println("Test starts from row - " + testStartRowNum);
        int colStartRowNum = testStartRowNum + 1;
        int dataStartRowNum = testStartRowNum + 2;

        // calculate rows of data
        int rows = 0;
        while (!xls.getCellData(sheetName, 0, dataStartRowNum + rows).equals("")) {
            rows++;
        }
        System.out.println("Total rows are  - " + rows);

        //calculate total cols
        int cols = 0;
        while (!xls.getCellData(sheetName, cols, colStartRowNum).equals("")) {
            cols++;
        }
        System.out.println("Total cols are  - " + cols);
        Object[][] data = new Object[rows][1];
        //read the data
        int dataRow = 0;
        Hashtable<String, String> table = null;
        for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {
            table = new Hashtable<String, String>();
            for (int cNum = 0; cNum < cols; cNum++) {
                String key = xls.getCellData(sheetName, cNum, colStartRowNum);
                String value = xls.getCellData(sheetName, cNum, rNum);
                table.put(key, value);
                // 0,0 0,1 0,2
                //1,0 1,1
            }
            data[dataRow][0] = table;
            dataRow++;
        }
        return data;
    }

    //Sets result in excel.
    public static void setResult(Xls_Reader xls, String testCaseName, String data) {
        String sheetName = Constants.TESTCASES_SHEET;
        int testStartRowNum = 1;
        while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) {
            testStartRowNum++;
        }
        //Enters result in excel.
        xls.setCellData(sheetName,"TestResult",testStartRowNum,data);
    }

    public static boolean isTestExecutable(Xls_Reader xls, String testCaseName) {
        int rows = xls.getRowCount(Constants.TESTCASES_SHEET);
        for (int rNum = 2; rNum <= rows; rNum++) {
            String tcid = xls.getCellData(Constants.TESTCASES_SHEET, "TCID", rNum);
            if (tcid.equals(testCaseName)) {
                String runmode = xls.getCellData(Constants.TESTCASES_SHEET, "Runmode", rNum);
                if (runmode.equals("Y"))
                    return true;
                else
                    return false;

            }
        }
        return false;
    }
}
