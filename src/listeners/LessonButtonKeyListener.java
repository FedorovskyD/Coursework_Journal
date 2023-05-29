package listeners;

import database.dao.impl.AbsenceDaoImpl;
import entity.Absence;
import entity.Grade;
import gui.StudentCard;
import gui.LessonButton;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LessonButtonKeyListener extends KeyAdapter {
	private StudentCard studentCard;

	public LessonButtonKeyListener(StudentCard studentCard) {
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
				handleSpaceKeyPressed(button, e.isShiftDown());
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
			case KeyEvent.VK_0:
			case KeyEvent.VK_NUMPAD0:
				studentCard.getGradeButtons().get(0).doClick();
				break;
			case KeyEvent.VK_1:
			case KeyEvent.VK_NUMPAD1:
				studentCard.getGradeButtons().get(1).doClick();
				break;
			case KeyEvent.VK_2:
			case KeyEvent.VK_NUMPAD2:
				studentCard.getGradeButtons().get(2).doClick();
				break;
			case KeyEvent.VK_3:
			case KeyEvent.VK_NUMPAD3:
				studentCard.getGradeButtons().get(3).doClick();
				break;
			case KeyEvent.VK_4:
			case KeyEvent.VK_NUMPAD4:
				studentCard.getGradeButtons().get(4).doClick();
				break;
			case KeyEvent.VK_5:
			case KeyEvent.VK_NUMPAD5:
				studentCard.getGradeButtons().get(5).doClick();
				break;
			case KeyEvent.VK_6:
			case KeyEvent.VK_NUMPAD6:
				studentCard.getGradeButtons().get(6).doClick();
				break;
			case KeyEvent.VK_7:
			case KeyEvent.VK_NUMPAD7:
				studentCard.getGradeButtons().get(7).doClick();
				break;
			case KeyEvent.VK_8:
			case KeyEvent.VK_NUMPAD8:
				studentCard.getGradeButtons().get(8).doClick();
				break;
			case KeyEvent.VK_9:
			case KeyEvent.VK_NUMPAD9:
				studentCard.getGradeButtons().get(9).doClick();
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
			nextLessonButton.setCurrentColor(true);
			button.setCurrentColor(false);
			button.repaint();
			nextLessonButton.repaint();
			studentCard.setCurrentLessonButton(nextLessonButton);
			studentCard.getMainWindow().getJcmbCurrentDate().setSelectedItem(nextLessonButton.getLesson());
			boolean isLecture = studentCard.getMainWindow().getJradiobtnLecture().isSelected();
			int index1 = isLecture ? 2 : 3;
			studentCard.getMainWindow().getStudentTable().setColumnSelectionInterval(
					studentCard.getMainWindow().getJcmbCurrentDate().getSelectedIndex() + index1, studentCard.getMainWindow().getJcmbCurrentDate().getSelectedIndex() + index1);
			int selectedColumn;
			if (nextIndex + index1 < 4) {
				selectedColumn = 0;
			} else {
				selectedColumn = nextIndex + index1;
			}
			studentCard.getMainWindow().getStudentTable().scrollRectToVisible(studentCard.getMainWindow().getStudentTable()
					.getCellRect(studentCard.getMainWindow().getStudentTable().getSelectedRow(), selectedColumn, true));
			studentCard.getMainWindow().repaint();
			nextLessonButton.requestFocus();
		}
	}

	private void handleSpaceKeyPressed(LessonButton button, boolean isHalf) {
		if (!button.getLesson().isHoliday()) {
			if (!button.isChecked()) {
				addAbsence(button, isHalf);
				if (studentCard.getMainWindow().getJCheckBoxMarkUnder().isSelected()) {
					moveTableRow(KeyEvent.VK_DOWN);
				}
			} else if (isHalf) {
				updateAbsence(button);
			} else {
				removeAbsence(button);
			}
		} else {
			JOptionPane.showMessageDialog(studentCard, "Нельзя отметить отсутствие в праздничный день");
		}
		studentCard.updateStudentCard(studentCard.getCurrStudent());
	}

	private void moveTableRow(int keyCode) {
		int firstIndex = studentCard.getMainWindow().getStudentTable().getStudentTableModel().getFIRST_LAB_COLUMN_INDEX();
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
						.getStudentTable().getCellRect(nextRow, studentCard.getMainWindow().getJcmbCurrentDate().getSelectedIndex() + firstIndex, true));
				studentCard.getMainWindow().getStudentTable().repaint();
			}
		}
	}

	private void addAbsence(LessonButton button, boolean isHalf) {
		Absence absence = new Absence(button.getLesson().getId(), studentCard.getMainWindow().getCurrentStudentFromTable().getId(), isHalf);
		if (isHalf) {
			button.setHalf(true);
		}
		Grade studentGrade = studentCard.getCurrStudent().getLessonGrade(button.getLesson());
		long id = AbsenceDaoImpl.getInstance().save(absence);
		if (id > 0) {
			absence.setId(id);
			if (button.getLesson().isLecture()) {
				studentCard.getMainWindow().getCurrentStudentFromTable().getLectureAbsenceList().add(absence);
			} else {
				studentCard.getMainWindow().getCurrentStudentFromTable().getLabAbsenceList().add(absence);
			}
			if (isHalf) {
				if (studentGrade != null) {
					button.setDateWithGrade(String.valueOf(studentGrade.getGrade()));
				}
			} else {
				button.setDate();
			}
			button.setAbsence(true);
			System.out.println("Запись о отсутствии c id = "+id+" добавлена");
			studentCard.getMainWindow().refreshStudentTable();
		}
	}

	private void removeAbsence(LessonButton button) {
		Absence absence = studentCard.getMainWindow().getCurrentStudentFromTable().getLessonAbsence(button.getLesson());
		if (absence != null) {
			if (AbsenceDaoImpl.getInstance().delete(absence)) {
				System.out.println("Запись об отсутствии c id = " + absence.getId() + " удалена");
				if (button.getLesson().isLecture()) {
					studentCard.getMainWindow().getCurrentStudentFromTable().getLectureAbsenceList().remove(absence);
				} else {
					studentCard.getMainWindow().getCurrentStudentFromTable().getLabAbsenceList().remove(absence);
				}
				button.setAbsence(false);
				studentCard.getMainWindow().refreshStudentTable();
				button.setHalf(false);
			}
		}
	}

	private void updateAbsence(LessonButton button) {
		Absence absence = studentCard.getMainWindow().getCurrentStudentFromTable().getLessonAbsence(button.getLesson());
		if (absence == null) return;
		if (!absence.isHalf()) {
			absence.setHalf(true);
		}
		if (AbsenceDaoImpl.getInstance().update(absence)) {
			System.out.println("Запись об отсутствии c id = "+absence.getId()+" обновлена");
			button.setHalf(true);
		}
	}
}
