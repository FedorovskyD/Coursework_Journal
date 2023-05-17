package gui.studentTable.studentCard.lessonButton;

import entity.Lesson;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class LessonButton extends JButton {
	private final Lesson lesson; //Лабораторная работа за которую отвечает данная кнопка
	private boolean isCurrent; //Показывает активна ли данная кнопка
	private boolean isChecked;
	private boolean isHalf;
	private String grade = "";

	public LessonButton(Lesson lesson) {
		this.lesson = lesson;
		isCurrent = false;
		isChecked = false;
		if (lesson.isHoliday()) {
			setBackground(Color.ORANGE);
		}
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setMaximumSize(new Dimension(180, 80));
	}

	public void setGrade(String grade) {
		setText("<html>" + getStringLabDate() + "<br> Оценка: " + grade + "</html>");
		this.grade = grade;
	}

	public void setData() {
		setText(getStringLabDate());
	}

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
		if (current) {
			setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR, 5));
		} else {
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
		isCurrent = current;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setHalf(boolean half) {
		if(half){
			setBackground(Constants.HALF_ABSENCE_COLOR);
		}
		isHalf = half;
	}

	public boolean isHalf() {
		return isHalf;
	}

	public void setChecked(boolean checked) {
		if (lesson.isHoliday()) {
			setBackground(Color.ORANGE);
		}else
		if (checked) {
			if(isHalf) {
				setBackground(Constants.HALF_ABSENCE_COLOR);
			}else {
				setBackground(Constants.ABSENCE_COLOR);
			}
		} else {
			setBackground(Color.WHITE);
		}
		isChecked = checked;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public String getGrade() {
		return grade;
	}
}