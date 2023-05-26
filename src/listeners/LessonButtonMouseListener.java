package listeners;

import dialogs.StudentCardDialog;
import gui.LessonButton;

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
		if (studentCard.getCurrLessonButton() != null) {
			studentCard.getCurrLessonButton().setCurrent(false);
		}
		studentCard.setCurrLessonButton(button);
		button.setCurrent(true);
		button.repaint();
		studentCard.getMainWindow().getCurrDateCmb().setSelectedItem(button.getLesson());
		int index1 = studentCard.getMainWindow().getRadioBtnLecture().isSelected() ? 2 : 3;
		studentCard.getMainWindow().getStudentTable().setColumnSelectionInterval(
				studentCard.getMainWindow().getCurrDateCmb().getSelectedIndex() + index1,
				studentCard.getMainWindow().getCurrDateCmb().getSelectedIndex() + index1);
	}
}
