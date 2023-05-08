package dialogs;

import database.dao.impl.LessonDaoImpl;
import entity.Group;
import entity.Lesson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;

public class ListenerJDialogAddLesson implements ActionListener {
	private JDialogAddLesson jDialogAddLesson;
	public ListenerJDialogAddLesson(JDialogAddLesson jDialogAddLesson){
		this.jDialogAddLesson = jDialogAddLesson;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jDialogAddLesson.addButton) {
			Group group = ((Group) jDialogAddLesson.getGroupComboBox().getSelectedItem());
			boolean isLecture = jDialogAddLesson.getRadioButtonLecture().isSelected();
			Lesson lesson = new Lesson(jDialogAddLesson.getRoomField().getText(),
					jDialogAddLesson.getDate(),group.getId(), jDialogAddLesson.getNameField().getText(),isLecture);
			long lessonId = LessonDaoImpl.getInstance().save(lesson);
			if(lessonId!= -1){
				lesson.setId(lessonId);
				group.getLessons().add(lesson);
				group.getLessons().sort(Comparator.comparing(Lesson::getDate));
				if(isLecture) {
					System.out.println("Лекционное добавлено");
				}else {
					System.out.println("Лабораторное занятие добавлено");
				}
				jDialogAddLesson.mainWindow.updateDateCmb();
				jDialogAddLesson.mainWindow.refreshStudentTable();

			}
		}
	}
}
