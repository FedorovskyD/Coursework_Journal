package gui.dialogs;

import database.dao.impl.LessonDaoImpl;
import entity.Group;
import entity.Lesson;
import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RemoveLessonDialog extends JDialog {
	private JComboBox<Group> groupJComboBox;
	private JComboBox<Lesson> lessonJComboBox;
	private JRadioButton lectureRadioButton;
	private JRadioButton labRadioButton;
	private JButton removeLesson;
	private MainFrame mainFrame;

	public RemoveLessonDialog(MainFrame mainFrame) {
		super(mainFrame,"Удаление занятия",true);
		this.mainFrame = mainFrame;
		groupJComboBox = new JComboBox<>(new DefaultComboBoxModel<>(mainFrame.getGroups().toArray(new Group[0])));
		groupJComboBox.setSelectedIndex(mainFrame.getCmbGroupNumber().getSelectedIndex());
		groupJComboBox.setMaximumSize(new Dimension(100,30));
		lessonJComboBox = new JComboBox<>();
		lessonJComboBox.setMaximumSize(new Dimension(100,30));
		ButtonGroup optionGroup = new ButtonGroup();
		lectureRadioButton = new JRadioButton("Лекции",mainFrame.getRadioBtnLecture().isSelected());
		labRadioButton = new JRadioButton("Лабораторные",mainFrame.getRadioBtnLab().isSelected());
		refreshLessonCmb(lectureRadioButton.isSelected());
		optionGroup.add(labRadioButton);
		optionGroup.add(lectureRadioButton);
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel.add(labRadioButton);
		panel.add(lectureRadioButton);
		lessonJComboBox.setSelectedIndex(mainFrame.getCurrDateCmb().getSelectedIndex());
		removeLesson = new JButton("Удалить");
		removeLesson.addActionListener(e -> {
			Lesson lesson = (Lesson) lessonJComboBox.getSelectedItem();
			if(lesson!=null) {
				if(LessonDaoImpl.getInstance().delete(lesson)) {
					System.out.println("Занятие удалено");
					mainFrame.getGroups().get(groupJComboBox.getSelectedIndex()).getLessons().remove(lesson);
					mainFrame.getJDialogStudentCard().getLessonButtons().clear();
					mainFrame.refreshDateCmb();
					mainFrame.refreshStudentTable();
					refreshLessonCmb(lectureRadioButton.isSelected());
				}
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				JDialog source = (JDialog)e.getSource();
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				source.dispose();
			}
		});
		lectureRadioButton.addActionListener(e -> {
			refreshLessonCmb(lectureRadioButton.isSelected());
		});
		labRadioButton.addActionListener(e -> refreshLessonCmb(lectureRadioButton.isSelected()));
		groupJComboBox.addActionListener(e -> {
			refreshLessonCmb(lectureRadioButton.isSelected());
		});
		setLayout(new GridLayout(4,2));
		add(new JLabel("Выберите группу:"));
		add(groupJComboBox);
		add(new JLabel("Тип занятия:"));
		add(panel);
		add(new JLabel("Дата занятия:"));
		add(lessonJComboBox);
		add(new JPanel());
		add(removeLesson);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);


	}
	private void refreshLessonCmb(boolean isLecture){
		if(isLecture){
			lessonJComboBox.setModel(new DefaultComboBoxModel<>(mainFrame.getGroups()
					.get(groupJComboBox.getSelectedIndex()).getLectures().toArray(new Lesson[0])));
		}else {
			lessonJComboBox.setModel(new DefaultComboBoxModel<>(mainFrame.getGroups()
					.get(groupJComboBox.getSelectedIndex()).getLabs().toArray(new Lesson[0])));
		}
	}
}
