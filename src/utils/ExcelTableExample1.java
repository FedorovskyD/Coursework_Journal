package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelTableExample1 {
	public static void main(String[] args) {
		String filePath = "посещаемость.xlsx";
		String sheetName = "Название_листа";
		int startRow = 1; // Номер строки, с которой начинается таблица
		int startColumn = 1; // Номер столбца, с которого начинается таблица
		int numRows = 5; // Количество строк в таблице
		int numColumns = 3; // Количество столбцов в таблице

		try (FileInputStream fis = new FileInputStream(filePath);
		     Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);

			// Создаем таблицу
			CellRangeAddress tableRange = new CellRangeAddress(startRow, startRow + numRows - 1,
					startColumn, startColumn + numColumns - 1);
			sheet.setAutoFilter(tableRange);

			// Заполняем данные в таблицу
			for (int row = startRow; row < startRow + numRows; row++) {
				Row dataRow = sheet.createRow(row);
				for (int column = startColumn; column < startColumn + numColumns; column++) {
					Cell dataCell = dataRow.createCell(column);
					dataCell.setCellValue("Значение " + row + "-" + column);
				}
			}

			// Автоматически подгоняем ширину столбцов
			for (int i = startColumn; i < startColumn + numColumns; i++) {
				sheet.autoSizeColumn(i);
			}

			// Сохраняем изменения в файл
			try (FileOutputStream fos = new FileOutputStream(filePath)) {
				workbook.write(fos);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

