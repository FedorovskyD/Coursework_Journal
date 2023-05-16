package listeners;

import database.dao.impl.LessonDaoImpl;
import gui.dialogs.AddLessonDialog;
import entity.Group;
import entity.Lesson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;

public class AddLessonDialogListener implements ActionListener {
	private AddLessonDialog addLessonDialog;

	public AddLessonDialogListener(AddLessonDialog addLessonDialog) {
		this.addLessonDialog = addLessonDialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addLessonDialog.getAddButton()) {
			Group group = ((Group) addLessonDialog.getGroupComboBox().getSelectedItem());
			boolean isLecture = addLessonDialog.getRadioButtonLecture().isSelected();
			boolean isHoliday = addLessonDialog.getIsHolidayRadioButton().isSelected();

			Lesson lesson = new Lesson(
					addLessonDialog.getDate(), group.getId(), isLecture, isHoliday);
			long lessonId = LessonDaoImpl.getInstance().save(lesson);
			if (lessonId != -1) {
				lesson.setId(lessonId);
				group.getLessons().add(lesson);
				group.getLessons().sort(Comparator.comparing(Lesson::getDate));
				if (isLecture) {
					System.out.println("Лекционное добавлено");
				} else {
					System.out.println("Лабораторное занятие добавлено");
				}
				addLessonDialog.getMainWindow().refreshDateCmb();
				if (addLessonDialog.getMainWindow().getRadioBtnLecture().isSelected() == isLecture) {
					addLessonDialog.getMainWindow().refreshStudentTable();
				}
				addLessonDialog.getMainWindow().getJDialogStudentCard().getLessonButtons().clear();

				addLessonDialog.getMainWindow().getStudentTable().requestFocus();
			}
		}
	}
}
