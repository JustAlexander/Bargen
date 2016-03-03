package ru.paltyshev.bargen.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ru.paltyshev.bargen.BarcodeGenerator;
import ru.paltyshev.bargen.Utils;
import ru.paltyshev.bargen.exeption.InvalidBarcodeNumberException;
import ru.paltyshev.bargen.exeption.RangeBarcodeNumbersException;

/**
 * Created by kd130487pas on 29.02.16.
 */
public class MainWindow {

    private Text textSingle;
    private Text textSome;
    private Text textFirst;
    private Text textLast;
    BarcodeGenerator barcodeGenerator = new BarcodeGenerator(this);

    public int getSingleNumber() throws InvalidBarcodeNumberException {
        return Utils.inputValidation(textSingle.getText());
    }

    public int getSomeCount() throws InvalidBarcodeNumberException {
        return Utils.inputValidation(textSome.getText());
    }

    public int getFirstNumber() throws InvalidBarcodeNumberException {
        return Utils.inputValidation(textFirst.getText());
    }

    public int getLastNumber() throws InvalidBarcodeNumberException {
        return Utils.inputValidation(textLast.getText());
    }


    public MainWindow() {
        initControls();

    }

    private void initControls() {
        Display display = new Display();
        Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE));
        shell.setText("Генератор штрих-кодов Code 128");

        GridLayout layout = new GridLayout(6, false);
        layout.marginLeft = 10;
        layout.marginRight = 10;
        layout.marginTop = 5;
        layout.marginBottom = 5;
        shell.setLayout(layout);

        Label labelSingle =  new Label(shell, SWT.NONE);
        labelSingle.setText("№ AR");

        textSingle = new Text(shell, SWT.NONE);

        Button buttonSingle = new Button(shell, SWT.PUSH);
        buttonSingle.setText("Создать");
        buttonSingle.addListener(SWT.Selection, event -> {
            try {
                barcodeGenerator.setFileName(getSingleNumber());
                barcodeGenerator.createSingleBarcode(getSingleNumber());
            } catch (InvalidBarcodeNumberException invalidBarcodeNumberException) {
                new ErrorMessage().open("Ошибка при вводе номера штрихкода");
            }
        });

        Label labelSome = new Label(shell, SWT.NONE);
        labelSome.setText("Кол-во");

        textSome = new Text(shell, SWT.NONE);

        Button buttonSome = new Button(shell, SWT.PUSH);
        buttonSome.setText("Создать");
        buttonSome.addListener(SWT.Selection, event -> {
            try {
                barcodeGenerator.setFileName(getSingleNumber());
                barcodeGenerator.createSomeBarcode(getSingleNumber(), getSomeCount());
            } catch (InvalidBarcodeNumberException invalidBarcodeNumberException) {
                new ErrorMessage().open("Ошибка при вводе номера штрихкода или количества");
            }
        });

        // 2 line

        Label labelFirst = new Label(shell, SWT.NONE);
        labelFirst.setText("№ AR от");

        textFirst = new Text(shell, SWT.NONE);

        Label l = new Label(shell, SWT.NONE);

        Label labelLast = new Label(shell, SWT.NONE);
        labelLast.setText("№ AR от");

        textLast = new Text(shell, SWT.NONE);

        Button buttonRange = new Button(shell, SWT.PUSH);
        buttonRange.setText("Создать");
        buttonRange.addListener(SWT.Selection, event -> {
            try {
                Utils.twoNumbersValidation(getFirstNumber(), getLastNumber());
                barcodeGenerator.setFileName(getFirstNumber(), getLastNumber());
                barcodeGenerator.createRangeBarcode(getFirstNumber(), getLastNumber());
            } catch (InvalidBarcodeNumberException invalidBarcodeNumberException) {
                new ErrorMessage().open("Ошибка при вводе номера штрихкода");
            } catch (RangeBarcodeNumbersException rangeBarcodeNumbersException) {
                new ErrorMessage().open("Номер второго штрихкода должен быть больше первого");
            }
        });

        shell.pack();
        shell.open();
        updateCurentBarcodText();
        while (!shell.isDisposed()) {

            if (!display.readAndDispatch())
            {

                display.sleep();
            }
        }


        display.dispose();
    }

    public void updateCurentBarcodText() {
        textSingle.setText(String.valueOf(Utils.getCountOfBarcode()));
    }

}
