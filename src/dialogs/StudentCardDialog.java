package dialogs;

import MainFrame.StudentCardPanel;
import entity.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StudentCardDialog extends JDialog {
	private final StudentCardPanel studentCardPanel;
	public StudentCardDialog(JFrame owner, String title) {
		super(owner, title, true);
		setLayout(new BorderLayout());

		// Добавляем элементы на панель содержимого
		studentCardPanel = new StudentCardPanel(owner);
		add(studentCardPanel, BorderLayout.CENTER);

		// Устанавливаем размер и положение окна
		setSize(1000, 800);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
	}

	public StudentCardPanel getStudentCardPanel() {
		return studentCardPanel;
	}
}