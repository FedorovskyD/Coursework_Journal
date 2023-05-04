package dialogs.studentCard;

import database.dao.impl.AttendanceDaoImpl;
import database.dao.impl.GradeDaoImpl;
import entity.Attendance;
import entity.Grade;
import entity.Lab;
import entity.Student;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;

public class LabButton extends JButton {
	protected final Lab lab; //Лабораторная работа за которую отвечает данная кнопка
	protected boolean isSelected; //Показывает активна ли данная кнопка

	public LabButton(Student currStudent, Lab lab) {
		this.lab = lab;
		Grade grade = currStudent.getLabGrade(lab);
		if (grade != null) {
			setGrade(String.valueOf(grade.getGrade()));
		} else {
			setGrade("Нет");
		}
		isSelected = false;
		if (currStudent.isAttendance(lab)) {
			setBackground(Constants.ATTENDANCE_COLOR);
		} else {
			setBackground(Constants.NO_ATTENDANCE_COLOR);
		}
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_UP ||
						keyCode == KeyEvent.VK_DOWN ||
						keyCode == KeyEvent.VK_LEFT ||
						keyCode == KeyEvent.VK_RIGHT) {
					e.consume(); // отмена действия для стрелочных клави
				}
			}
		});
		setPreferredSize(new Dimension(200,30));
		setMaximumSize(getPreferredSize());
	}
	public void setGrade(String grade) {
		setText("<html>" + getStringLabDate() + "<br> Оценка: " + grade + "</html>");
	}

	public Lab getLab() {
		return lab;
	}

	public String getStringLabDate() {
		return new SimpleDateFormat("dd.MM.yyyy").format(lab.getDate());
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