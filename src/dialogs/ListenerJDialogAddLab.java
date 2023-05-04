package dialogs;

import MainFrame.studentTable.StudentLabTableModel;
import database.dao.impl.LabDaoImpl;
import entity.Group;
import entity.Lab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerJDialogAddLab implements ActionListener {
	private JDialogAddLab jDialogAddLab;
	public ListenerJDialogAddLab(JDialogAddLab jDialogAddLab){
		this.jDialogAddLab = jDialogAddLab;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jDialogAddLab.addButton) {
			Group group = ((Group) jDialogAddLab.getGroupComboBox().getSelectedItem());
			Lab lab = new Lab(jDialogAddLab.getRoomField().getText(),
					jDialogAddLab.getDateChooser().getDate(),group.getId(), jDialogAddLab.getNameField().getText());
			long labID = LabDaoImpl.getInstance().save(lab);
			if(labID!= -1){
				lab.setId(labID);
				group.getLabs().add(lab);
				System.out.println("Лабораторная добавлена");
				jDialogAddLab.mainWindow.getStudentTable().setModel(new StudentLabTableModel(group));
			}
		}
	}
}
