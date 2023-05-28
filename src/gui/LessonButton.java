package gui;

import entity.Lesson;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * Класс, представляющий кнопку для отображения занятия.
 */
public class LessonButton extends JButton {
	private final Lesson lesson;
	private boolean isChecked;
	private boolean isHalf;
	private String grade = "";

	/**
	 * Конструктор кнопки занятия.
	 *
	 * @param lesson занятие
	 */
	public LessonButton(Lesson lesson) {
		this.lesson = lesson;
		isChecked = false;
		if (lesson.isHoliday()) {
			setBackground(Color.ORANGE);
		}
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setMaximumSize(new Dimension(180, 80));
	}

	/**
	 * Устанавливает текст кнопки с указанием даты занятия и оценки.
	 *
	 * @param grade оценка
	 */
	public void setDateWithGrade(String grade) {
		setText("<html>" + getStringLabDate() + "<br> Оценка: " + grade + "</html>");
		this.grade = grade;
	}

	/**
	 * Устанавливает текст кнопки с указанием даты занятия.
	 */
	public void setDate() {
		setText(getStringLabDate());
	}

	/**
	 * Возвращает строковое представление даты лабораторной работы.
	 *
	 * @return строковое представление даты
	 */
	public String getStringLabDate() {
		return new SimpleDateFormat("dd.MM.yyyy").format(lesson.getDate());
	}

	/**
	 * Устанавливает цвет границы кнопки для текущего занятия.
	 *
	 * @param current флаг, указывающий, является ли кнопка текущей
	 */
	public void setCurrentColor(boolean current) {
		if (current) {
			setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR, 5));
		} else {
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
	}

	/**
	 * Проверяет, выбрана ли кнопка занятия.
	 *
	 * @return true, если кнопка выбрана, false в противном случае
	 */
	public boolean isChecked() {
		return isChecked;
	}

	/**
	 * Устанавливает отсутствие на половине занятия.
	 *
	 * @param half флаг, указывающий, является ли отсутствие не полным
	 */
	public void setHalf(boolean half) {
		if (half) {
			setBackground(Constants.HALF_ABSENCE_COLOR);
		}
		isHalf = half;
	}

	/**
	 * Устанавливает отсутствие на занятии.
	 *
	 * @param checked флаг, отсутствует ли студент на занятии
	 */
	public void setAbsence(boolean checked) {
		if (lesson.isHoliday()) {
			setBackground(Color.ORANGE);
		} else if (checked) {
			if (isHalf) {
				setBackground(Constants.HALF_ABSENCE_COLOR);
			} else {
				setBackground(Constants.ABSENCE_COLOR);
			}
		} else {
			setBackground(Color.WHITE);
		}
		isChecked = checked;
	}

	/**
	 * Возвращает занятие работу, связанное с кнопкой.
	 *
	 * @return занятие
	 */
	public Lesson getLesson() {
		return lesson;
	}

	/**
	 * Возвращает оценку, связанную с кнопкой занятия.
	 *
	 * @return оценка
	 */
	public String getGrade() {
		return grade;
	}
}
