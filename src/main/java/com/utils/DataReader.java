package com.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class DataReader{
    private static Map<String, DataElements> testCaseMapping=new HashMap<>();
    Workbook workBook=null;
    Sheet testCasesSheet=null;
    private static DataReader dataReader;
    private static GlobalVars globalVars;

    public static DataReader getInstance(){
        if(dataReader==null)
            dataReader=new DataReader();
        return dataReader;
    }

    /**
     * @param: none
     * @return: void
     * @description: This function sets up the entire test data from the excel sheet.
     */
    public void setupDataSheet() throws IOException {
        globalVars=GlobalVars.getInstance();
        InputStream input = getClass().getClassLoader().getResourceAsStream("testdata.xlsx");
        setDataObject(input);
    }

    /**
     * Returns map of test cases and their params
     */
    public Map<String, DataElements> getTestCaseMapping(){
        return testCaseMapping;
    }


    /**
     * @param inputStream
     * @return: void
     * @description: Presets all test case data from the excel sheet.
     */
    private void setDataObject(InputStream inputStream) throws IOException {
        workBook = new XSSFWorkbook(inputStream);
        try{
            testCasesSheet=workBook.getSheet(globalVars.getProjectName());
            Map<String, DataElements> tempMap;
            tempMap=getDataElements(testCasesSheet);
            for(Map.Entry<String, DataElements> entry: tempMap.entrySet()){
                testCaseMapping.put(entry.getKey(), entry.getValue());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param cell
     * @return: cellData as String
     * @description: This function takes a cell as an argument and returns the cell value based on the type of cell value type
     */
    @SuppressWarnings("deprecation")
    public String getCellData(Cell cell){
        String cellData="";
        try{
            if(cell!=null){
                switch(cell.getCellType()){

                    case Cell.CELL_TYPE_STRING:
                        cellData=cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        cellData=(int)cell.getNumericCellValue()+"";
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        cellData="";
                        break;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return cellData.trim();
    }

    /**
     * @param sheet takes the Sheet object returned from the workbook
     * @return: Map<String, DataElements>
     * @description return the map of page elements with element name and object of pageElement for the same.
     */
    private Map<String, DataElements> getDataElements(Sheet sheet) {
        Map<String, DataElements> dataElementsMap = new LinkedHashMap<>();
        String testCaseId="";
        String testCaseTitle = "";
        String parameters="";
        boolean isRunTrue =false;
        boolean isSanityTrue =false;
        boolean isProductionTrue =false;
        boolean isRegressionTrue =false;
        boolean isWebDesktopTrue =false;
        boolean isWebAndroidTrue =false;
        boolean isWebIOSTrue =false;
        boolean isAndroidAMPTrue=false;
        boolean isIOSAMPTrue=false;
        boolean isAndroidNativeTrue=false;
        boolean isIOSNativeTrue=false;

        for (int row = 1; row <= sheet.getLastRowNum(); row++) {
            Row dataRow = sheet.getRow(row);
            try{
                if(dataRow!=null) {
                    testCaseId = getCellData(dataRow.getCell(0));
                    testCaseTitle = getCellData(dataRow.getCell(1));
                    parameters = getCellData(dataRow.getCell(2));
                    isRunTrue = parseStringToBoolean(getCellData(dataRow.getCell(3)));
                    isProductionTrue = parseStringToBoolean(getCellData(dataRow.getCell(4)));
                    isSanityTrue = parseStringToBoolean(getCellData(dataRow.getCell(5)));
                    isRegressionTrue = parseStringToBoolean(getCellData(dataRow.getCell(6)));
                    isWebDesktopTrue = parseStringToBoolean(getCellData(dataRow.getCell(7)));
                    isWebAndroidTrue = parseStringToBoolean(getCellData(dataRow.getCell(8)));
                    isWebIOSTrue = parseStringToBoolean(getCellData(dataRow.getCell(9)));
                    isAndroidAMPTrue = parseStringToBoolean(getCellData(dataRow.getCell(10)));
                    isIOSAMPTrue = parseStringToBoolean(getCellData(dataRow.getCell(11)));
                    isAndroidNativeTrue = parseStringToBoolean(getCellData(dataRow.getCell(12)));
                    isIOSNativeTrue = parseStringToBoolean(getCellData(dataRow.getCell(13)));
                }
                else{
                    break;
                }

            }catch(Exception e){
                e.printStackTrace();
            }
            dataElementsMap.put(testCaseTitle, new DataElements(testCaseId, testCaseTitle, parameters, isRunTrue, isSanityTrue, isProductionTrue, isRegressionTrue, isWebDesktopTrue, isWebAndroidTrue, isWebIOSTrue, isAndroidAMPTrue, isIOSAMPTrue, isAndroidNativeTrue, isIOSNativeTrue));
        }
        return dataElementsMap;
    }

    /**
     * @param: String
     * @return: true/false
     * @description: This function takes a String with value yes or no and returns the corresponding boolean value
     */
    public static boolean parseStringToBoolean(String value){
        boolean boolResult=false;
        if(value.equalsIgnoreCase(Constants.YES) || value.equalsIgnoreCase("y")){
            boolResult=true;
        }
        else if(value.equalsIgnoreCase(Constants.NO) || value.equalsIgnoreCase("n")){
            boolResult=false;
        }
        return boolResult;
    }
}
