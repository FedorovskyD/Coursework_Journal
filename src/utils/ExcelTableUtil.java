package utils;

import entity.Absence;
import entity.Grade;
import entity.Lesson;
import entity.Student;
import gui.studentTable.StudentTableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExcelTableUtil {
	public static void main(String[] args) {
	}

	public static byte[] createAttendanceTable(StudentTableModel studentTableModel) {
		List<Student> students = studentTableModel.getStudents();
		List<Lesson> lessons = studentTableModel.getLessons();

		Workbook workbook = new XSSFWorkbook();
		String lessonType = studentTableModel.isLecture() ? "Лекции" : "Лабораторные";
		Sheet sheet = workbook.createSheet(studentTableModel.getGroupNumber() + ". " + lessonType);
		int currCell = 0;

		Row headerRow = sheet.createRow(0);
		Cell headerCell = headerRow.createCell(currCell);
		headerCell.setCellValue("Студенты/Даты");
		currCell++;

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

		int lastColumn = lessons.size() + (studentTableModel.isLecture() ? 1 : 2);

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

			CellStyle studentCellStyle = workbook.createCellStyle();
			studentCellStyle.setBorderTop(BorderStyle.THIN);
			studentCellStyle.setBorderBottom(BorderStyle.THIN);
			studentCellStyle.setBorderLeft(BorderStyle.THIN);
			studentCellStyle.setBorderRight(BorderStyle.THIN);
			studentCell.setCellStyle(studentCellStyle);
			studentCell.setCellValue(students.get(i).toString());

			int currDateColumn = studentTableModel.isLecture() ? 2 : 3;
			for (Lesson lesson : lessons) {
				Cell attendanceValueCell = dataRow.createCell(currDateColumn);
				attendanceValueCell.setCellStyle(centerAlignStyle);
				Absence absence = students.get(i).getLessonAbsence(lesson);
				Grade grade = students.get(i).getLessonGrade(lesson);

				if (absence != null && grade != null) {
					if (absence.isHalf()) {
						attendanceValueCell.setCellValue(grade.getGrade() + "(1н)");
					} else {
						attendanceValueCell.setCellValue(grade.getGrade() + "(н)");
					}
				} else if (grade != null) {
					attendanceValueCell.setCellValue(grade.getGrade());
				} else if (absence != null) {
					attendanceValueCell.setCellValue("н");
				}

				currDateColumn++;
			}
		}

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

		for (int i = 0; i <= lastColumn; i++) {
			sheet.autoSizeColumn(i);
		}

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			workbook.write(baos);
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
