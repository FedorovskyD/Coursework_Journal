package gui.studentTable.studentCard;

import entity.Absence;
import gui.MainFrame;
import database.dao.impl.GradeDaoImpl;
import entity.Grade;
import entity.Lesson;
import entity.Student;
import gui.studentTable.studentCard.lessonButton.LessonButton;
import gui.studentTable.studentCard.lessonButton.LessonButtonKeyListener;
import gui.studentTable.studentCard.lessonButton.LessonButtonMouseListener;
import listeners.StudentCardDialogListener;
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
public class StudentCardDialog extends JDialog {
	private final JLabel photoLabel;
	private final JTextField txtFullName, txtEmail, txtPhone;
	private final JLabel txtAverageGrade, lblLessonCount, lblStudentHours, lblPercentage;
	private final JLabel phoneLabel;
	private final JLabel gpaLabel;
	private final JLabel emailLabel;
	private final JButton deleteButton, editButton, btnEditPhoto;
	private final JPanel calendarPanel;
	private LessonButton currLessonButton;
	private final List<LessonButton> lessonButtons;
	private final MainFrame mainFrame;
	private final JScrollPane scrollPane;
	private final JPanel gradePanel;
	private final List<JButton> gradeButtons;


	public StudentCardDialog(JFrame owner, String title) {
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
		lblLessonCount = new JLabel();
		lblStudentHours = new JLabel();
		lblPercentage = new JLabel();
		JLabel lessonCount = new JLabel("Часов занятий:");
		JLabel hoursCount = new JLabel("Часов присутствия:");
		JLabel persantage = new JLabel("Процент посещения:");

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
		gradeButtons = new ArrayList<>();
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
						.addComponent(lessonCount)
						.addComponent(hoursCount)
						.addComponent(persantage)
						.addComponent(gpaLabel)
						.addComponent(editButton))
				.addGap(10)
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(txtFullName)
						.addComponent(txtEmail)
						.addComponent(txtPhone)
						.addComponent(lblLessonCount)
						.addComponent(lblStudentHours)
						.addComponent(lblPercentage)
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
								.addComponent(lessonCount)
								.addComponent(lblLessonCount))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(hoursCount)
								.addComponent(lblStudentHours))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(persantage)
								.addComponent(lblPercentage))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(gpaLabel)
								.addComponent(txtAverageGrade))
						.addGap(50) // добавляем 10 пикселей расстояния между строками
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
		scrollPane = new JScrollPane(calendarPanel);
		scrollPane.setBorder(BorderFactory.createTitledBorder(
				mainFrame.getRadioBtnLab().isSelected() ? "Лабораторные работы" : "Лекции"));
		calendarPanel.setPreferredSize(new Dimension(800, 500));
		calendarPanel.setMinimumSize(calendarPanel.getPreferredSize());
		calendarPanel.setMaximumSize(calendarPanel.getPreferredSize());
		add(infoPanel);
		add(gradePanel);
		add(scrollPane);
		StudentCardDialogListener studentCardDialogListener = new StudentCardDialogListener(this);
		btnEditPhoto.addActionListener(e -> {
			editPhoto();
		});
		deleteButton.addActionListener(studentCardDialogListener);
		editButton.addActionListener(studentCardDialogListener);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (editButton.getText().equalsIgnoreCase("редактировать")) {
					mainFrame.refreshStudentTable();
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(mainFrame, "Перед закрытием карточки нужно завершить редактирование");
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
			txtAverageGrade.setText(String.format("%.2f", student.getAverageGrade()));
			gpaLabel.setText("Cредний балл:");
			int labHoursCount = mainFrame.getCurrentGroup().getLabHours();
			lblLessonCount.setText(String.valueOf(labHoursCount));
			int labHoursAttendance = labHoursCount - student.getLabAbsenceHours();
			lblStudentHours.setText(String.valueOf(labHoursAttendance));
			lblPercentage.setText(String.format("%.1f ", ((double) labHoursAttendance / labHoursCount) * 100) + " %");
		} else {
			gpaLabel.setText("");
			int lectureHoursCount = mainFrame.getCurrentGroup().getLectureHours();
			lblLessonCount.setText(String.valueOf(lectureHoursCount));
			int lectureHoursAttendance = lectureHoursCount - student.getLectureAbsenceHours();
			lblStudentHours.setText(String.valueOf(lectureHoursAttendance));
			lblPercentage.setText(String.format("%.1f", ((double) lectureHoursAttendance / lectureHoursCount) * 100) + " %");
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
				Grade grade = mainFrame.getCurrStudent().getLessonGrade(lessonButton.getLesson());
				if (mainFrame.getRadioBtnLecture().isSelected()) {
					lessonButton.setData();
				} else if (grade != null) {
					lessonButton.setGrade(String.valueOf(grade.getGrade()));
				} else {
					lessonButton.setData();
				}
				Absence absence = mainFrame.getCurrStudent().getLessonAbsence(lessonButton.getLesson());
				if (absence != null) {
					lessonButton.setHalf(absence.isHalf());
				}
				lessonButton.setChecked(absence != null);
				setInitialSelection(lessonButton);
				lessonButton.repaint();
			});
		}
	}

	private void createLessonButtons(List<Lesson> lessons) {
		calendarPanel.removeAll();
		for (Lesson lesson : lessons) {

			LessonButton lessonButton = new LessonButton(lesson);
			Grade grade = mainFrame.getCurrStudent().getLessonGrade(lesson);
			if (mainFrame.getRadioBtnLecture().isSelected() || lesson.isLecture()) {
				lessonButton.setData();
			} else if (grade != null) {
				lessonButton.setGrade(String.valueOf(grade.getGrade()));
			} else {
				lessonButton.setData();
			}
			lessonButton.setPreferredSize(new Dimension(180, 80));
			Absence absence = mainFrame.getCurrStudent().getLessonAbsence(lesson);
			if (absence != null) {
				lessonButton.setHalf(absence.isHalf());
			}
			lessonButton.setChecked(absence != null);
			setButtonClickListener(lessonButton);
			setButtonKeyListener(lessonButton);
			lessonButton.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					if (editButton.getText().equalsIgnoreCase("сохранить")) {
						editButton.requestFocus();
					}
				}

				@Override
				public void focusLost(FocusEvent e) {

				}
			});
			lessonButtons.add(lessonButton);
			calendarPanel.add(lessonButton);
			setInitialSelection(lessonButton);
		}
	}

	private void setInitialSelection(LessonButton lessonButton) {
		lessonButton.setCurrent(false);
		if (lessonButton.getLesson().equals(mainFrame.getCurrDate())) {
			if (currLessonButton != null) {
				currLessonButton.setCurrent(false);
			}
			lessonButton.setCurrent(true);
			currLessonButton = lessonButton;
		}
	}

	private void setButtonClickListener(LessonButton lessonButton) {
		lessonButton.addMouseListener(new LessonButtonMouseListener(mainFrame.getJDialogStudentCard()));
	}

	private void setButtonKeyListener(LessonButton lessonButton) {
		lessonButton.addKeyListener(new LessonButtonKeyListener(mainFrame.getJDialogStudentCard()));
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
			gradeButtons.add(gradeButton);
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
		if (currLessonButton.getLesson().isLecture()) return;
		String gradeStr = "";
		if ((!currLessonButton.getLesson().isHoliday())) {
			if (currLessonButton.getGrade().equals("1") && gradeButton.getText().equals("0")) {
				currLessonButton.setGrade("10");
				gradeStr = "10";
			} else {
				currLessonButton.setGrade(gradeButton.getText());
				gradeStr = gradeButton.getText();
			}

			Grade grade = mainFrame.getCurrStudent().getLessonGrade(currLessonButton.getLab());
			if (grade == null) {
				long id;
				Grade grade1 = new Grade(currLessonButton.getLab().getId(),
						mainFrame.getCurrStudent().getId(), Integer.parseInt(gradeStr));
				if ((id = GradeDaoImpl.getInstance().save(grade1)) != -1) {
					System.out.println("Оценка c id " + id + " добавлена");
					grade1.setId(id);
					mainFrame.getCurrStudent().getGradeList().add(grade1);
					mainFrame.refreshStudentTable();
				}
			} else {
				grade.setGrade(Integer.parseInt(gradeStr));
				mainFrame.refreshStudentTable();
				if (GradeDaoImpl.getInstance().update(grade)) {
					System.out.println("Оценка c id = " + grade.getId() + " изменена");
				}
			}
			if (mainFrame.getRadioBtnLab().isSelected()) {
				txtAverageGrade.setText(String.format("%.2f", mainFrame.getCurrStudent().getAverageGrade()));
			}
		} else {
			JOptionPane.showMessageDialog(mainFrame, "Нельзя выставить оценку в этот день");
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

	public void setCurrLessonButton(LessonButton currLessonButton) {
		this.currLessonButton = currLessonButton;
	}

	public List<JButton> getGradeButtons() {
		return gradeButtons;
	}

	public JLabel getLblLessonCount() {
		return lblLessonCount;
	}
}