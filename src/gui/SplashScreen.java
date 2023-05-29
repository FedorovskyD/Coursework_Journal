package gui;

import utils.PhotoUtils;

import javax.swing.*;
import java.awt.*;

/**
 * SplashScreen - класс, представляющий заставку приложения.
 * Наследуется от класса JFrame.
 */
public class SplashScreen extends JFrame {
	private final JButton jbtnNext;

	public JButton getJbtnNext() {
		return jbtnNext;
	}
	JLabel jlblBntu, jlblFitr, jlblDepartment, jlblCourseWork, jlblDiscipline, jlblCourseWorkName, jlblAuthorInfo,
			jlblAuthor1Initials, jlblAuthor2Initials, jlblReviewerInfo, jlblReviewerInitials, jlblPlace, jlblImage;
	JPanel jpanelTop, jpanelTitle, jpanelMiddle, jpanelPlace, jpanelButtons, jpanelBntu, jpanelFitr, jpanelDepartment,
			jpanelCourseWork, jpanelDiscipline, jpanelTitleName, jpanelIconImage, jpanelOtherInfo, jpanelAuthorInfo, jpanelReviewerInfo;
	/**
	 * Конструктор класса SplashScreen.
	 * Создает окно заставки и инициализирует все компоненты.
	 */
	public SplashScreen() {

		setSize(800, 700);
		setLocationRelativeTo(null);

		setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

		/*Создаем все лейблы*/
		jlblBntu = new JLabel("Белорусский национальный технический универистет",SwingConstants.CENTER);
		jlblFitr = new JLabel("Факультет информационных технологий и робототехники");
		jlblDepartment = new JLabel("Кафедра программного обеспечения информационных информационных систем и технологий",SwingConstants.CENTER);
		jlblCourseWork = new JLabel("Курсовая Работа",SwingConstants.CENTER);
		jlblDiscipline = new JLabel("по дисциплине \"Программирование на Java\"",SwingConstants.CENTER);
		jlblCourseWorkName = new JLabel("Электронный журнал",SwingConstants.CENTER);
		jlblCourseWorkName.setForeground(new Color(0x2F70AF));
		jlblAuthorInfo = new JLabel("Выполнили: студенты группы 10702420");
		jlblAuthor1Initials = new JLabel("Синявская Ульяна Александровна");
		jlblAuthor2Initials = new JLabel("Федоровский Дмитрий Александрович");
		jlblReviewerInfo = new JLabel("Преподаватель:к.ф.-м.н.,доц.");
		jlblReviewerInitials = new JLabel("Сидорик Валерий Владимирович");
		jlblPlace = new JLabel("Минск, 2023",SwingConstants.CENTER);
		Font font = new Font("Calibri", Font.BOLD, 18);
		Font font2 = new Font("Calibri", Font.BOLD, 16);
		Font font3 = new Font("Calibri", Font.BOLD, 22);

		jlblBntu.setFont(font);
		jlblFitr.setFont(font);
		jlblDepartment.setFont(font2);
		jlblCourseWork.setFont(font2);
		jlblDiscipline.setFont(font2);
		jlblCourseWorkName.setFont(font3);
		jlblAuthorInfo.setFont(font2);
		jlblAuthor1Initials.setFont(font2);
		jlblAuthor2Initials.setFont(font2);
		jlblReviewerInfo.setFont(font2);
		jlblReviewerInitials.setFont(font2);
		jlblPlace.setFont(font2);

		/*Создаем панели*/
		jpanelTop = new JPanel(new BorderLayout());
		jpanelTitle = new JPanel(new BorderLayout());
		jpanelMiddle = new JPanel(new GridLayout(1,2,15,0));
		jpanelPlace = new JPanel(new BorderLayout());

		jpanelButtons = new JPanel(new FlowLayout());

		/*Добавляем панели на фрейм*/
		add(jpanelTop,0);
		add(jpanelTitle,1);
		add(jpanelMiddle,2);
		add(jpanelPlace,3);
		add(jpanelButtons,4);

		/*Добавляем лейблы на панели, задаем высоту ширину*/
		jpanelBntu = new JPanel();
		jpanelBntu.setPreferredSize(new Dimension(WIDTH,35));
		jpanelBntu.add(jlblBntu);

		jpanelFitr = new JPanel();
		jpanelFitr.setPreferredSize(new Dimension(WIDTH,25));
		jpanelFitr.add(jlblFitr);

		jpanelDepartment = new JPanel();
		jpanelDepartment.setPreferredSize(new Dimension(WIDTH,40));
		jpanelDepartment.add(jlblDepartment);

		/*Добавляем 3 панели на topJP*/
		jpanelTop.add(jpanelBntu,BorderLayout.NORTH);
		jpanelTop.add(jpanelFitr,BorderLayout.CENTER);
		jpanelTop.add(jpanelDepartment,BorderLayout.SOUTH);

		/*Добавляем лейблы на панели, задаем высоту ширину*/
		jpanelCourseWork = new JPanel();
		jpanelCourseWork.add(jlblCourseWork);

		jpanelDiscipline = new JPanel();
		jpanelDiscipline.add(jlblDiscipline);

		jpanelTitleName = new JPanel();
		jpanelTitleName.add(jlblCourseWorkName);

		/*Добавляем 3 панели на titleJP*/
		jpanelTitle.add(jpanelCourseWork,BorderLayout.NORTH);
		jpanelTitle.add(jpanelDiscipline,BorderLayout.CENTER);
		jpanelTitle.add(jpanelTitleName,BorderLayout.SOUTH);

		/*Добавляем 3 панели на middleJP*/
		jpanelIconImage = new JPanel();
		ImageIcon logoImage = PhotoUtils.getInstance().loadImageIconFromProperties("teacher.png");
		jlblImage = new JLabel(new ImageIcon(logoImage.getImage().getScaledInstance(170,170,Image.SCALE_SMOOTH)));
		jpanelIconImage.add(jlblImage);

		jpanelOtherInfo = new JPanel(new BorderLayout());
		jpanelAuthorInfo = new JPanel();

		jpanelAuthorInfo.setLayout(new BoxLayout(jpanelAuthorInfo,BoxLayout.PAGE_AXIS));
		jpanelAuthorInfo.add(jlblAuthorInfo);
		jpanelAuthorInfo.add(Box.createVerticalStrut(5));
		jpanelAuthorInfo.add(jlblAuthor1Initials);
		jpanelAuthorInfo.add(Box.createVerticalStrut(5));
		jpanelAuthorInfo.add(jlblAuthor2Initials);
		jpanelAuthorInfo.add(Box.createVerticalStrut(5));
		jpanelAuthorInfo.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

		jpanelReviewerInfo = new JPanel();
		jpanelReviewerInfo.setLayout(new BoxLayout(jpanelReviewerInfo,BoxLayout.PAGE_AXIS));
		jpanelReviewerInfo.add(jlblReviewerInfo);
		jpanelAuthorInfo.add(Box.createVerticalStrut(25));
		jpanelReviewerInfo.add(jlblReviewerInitials);

		jpanelOtherInfo.add(jpanelAuthorInfo, BorderLayout.NORTH);
		jpanelOtherInfo.add(jpanelReviewerInfo, BorderLayout.CENTER);

		jpanelMiddle.add(jpanelIconImage,0);
		jpanelMiddle.add(jpanelOtherInfo,1);

		/*SETTING UP PLACE PANEL*/
		jpanelPlace.add(jlblPlace, BorderLayout.CENTER);
		jpanelPlace.setPreferredSize(new Dimension(WIDTH,6));

		jbtnNext = new JButton("Далее");
		jbtnNext.setPreferredSize(new Dimension(150,40));
		jbtnNext.setBackground(new Color(0x2F70AF));
		jbtnNext.setForeground(Color.white);
		jbtnNext.setFont(new Font("Calibri", Font.BOLD, 18));
		jpanelButtons.add(jbtnNext);
		setVisible(true);
	}
}







