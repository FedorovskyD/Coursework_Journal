package listeners;

import gui.StudentCard;
import gui.LessonButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LessonButtonMouseListener extends MouseAdapter {
	private final StudentCard studentCard;

	public LessonButtonMouseListener(StudentCard studentCard) {
		this.studentCard = studentCard;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			LessonButton button = (LessonButton) e.getSource();
			if (studentCard.getCurrentLessonButton() != null) {
				studentCard.getCurrentLessonButton().setCurrentColor(false);
			}
			studentCard.setCurrentLessonButton(button);
			button.setCurrentColor(true);
			button.repaint();
			studentCard.getMainWindow().getJcmbCurrentDate().setSelectedItem(button.getLesson());
			int index1 = studentCard.getMainWindow().getJradiobtnLecture().isSelected() ? 2 : 3;
			studentCard.getMainWindow().getStudentTable().setColumnSelectionInterval(
					studentCard.getMainWindow().getJcmbCurrentDate().getSelectedIndex() + index1,
					studentCard.getMainWindow().getJcmbCurrentDate().getSelectedIndex() + index1);
		}
	}
}
