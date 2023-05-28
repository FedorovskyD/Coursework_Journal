package dialogs;

import gui.MainFrame;
import entity.Group;
import listeners.AddLessonDialogListener;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Диалоговое окно для добавления занятия.
 */
public class AddLessonDialog extends JDialog {
	private final JRadioButton jradiobtnLecture;
	private final JSpinner dateSpinner;
	private final JComboBox<Group> jcmbGroupNumber;
	private final JButton jbtnAddLesson, jbtnCancel;
	private final JRadioButton jradionbtnIsHoliday;
	private final MainFrame mainFrame;

	/**
	 * Конструктор класса.
	 *
	 * @param parent родительское окно (MainFrame).
	 */
	public AddLessonDialog(JFrame parent) {
		super(parent, "Добавление занятия", true);
		mainFrame = (MainFrame) parent;

		// Создание компонентов интерфейса
		JLabel jlblLessonType = new JLabel("Тип занятия:");
		JLabel jlblHoliday = new JLabel("Праздничный день:");
		JLabel jlblDate = new JLabel("Дата:");
		JLabel jlblGroup = new JLabel("Группа:");
		JRadioButton jradiobtnLab = new JRadioButton("Лабораторная", mainFrame.getJradiobtnLab().isSelected());
		jradiobtnLecture = new JRadioButton("Лекция", mainFrame.getJradiobtnLecture().isSelected());
		ButtonGroup buttonGroupLessonType = new ButtonGroup();
		ButtonGroup buttonGroupHoliday = new ButtonGroup();
		buttonGroupLessonType.add(jradiobtnLab);
		buttonGroupLessonType.add(jradiobtnLecture);
		JPanel jpanelLessonType = new JPanel(new GridLayout(1, 2));
		jpanelLessonType.add(jradiobtnLab);
		jpanelLessonType.add(jradiobtnLecture);
		jradionbtnIsHoliday = new JRadioButton("Да", false);
		JRadioButton jradiobtnNotHoliday = new JRadioButton("Нет", true);
		buttonGroupHoliday.add(jradionbtnIsHoliday);
		buttonGroupHoliday.add(jradiobtnNotHoliday);
		JPanel jpanelHolidayOption = new JPanel(new GridLayout(1, 2));
		jpanelHolidayOption.add(jradionbtnIsHoliday);
		jpanelHolidayOption.add(jradiobtnNotHoliday);
		jcmbGroupNumber = new JComboBox<>(new DefaultComboBoxModel<>(mainFrame.getGroups().toArray(new Group[0])));
		jcmbGroupNumber.setSelectedItem(mainFrame.getCurrentGroup());
		SpinnerDateModel dateModel = new SpinnerDateModel();
		dateSpinner = new JSpinner(dateModel);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, dateFormat.toPattern());
		dateSpinner.setEditor(dateEditor);

		jbtnAddLesson = new JButton("Добавить");
		jbtnCancel = new JButton("Закрыть");

		// Создание панели с компонентами интерфейса
		JPanel jpanel = new JPanel(new GridLayout(5, 2));
		jpanel.add(jlblLessonType);
		jpanel.add(jpanelLessonType);
		jpanel.add(jlblHoliday);
		jpanel.add(jpanelHolidayOption);
		jpanel.add(jlblDate);
		jpanel.add(dateSpinner);
		jpanel.add(jlblGroup);
		jpanel.add(jcmbGroupNumber);
		jpanel.add(jbtnAddLesson);
		jpanel.add(jbtnCancel);

		// Добавление слушателей событий
		AddLessonDialogListener addLessonDialogListener = new AddLessonDialogListener(this);
		jbtnAddLesson.addActionListener(addLessonDialogListener);
		jbtnCancel.addActionListener(addLessonDialogListener);

		this.setContentPane(jpanel);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	/**
	 * Получение комбобокса группы.
	 *
	 * @return комбобокс группы.
	 */
	public JComboBox<Group> getJcmbGroupNumber() {
		return jcmbGroupNumber;
	}

	/**
	 * Получение выбранной даты.
	 *
	 * @return выбранная дата.
	 */
	public Date getDate() {
		return ((SpinnerDateModel) dateSpinner.getModel()).getDate();
	}

	/**
	 * Получение радиокнопки для типа занятия "Лекция".
	 *
	 * @return радиокнопка "Лекция".
	 */
	public JRadioButton getJradiobtnLecture() {
		return jradiobtnLecture;
	}

	/**
	 * Получение кнопки "Добавить".
	 *
	 * @return кнопка "Добавить".
	 */
	public JButton getJbtnAddLesson() {
		return jbtnAddLesson;
	}

	/**
	 * Получение главного окна (MainFrame).
	 *
	 * @return главное окно (MainFrame).
	 */
	public MainFrame getMainWindow() {
		return mainFrame;
	}

	/**
	 * Получение радиокнопки для праздничного дня.
	 *
	 * @return радиокнопка для праздничного дня.
	 */
	public JRadioButton getJradionbtnIsHoliday() {
		return jradionbtnIsHoliday;
	}
	/**
	 * Получение кнопки "Назад".
	 *
	 * @return кнопка "Назад".
	 */
	public JButton getJbtnCancel() {
		return jbtnCancel;
	}
}
