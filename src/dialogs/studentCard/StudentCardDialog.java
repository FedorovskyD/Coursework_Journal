package dialogs.studentCard;

import MainFrame.MainWindow;
import database.dao.impl.GradeDaoImpl;
import entity.Grade;
import entity.Lab;
import entity.Student;
import utils.PhotoUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс расширяющий JDialog
 * для отображения информации
 * о студенте
 *
 * @author Fedorovsky D. A.
 */
public class StudentCardDialog extends JDialog {
	protected Student currStudent;
	protected JLabel photoLabel;
	protected final JTextField txtFullName, txtEmail, txtPhone, txtGpa;
	protected JLabel phoneLabel;
	protected JLabel emailLabel;
	protected final JButton deleteButton, editButton;
	private final JPanel calendarPanel;
	protected LabButton currLabButton;
	private final List<LabButton> labButtons;
	protected final MainWindow mainWindow;

	public StudentCardDialog(JFrame owner, String title) {
		super(owner, title, true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setSize(new Dimension(1000, 735));
		mainWindow = (MainWindow) owner;
		labButtons = new ArrayList<>();
		BoxLayout layout1 = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		getContentPane().setLayout(layout1);
		// Создание панели информации о студенте
		JPanel infoPanel = new JPanel();
		// Создаем компоненты
		photoLabel = new JLabel(new ImageIcon("photos/default.jpg"));
		JLabel nameLabel = new JLabel("ФИО:");
		Dimension txtFieldDimension = new Dimension(300, 20);
		txtFullName = new JTextField(20);
		txtFullName.setMaximumSize(txtFieldDimension);
		txtFullName.setEditable(false);
		txtFullName.setBorder(null);
		emailLabel = new JLabel("Почта:");
		txtEmail = new JTextField(20);
		txtEmail.setMaximumSize(txtFieldDimension);
		txtEmail.setEditable(false);
		txtEmail.setBorder(null);
		phoneLabel = new JLabel("Телефон:");
		txtPhone = new JTextField(20);
		txtPhone.setMaximumSize(txtFieldDimension);
		txtPhone.setEditable(false);
		txtPhone.setBorder(null);
		JLabel gpaLabel = new JLabel("Средний балл:");
		txtGpa = new JTextField(20);
		txtGpa.setMaximumSize(txtFieldDimension);
		txtGpa.setEditable(false);
		txtGpa.setBorder(null);
		deleteButton = new JButton("Удалить студента");
		editButton = new JButton("Редактировать");
		deleteButton.setVisible(false);
		JButton btnEditPhoto = new JButton("Изменить фото");
		btnEditPhoto.setVisible(false);
		btnEditPhoto.setMaximumSize(new Dimension(10, 30));
		btnEditPhoto.setPreferredSize(btnEditPhoto.getMaximumSize());
		JPanel photoPanel = new JPanel(new BorderLayout());
		photoPanel.setPreferredSize(new Dimension(200, 240));
		photoPanel.setMaximumSize(photoPanel.getPreferredSize());
		photoPanel.setMinimumSize(photoPanel.getPreferredSize());
		photoPanel.add(photoLabel, BorderLayout.CENTER);
		photoPanel.add(btnEditPhoto, BorderLayout.SOUTH);
		// Создаем менеджер компоновки
		GroupLayout layout = new GroupLayout(infoPanel);
		infoPanel.setLayout(layout);
		// Определяем горизонтальную группу
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(photoPanel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameLabel)
						.addComponent(emailLabel)
						.addComponent(phoneLabel)
						.addComponent(gpaLabel)
						.addComponent(editButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(txtFullName)
						.addComponent(txtEmail)
						.addComponent(txtPhone)
						.addComponent(txtGpa)
						.addComponent(deleteButton))
		);
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(photoPanel)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(nameLabel)
								.addComponent(txtFullName))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(emailLabel)
								.addComponent(txtEmail))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(phoneLabel)
								.addComponent(txtPhone))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(gpaLabel)
								.addComponent(txtGpa))
						.addGap(60) // добавляем 10 пикселей расстояния между строками
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(editButton)
								.addComponent(deleteButton)))
		);
		infoPanel.setPreferredSize(new Dimension(800, 200));
		infoPanel.setMinimumSize(infoPanel.getPreferredSize());
		infoPanel.setMaximumSize(infoPanel.getPreferredSize());
		JPanel markPanel = new JPanel(new FlowLayout());
		for (int i = 0; i < 11; i++) {
			JButton jButton = new JButton(String.valueOf(i));
			jButton.addActionListener(e -> {
				if (currLabButton.getBackground().equals(Color.GREEN)) {
					currLabButton.updateGrade(jButton.getText());
					Grade grade = currStudent.getLabGrade(currLabButton.getLab());
					if (grade == null) {
						long id;
						Grade grade1 = new Grade(currLabButton.getLab().getId(),
								currStudent.getId(), Integer.parseInt(jButton.getText()));
						if ((id = GradeDaoImpl.getInstance().save(grade1)) != -1) {
							System.out.println("Оценка добавлена");
							grade1.setId(id);
							currStudent.getGradeList().add(grade1);
						}
					} else {
						grade.setGrade(Integer.parseInt(jButton.getText()));
						if (GradeDaoImpl.getInstance().update(grade)) {
							System.out.println("Оценка изменена");
						}
					}
					txtGpa.setText(String.valueOf(currStudent.getMark()));
				} else {
					JOptionPane.showMessageDialog(mainWindow, "Перед выставлением оценки нужно отметить студента");
				}
				SwingUtilities.invokeLater(currLabButton::requestFocus);
			});
			jButton.setPreferredSize(new Dimension(80, 30));
			markPanel.add(jButton);
		}
		// Добавление панели календаря
		calendarPanel = new JPanel(new BorderLayout());
		calendarPanel.setBorder(BorderFactory.createTitledBorder(
				mainWindow.getRadioBtnLab().isSelected() ? "Лабораторные работы" : "Лекции"));
		calendarPanel.setPreferredSize(new Dimension(800, 500));
		calendarPanel.setMinimumSize(calendarPanel.getPreferredSize());
		calendarPanel.setMaximumSize(calendarPanel.getPreferredSize());
		photoLabel.setPreferredSize(new Dimension(200, 220));
		photoLabel.setMaximumSize(photoLabel.getPreferredSize());
		photoLabel.setMaximumSize(photoLabel.getPreferredSize());
		getCalendarPanel().setLayout(new GridLayout(5, 5, 5, 5)); // задаем сетку для кнопок
		// Создаем кнопки для каждой лабораторной работы и добавляем их на панель
		infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
		add(infoPanel);

		markPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		add(markPanel);

		calendarPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 450));
		add(calendarPanel);

		createLabButtons(mainWindow.getCurrentGroup().getLabs());
		StudentCardDialogListener studentCardDialogListener = new StudentCardDialogListener(this);
		deleteButton.addActionListener(studentCardDialogListener);
		editButton.addActionListener(studentCardDialogListener);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				mainWindow.getStudentTable().clearSelection();
			}
		});
	}

	public void update(Student student) {
		currStudent = student;
		this.setLocationRelativeTo(mainWindow);
		calendarPanel.removeAll();
		txtFullName.setText(student.getSurname() + " " + student.getName() +
				" " + student.getMiddleName());
		txtEmail.setText(student.getEmail());
		txtPhone.setText(student.getTelephone());
		txtGpa.setText(String.valueOf(student.getMark()));
		Image image = PhotoUtils.getInstance().loadPhoto(student).getImage();
		if (image != null) {
			photoLabel.setSize(new Dimension(200, 220));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(photoLabel.getWidth(),
					photoLabel.getHeight(), Image.SCALE_SMOOTH));
			photoLabel.setIcon(icon);
		} else {
			photoLabel.setSize(new Dimension(0, 0));
		}
		updateLabButtons(student);
		calendarPanel.setBorder(BorderFactory.createTitledBorder(
				mainWindow.getRadioBtnLab().isSelected() ? "Лабораторные работы" : "Лекции"));
	}

	private void createLabButtons(List<Lab> labs) {
		for (Lab lab : labs) {
			// получаем дату лабораторной работы
			// Создаем новую кнопку с датой и оценкой студент
			LabButton labButton = new LabButton(this, lab, false);
			getCalendarPanel().add(labButton); // добавляем кнопку на панель
			labButtons.add(labButton);
		}
		int remaining = 25 - labs.size();
		for (int i = 1; i <= remaining; i++) {
			JPanel filler = new JPanel();
			getCalendarPanel().add(filler);
		}
	}
	private void updateLabButtons(Student student){
		for (LabButton labButton : labButtons){
			Grade grade = student.getLabGrade(labButton.getLab());
			if (grade!=null) {
				labButton.updateGrade(String.valueOf(grade.getGrade()));
			}else {
				labButton.updateGrade("Нет");
			}
			if(labButton.getLab()==mainWindow.getCurrDateCmb().getSelectedItem()){
				currLabButton = labButton;
				labButton.selected(true);
			}
			if (currStudent.isAttendance(labButton.getLab())) {
				labButton.setBackground(Color.GREEN);
			} else {
				labButton.setBackground(Color.GRAY);
			}
		}
		getCalendarPanel().repaint();
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public JButton getEditButton() {
		return editButton;
	}

	public JPanel getCalendarPanel() {
		return calendarPanel;
	}

	public Student getStudent() {
		return currStudent;
	}

	public LabButton getCurrLabButton() {
		return currLabButton;
	}
}