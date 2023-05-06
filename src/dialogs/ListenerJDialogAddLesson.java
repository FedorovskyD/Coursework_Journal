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
			long labID = LessonDaoImpl.getInstance().save(lesson);
			if(labID!= -1){
				lesson.setId(labID);
				group.getLabs().add(lesson);
				Collections.sort(group.getLabs(), Comparator.comparing(Lesson::getDate));
				System.out.println("Лабораторная добавлена");
				jDialogAddLesson.mainWindow.updateCurrDateCmb();
				jDialogAddLesson.mainWindow.refreshStudentTable();

			}
		}
	}
}
