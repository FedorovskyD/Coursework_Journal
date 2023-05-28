package listeners;

import database.dao.impl.LessonDaoImpl;
import dialogs.AddLessonDialog;
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
		if (e.getSource() == addLessonDialog.getJbtnAddLesson()) {
			Group group = ((Group) addLessonDialog.getJcmbGroupNumber().getSelectedItem());
			boolean isLecture = addLessonDialog.getJradiobtnLecture().isSelected();
			boolean isHoliday = addLessonDialog.getJradionbtnIsHoliday().isSelected();

			Lesson lesson = new Lesson(
					addLessonDialog.getDate(), group.getId(), isLecture, isHoliday);
			long lessonId = LessonDaoImpl.getInstance().save(lesson);
			if (lessonId != -1) {
				lesson.setId(lessonId);
				group.getLessons().add(lesson);
				group.getLessons().sort(Comparator.comparing(Lesson::getDate));
				if (isLecture) {
					System.out.println("Лекционное занятие c id = " + lessonId + " добавлено");
				} else {
					System.out.println("Лабораторное занятие c id = " + lessonId + " добавлено");
				}
				addLessonDialog.getMainWindow().refreshDateCmb();
				if (addLessonDialog.getMainWindow().getJradiobtnLecture().isSelected() == isLecture) {
					addLessonDialog.getMainWindow().refreshStudentTable();
				}
				addLessonDialog.getMainWindow().getJDialogStudentCard().getLessonButtons().clear();

				addLessonDialog.getMainWindow().getStudentTable().requestFocus();
			}
		} else if (e.getSource() == addLessonDialog.getJbtnCancel()) {
			addLessonDialog.dispose();
		}
	}
}
