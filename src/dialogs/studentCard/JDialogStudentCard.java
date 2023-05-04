package dialogs.studentCard;

import MainFrame.MainWindow;
import database.dao.impl.GradeDaoImpl;
import entity.Grade;
import entity.Lab;
import entity.Student;
import utils.PhotoUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
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
		//photoLabel.setBorder(new LineBorder(Color.orange,10,true));
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
		btnEditPhoto.addActionListener(e -> {
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
		});
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
							mainWindow.updateStudentTable();
						}
					} else {
						grade.setGrade(Integer.parseInt(jButton.getText()));
						mainWindow.updateStudentTable();
						if (GradeDaoImpl.getInstance().update(grade)) {
							System.out.println("Оценка изменена");
						}
					}
					txtGpa.setText(String.valueOf(currStudent.getAverageGrade()));
				} else {
					JOptionPane.showMessageDialog(mainWindow, "Перед выставлением оценки нужно отметить студента");
				}
				SwingUtilities.invokeLater(currLabButton::requestFocus);
			});
			jButton.setPreferredSize(new Dimension(80, 30));
			markPanel.add(jButton);
		}
		// Добавление панели календаря
		calendarPanel = new JPanel(new GridLayout(0, 5, 5, 5));
		JScrollPane scrollPane = new JScrollPane(calendarPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createTitledBorder(
				mainWindow.getRadioBtnLab().isSelected() ? "Лабораторные работы" : "Лекции"));
		calendarPanel.setPreferredSize(new Dimension(800, 500));
		calendarPanel.setMinimumSize(calendarPanel.getPreferredSize());
		calendarPanel.setMaximumSize(calendarPanel.getPreferredSize());
		currStudent = new Student();
		updateStudentCard(currStudent);
		add(infoPanel);
		add(markPanel);
		add(scrollPane);

		ListenerJDialogStudentCard listenerJDialogStudentCard = new ListenerJDialogStudentCard(this);
		deleteButton.addActionListener(listenerJDialogStudentCard);
		editButton.addActionListener(listenerJDialogStudentCard);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (editButton.getText().equalsIgnoreCase("редактировать")) {
				mainWindow.getStudentTable().clearSelection();
				setVisible(false);
				mainWindow.updateStudentTable();
				mainWindow.sortTable();
			}else {
					JOptionPane.showMessageDialog(mainWindow, "Перед закрытием карточки нужно завершить редактирование");
					setVisible(true);
				}
			}
		});
	}

	public void updateStudentCard(Student student) {
		currStudent = student;
		this.setLocationRelativeTo(mainWindow);
		calendarPanel.removeAll();
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
		createLabButtons(mainWindow.getCurrentGroup().getLabs());
		getContentPane().repaint();

	}

	private void createLabButtons(List<Lab> labs) {
		for (Lab lab : labs) {
			// Создаем новую кнопку с датой и оценкой студент
			LabButton labButton = new LabButton(this, lab, false);
			labButton.setToolTipText("<html>"+lab.getLabName()+
					"<br>"+lab.getClassroom()+
					"</html>");
			Grade grade = currStudent.getLabGrade(labButton.getLab());
			if (grade != null) {
				labButton.updateGrade(String.valueOf(grade.getGrade()));
			} else {
				labButton.updateGrade("Нет");
			}
			labButton.setBorder(null);
			labButton.selected(false);
			if (labButton.getLab() == mainWindow.getCurrDateCmb().getSelectedItem()) {
				currLabButton = labButton;
				labButton.selected(true);
				currLabButton.setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
			}
			if (currStudent.isAttendance(labButton.getLab())) {
				labButton.setBackground(Color.GREEN);
			} else {
				labButton.setBackground(Color.GRAY);
			}
			calendarPanel.add(labButton);

			SwingUtilities.invokeLater(currLabButton::requestFocus);
		}
		int remaining = 25 - labs.size();
		for (int i = 1; i <= remaining; i++) {
			JPanel filler = new JPanel();
			getCalendarPanel().add(filler);
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
}