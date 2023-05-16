package gui.studentTable.studentCard.lessonButton;

import gui.studentTable.studentCard.StudentCardDialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LessonButtonMouseListener extends MouseAdapter {
	private final StudentCardDialog studentCard;

	public LessonButtonMouseListener(StudentCardDialog studentCard) {
		this.studentCard = studentCard;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		LessonButton button = (LessonButton) e.getSource();
		if (!button.isCurrent()) {
			if (studentCard.getCurrLessonButton()!= null) {
				studentCard.getCurrLessonButton().setCurrent(false);
				studentCard.getCurrLessonButton().repaint();
			}
				button.setCurrent(true);
				studentCard.setCurrLessonButton(button);
		}
		studentCard.getMainWindow().getCurrDateCmb().setSelectedItem(button.getLesson());
		int index1 = studentCard.getMainWindow().getRadioBtnLecture().isSelected() ? 1 : 2;
		studentCard.getMainWindow().getStudentTable().setColumnSelectionInterval(
				studentCard.getMainWindow().getCurrDateCmb().getSelectedIndex() + index1,
				studentCard.getMainWindow().getCurrDateCmb().getSelectedIndex() + index1);
	}
}
