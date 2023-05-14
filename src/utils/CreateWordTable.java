package utils;

import entity.Group;
import entity.Student;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.main.STTextVerticalType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import static javax.swing.text.StyleConstants.Orientation;


public class CreateWordTable {
	public static void generateTable(Group group) {
		// создание документа Word
		XWPFDocument doc = new XWPFDocument();
		// создание таблицы
		int rowsCount = group.getStudents().size() + 1;
		int columnCount = group.getLectures().size() + 1;
		XWPFTable table = doc.createTable(rowsCount, columnCount);

		for (int i = 0; i < rowsCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				XWPFTableCell cell = table.getRow(i).getCell(j);

				// Установить вертикальное выравнивание по центру
				cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
				if (j > 0 || i == 0) {
					// Получить параграф внутри ячейки
					XWPFParagraph paragraph = cell.getParagraphs().get(0);

					// Установить выравнивание содержимого по центру
					paragraph.setAlignment(ParagraphAlignment.CENTER);
				}
			}
		}
		table.getRow(0).setHeight(1440); // 1 см = 1440 твипов
		table.getRow(0).getCell(0).setText("Студент/Дата");
		// заполнение первого столбца и первой строки таблицы
		String[] students = new String[group.getStudents().size()];
		for (int i = 0; i < group.getStudents().size(); i++) {
			students[i] = group.getStudents().get(i).toString();
		}

		for (int i = 0; i < students.length; i++) {
			table.getRow(i + 1).getCell(0).setText(students[i]);
		}
		String[] dates = new String[group.getLectures().size()];
		for (int i = 0; i < group.getLectures().size(); i++) {
			dates[i] = group.getLectures().get(i).toString();
		}

		for (int i = 0; i < dates.length; i++) {
			XWPFTableCell cell = table.getRow(0).getCell(i + 1);
			cell.getCTTc().addNewTcPr().addNewTextDirection().setVal(STTextDirection.BT_LR);
			XWPFParagraph paragraph = cell.getParagraphArray(0);
			XWPFRun run = paragraph.createRun();
			run.setText(dates[i]);
		}
		// заполнение ячеек таблицы случайными значениями
		Random rand = new Random();
		for (int i = 1; i < rowsCount; i++) {
			for (int j = 1; j < columnCount; j++) {
				if (group.getStudents().get(i - 1).isAbsence(group.getLectures().get(j - 1))) {
					table.getRow(i).getCell(j).setText("н");
				}
			}
		}
		// сохранение документа Word в файл
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("table.docx");
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		try {
			doc.write(out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			doc.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
