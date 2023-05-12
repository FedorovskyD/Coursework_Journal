package gui.studentTable.studentCard.lessonButton;

import gui.studentTable.studentCard.JDialogStudentCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LessonButtonMouseListener extends MouseAdapter {
	private JDialogStudentCard studentCard;

	public LessonButtonMouseListener(JDialogStudentCard studentCard) {
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
			studentCard.getMainWindow().getCurrDateCmb().setSelectedItem(button.getLesson());
		}
	}
}
