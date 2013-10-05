package wuziqi;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Mydialog extends Dialog {

	public Mydialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	protected Point getInitialSize() {
		return new Point(520, 340);
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		Display display = parent.getDisplay();
		composite.getShell().setText("关于\"五子棋\"");
		Label label3 = new Label(composite, SWT.NONE);
		label3.setImage(new Image(display, "Iconico.ico"));
		Composite panel = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		panel.setLayout(layout);
		Label label1 = new Label(panel, SWT.NONE);
		label1.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		label1.setText("版本2.1.1\nBuild id：20130929-2323");
		Label label4=new Label(panel,SWT.SEPARATOR|SWT.HORIZONTAL);
		GridData  gridData  =  new  GridData(GridData.HORIZONTAL_ALIGN_FILL);
		label4.setLayoutData(gridData);
		Label label2 = new Label(panel, SWT.NONE);
		label2.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		label2.setText("版权所有@2013 God'son。保留所有权利。");
		return composite;
	}

	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		return null;
	}

	protected void initializeBounds() {
		// 我们可以利用原有的ID创建按钮,也可以用自定义的ID创建按钮
		// 但是调用的都是父类的createButton方法.
		super.createButton((Composite) getButtonBar(), IDialogConstants.OK_ID,
				"确定", false);
		// 下面这个方法一定要加上,并且要放在创建按钮之后,否则就不会创建按钮
		super.initializeBounds();
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			// 做我们想做的事情
			this.close();
		}
		// 事后要记得调用父类的buttonPressed方法
		super.buttonPressed(buttonId);
	}

}
