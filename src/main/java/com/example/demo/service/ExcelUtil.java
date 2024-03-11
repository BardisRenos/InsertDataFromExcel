package com.example.demo.service;

import com.example.demo.resources.Client;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtil {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String SHEET = "Clients";

    public static boolean isExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Client> excelToData(InputStream fileExcel) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fileExcel);

            XSSFSheet sheet = workbook.getSheet(SHEET);

            Iterator<Row> rows = sheet.iterator();

            List<Client> clientList = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // in order to skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Client client = new Client();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            client.setId((int) currentCell.getNumericCellValue());
                            break;

                        case 1:
                            client.setName(currentCell.getStringCellValue());
                            break;

                        case 2:
                            client.setLastName(currentCell.getStringCellValue());
                            break;

                        case 3:
                            client.setAddress(currentCell.getStringCellValue());
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                clientList.add(client);
            }

            workbook.close();

            return clientList;

        } catch (IOException e) {
            throw new RuntimeException("Fail to parse the Excel file: " + e.getMessage());
        }
    }
}
