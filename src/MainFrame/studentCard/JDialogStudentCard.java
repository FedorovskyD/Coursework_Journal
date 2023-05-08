package MainFrame.studentCard;

import MainFrame.MainWindow;
import database.dao.impl.AttendanceDaoImpl;
import database.dao.impl.GradeDaoImpl;
import entity.Attendance;
import entity.Grade;
import entity.Lesson;
import entity.Student;
import utils.Constants;
import utils.PhotoUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
	protected final JTextField txtFullName, txtEmail, txtPhone;
	protected final JLabel txtAverageGrade;
	protected JLabel phoneLabel;
	protected JLabel gpaLabel;
	protected JLabel emailLabel;
	protected final JButton deleteButton, editButton, btnEditPhoto;
	protected final JPanel calendarPanel;
	protected LessonButton currLessonButton;
	private final List<LessonButton> lessonButtons;
	protected final MainWindow mainWindow;
	private final JScrollPane scrollPane;
	private JPanel gradePanel;


	public JDialogStudentCard(JFrame owner, String title) {
		super(owner, title, true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(1000, 800));
		mainWindow = (MainWindow) owner;
		BoxLayout layout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		getContentPane().setLayout(layout);
		// Создание панели информации о студенте
		JPanel infoPanel = new JPanel();
		// Создаем компоненты
		photoLabel = new JLabel();
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
		currStudent = mainWindow.getCurrentStudent();
		gpaLabel = new JLabel(mainWindow.getRadioBtnLab().isSelected()?"Средний балл:":" ");
		txtAverageGrade = new JLabel();
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
		lessonButtons = new ArrayList<>();
		// Создаем менеджер компоновки
		GroupLayout groupLayout = new GroupLayout(infoPanel);
		infoPanel.setLayout(groupLayout);
		// Определяем горизонтальную группу
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
				.addGap(15)
				.addGroup(groupLayout.createParallelGroup()
						.addComponent(photoLabel)
						.addComponent(btnEditPhoto))
				.addGap(15)
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameLabel)
						.addComponent(emailLabel)
						.addComponent(phoneLabel)
						.addComponent(gpaLabel)
						.addComponent(editButton))
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(txtFullName)
						.addComponent(txtEmail)
						.addComponent(txtPhone)
						.addComponent(txtAverageGrade)
						.addComponent(deleteButton))
		);
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(15)
						.addComponent(photoLabel)
						.addComponent(btnEditPhoto))
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(15)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(nameLabel)
								.addComponent(txtFullName))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(emailLabel)
								.addComponent(txtEmail))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(phoneLabel)
								.addComponent(txtPhone))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(gpaLabel)
								.addComponent(txtAverageGrade))
						.addGap(130) // добавляем 10 пикселей расстояния между строками
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(editButton)
								.addComponent(deleteButton)))
		);
		infoPanel.setPreferredSize(new Dimension(1000, 300));
		infoPanel.setMinimumSize(infoPanel.getPreferredSize());
		infoPanel.setMaximumSize(infoPanel.getPreferredSize());
		gradePanel = new JPanel(new GridLayout(1,11));
		gradePanel.setMinimumSize(new Dimension(100, 100));
		createGradeButtons(gradePanel);
		// Добавление панели календаря
		calendarPanel = new JPanel(new GridLayout(0, 5, 5, 5));
		scrollPane = new JScrollPane(calendarPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
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
				} else {
					JOptionPane.showMessageDialog(mainWindow, "Перед закрытием карточки нужно завершить редактирование");
					setVisible(true);
				}
			}
		});
		gradePanel.setVisible(mainWindow.getRadioBtnLab().isSelected());
		setLocationRelativeTo(owner);
	}

	public void updateStudentCard(Student student) {
		if (student == null) return;
		currStudent = student;
		txtFullName.setText(student.getLastName() + " " + student.getFirstName() +
				" " + student.getMiddleName());
		txtEmail.setText(student.getEmail());
		txtPhone.setText(student.getTelephone());
		if(!mainWindow.getRadioBtnLecture().isSelected()) {
			txtAverageGrade.setText(String.valueOf(student.getAverageGrade()));
		}
		Image image = PhotoUtils.getInstance().loadPhoto(student).getImage();
		if (image != null) {
			photoLabel.setSize(new Dimension(187, 250));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(photoLabel.getWidth(),
					photoLabel.getHeight(), Image.SCALE_SMOOTH));
			photoLabel.setIcon(icon);
		} else {
			photoLabel.setSize(new Dimension(0, 0));
		}
		List<Lesson> lessons;
		if(mainWindow.getRadioBtnLecture().isSelected()){
			lessons = mainWindow.getCurrGroup().getLectures();
		}else {
			lessons = mainWindow.getCurrentGroup().getLabs();
		}
		if (lessonButtons.size() == 0) {
			createLessonButtons(lessons);
		} else {
			updateLessonButtons(lessonButtons);
		}
		mainWindow.repaint();
		repaint();

	}

	private void updateLessonButtons(List<LessonButton> lessonButtons) {
		for (LessonButton lessonButton : lessonButtons) {
			SwingUtilities.invokeLater(() -> {
				Grade grade = currStudent.getLabGrade(lessonButton.lesson);
				String gradeInfo = grade != null ? String.valueOf(grade.getGrade()) : "Нет";
				lessonButton.setGrade(gradeInfo);
				Color color = currStudent.isAttendance(lessonButton.lesson) ? Constants.ATTENDANCE_COLOR : Constants.NO_ATTENDANCE_COLOR;
				lessonButton.setBackground(color);
				setInitialSelection(lessonButton);
			});
		}
	}

	private void createLessonButtons(List<Lesson> lessons) {
		calendarPanel.removeAll();
		lessonButtons.clear();
		for (Lesson lesson : lessons) {
			LessonButton lessonButton = new LessonButton(lesson);
			Grade grade = currStudent.getLabGrade(lesson);
			String gradeInfo = grade != null ? String.valueOf(grade.getGrade()) : "Нет";
			lessonButton.setGrade(gradeInfo);
			Color color = currStudent.isAttendance(lesson) ? Constants.ATTENDANCE_COLOR : Constants.NO_ATTENDANCE_COLOR;
			lessonButton.setBackground(color);
			setInitialSelection(lessonButton);
			setButtonClickListener(lessonButton);
			setButtonKeyListener(lessonButton);
			lessonButtons.add(lessonButton);
			calendarPanel.add(lessonButton);
		}
	}

	private void setInitialSelection(LessonButton lessonButton) {
		if (lessonButton.lesson.equals(mainWindow.getCurrDate())) {
			lessonButton.isSelected = true;
			if(currLessonButton!=null){
				currLessonButton.setBorder(null);
				lessonButton.setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR, 5));
			}else {
				lessonButton.setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR, 5));
			}
			currLessonButton = lessonButton;
			currLessonButton.requestFocus();
		}
	}

	private void setButtonClickListener(LessonButton lessonButton) {
		lessonButton.addActionListener(e -> {
			LessonButton button = (LessonButton) e.getSource();
			if (!button.isSelected) {
				button.setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR, 5));
				button.isSelected = true;
				if (currLessonButton != null) {
					currLessonButton.isSelected = false;
					currLessonButton.setBorder(null);
					currLessonButton.repaint();
				}
				currLessonButton = button;
				currLessonButton.requestFocus();
				mainWindow.getCurrDateCmb().setSelectedItem(lessonButton.lesson);
			}
		});
	}

	private void setButtonKeyListener(LessonButton lessonButton) {
		lessonButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				LessonButton button = (LessonButton) e.getSource();
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_SPACE) {
					handleSpaceKeyPressed(button, button.getBackground());
					button.requestFocus();
					button.repaint();
				} else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
					int index = lessonButtons.indexOf(button);
					int nextIndex = index + (keyCode == KeyEvent.VK_LEFT ? -1 : 1);
					if (nextIndex >= 0 && nextIndex < lessonButtons.size()) {
						LessonButton nextLessonButton = lessonButtons.get(nextIndex);
						nextLessonButton.setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR, 5));
						button.setBorder(null);
						button.repaint();
						nextLessonButton.requestFocus();
						mainWindow.getCurrDateCmb().setSelectedItem(nextLessonButton.lesson);
					}
				} else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
					int selectedRow = mainWindow.getStudentTable().getSelectedRow();
					if (selectedRow != -1) {
						int nextRow = selectedRow + (keyCode == KeyEvent.VK_UP ? -1 : 1);
						while (nextRow >= 0 && nextRow < mainWindow.getStudentTable().getRowCount() &&
								mainWindow.getStudentTable().getStudentTableModel().isBlankRow(nextRow)) {
							nextRow += (keyCode == KeyEvent.VK_UP ? -1 : 1);
						}
						if (nextRow >= 0 && nextRow < mainWindow.getStudentTable().getRowCount()) {
							mainWindow.getStudentTable().setRowSelectionInterval(nextRow, nextRow);
							mainWindow.getStudentTable().scrollRectToVisible(mainWindow.getStudentTable().getCellRect(nextRow, 0, true));
							mainWindow.getStudentTable().repaint();
						}
					}
				}
				e.consume();
			}
		});
	}

	private void handleSpaceKeyPressed(LessonButton button, Color color) {
		if (color.equals(Constants.NO_ATTENDANCE_COLOR)) {
			addAttendance(button);
		} else if (color.equals(Constants.ATTENDANCE_COLOR)) {
			removeAttendance(button);
		}
	}

	private void addAttendance(LessonButton button) {
		Attendance attendance = new Attendance(button.lesson.getId(), currStudent.getId());
		long id = AttendanceDaoImpl.getInstance().save(attendance);
		if (id > 0) {
			attendance.setId(id);
			currStudent.getAttendanceList().add(attendance);
			button.setBackground(Constants.ATTENDANCE_COLOR);
			System.out.println("Запись о посещении добавлена");
			mainWindow.refreshStudentTable();
		}
	}

	private void removeAttendance(LessonButton button) {
		Attendance attendance = currStudent.getLabAttendance(button.lesson);
		Grade studentGrade = currStudent.getLabGrade(button.lesson);
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
				button.setGrade("Нет");
				mainWindow.refreshStudentTable();
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

	public List<LessonButton> getLabButtons() {
		return lessonButtons;
	}

	public Student getStudent() {
		return currStudent;
	}

	public LessonButton getCurrLessonButton() {
		return currLessonButton;
	}

	public void updateGpa(Student student) {
		txtAverageGrade.setText(String.valueOf(student.getAverageGrade()));
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	private void editPhoto() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				PhotoUtils.getInstance().savePhoto(currStudent, selectedFile);
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

	private void createGradeButtons(JPanel gradePanel) {
		for (int i = 0; i < 11; i++) {
			JButton gradeButton = new JButton(String.valueOf(i));
			gradeButton.addActionListener(e -> {
				setGrade(gradeButton);
			});
			gradeButton.setPreferredSize(new Dimension(80, 30));
			gradePanel.add(gradeButton);
		}
	}

	public JLabel getTxtAverageGrade() {
		return txtAverageGrade;
	}

	public JLabel getGpaLabel() {
		return gpaLabel;
	}

	private void setGrade(JButton gradeButton) {
		if (currLessonButton.getBackground().equals(Constants.ATTENDANCE_COLOR)) {
			currLessonButton.setGrade(gradeButton.getText());
			Grade grade = currStudent.getLabGrade(currLessonButton.getLab());
			if (grade == null) {
				long id;
				Grade grade1 = new Grade(currLessonButton.getLab().getId(),
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
			if(mainWindow.getRadioBtnLab().isSelected()) {
				txtAverageGrade.setText(String.valueOf(currStudent.getAverageGrade()));
			}
		} else {
			JOptionPane.showMessageDialog(mainWindow, "Перед выставлением оценки нужно отметить студента");
		}
		currLessonButton.requestFocus();
	}

	public JPanel getGradePanel() {
		return gradePanel;
	}

}