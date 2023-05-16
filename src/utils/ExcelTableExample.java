package utils;

import database.dao.impl.LessonDaoImpl;
import database.dao.impl.StudentDaoImpl;
import entity.Lesson;
import entity.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExcelTableExample {
	public static void main(String[] args) {
		List<Student> students = StudentDaoImpl.getInstance().getStudentsByGroupId(1);
		List<Lesson> dates = LessonDaoImpl.getInstance().getAllLabByGroupId(1); // Здесь можно передать реальные даты
		createAttendanceTable(students, dates);
	}

	public static void createAttendanceTable(List<Student> students, List<Lesson> dates) {
		// Создаем новую книгу Excel
		Workbook workbook = new XSSFWorkbook();

		// Создаем лист
		Sheet sheet = workbook.createSheet("Посещаемость");

		// Создаем заголовок
		Row headerRow = sheet.createRow(0);
		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue("Студенты/Даты");
		// Создаем стиль для выравнивания по центру
		CellStyle centerAlignStyle = workbook.createCellStyle();
		centerAlignStyle.setAlignment(HorizontalAlignment.CENTER);
		centerAlignStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		headerCell.setCellStyle(centerAlignStyle);
		for (int i = 0; i < dates.size(); i++) {
			Cell dateCell = headerRow.createCell(i + 1);
			dateCell.setCellValue(new SimpleDateFormat("dd.MM.yyyy").format(dates.get(i).getDate()));
		}

		// Добавляем данные в таблицу
		for (int i = 0; i < students.size(); i++) {
			Row dataRow = sheet.createRow(i + 1);
			Cell studentCell = dataRow.createCell(0);
			studentCell.setCellValue(students.get(i).toString());
		}
		// Разворачиваем текст в первой строке, начиная со второго столбца
		CellStyle verticalTextStyle = workbook.createCellStyle();
		verticalTextStyle.setRotation((short) 90);
		for (int i = 1; i <= dates.size(); i++) {
			Cell dateCell = headerRow.getCell(i);
			dateCell.setCellStyle(verticalTextStyle);
		}

		// Автоматически подгоняем ширину столбцов
		for (int i = 0; i <= dates.size(); i++) {
			sheet.autoSizeColumn(i);
		}

		// Сохраняем книгу Excel в файл
		try (FileOutputStream fileOut = new FileOutputStream("посещаемость.xlsx")) {
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

