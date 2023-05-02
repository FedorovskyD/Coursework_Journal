package dialogs.studentCard;

import database.dao.AttendanceDao;
import database.dao.impl.AttendanceDaoImpl;
import database.dao.impl.GradeDaoImpl;
import database.dao.impl.GroupDaoImpl;
import entity.Attendance;
import entity.Grade;
import entity.Lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public class LabButton extends JButton {
	private final Lab lab; //Лабораторная работа за которую отвечает данная кнопка
	private boolean isSelected; //Показывает активна ли данная кнопка

	public LabButton(StudentCardDialog owner, Lab lab, boolean isSelected) {
		this.lab = lab;
		this.isSelected = isSelected;
		//Получаем дату лабораторной работы для отображения на кнопке
		String labDate = new SimpleDateFormat("dd.MM.yyyy").format(lab.getDate());
		updateGrade("Нет");
		setPreferredSize(new Dimension(100, 30));
		//Добавляем слушателя который изменяет выбранную кнопку
		addActionListener(e -> {
			if (!this.isSelected) {
				this.setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
				this.isSelected = true;
				owner.currLabButton.isSelected = false;
				owner.currLabButton.setBorder(null);
				owner.currLabButton = this;
			}
		});
		//Добавляем слушателя кнопки пробела с помощью абстрактного класса KeyAdapter
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Object o = e.getSource();
				if (o instanceof LabButton jButton) {
					Color color = jButton.getBackground();
					if (e.getKeyCode() == KeyEvent.VK_SPACE && color.equals(Color.GRAY)) { // если запись о посещении не была добавлена
						Attendance attendance = new Attendance(jButton.lab.getId(), owner.currStudent.getId(), false);
						long id;
						if ((id = AttendanceDaoImpl.getInstance().save(attendance)) > 0) {
							attendance.setId(id);
							owner.currStudent.getAttendanceList().add(attendance);
							System.out.println("Запись о посещении добавлена");
							setBackground(Color.GREEN); // меняем цвет кнопки на зеленый
							setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
						}
					} else if (e.getKeyCode() == KeyEvent.VK_SPACE && color.equals(Color.GREEN)) {
						int choice = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите удалить запись о посещении?",
								"Подтверждение", JOptionPane.YES_NO_OPTION);
						if (choice == JOptionPane.YES_OPTION) {
							Attendance attendance = owner.currStudent.getLabAttendance(jButton.lab);
							Grade studentGrade = owner.currStudent.getLabGrade(lab);
							if (attendance != null) {
								if (AttendanceDaoImpl.getInstance().delete(attendance)) {
									System.out.println("Запись о посещении удалена");
									owner.currStudent.getAttendanceList().remove(attendance);
									if (GradeDaoImpl.getInstance().delete(studentGrade)) {
										owner.currStudent.getGradeList().remove(studentGrade);
										System.out.println("Оценка за лабораторную " + labDate + " удалена");
									}
									updateGrade("Нет");
									owner.update(owner.currStudent);
									setBackground(Color.GRAY);
									setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
								}
							}
						}
					}
				}
			}
		});
	}
	public void updateGrade(String grade) {
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
}
