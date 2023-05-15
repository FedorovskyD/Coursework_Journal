package utils;

import entity.Group;
import entity.Lesson;
import entity.Student;
import gui.studentTable.StudentTableModel;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;


public class WordConnector {
	public static void generateTable(StudentTableModel studentTableModel, File file) {
		// создание документа Word
		XWPFDocument doc = new XWPFDocument();
		// установка альбомной ориентации и размера страницы
		CTPageSz pageSize = doc.getDocument().getBody().addNewSectPr().addNewPgSz();
		pageSize.setOrient(org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation.LANDSCAPE);
		pageSize.setW(15840); // ширина страницы в твипах (A4 210 мм = 595,2 pt = 842 фактических пикселя)
		pageSize.setH(12240); // высота страницы в твипах (A4 297 мм = 841,68 pt = 1190 фактических пикселя)
		CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
		CTPageMar pageMar = sectPr.addNewPgMar();
		pageMar.setLeft(BigInteger.valueOf(500L));
		pageMar.setRight(BigInteger.valueOf(500L));
		XWPFParagraph para = doc.createParagraph();
		para.createRun().setText("Группа "+studentTableModel.getGroupNumber() + (studentTableModel.isLecture()?". Лекции ":
				". Лабораторные"));
		// создание таблицы
		List<Student> students = studentTableModel.getStudents();
		List<Lesson> lessons = studentTableModel.getLessons();
		int rowsCount = students.size() + 1;
		int columnCount = studentTableModel.getColumnCount();
		XWPFTable table = doc.createTable(rowsCount, columnCount);
		table.getRow(0).setHeight(1440); // 1 см = 1440 твипов
		table.getRow(0).getCell(0).setText(studentTableModel.getColumnName(0));
		for (int i = 1; i < columnCount; i++) {
			XWPFTableCell cell = table.getRow(0).getCell(i);
			cell.getCTTc().addNewTcPr().addNewTextDirection().setVal(STTextDirection.BT_LR);
			XWPFParagraph paragraph = cell.getParagraphArray(0);
			XWPFRun run = paragraph.createRun();
			run.setText(studentTableModel.getColumnName(i));
		}
		for (int i = 0; i < students.size(); i++) {
			table.getRow(i + 1).getCell(0).setText(students.get(i).toString());
		}
		if (columnCount > lessons.size() + 1) {
			for (int i = 1; i < rowsCount; i++) {
				table.getRow(i).getCell(1).setText(String.valueOf(students.get(i - 1).getAverageGrade()));
			}
		}
		int columnFirstDate = -1;
		if (lessons.size()!=0) {
			columnFirstDate = lessons.get(0).isLecture() ? 1 : 2;

			for (int i = 1; i < rowsCount; i++) {
				for (int j = columnFirstDate; j < columnCount; j++) {
					String text = null;
					if (students.get(i - 1).isAbsence(lessons.get(j - columnFirstDate))) {
						text = "н";
					} else if (students.get(i - 1).getLessonGrade(lessons.get(j - columnFirstDate)) != null) {
						text = String.valueOf(students.get(i - 1).getLessonGrade(lessons.get(j - columnFirstDate)).getGrade());
					}
					if (text != null) {
						table.getRow(i).getCell(j).setText(text);
					}

				}
			}
		}
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
		// перебор всех строк и столбцов в таблице
		for (XWPFTableRow row : table.getRows()) {
			for (XWPFTableCell cell : row.getTableCells()) {
				// перебор всех параграфов внутри ячейки
				for (XWPFParagraph paragraph : cell.getParagraphs()) {
					// установка отступов в ноль
					paragraph.setIndentationLeft(0);
					paragraph.setIndentationRight(0);
					paragraph.setSpacingBefore(0);
					paragraph.setSpacingAfter(0);
				}
			}
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			System.out.println("Не удалось создать файл");
		}
		try {
			doc.write(out);
		} catch (IOException e) {
			System.out.println("Не удалось записать в файл");
		}
		try {
			out.close();
		} catch (IOException e) {
			System.out.println("Не удалось закрыть файл");
		}
		try {
			doc.close();
		} catch (IOException e) {
			System.out.println("Не удалось закрыть документ");
		}
	}
}
