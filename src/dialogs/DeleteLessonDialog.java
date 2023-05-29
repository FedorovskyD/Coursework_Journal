package dialogs;

import database.dao.impl.LessonDaoImpl;
import entity.Group;
import entity.Lesson;
import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Диалоговое окно для удаления занятия.
 */
public class DeleteLessonDialog extends JDialog {
	private JComboBox<Group> jcmbGroupNumber;
	private JComboBox<Lesson> jcmbLesson;
	private JRadioButton jradiobtnIsLecture;
	private JRadioButton jradiobtnIsLab;
	private JButton jbtDeleteLesson, jbtnClose;
	private final MainFrame mainFrame;

	/**
	 * Создает новый экземпляр диалогового окна для удаления занятия.
	 *
	 * @param mainFrame основное окно приложения
	 */
	public DeleteLessonDialog(MainFrame mainFrame) {
		super(mainFrame, "Удаление занятия", true);
		this.mainFrame = mainFrame;
		if (mainFrame.getCurrGroup() == null || !hasAnyLesson(mainFrame.getGroups())) {
			JOptionPane.showMessageDialog(mainFrame, "Нет ни одного занятия!");
			return;
		}

		// Инициализация компонентов интерфейса
		initComponents();

		// Настройка слушателей событий
		setupListeners();

		// Установка визуальных настроек диалогового окна
		setupDialog();

		// Отображение диалогового окна
		setVisible(true);
	}

	/*
	 * Инициализация компонентов интерфейса.
	 */
	private void initComponents() {
		jcmbGroupNumber = new JComboBox<>(new DefaultComboBoxModel<>(mainFrame.getGroups().toArray(new Group[0])));
		jcmbGroupNumber.setSelectedIndex(mainFrame.getJcmbGroupNumber().getSelectedIndex());
		jcmbGroupNumber.setMaximumSize(new Dimension(100, 30));

		jcmbLesson = new JComboBox<>();
		jcmbLesson.setMaximumSize(new Dimension(100, 30));

		ButtonGroup optionGroup = new ButtonGroup();
		jradiobtnIsLecture = new JRadioButton("Лекции", mainFrame.getJradiobtnLecture().isSelected());
		jradiobtnIsLab = new JRadioButton("Лабораторные", mainFrame.getJradiobtnLab().isSelected());
		refreshLessonCmb(jradiobtnIsLecture.isSelected());
		optionGroup.add(jradiobtnIsLab);
		optionGroup.add(jradiobtnIsLecture);

		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(jradiobtnIsLab);
		panel.add(jradiobtnIsLecture);

		jcmbLesson.setSelectedIndex(mainFrame.getJcmbCurrentDate().getSelectedIndex());

		jbtDeleteLesson = new JButton("Удалить");
		jbtnClose = new JButton("Закрыть");

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				JDialog source = (JDialog) e.getSource();
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					source.dispose();
			}
		});

		setLayout(new GridLayout(4, 2,5,5));
		add(new JLabel("Выберите группу:"));
		add(jcmbGroupNumber);
		add(new JLabel("Тип занятия:"));
		add(panel);
		add(new JLabel("Дата занятия:"));
		add(jcmbLesson);
		add(jbtDeleteLesson);
		add(jbtnClose);

		pack();
	}

	/*
	 * Настройка слушателей событий.
	 */
	private void setupListeners() {
		jbtDeleteLesson.addActionListener(e -> {
			Lesson lesson = (Lesson) jcmbLesson.getSelectedItem();
			if (lesson != null) {
				if (LessonDaoImpl.getInstance().delete(lesson)) {
					System.out.println("Занятие удалено");
					mainFrame.getGroups().get(jcmbGroupNumber.getSelectedIndex()).getLessons().remove(lesson);
					mainFrame.getJDialogStudentCard().getLessonButtons().clear();
					mainFrame.refreshDateCmb();
					mainFrame.refreshStudentTable();
					refreshLessonCmb(jradiobtnIsLecture.isSelected());
					JOptionPane.showMessageDialog(mainFrame,"Занятие успешно удалено!");
				}
			}else {
				JOptionPane.showMessageDialog(mainFrame,"Занятие для удаления не выбрано!");
			}
		});
		// Добавление слушателей
		jradiobtnIsLecture.addActionListener(e -> {
			refreshLessonCmb(jradiobtnIsLecture.isSelected());
		});
		jradiobtnIsLab.addActionListener(e -> refreshLessonCmb(jradiobtnIsLecture.isSelected()));
		jcmbGroupNumber.addActionListener(e -> {
			refreshLessonCmb(jradiobtnIsLecture.isSelected());
		});
		jcmbLesson.addActionListener(e -> {
			mainFrame.getJcmbCurrentDate().setSelectedItem(jcmbLesson.getSelectedItem());
		});
		jbtnClose.addActionListener(e -> dispose());
	}

	/*
	 * Установка визуальных настроек диалогового окна.
	 */
	private void setupDialog() {
		setLocationRelativeTo(null);
	}

	/*
	 * Обновляет список занятий в выпадающем списке в зависимости от выбранного типа занятия (лекции/лабораторные).
	 */
	private void refreshLessonCmb(boolean isLecture) {
		int index = jcmbGroupNumber.getSelectedIndex();
		if (index < 0) return;
		Group group = mainFrame.getGroups().get(index);
		if (group == null) return;
		if (isLecture) {
			jcmbLesson.setModel(new DefaultComboBoxModel<>(group.getLectures().toArray(new Lesson[0])));
		} else {
			jcmbLesson.setModel(new DefaultComboBoxModel<>(group.getLabs().toArray(new Lesson[0])));
		}
	}
	/*
	Проверяем существует ли хотя бы одно занятие
	 */
	private boolean hasAnyLesson(List<Group> groups) {
		for (Group group : groups) {
			if (!group.getLabs().isEmpty() || !group.getLectures().isEmpty()) {
				return true;
			}
		}
		return false;
	}
}
