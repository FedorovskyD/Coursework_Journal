package dialogs;

import MainFrame.MainWindow;
import database.dao.impl.GroupDaoImpl;
import entity.Group;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddGroupDialog extends JDialog {

	private JTextField groupNameField;
	private JButton btnOk, btnCancel;
	private boolean okPressed;
	private MainWindow mainWindow;

	public AddGroupDialog(JFrame parent) {

		super(parent, "Add Group", true); // Устанавливаем заголовок и режим модальности
		setSize(300, 150);
		setLocationRelativeTo(null); // Центрируем диалоговое окно
		mainWindow = (MainWindow)parent;
		// Создаем компоненты для ввода данных
		JLabel groupNameLabel = new JLabel("Group name:");
		groupNameField = new JTextField();

		// Создаем кнопки
		btnOk = new JButton("OK");
		btnCancel = new JButton("Cancel");

		// Добавляем обработчики событий для кнопок
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Group group = new Group();
				group.setName(groupNameField.getText());
				group.setId(GroupDaoImpl.getInstance().save(group));
				mainWindow.getGroups().add(group);
				mainWindow.updateGroupCmb();
				okPressed = true;
				dispose(); // Закрываем диалоговое окно
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okPressed = false;
				dispose(); // Закрываем диалоговое окно
			}
		});

		// Создаем панель для компонентов ввода данных
		JPanel panelInput = new JPanel(new GridLayout(1, 2, 5, 5));
		panelInput.add(groupNameLabel);
		panelInput.add(groupNameField);

		// Создаем панель для кнопок
		JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		panelButton.add(btnOk);
		panelButton.add(btnCancel);

		// Добавляем панели на диалоговое окно
		getContentPane().setLayout(new BorderLayout(10, 10));
		getContentPane().add(panelInput, BorderLayout.CENTER);
		getContentPane().add(panelButton, BorderLayout.SOUTH);
	}

	public String getGroupName() {
		return groupNameField.getText();
	}

	public boolean isOkPressed() {
		return okPressed;
	}
}
