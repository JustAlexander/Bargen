package ru.paltyshev.bargen.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import java.nio.file.Paths;

/**
 * Created by kd130487pas on 29.02.16.
 */
public class SaveDialog {
    public SaveDialog() {
    }
    public String open(String fileName) {
        Shell parent = new Shell(Display.getCurrent().getShells()[0]);
        FileDialog dialog = new FileDialog(parent, SWT.SAVE);
        dialog.setText("Сохранить как");
        dialog.setFilterNames(new String[] {"PDF файлы", "Все файлы (*.*)"});
        dialog.setFilterExtensions(new String[] {"*.pdf", "*.*"});
        dialog.setFilterPath(Paths.get("").toAbsolutePath().toString());
        dialog.setFileName(fileName);
        return dialog.open();
    }
}
