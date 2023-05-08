package MainFrame.studentCard;

import entity.Lesson;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class LessonButton extends JButton {
	protected final Lesson lesson; //Лабораторная работа за которую отвечает данная кнопка
	protected boolean isSelected; //Показывает активна ли данная кнопка

	public LessonButton(Lesson lesson) {
		this.lesson = lesson;
		isSelected = false;
	}
	public void setGrade(String grade) {
		setText("<html>" + getStringLabDate() + "<br> Оценка: " + grade + "</html>");
	}

	public Lesson getLab() {
		return lesson;
	}

	public String getStringLabDate() {
		return new SimpleDateFormat("dd.MM.yyyy").format(lesson.getDate());
	}

	@Override
	public boolean isSelected() {
		return isSelected;
	}
}