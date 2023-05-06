package dialogs.studentCard;

import entity.Grade;
import entity.Lesson;
import entity.Student;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class LabButton extends JButton {
	protected final Lesson lesson; //Лабораторная работа за которую отвечает данная кнопка
	protected boolean isSelected; //Показывает активна ли данная кнопка

	public LabButton(Student currStudent, Lesson lesson) {
		this.lesson = lesson;
		Grade grade = currStudent.getLabGrade(lesson);
		String gradeStr = (grade != null) ? String.valueOf(grade.getGrade()) : "Нет";
		setGrade(gradeStr);
		isSelected = false;
		Color color = currStudent.isAttendance(lesson) ? Constants.ATTENDANCE_COLOR : Constants.NO_ATTENDANCE_COLOR;
		setBackground(color);
		setPreferredSize(new Dimension(200, 30));
		setMaximumSize(getPreferredSize());
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

	public void selected(boolean selected) {
		isSelected = selected;
	}
	private void selectButton(JDialogStudentCard owner) {
		if (!this.isSelected) {
			this.setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
			this.isSelected = true;
			owner.currLabButton.isSelected = false;
			owner.currLabButton.setBorder(null);
			owner.currLabButton = this;
		}
	}




}