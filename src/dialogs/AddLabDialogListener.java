package dialogs;

import database.dao.impl.LabDaoImpl;
import dialogs.AddLabDialog;
import entity.Group;
import entity.Lab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddLabDialogListener implements ActionListener {
	private AddLabDialog addLabDialog;
	public AddLabDialogListener (AddLabDialog addLabDialog){
		this.addLabDialog = addLabDialog;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addLabDialog.addButton) {
			long groupID = ((Group)addLabDialog.getGroupComboBox().getSelectedItem()).getId();
			Lab lab = new Lab(addLabDialog.getRoomField().getText(),
					addLabDialog.getDateChooser().getDate(),groupID,addLabDialog.getNameField().getText());
			LabDaoImpl.getInstance().save(lab);
		}
	}
}
