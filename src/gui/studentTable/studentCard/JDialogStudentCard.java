package gui.studentTable.studentCard;

import gui.MainFrame;
import database.dao.impl.AttendanceDaoImpl;
import database.dao.impl.GradeDaoImpl;
import entity.Attendance;
import entity.Grade;
import entity.Lesson;
import entity.Student;
import listeners.JDialogStudentCardListener;
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
	protected final MainFrame mainFrame;
	private final JScrollPane scrollPane;
	private final JPanel gradePanel;


	public JDialogStudentCard(JFrame owner, String title) {
		super(owner, title, true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(1000, 800));
		mainFrame = (MainFrame) owner;
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
		gpaLabel = new JLabel(mainFrame.getRadioBtnLab().isSelected() ? "Средний балл:" : " ");
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
		gradePanel = new JPanel(new GridLayout(1, 11));
		gradePanel.setMinimumSize(new Dimension(100, 100));
		createGradeButtons(gradePanel);
		// Добавление панели календаря
		calendarPanel = new JPanel(new GridLayout(0, 5, 5, 5));
		scrollPane = new JScrollPane(calendarPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createTitledBorder(
				mainFrame.getRadioBtnLab().isSelected() ? "Лабораторные работы" : "Лекции"));
		calendarPanel.setPreferredSize(new Dimension(800, 500));
		calendarPanel.setMinimumSize(calendarPanel.getPreferredSize());
		calendarPanel.setMaximumSize(calendarPanel.getPreferredSize());
		add(infoPanel);
		add(gradePanel);
		add(scrollPane);
		JDialogStudentCardListener JDialogStudentCardListener = new JDialogStudentCardListener(this);
		btnEditPhoto.addActionListener(e -> {
			editPhoto();
		});
		deleteButton.addActionListener(JDialogStudentCardListener);
		editButton.addActionListener(JDialogStudentCardListener);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (editButton.getText().equalsIgnoreCase("редактировать")) {
					mainFrame.refreshStudentTable();
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(mainFrame, "Перед закрытием карточки нужно завершить редактирование");
					setVisible(true);
				}
			}
		});
		gradePanel.setVisible(mainFrame.getRadioBtnLab().isSelected());
		setLocationRelativeTo(owner);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				if (currLessonButton != null) {
					currLessonButton.requestFocus();
				}
			}
		});
	}

	public void updateStudentCard(Student student) {
		if (student == null) return;
		txtFullName.setText(student.getLastName() + " " + student.getFirstName() +
				" " + student.getMiddleName());
		txtEmail.setText(student.getEmail());
		txtPhone.setText(student.getTelephone());
		if (!mainFrame.getRadioBtnLecture().isSelected()) {
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
		if (mainFrame.getRadioBtnLecture().isSelected()) {
			lessons = mainFrame.getCurrGroup().getLectures();
		} else {
			lessons = mainFrame.getCurrentGroup().getLabs();
		}
		if (lessonButtons.size() == 0) {
			createLessonButtons(lessons);
		} else {
			updateLessonButtons(lessonButtons);
		}
		mainFrame.getStudentTable().repaint();
	}

	private void updateLessonButtons(List<LessonButton> lessonButtons) {
		for (LessonButton lessonButton : lessonButtons) {
			SwingUtilities.invokeLater(() -> {
				Grade grade = mainFrame.getCurrStudent().getLabGrade(lessonButton.lesson);
				String gradeInfo = grade != null ? String.valueOf(grade.getGrade()) : "Нет";

				if (mainFrame.getRadioBtnLecture().isSelected()) {
					lessonButton.setData();
				} else {
					lessonButton.setGrade(gradeInfo);
				}

				Color color = mainFrame.getCurrStudent().isAttendance(lessonButton.lesson) ? Constants.ATTENDANCE_COLOR : Constants.NO_ATTENDANCE_COLOR;
				lessonButton.setBackground(color);
				setInitialSelection(lessonButton);
				lessonButton.repaint();
			});
		}
	}

	private void createLessonButtons(List<Lesson> lessons) {
		calendarPanel.removeAll();
		for (Lesson lesson : lessons) {
			LessonButton lessonButton = new LessonButton(lesson);
			Grade grade = mainFrame.getCurrStudent().getLabGrade(lesson);
			String gradeInfo = grade != null ? String.valueOf(grade.getGrade()) : "Нет";
			if (mainFrame.getRadioBtnLecture().isSelected()) {
				lessonButton.setData();
			} else {
				lessonButton.setGrade(gradeInfo);
			}
			Color color = mainFrame.getCurrStudent().isAttendance(lesson) ? Constants.ATTENDANCE_COLOR : Constants.NO_ATTENDANCE_COLOR;
			lessonButton.setBackground(color);
			setButtonClickListener(lessonButton);
			setButtonKeyListener(lessonButton);
			lessonButtons.add(lessonButton);
			calendarPanel.add(lessonButton);
			setInitialSelection(lessonButton);
		}
	}

	private void setInitialSelection(LessonButton lessonButton) {
		if (lessonButton.lesson.equals(mainFrame.getCurrDate())) {
			lessonButton.isSelected = true;
			if (currLessonButton != null) {
				currLessonButton.setBorder(null);
				lessonButton.setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR, 5));
			} else {
				lessonButton.setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR, 5));
			}
			currLessonButton = lessonButton;
		}
	}

	private void setButtonClickListener(LessonButton lessonButton) {
		lessonButton.addActionListener(e -> {
			LessonButton button = (LessonButton) e.getSource();
			if (!button.isSelected && button!=currLessonButton) {
				button.setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR, 5));
				if (currLessonButton != null) {
					currLessonButton.setBorder(null);
					currLessonButton.repaint();
				}
				currLessonButton = button;
				mainFrame.getCurrDateCmb().setSelectedItem(lessonButton.lesson);
			}
		});
	}

	private void setButtonKeyListener(LessonButton lessonButton) {
		lessonButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				LessonButton button = (LessonButton) e.getSource();
				int keyCode = e.getKeyCode();

				switch (keyCode) {
					case KeyEvent.VK_ESCAPE:
						mainFrame.getJDialogStudentCard().dispose();
						break;
					case KeyEvent.VK_SPACE:
						handleSpaceKeyPressed(button, button.getBackground());
						button.requestFocus();
						button.repaint();
						break;
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_RIGHT:
						moveLessonButton(keyCode, button);
						break;
					case KeyEvent.VK_UP:
					case KeyEvent.VK_DOWN:
						moveTableRow(keyCode);
						e.consume();
						break;
					default:
						break;
				}
			}
		});
	}

	private void moveLessonButton(int keyCode, LessonButton button) {
		int index = lessonButtons.indexOf(button);
		int nextIndex = index + (keyCode == KeyEvent.VK_LEFT ? -1 : 1);
		if (nextIndex >= 0 && nextIndex < lessonButtons.size()) {
			LessonButton nextLessonButton = lessonButtons.get(nextIndex);
			nextLessonButton.setBorder(BorderFactory.createLineBorder(Constants.SELECTED_COLOR, 5));
			button.setBorder(null);
			button.repaint();
			nextLessonButton.repaint();
			currLessonButton = nextLessonButton;
			nextLessonButton.isSelected = true;
			mainFrame.getCurrDateCmb().setSelectedItem(nextLessonButton.lesson);
			mainFrame.getStudentTable().setColumnSelectionInterval(mainFrame.getCurrDateCmb().getSelectedIndex()+2,mainFrame.getCurrDateCmb().getSelectedIndex()+2);
			nextLessonButton.requestFocus();
		}
	}

	private void moveTableRow(int keyCode) {
		int selectedRow = mainFrame.getStudentTable().getSelectedRow();
		if (selectedRow != -1) {
			int nextRow = selectedRow + (keyCode == KeyEvent.VK_UP ? -1 : 1);
			while (nextRow >= 0 && nextRow < mainFrame.getStudentTable().getRowCount() &&
					mainFrame.getStudentTable().getStudentTableModel().isBlankRow(nextRow)) {
				nextRow += (keyCode == KeyEvent.VK_UP ? -1 : 1);
			}
			if (nextRow >= 0 && nextRow < mainFrame.getStudentTable().getRowCount()) {
				mainFrame.getStudentTable().setRowSelectionInterval(nextRow, nextRow);
				mainFrame.getStudentTable().scrollRectToVisible(mainFrame.getStudentTable().getCellRect(nextRow, 0, true));
				mainFrame.getStudentTable().repaint();
			}
		}
	}


	private void handleSpaceKeyPressed(LessonButton button, Color color) {
		if (color.equals(Constants.NO_ATTENDANCE_COLOR)) {
				addAttendance(button);
			if(mainFrame.getCheckBox().isSelected()){
				moveTableRow(KeyEvent.VK_DOWN);
			}
		} else if (color.equals(Constants.ATTENDANCE_COLOR)) {
			removeAttendance(button);
		}
	}

	private void addAttendance(LessonButton button) {
		Attendance attendance = new Attendance(button.lesson.getId(), mainFrame.getCurrStudent().getId());
		long id = AttendanceDaoImpl.getInstance().save(attendance);
		if (id > 0) {
			attendance.setId(id);
			if (button.lesson.isLecture()) {
				mainFrame.getCurrStudent().getLectureAttendanceList().add(attendance);
			} else {
				mainFrame.getCurrStudent().getLabAttendanceList().add(attendance);
			}

			button.setBackground(Constants.ATTENDANCE_COLOR);
			System.out.println("Запись о посещении добавлена");
			mainFrame.refreshStudentTable();
		}
	}

	private void removeAttendance(LessonButton button) {
		Attendance attendance = mainFrame.getCurrStudent().getLabAttendance(button.lesson);
		Grade studentGrade = mainFrame.getCurrStudent().getLabGrade(button.lesson);
		if (attendance != null) {
			if (AttendanceDaoImpl.getInstance().delete(attendance)) {
				System.out.println("Запись о посещении удалена");
				if (button.lesson.isLecture()) {
					mainFrame.getCurrStudent().getLectureAttendanceList().remove(attendance);
				} else {
					mainFrame.getCurrStudent().getLabAttendanceList().remove(attendance);
				}

				if (studentGrade != null && GradeDaoImpl.getInstance().delete(studentGrade)) {
					mainFrame.getCurrStudent().getGradeList().remove(studentGrade);
					System.out.println("Оценка за лабораторную удалена");
				}
				updateGpa(mainFrame.getCurrStudent());
				button.setBackground(Constants.NO_ATTENDANCE_COLOR);
				if(mainFrame.getRadioBtnLecture().isSelected()){
					button.setData();
				}else {
					button.setGrade("Нет");
				}
				mainFrame.refreshStudentTable();
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
				PhotoUtils.getInstance().savePhoto(mainFrame.getCurrStudent(), selectedFile);
				BufferedImage image = ImageIO.read(new File(mainFrame.getCurrStudent().getPhotoPath()));
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
			Grade grade = mainFrame.getCurrStudent().getLabGrade(currLessonButton.getLab());
			if (grade == null) {
				long id;
				Grade grade1 = new Grade(currLessonButton.getLab().getId(),
						mainFrame.getCurrStudent().getId(), Integer.parseInt(gradeButton.getText()));
				if ((id = GradeDaoImpl.getInstance().save(grade1)) != -1) {
					System.out.println("Оценка добавлена");
					grade1.setId(id);
					mainFrame.getCurrStudent().getGradeList().add(grade1);
					mainFrame.refreshStudentTable();
				}
			} else {
				grade.setGrade(Integer.parseInt(gradeButton.getText()));
				mainFrame.refreshStudentTable();
				if (GradeDaoImpl.getInstance().update(grade)) {
					System.out.println("Оценка изменена");
				}
			}
			if (mainFrame.getRadioBtnLab().isSelected()) {
				txtAverageGrade.setText(String.valueOf(mainFrame.getCurrStudent().getAverageGrade()));
			}
		} else {
			JOptionPane.showMessageDialog(mainFrame, "Перед выставлением оценки нужно отметить студента");
		}
		currLessonButton.requestFocus();
	}

	public Student getCurrStudent() {
		return mainFrame.getCurrStudent();
	}

	public JTextField getTxtFullName() {
		return txtFullName;
	}

	public JTextField getTxtEmail() {
		return txtEmail;
	}

	public JTextField getTxtPhone() {
		return txtPhone;
	}

	public JLabel getPhoneLabel() {
		return phoneLabel;
	}

	public JButton getBtnEditPhoto() {
		return btnEditPhoto;
	}

	public JLabel getEmailLabel() {
		return emailLabel;
	}

	public MainFrame getMainWindow() {
		return mainFrame;
	}

	public JPanel getGradePanel() {
		return gradePanel;
	}

	public List<LessonButton> getLessonButtons() {
		return lessonButtons;
	}
}