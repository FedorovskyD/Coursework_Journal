package dialogs;

import MainFrame.StudentCardPanel;
import entity.Student;

import javax.swing.*;
import java.awt.*;

public class StudentCardDialog extends JDialog {
private StudentCardPanel studentCardPanel;
	public StudentCardDialog(Frame owner, String title, Student student) {
		super(owner, title, false);
		setLayout(new BorderLayout());

		// Добавляем элементы на панель содержимого
		studentCardPanel = new StudentCardPanel(student);
		add(studentCardPanel, BorderLayout.CENTER);

		// Устанавливаем размер и положение окна
		setSize(700, 900);
		setLocationRelativeTo(owner);
	}
	public void updateData(Student student) {
		// обновляем поля на диалоговом окне с информацией о студенте
		removeAll();
		validate();
		repaint();
		add(new StudentCardPanel(student));
		// и т.д. - здесь нужно обновить все соответствующие поля
		// перерисовываем диалоговое окно


	}
}