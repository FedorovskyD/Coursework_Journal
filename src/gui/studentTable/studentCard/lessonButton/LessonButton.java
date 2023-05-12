package gui.studentTable.studentCard.lessonButton;

import entity.Lesson;
import utils.Constants;

import javax.swing.*;
import java.text.SimpleDateFormat;

public class LessonButton extends JButton {
	private final Lesson lesson; //Лабораторная работа за которую отвечает данная кнопка
	private boolean isCurrent; //Показывает активна ли данная кнопка
	private boolean isChecked;

	public LessonButton(Lesson lesson) {
		this.lesson = lesson;
		isCurrent = false;
	}
	public void setGrade(String grade) {
		setText("<html>" + getStringLabDate() + "<br> Оценка: " + grade + "</html>");
	}
	public void  setData(){setText(getStringLabDate());}

	public Lesson getLab() {
		return lesson;
	}

	public String getStringLabDate() {
		return new SimpleDateFormat("dd.MM.yyyy").format(lesson.getDate());
	}

	public boolean isCurrent() {
		return isCurrent;
	}

	public void setCurrent(boolean current) {
		if(current){
			setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR,5));
		}else {
			setBorder(null);
		}
		isCurrent = current;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	public Lesson getLesson() {
		return lesson;
	}
}