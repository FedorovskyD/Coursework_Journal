package utils;

import database.dao.impl.LessonDaoImpl;
import database.dao.impl.StudentDaoImpl;
import entity.Absence;
import entity.Grade;
import entity.Lesson;
import entity.Student;
import gui.studentTable.StudentTableModel;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExcelTableExample {
	public static void main(String[] args) {
	}

	public static void createAttendanceTable(StudentTableModel studentTableModel, File file) {
		List<Student> students = studentTableModel.getStudents();
		List<Lesson> lessons = studentTableModel.getLessons();
		// Создаем новую книгу Excel
		Workbook workbook = new XSSFWorkbook();

		// Создаем лист
		String lessonType = studentTableModel.isLecture() ? "Лекции" : "Лабораторные";
		Sheet sheet = workbook.createSheet(studentTableModel.getGroupNumber() + ". " + lessonType);
		int currCell = 0;
		// Создаем заголовок
		Row headerRow = sheet.createRow(0);
		Cell headerCell = headerRow.createCell(currCell);
		headerCell.setCellValue("Студенты/Даты");
		currCell++;
		// Создаем стиль для выравнивания по центру
		CellStyle centerAlignStyle = workbook.createCellStyle();
		centerAlignStyle.setAlignment(HorizontalAlignment.CENTER);
		centerAlignStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		centerAlignStyle.setBorderTop(BorderStyle.THIN);
		centerAlignStyle.setBorderBottom(BorderStyle.THIN);
		centerAlignStyle.setBorderLeft(BorderStyle.THIN);
		centerAlignStyle.setBorderRight(BorderStyle.THIN);
		headerCell.setCellStyle(centerAlignStyle);
		Cell cell = headerRow.createCell(currCell);
		cell.setCellValue("Посещаемость, %");
		currCell++;
		if (!studentTableModel.isLecture()) {
			cell = headerRow.createCell(currCell);
			cell.setCellValue("Средний балл");
			currCell++;
		}

		for (Lesson lesson : lessons) {
			Cell dateCell = headerRow.createCell(currCell);
			currCell++;
			dateCell.setCellValue(new SimpleDateFormat("dd.MM.yyyy").format(lesson.getDate()));
		}
		int lastColumn = lessons.size() + (studentTableModel.isLecture() ? +1 : 2);
		// Добавляем данные в таблицу
		for (int i = 0; i < students.size(); i++) {
			Row dataRow = sheet.createRow(i + 1);
			Cell studentCell = dataRow.createCell(0);
			Cell attendanceCell = dataRow.createCell(1);
			Cell gradeCell = null;
			if (studentTableModel.isLecture()) {
				int lectureHours = studentTableModel.getGroup().getLectureHours();
				attendanceCell.setCellValue((lectureHours - students.get(i).getLectureAbsenceHours()) / (double) lectureHours * 100);
			} else {
				int labHours = studentTableModel.getGroup().getLabHours();
				attendanceCell.setCellValue((labHours - students.get(i).getLabAbsenceHours()) / (double) labHours * 100);
				gradeCell = dataRow.createCell(2);
				gradeCell.setCellValue(students.get(i).getAverageGrade());
			}
			CellStyle style = workbook.createCellStyle();
			style.setBorderTop(BorderStyle.THIN);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setDataFormat(workbook.createDataFormat().getFormat("#0.0"));
			attendanceCell.setCellStyle(style);
			if (gradeCell != null) {
				style.setDataFormat(workbook.createDataFormat().getFormat("#0.00"));
				gradeCell.setCellStyle(style);
			}
			CellStyle studetCellStyle = workbook.createCellStyle();
			studetCellStyle.setBorderTop(BorderStyle.THIN);
			studetCellStyle.setBorderBottom(BorderStyle.THIN);
			studetCellStyle.setBorderLeft(BorderStyle.THIN);
			studetCellStyle.setBorderRight(BorderStyle.THIN);
			studentCell.setCellStyle(studetCellStyle);
			studentCell.setCellValue(students.get(i).toString());

			// Заполняем оставшиеся ячейки значениями
			int currDateColumn = studentTableModel.isLecture() ? 2 : 3; // Начинаем с 3-й ячейки (после столбца среднего балла, если это лабораторные работы)
			for (Lesson lesson : lessons) {
				Cell attendanceValueCell = dataRow.createCell(currDateColumn);
				attendanceValueCell.setCellStyle(centerAlignStyle);
				Absence absence = students.get(i).getLessonAbsence(lesson);
				Grade grade = students.get(i).getLessonGrade(lesson);
				if (absence != null && grade!=null) {
					if (absence.isHalf()) {
						attendanceValueCell.setCellValue(grade.getGrade() + "(1н)");
					} else {
						attendanceValueCell.setCellValue(grade.getGrade() + "(н)");
					}
				} else if (grade != null) {
					attendanceValueCell.setCellValue(grade.getGrade());
				}else if(absence!=null){
					attendanceValueCell.setCellValue("н");
				}
				// Здесь установите нужное значение для ячейки, например, посещаемость или что-то другое
				// attendanceValueCell.setCellValue(...);
				currDateColumn++;
			}
		}
		// Разворачиваем текст в первой строке, начиная со второго столбца
		CellStyle verticalTextStyle = workbook.createCellStyle();
		verticalTextStyle.setBorderTop(BorderStyle.THIN);
		verticalTextStyle.setBorderBottom(BorderStyle.THIN);
		verticalTextStyle.setBorderLeft(BorderStyle.THIN);
		verticalTextStyle.setBorderRight(BorderStyle.THIN);
		verticalTextStyle.setRotation((short) 90);
		for (int i = 1; i <= lastColumn; i++) {
			Cell dateCell = headerRow.getCell(i);
			dateCell.setCellStyle(verticalTextStyle);
		}

		// Автоматически подгоняем ширину столбцов
		for (int i = 0; i <= lastColumn; i++) {
			sheet.autoSizeColumn(i);
		}

		// Сохраняем книгу Excel в файл
		try (FileOutputStream fileOut = new FileOutputStream(file)) {
			workbook.write(fileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Закрываем книгу
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

