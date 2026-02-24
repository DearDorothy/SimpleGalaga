package org.example.view;

import org.example.model.event.*;
import org.example.model.field.Bullet;
import org.example.model.field.Field;
import org.example.model.field.FieldObject;
import org.example.model.field.Ship;
import org.example.view.widget.BulletWidget;
import org.example.view.widget.FieldObjectWidget;
import org.example.view.widget.ShipWidget;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FieldWidget extends JPanel {

    private Field field;
    private Map<FieldObject, FieldObjectWidget> objectWidgetMap = new HashMap<>();

    public FieldWidget(Field field) {
        this.field = field;
        setBackground(Color.BLACK);
        setLayout(null);
        setPreferredSize(new Dimension(field.getWidth(), field.getHeight()));
        subscribeToEvents();
    }

    public Field getField() {
        return field;
    }

    private void subscribeToEvents() {
        field.addFieldObjectListener(new FieldObjectObserver());
    }

    private void addWidget(FieldObject object) {
        FieldObjectWidget widget = null;
        if (object instanceof Ship) {
            widget = new ShipWidget((Ship) object);
        } if (object instanceof Bullet) {
            widget = new BulletWidget((Bullet) object);
        }
        if (widget != null) {
            objectWidgetMap.put(object, widget);
            add(widget);
        }
    }

    private void removeWidget(FieldObject object) {
        FieldObjectWidget widget = objectWidgetMap.remove(object);
        if (widget != null) {
            remove(widget);
        }
    }

    private void updateWidgetBounds(FieldObject obj) {
        FieldObjectWidget widget = objectWidgetMap.get(obj);
        if (widget != null) {
            int x = obj.getPoint().getX();
            int y = obj.getPoint().getY();
            int size = obj.getSizeCollisionModel();
            widget.setBounds(x, y, size, size);
        }
    }

    private class FieldObjectObserver implements FieldObjectListener {
        @Override
        public void addFieldObjectOnField(FieldObjectEvent event) {
            FieldObject fieldObject = event.getFieldObject();
            addWidget(fieldObject);
            updateWidgetBounds(fieldObject);
        }

        @Override
        public void removeFieldObjectFromField(FieldObjectEvent event) {
            FieldObject object = event.getFieldObject();
            removeWidget(object);
        }

        @Override
        public void fieldObjectIsMoved(FieldObjectEvent event) {
            FieldObject object = event.getFieldObject();
            updateWidgetBounds(object);
        }
    }
}
