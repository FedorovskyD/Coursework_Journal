package listeners;

import database.dao.impl.GroupDaoImpl;
import entity.Group;
import gui.MainFrame;
import dialogs.AddLessonDialog;
import dialogs.AddStudentDialog;
import dialogs.DeleteLessonDialog;
import gui.StudentTableCellRender;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameListener implements ActionListener, ListSelectionListener {

	private final MainFrame mainFrame;

	public MainFrameListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainFrame.getJbtnAddStudent()) {
			new AddStudentDialog(mainFrame);
		} else if (e.getSource() == mainFrame.getBtnAddGroup()) {
			addNewGroup();
		} else if (e.getSource() == mainFrame.getBtnDeleteGroup()) {
			deleteGroup();
		} else if (e.getSource() == mainFrame.getBtnAddLesson()) {
			new AddLessonDialog(mainFrame);
		} else if (e.getSource() == mainFrame.getJcmbGroupNumber()) {
			onCmbGroupNumberActionPerformed();
		} else if (e.getSource() == mainFrame.getJcmbCurrentDate()) {
			mainFrame.getStudentTable().setDefaultRenderer(Object.class,
					new StudentTableCellRender(mainFrame.getJcmbCurrentDate().getSelectedIndex()+(mainFrame.getJradiobtnLecture().isSelected()?2:3)));
			mainFrame.getStudentTable().repaint();
			mainFrame.repaint();
		} else if (e.getSource() == mainFrame.getJradiobtnDec() ||
				e.getSource() == mainFrame.getJradiobtnInc() ||
				e.getSource() == mainFrame.getJcmbSortOption()) {
			onSortActionPerformed();
		} else if (e.getSource() == mainFrame.getJradiobtnLecture() ||
				e.getSource() == mainFrame.getJradiobtnLab()) {
			onRadioActionPerformed();
		} else if (e.getSource()==mainFrame.getBtnDeleteLesson()) {
			new DeleteLessonDialog(mainFrame);
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
		if(mainFrame.getCurrGroup()==null){
			JOptionPane.showMessageDialog(mainFrame,"Нет ни одной группы!");
			return;
		}
		Group[] groups = mainFrame.getGroups().toArray(new Group[0]);
		int index = mainFrame.getJcmbGroupNumber().getSelectedIndex();
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
		mainFrame.refreshDateCmb();
		mainFrame.refreshStudentTable();
		int indexOfFirstLesson = mainFrame.getStudentTable().getStudentTableModel().getFIRST_LAB_COLUMN_INDEX();
		mainFrame.getStudentTable().setDefaultRenderer(Object.class, new StudentTableCellRender(indexOfFirstLesson));
		mainFrame.getJDialogStudentCard().getLabButtons().clear();
		mainFrame.getJDialogStudentCard().updateStudentCard(mainFrame.getCurrentStudentFromTable());
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
		if (mainFrame.getJradiobtnLecture().isSelected()) {
			mainFrame.getJDialogStudentCard().getJScrollPaneCalendar().setBorder(BorderFactory.createTitledBorder("Лекции"));
			mainFrame.getJDialogStudentCard().getJlblAverageGradeValue().setText(" ");
			mainFrame.getJDialogStudentCard().getJpanelGrade().setVisible(false);
		} else {
			mainFrame.getJDialogStudentCard().getJScrollPaneCalendar().setBorder(BorderFactory.createTitledBorder("Лабораторные"));
			mainFrame.getJDialogStudentCard().getJlblAverageMark().setText("Средний балл: ");
			mainFrame.getJDialogStudentCard().getJpanelGrade().setVisible(true);
		}

		mainFrame.getStudentTable().setDefaultRenderer(Object.class, new StudentTableCellRender(mainFrame.getJradiobtnLecture().isSelected()?2:3));
		mainFrame.getStudentTable().repaint();
		mainFrame.getJDialogStudentCard().getLabButtons().clear();
		mainFrame.getJDialogStudentCard().updateStudentCard(mainFrame.getCurrentStudentFromTable());

		if (mainFrame.getJDialogStudentCard().isVisible()) {
			mainFrame.getJDialogStudentCard().getCurrentLessonButton().requestFocus();
		}

		mainFrame.refreshDateCmb();
		mainFrame.refreshSortCmb();
		mainFrame.refreshStudentTable();
		mainFrame.getJDialogStudentCard().getJpanelGrade().repaint();
		mainFrame.getStudentTable().requestFocus();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

	}
}
