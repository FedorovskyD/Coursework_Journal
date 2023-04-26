package dialogs;

import MainFrame.StudentCardPanel;
import entity.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StudentCardDialog extends JDialog {
	private StudentCardPanel studentCardPanel;
	public StudentCardDialog(Frame owner, String title, Student student) {
		super(owner, title, true);
		setLayout(new BorderLayout());

		// Добавляем элементы на панель содержимого
		studentCardPanel = new StudentCardPanel(student);
		add(studentCardPanel, BorderLayout.CENTER);

		// Устанавливаем размер и положение окна
		setSize(1000, 800);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					setVisible(false);
				}
			}
		});
	}

	public StudentCardPanel getStudentCardPanel() {
		return studentCardPanel;
	}
}