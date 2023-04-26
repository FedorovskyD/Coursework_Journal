package dialogs;

import MainFrame.MainWindow;
import MainFrame.StudentCardPanel;
import entity.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class StudentCardDialog extends JDialog {
private StudentCardPanel studentCardPanel;
	public StudentCardDialog(Frame owner, String title, Student student) {
		super(owner, title, true);
		setLayout(new BorderLayout());

		// Добавляем элементы на панель содержимого
		studentCardPanel = new StudentCardPanel(student);
		add(studentCardPanel, BorderLayout.CENTER);

		// Устанавливаем размер и положение окна
		setSize(700, 900);
		setLocationRelativeTo(owner);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MainWindow.getInstance().getStudentTable().clearSelection();
			}
		});
	}
	public void updateData(Student student) {
		// обновляем поля на диалоговом окне с информацией о студенте
		getContentPane().removeAll();
		studentCardPanel = new StudentCardPanel(student);
		add(studentCardPanel);
		studentCardPanel.setVisible(true);
		getContentPane().revalidate();
		getContentPane().repaint();
		// и т.д. - здесь нужно обновить все соответствующие поля
		// перерисовываем диалоговое окно
	}
}