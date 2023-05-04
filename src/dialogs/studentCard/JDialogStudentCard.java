package dialogs.studentCard;

import MainFrame.MainWindow;
import database.dao.impl.AttendanceDaoImpl;
import database.dao.impl.GradeDaoImpl;
import entity.Attendance;
import entity.Grade;
import entity.Lab;
import entity.Student;
import utils.Constants;
import utils.PhotoUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Класс расширяющий JDialog
 * для отображения информации
 * о студенте
 *
 * @author Fedorovsky D. A.
 */
public class JDialogStudentCard extends JDialog {
	protected Student currStudent;
	protected JLabel photoLabel;
	protected final JTextField txtFullName, txtEmail, txtPhone, txtGpa;
	protected JLabel phoneLabel;
	protected JLabel emailLabel;
	protected final JButton deleteButton, editButton,btnEditPhoto;
	protected final JPanel calendarPanel;
	protected LabButton currLabButton;
	protected final MainWindow mainWindow;


	public JDialogStudentCard(JFrame owner, String title) {
		super(owner, title, true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(1000, 800));
		mainWindow = (MainWindow) owner;
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
		btnEditPhoto = new JButton("Изменить фото");
		btnEditPhoto.setVisible(false);
		btnEditPhoto.setMaximumSize(new Dimension(187, 25));
		btnEditPhoto.setPreferredSize(btnEditPhoto.getMaximumSize());
		btnEditPhoto.setMinimumSize(btnEditPhoto.getMaximumSize());
		photoLabel.setPreferredSize(new Dimension(187, 250));
		photoLabel.setMaximumSize(photoLabel.getPreferredSize());
		photoLabel.setMaximumSize(photoLabel.getPreferredSize());
		// Создаем менеджер компоновки
		GroupLayout layout = new GroupLayout(infoPanel);
		infoPanel.setLayout(layout);
		// Определяем горизонтальную группу
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGap(15)
				.addGroup(layout.createParallelGroup()
						.addComponent(photoLabel)
						.addComponent(btnEditPhoto))
				.addGap(15)
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
				.addGroup(layout.createSequentialGroup()
						.addGap(15)
						.addComponent(photoLabel)
						.addComponent(btnEditPhoto))
				.addGroup(layout.createSequentialGroup()
						.addGap(15)
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
						.addGap(130) // добавляем 10 пикселей расстояния между строками
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(editButton)
								.addComponent(deleteButton)))
		);
		infoPanel.setPreferredSize(new Dimension(1000, 300));
		infoPanel.setMinimumSize(infoPanel.getPreferredSize());
		infoPanel.setMaximumSize(infoPanel.getPreferredSize());
		JPanel gradePanel = new JPanel(new FlowLayout());
		createGradeButtons(gradePanel);
		// Добавление панели календаря
		calendarPanel = new JPanel(new GridLayout(0, 5, 5, 5));
		JScrollPane scrollPane = new JScrollPane(calendarPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createTitledBorder(
				mainWindow.getRadioBtnLab().isSelected() ? "Лабораторные работы" : "Лекции"));
		calendarPanel.setPreferredSize(new Dimension(800, 500));
		calendarPanel.setMinimumSize(calendarPanel.getPreferredSize());
		calendarPanel.setMaximumSize(calendarPanel.getPreferredSize());
		add(infoPanel);
		add(gradePanel);
		add(scrollPane);
		ListenerJDialogStudentCard listenerJDialogStudentCard = new ListenerJDialogStudentCard(this);
		btnEditPhoto.addActionListener(e -> {
			editPhoto();
		});
		deleteButton.addActionListener(listenerJDialogStudentCard);
		editButton.addActionListener(listenerJDialogStudentCard);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (editButton.getText().equalsIgnoreCase("редактировать")) {
				mainWindow.refreshStudentTable();
				setVisible(false);
			}else {
					JOptionPane.showMessageDialog(mainWindow, "Перед закрытием карточки нужно завершить редактирование");
					setVisible(true);
				}
			}
		});

	}

	public void updateStudentCard(Student student) {
		if(student == null)return;
		currStudent = student;
		txtFullName.setText(student.getLastName() + " " + student.getFirstName() +
				" " + student.getMiddleName());
		txtEmail.setText(student.getEmail());
		txtPhone.setText(student.getTelephone());
		txtGpa.setText(String.valueOf(student.getAverageGrade()));
		Image image = PhotoUtils.getInstance().loadPhoto(student).getImage();
		if (image != null) {
			photoLabel.setSize(new Dimension(187, 250));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(photoLabel.getWidth(),
					photoLabel.getHeight(), Image.SCALE_SMOOTH));
			photoLabel.setIcon(icon);
		} else {
			photoLabel.setSize(new Dimension(0, 0));
		}
		setLocationRelativeTo(mainWindow);
		calendarPanel.removeAll();
		createLabButtons(mainWindow.getCurrentGroup().getLabs());
	}

	private void createLabButtons(List<Lab> labs) {
		for (Lab lab : labs) {
			LabButton labButton = new LabButton(currStudent, lab);
			calendarPanel.add(labButton);
			setInitialSelection(labButton);
			setButtonClickListener(labButton);
			setButtonKeyListener(labButton);
		}
	}
	private void setInitialSelection(LabButton labButton) {
		if (labButton.lab.equals(mainWindow.getCurrDate())) {
			labButton.isSelected = true;
			labButton.setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR, 5));
			labButton.repaint();
			currLabButton = labButton;
			labButton.requestFocus();
		}
	}
	private void setButtonClickListener(LabButton labButton) {
		labButton.addActionListener(e -> {
			LabButton button = (LabButton) e.getSource();
			if (!button.isSelected) {
				button.setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR, 5));
				button.isSelected = true;
				if (currLabButton != null) {
					currLabButton.isSelected = false;
					currLabButton.setBorder(null);
					currLabButton.repaint();
				}
				currLabButton = button;
			}
		});
	}
	private void setButtonKeyListener(LabButton labButton) {
		labButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				LabButton button = (LabButton) e.getSource();
				Color color = button.getBackground();
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					handleSpaceKeyPressed(button, color);
					SwingUtilities.invokeLater(button::repaint);
					button.requestFocus();
				}
			}
		});
	}
	private void handleSpaceKeyPressed(LabButton button, Color color) {
		if (color.equals(Constants.NO_ATTENDANCE_COLOR)) {
			addAttendance(button);
		} else if (color.equals(Constants.ATTENDANCE_COLOR)) {
			removeAttendance(button);
		}
	}
	private void addAttendance(LabButton button) {
		Attendance attendance = new Attendance(button.lab.getId(), currStudent.getId(), false);
		long id = AttendanceDaoImpl.getInstance().save(attendance);
		if (id > 0) {
			attendance.setId(id);
			currStudent.getAttendanceList().add(attendance);
			button.setBackground(Constants.ATTENDANCE_COLOR);
			System.out.println("Запись о посещении добавлена");
			mainWindow.refreshStudentTable();
		}
	}
	private void removeAttendance(LabButton button) {
		int choice = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите удалить запись о посещении?", "Подтверждение", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			Attendance attendance = currStudent.getLabAttendance(button.lab);
			Grade studentGrade = currStudent.getLabGrade(button.lab);
			if (attendance != null) {
				if (AttendanceDaoImpl.getInstance().delete(attendance)) {
					System.out.println("Запись о посещении удалена");
					currStudent.getAttendanceList().remove(attendance);
					if (studentGrade != null && GradeDaoImpl.getInstance().delete(studentGrade)) {
						currStudent.getGradeList().remove(studentGrade);
						System.out.println("Оценка за лабораторную удалена");
					}
					updateGpa(currStudent);
					button.setBackground(Constants.NO_ATTENDANCE_COLOR);
					mainWindow.refreshStudentTable();
				}
			}
		}
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

	public void updateGpa(Student student) {
		txtGpa.setText(String.valueOf(student.getAverageGrade()));
	}
	private void editPhoto(){
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				PhotoUtils.getInstance().savePhoto(currStudent,selectedFile);
				BufferedImage image = ImageIO.read(new File(currStudent.getPhotoPath()));
				// Масштабируем изображение и создаем иконку
				ImageIcon icon = new ImageIcon(image.getScaledInstance(photoLabel.getWidth(),
						photoLabel.getHeight(), Image.SCALE_SMOOTH));
				// Устанавливаем иконку изображения в JLabel
				photoLabel.setIcon(icon);
			} catch (IOException ex) {
				System.out.println("Не удалось сохранить фото");
			}
		}
	}
	private void createGradeButtons(JPanel gradePanel){
		for (int i = 0; i < 11; i++) {
			JButton gradeButton = new JButton(String.valueOf(i));
			gradeButton.addActionListener(e -> {
				setGrade(gradeButton);
			});
			gradeButton.setPreferredSize(new Dimension(80, 30));
			gradePanel.add(gradeButton);
		}
	}
	private void setGrade(JButton gradeButton){
		if (currLabButton.getBackground().equals(Constants.ATTENDANCE_COLOR)) {
			currLabButton.setGrade(gradeButton.getText());
			Grade grade = currStudent.getLabGrade(currLabButton.getLab());
			if (grade == null) {
				long id;
				Grade grade1 = new Grade(currLabButton.getLab().getId(),
						currStudent.getId(), Integer.parseInt(gradeButton.getText()));
				if ((id = GradeDaoImpl.getInstance().save(grade1)) != -1) {
					System.out.println("Оценка добавлена");
					grade1.setId(id);
					currStudent.getGradeList().add(grade1);
					mainWindow.refreshStudentTable();
				}
			} else {
				grade.setGrade(Integer.parseInt(gradeButton.getText()));
				mainWindow.refreshStudentTable();
				if (GradeDaoImpl.getInstance().update(grade)) {
					System.out.println("Оценка изменена");
				}
			}
			txtGpa.setText(String.valueOf(currStudent.getAverageGrade()));
		} else {
			JOptionPane.showMessageDialog(mainWindow, "Перед выставлением оценки нужно отметить студента");
		}
		currLabButton.requestFocus();
	}
}