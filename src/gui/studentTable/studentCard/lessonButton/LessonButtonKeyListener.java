package gui.studentTable.studentCard.lessonButton;

import database.dao.impl.AbsenceDaoImpl;
import database.dao.impl.GradeDaoImpl;
import entity.Absence;
import entity.Grade;
import gui.studentTable.studentCard.JDialogStudentCard;
import utils.Constants;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LessonButtonKeyListener extends KeyAdapter {
	private JDialogStudentCard studentCard;

	public LessonButtonKeyListener(JDialogStudentCard studentCard) {
		this.studentCard = studentCard;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		LessonButton button = (LessonButton) e.getSource();
		int keyCode = e.getKeyCode();

		switch (keyCode) {
			case KeyEvent.VK_ESCAPE:
				studentCard.getMainWindow().getJDialogStudentCard().dispose();
				break;
			case KeyEvent.VK_SPACE:
				handleSpaceKeyPressed(button);
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
	private void moveLessonButton(int keyCode, LessonButton button) {
		int index = studentCard.getLessonButtons().indexOf(button);
		int nextIndex = index + (keyCode == KeyEvent.VK_LEFT ? -1 : 1);
		if (nextIndex >= 0 && nextIndex < studentCard.getLessonButtons().size()) {
			LessonButton nextLessonButton = studentCard.getLessonButtons().get(nextIndex);
			nextLessonButton.setCurrent(true);
			button.setCurrent(false);
			button.setBorder(null);
			button.repaint();
			nextLessonButton.repaint();
			studentCard.setCurrLessonButton(nextLessonButton);
			studentCard.getMainWindow().getCurrDateCmb().setSelectedItem(nextLessonButton.getLesson());
			studentCard.getMainWindow().getStudentTable().setColumnSelectionInterval(
					studentCard.getMainWindow().getCurrDateCmb().getSelectedIndex()+2,studentCard.getMainWindow().getCurrDateCmb().getSelectedIndex()+2);
			studentCard.getMainWindow().repaint();
			nextLessonButton.requestFocus();
		}
	}
	private void handleSpaceKeyPressed(LessonButton button) {
		if (!button.isChecked()) {
			addAbsence(button);
			if(studentCard.getMainWindow().getCheckBox().isSelected()){
				moveTableRow(KeyEvent.VK_DOWN);
			}
		} else {
			removeAbsence(button);
		}
	}
	private void moveTableRow(int keyCode) {
		int selectedRow = studentCard.getMainWindow().getStudentTable().getSelectedRow();
		if (selectedRow != -1) {
			int nextRow = selectedRow + (keyCode == KeyEvent.VK_UP ? -1 : 1);
			while (nextRow >= 0 && nextRow < studentCard.getMainWindow().getStudentTable().getRowCount() &&
					studentCard.getMainWindow().getStudentTable().getStudentTableModel().isBlankRow(nextRow)) {
				nextRow += (keyCode == KeyEvent.VK_UP ? -1 : 1);
			}
			if (nextRow >= 0 && nextRow < studentCard.getMainWindow().getStudentTable().getRowCount()) {
				studentCard.getMainWindow().getStudentTable().setRowSelectionInterval(nextRow, nextRow);
				studentCard.getMainWindow().getStudentTable().scrollRectToVisible(studentCard.getMainWindow()
						.getStudentTable().getCellRect(nextRow, 0, true));
				studentCard.getMainWindow().getStudentTable().repaint();
			}
		}
	}
	private void addAbsence(LessonButton button) {
		Absence absence = new Absence(button.getLesson().getId(), studentCard.getMainWindow().getCurrStudent().getId());
		Grade studentGrade = studentCard.getMainWindow().getCurrStudent().getLessonGrade(button.getLesson());
		long id = AbsenceDaoImpl.getInstance().save(absence);
		if (id > 0) {
			absence.setId(id);
			if (button.getLesson().isLecture()) {
				studentCard.getMainWindow().getCurrStudent().getLectureAbsenceList().add(absence);
			} else {
				studentCard.getMainWindow().getCurrStudent().getLabAbsenceList().add(absence);
			}
			button.setBackground(Constants.ABSENCE_COLOR);
			if (studentGrade != null && GradeDaoImpl.getInstance().delete(studentGrade)) {
				studentCard.getMainWindow().getCurrStudent().getGradeList().remove(studentGrade);
				System.out.println("Оценка за лабораторную удалена");
			}
			button.setData();
			button.setChecked(true);
			System.out.println("Запись о отсутствии добавлена");
			studentCard.getMainWindow().refreshStudentTable();
		}
	}

	private void removeAbsence(LessonButton button) {
		Absence absence = studentCard.getMainWindow().getCurrStudent().getLessonAbsence(button.getLesson());

		if (absence != null) {
			if (AbsenceDaoImpl.getInstance().delete(absence)) {
				System.out.println("Запись об отсутствии удалена");
				if (button.getLesson().isLecture()) {
					studentCard.getMainWindow().getCurrStudent().getLectureAbsenceList().remove(absence);
				} else {
					studentCard.getMainWindow().getCurrStudent().getLabAbsenceList().remove(absence);
				}
				button.setBackground(Color.WHITE);
				button.setData();
				button.setChecked(false);
				studentCard.getMainWindow().refreshStudentTable();
			}
		}
	}
}
