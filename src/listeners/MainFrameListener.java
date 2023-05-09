package listeners;

import gui.MainFrame;
import gui.dialogs.AddLessonDialog;
import gui.dialogs.AddStudentDialog;
import gui.studentTable.StudentLabTableModel;
import database.dao.impl.GroupDaoImpl;
import entity.Group;
import entity.Student;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

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
			mainFrame.refreshDateCmb();
			mainFrame.refreshStudentTable();
			mainFrame.getJDialogStudentCard().getLabButtons().clear();
			mainFrame.getJDialogStudentCard().updateStudentCard(mainFrame.getCurrStudent());
			mainFrame.getJDialogStudentCard().revalidate();
			mainFrame.getJDialogStudentCard().repaint();
			mainFrame.getJDialogStudentCard().requestFocus();
		} else if (e.getSource() == mainFrame.getRadioBtnDec() || e.getSource() == mainFrame.getRadioBtnInc() || e.getSource() == mainFrame.getCmbSort()) {
			mainFrame.refreshStudentTable();
			mainFrame.getStudentTable().requestFocus();
		} else if (e.getSource() == mainFrame.getRadioBtnLecture()) {
			mainFrame.getJDialogStudentCard().getScrollPane().setBorder(BorderFactory.createTitledBorder("Лекции"));
			mainFrame.getJDialogStudentCard().getTxtAverageGrade().setText(" ");
			mainFrame.getJDialogStudentCard().getGradePanel().setVisible(mainFrame.getRadioBtnLecture().isSelected());
			mainFrame.getJDialogStudentCard().getLabButtons().clear();
			mainFrame.getJDialogStudentCard().updateStudentCard(mainFrame.getCurrentStudent());
			if (mainFrame.getJDialogStudentCard().isVisible()) {
				mainFrame.getJDialogStudentCard().getCurrLessonButton().requestFocus();
			}
			mainFrame.refreshDateCmb();
			mainFrame.refreshSortCmb();
			mainFrame.refreshStudentTable();
			mainFrame.getStudentTable().requestFocus();
		} else if (e.getSource() == mainFrame.getRadioBtnLab()) {
			mainFrame.getJDialogStudentCard().getScrollPane().setBorder(BorderFactory.createTitledBorder("Лабораторные"));
			mainFrame.getJDialogStudentCard().getGpaLabel().setText("Средний балл: ");
			mainFrame.getJDialogStudentCard().getGradePanel().setVisible(!mainFrame.getRadioBtnLecture().isSelected());
			mainFrame.getJDialogStudentCard().getLabButtons().clear();
			mainFrame.getJDialogStudentCard().updateStudentCard(mainFrame.getCurrentStudent());
			if (mainFrame.getJDialogStudentCard().isVisible()) {
				mainFrame.getJDialogStudentCard().getCurrLessonButton().requestFocus();
			}
			mainFrame.refreshDateCmb();
			mainFrame.refreshSortCmb();
			mainFrame.refreshStudentTable();
			mainFrame.getStudentTable().requestFocus();
		} else if (e.getSource() == mainFrame.getCmbGroupNumber()) {

		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == mainFrame.getStudentTable().getSelectionModel()) {
			int selectedRowIndex = mainFrame.getStudentTable().getSelectedRow();
			if (selectedRowIndex != -1) {
				Object value = mainFrame.getStudentTable().getValueAt(selectedRowIndex, 0); // Получаем значение в ячейке первого столбца строки
				if (value == null) {
					int index = mainFrame.getStudentTable().getStudentTableModel().getRowIndex(mainFrame.getCurrStudent());
					if (index != -1) {
						mainFrame.getStudentTable().setRowSelectionInterval(index, index);
						mainFrame.getJDialogStudentCard().setVisible(false);
					}
				} else {
					Student selectedStudent = (Student) value;
					boolean isVisible = mainFrame.getCurrStudent() != selectedStudent
							&& mainFrame.getCurrStudent()!=null
							&& mainFrame.getCurrStudent().getGroupId() == selectedStudent.getGroupId();
					if (isVisible) {
						mainFrame.setCurrStudent(selectedStudent);
						mainFrame.getJDialogStudentCard().updateStudentCard(selectedStudent);
						mainFrame.getJDialogStudentCard().setVisible(true);
					}
				}
			}
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
}

