package dialogs;

import database.dao.impl.LessonDaoImpl;
import entity.Group;
import entity.Lesson;
import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		jcmbGroupNumber.setSelectedIndex(mainFrame.getCmbGroupNumber().getSelectedIndex());
		jcmbGroupNumber.setMaximumSize(new Dimension(100, 30));

		jcmbLesson = new JComboBox<>();
		jcmbLesson.setMaximumSize(new Dimension(100, 30));

		ButtonGroup optionGroup = new ButtonGroup();
		jradiobtnIsLecture = new JRadioButton("Лекции", mainFrame.getRadioBtnLecture().isSelected());
		jradiobtnIsLab = new JRadioButton("Лабораторные", mainFrame.getRadioBtnLab().isSelected());
		refreshLessonCmb(jradiobtnIsLecture.isSelected());
		optionGroup.add(jradiobtnIsLab);
		optionGroup.add(jradiobtnIsLecture);

		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(jradiobtnIsLab);
		panel.add(jradiobtnIsLecture);

		jcmbLesson.setSelectedIndex(mainFrame.getCurrDateCmb().getSelectedIndex());

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

		setLayout(new GridLayout(4, 2));
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
				}
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
			mainFrame.getCurrDateCmb().setSelectedItem(jcmbLesson.getSelectedItem());
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
		if (isLecture) {
			jcmbLesson.setModel(new DefaultComboBoxModel<>(mainFrame.getGroups()
					.get(jcmbGroupNumber.getSelectedIndex()).getLectures().toArray(new Lesson[0])));
		} else {
			jcmbLesson.setModel(new DefaultComboBoxModel<>(mainFrame.getGroups()
					.get(jcmbGroupNumber.getSelectedIndex()).getLabs().toArray(new Lesson[0])));
		}
	}
}
