package listeners;

import database.dao.impl.GroupDaoImpl;
import entity.Group;
import entity.Student;
import gui.MainFrame;
import gui.dialogs.AddLessonDialog;
import gui.dialogs.AddStudentDialog;
import gui.studentTable.StudentTableCellRender;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameListener implements ActionListener, ListSelectionListener {

	private final MainFrame mainFrame;

	public MainFrameListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainFrame.getBtnAddStudent()) {
			new AddStudentDialog(mainFrame).setVisible(true);
		} else if (e.getSource() == mainFrame.getBtnAddGroup()) {
			addNewGroup();
		} else if (e.getSource() == mainFrame.getBtnDeleteGroup()) {
			deleteGroup();
		} else if (e.getSource() == mainFrame.getBtnAddLab()) {
			new AddLessonDialog(mainFrame).setVisible(true);
		} else if (e.getSource() == mainFrame.getCmbGroupNumber()) {
			onCmbGroupNumberActionPerformed();
		} else if (e.getSource() == mainFrame.getCurrDateCmb()) {
			mainFrame.getStudentTable().setDefaultRenderer(Object.class,
					new StudentTableCellRender(mainFrame.getCurrDateCmb().getSelectedIndex() +2));
			mainFrame.getStudentTable().repaint();
		} else if (e.getSource() == mainFrame.getRadioBtnDec() ||
				e.getSource() == mainFrame.getRadioBtnInc() ||
				e.getSource() == mainFrame.getCmbSort()) {
			onSortActionPerformed();
		} else if (e.getSource() == mainFrame.getRadioBtnLecture() ||
				e.getSource() == mainFrame.getRadioBtnLab()) {
			onRadioActionPerformed();
		}

	}

	private void addNewGroup() {
		String groupName = JOptionPane.showInputDialog(mainFrame,
				"Введите название группы:", "Добавление группы", JOptionPane.PLAIN_MESSAGE);
		if (groupName != null && !groupName.isEmpty()) {
			Group group = new Group();
			group.setName(groupName);
			group.setId(GroupDaoImpl.getInstance().save(group));
			mainFrame.getGroups().add(group);
			mainFrame.refreshGroupCmb();
			mainFrame.getStudentTable().requestFocus();
		}
	}

	private void deleteGroup() {
		Group[] groups = mainFrame.getGroups().toArray(new Group[0]);
		int index = mainFrame.getCmbGroupNumber().getSelectedIndex();
		Group selectedGroup = (Group) JOptionPane.showInputDialog(
				mainFrame,
				"Выберите группу для удаления:",
				"Удаление группы",
				JOptionPane.QUESTION_MESSAGE,
				null,
				groups,
				groups[index]);

		boolean isUpdate = selectedGroup == mainFrame.getCurrentGroup();
		// Удаляем группу из базы данных
		if (selectedGroup != null) {
			if (GroupDaoImpl.getInstance().delete(selectedGroup)) {
				mainFrame.getGroups().remove(selectedGroup);
				mainFrame.refreshGroupCmb();
				// Устанавливаем новую модель в JComboBox
				selectedGroup = mainFrame.getCurrentGroup();
				if (selectedGroup != null && isUpdate) {
					mainFrame.refreshDateCmb();
					mainFrame.refreshStudentTable();
				}
			}
			mainFrame.getStudentTable().requestFocus();
		}
	}

	private void onCmbGroupNumberActionPerformed() {
		mainFrame.getStudentTable().setDefaultRenderer(Object.class, new StudentTableCellRender(2));
		mainFrame.refreshDateCmb();
		mainFrame.refreshStudentTable();
		mainFrame.getJDialogStudentCard().getLabButtons().clear();
		mainFrame.getJDialogStudentCard().updateStudentCard(mainFrame.getCurrStudent());
		mainFrame.getJDialogStudentCard().revalidate();
		mainFrame.getJDialogStudentCard().repaint();
		mainFrame.getStudentTable().repaint();
		mainFrame.getJDialogStudentCard().requestFocus();
	}

	private void onSortActionPerformed() {
		mainFrame.refreshStudentTable();
		mainFrame.getStudentTable().requestFocus();
	}

	public void onRadioActionPerformed() {
		if (mainFrame.getRadioBtnLecture().isSelected()) {
			mainFrame.getJDialogStudentCard().getScrollPane().setBorder(BorderFactory.createTitledBorder("Лекции"));
			mainFrame.getJDialogStudentCard().getTxtAverageGrade().setText(" ");
			mainFrame.getJDialogStudentCard().getGradePanel().setVisible(false);
		} else {
			mainFrame.getJDialogStudentCard().getScrollPane().setBorder(BorderFactory.createTitledBorder("Лабораторные"));
			mainFrame.getJDialogStudentCard().getGpaLabel().setText("Средний балл: ");
			mainFrame.getJDialogStudentCard().getGradePanel().setVisible(true);
		}

		mainFrame.getStudentTable().setDefaultRenderer(Object.class, new StudentTableCellRender(2));
		mainFrame.getStudentTable().repaint();
		mainFrame.getJDialogStudentCard().getLabButtons().clear();
		mainFrame.getJDialogStudentCard().updateStudentCard(mainFrame.getCurrentStudent());

		if (mainFrame.getJDialogStudentCard().isVisible()) {
			mainFrame.getJDialogStudentCard().getCurrLessonButton().requestFocus();
		}

		mainFrame.refreshDateCmb();
		mainFrame.refreshSortCmb();
		mainFrame.refreshStudentTable();
		mainFrame.getJDialogStudentCard().getGradePanel().repaint();
		mainFrame.getStudentTable().requestFocus();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

	}
}
