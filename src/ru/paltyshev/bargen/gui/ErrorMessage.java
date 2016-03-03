package ru.paltyshev.bargen.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * Created by kd130487pas on 29.02.16.
 */
public class ErrorMessage {
    public ErrorMessage() {
    }
    public void open(String message) {
        Shell parent = new Shell(Display.getCurrent().getShells()[0]);
        MessageBox messageBox = new MessageBox(parent, SWT.ICON_ERROR);

        messageBox.setMessage(message);
        messageBox.open();
    }
}
