package org.example.model.manager;

import org.example.model.ActionPilot;
import org.example.model.DirectionObjectMovment;

public interface PilotManager {
    void shipControl(ActionPilot actionPilot, DirectionObjectMovment directionObjectMovment);
}
