package org.example.view.widget;

import org.example.model.field.FieldObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class FieldObjectWidget extends JPanel {

    private FieldObject fieldObject;

    public FieldObjectWidget(FieldObject fieldObject) {
        this.fieldObject = fieldObject;
        setOpaque(false);
        setPreferredSize(getDimension());
    }

    public FieldObject getFieldObject() {
        return fieldObject;
    }

    /**
    * Получить изображение виджета
    * @return изображение виджета
    */
    protected abstract BufferedImage getImage();

    protected abstract File getImageFile();

    protected Dimension getDimension() {
        int size = fieldObject.getSizeCollisionModel();
        return new Dimension(size, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, this);
    }
}
