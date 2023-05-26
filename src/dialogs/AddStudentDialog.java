package dialogs;

import gui.MainFrame;
import entity.Group;
import listeners.AddStudentDialogListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Диалоговое окно для добавления студента.
 */
public class AddStudentDialog extends JDialog {
	private final JTextField jTextFieldFirstName;
	private final JTextField jTextFieldLastName;
	private final JTextField jTextFieldMiddleName;
	private final JLabel jlblPhotoPath;
	private final JComboBox<Group> jcmbGroupNumber;
	private final JTextField jTextFieldEmail;
	private final JTextField jTextFieldTelephone;
	private final JButton jbtnAddStudent, jbtnChoosePhoto, jbtnClose;
	private File photoPath;
	protected MainFrame mainFrame;

	/**
	 * Конструктор класса.
	 *
	 * @param parent родительское окно (MainFrame).
	 */
	public AddStudentDialog(JFrame parent) {
		super(parent, "Добавить студента", true);
		mainFrame = (MainFrame) parent;
		photoPath = new File("photos/default.jpg");

		// Создание компонентов интерфейса
		JLabel firstNameLabel = new JLabel("Имя:");
		jTextFieldFirstName = new JTextField(20);
		JLabel lastNameLabel = new JLabel("Фамилия:");
		jTextFieldLastName = new JTextField(20);
		JLabel middleNameLabel = new JLabel("Отчество:");
		jTextFieldMiddleName = new JTextField(20);
		JLabel groupLabel = new JLabel("Группа:");
		jcmbGroupNumber = new JComboBox<>(((MainFrame) parent).getGroups().toArray(new Group[0]));
		jcmbGroupNumber.setSelectedItem(mainFrame.getCurrentGroup());
		JLabel emailLabel = new JLabel("Email:");
		jTextFieldEmail = new JTextField(20);
		JLabel telephoneLbl = new JLabel("Telephone:");
		jTextFieldTelephone = new JTextField(20);
		JLabel photoLabel = new JLabel("Путь к фото:");
		jbtnChoosePhoto = new JButton("Выбрать файл");
		jlblPhotoPath = new JLabel(photoPath.toString());
		jbtnAddStudent = new JButton("Добавить");
		jbtnClose = new JButton("Закрыть");

		// Расположени компонентов интерфейса на диалоговом окне
		JPanel studentDataPanel = new JPanel(new GridLayout(10, 2));
		studentDataPanel.add(lastNameLabel);
		studentDataPanel.add(jTextFieldLastName);
		studentDataPanel.add(firstNameLabel);
		studentDataPanel.add(jTextFieldFirstName);
		studentDataPanel.add(middleNameLabel);
		studentDataPanel.add(jTextFieldMiddleName);
		studentDataPanel.add(groupLabel);
		studentDataPanel.add(jcmbGroupNumber);
		studentDataPanel.add(emailLabel);
		studentDataPanel.add(jTextFieldEmail);
		studentDataPanel.add(telephoneLbl);
		studentDataPanel.add(jTextFieldTelephone);
		studentDataPanel.add(photoLabel);
		studentDataPanel.add(jlblPhotoPath);
		studentDataPanel.add(new JLabel());
		studentDataPanel.add(jbtnChoosePhoto);
		studentDataPanel.add(new JLabel());
		studentDataPanel.add(new JLabel());
		studentDataPanel.add(jbtnAddStudent);
		studentDataPanel.add(jbtnClose);
		// Устаналиваем менеджер компоновки для диалогового окна
		setLayout(new BorderLayout());
		getContentPane().add(studentDataPanel, BorderLayout.CENTER);
		// Добавление слушателя для кнопки ОК
		AddStudentDialogListener addStudentDialogListener = new AddStudentDialogListener(this);
		jbtnAddStudent.addActionListener(addStudentDialogListener);
		jbtnChoosePhoto.addActionListener(addStudentDialogListener);
		jbtnClose.addActionListener(addStudentDialogListener);
		pack();
		setLocationRelativeTo(parent);
	}

	// Геттеры для получения значений полей формы

	/**
	 * Получение значения поля "Имя".
	 *
	 * @return значение поля "Имя".
	 */
	public String getFirstName() {
		return jTextFieldFirstName.getText();
	}

	/**
	 * Получение значения поля "Фамилия".
	 *
	 * @return значение поля "Фамилия".
	 */
	public String getLastName() {
		return jTextFieldLastName.getText();
	}

	/**
	 * Получение значения поля "Отчество".
	 *
	 * @return значение поля "Отчество".
	 */
	public String getMiddleName() {
		return jTextFieldMiddleName.getText();
	}

	/**
	 * Получение значения поля "Email".
	 *
	 * @return значение поля "Email".
	 */
	public String getEmail() {
		return jTextFieldEmail.getText();
	}

	/**
	 * Получение комбобокса для выбора группы.
	 *
	 * @return комбобокс для выбора группы.
	 */
	public JComboBox<Group> getJcmbGroupNumber() {
		return jcmbGroupNumber;
	}

	/**
	 * Получение значения поля "Telephone".
	 *
	 * @return значение поля "Telephone".
	 */
	public String getTelephone() {
		return jTextFieldTelephone.getText();
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
	 * Получение пути к фото.
	 *
	 * @return путь к фото.
	 */
	public File getPhotoPath() {
		return photoPath;
	}

	/**
	 * Устанавливает путь к фото.
	 *
	 * @param photoPath путь к фото.
	 */
	public void setPhotoPath(File photoPath) {
		this.photoPath = photoPath;
	}

	/**
	 * Получение кнопки "Добавить".
	 *
	 * @return кнопка "Добавить".
	 */
	public JButton getAddStudentButton() {
		return jbtnAddStudent;
	}

	/**
	 * Получение кнопки "Выбрать фото".
	 *
	 * @return кнопка "Выбрать фото".
	 */
	public JButton getJbtnChoosePhoto() {
		return jbtnChoosePhoto;
	}
	/**
	 * Получение кнопки "Закрыть".
	 *
	 * @return кнопка "Закрыть".
	 */
	public JButton getJbtnClose() {
		return jbtnClose;
	}

	/**
	 * Возвращает JLabel, отображающий путь к фото.
	 *
	 * @return JLabel, отображающий путь к фото.
	 */
	public JLabel getJlblPhotoPath() {
		return jlblPhotoPath;
	}
}
