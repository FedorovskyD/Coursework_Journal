package dialogs;

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
			long groupID = ((Group) jDialogAddLab.getGroupComboBox().getSelectedItem()).getId();
			Lab lab = new Lab(jDialogAddLab.getRoomField().getText(),
					jDialogAddLab.getDateChooser().getDate(),groupID, jDialogAddLab.getNameField().getText());
			LabDaoImpl.getInstance().save(lab);
		}
	}
}
