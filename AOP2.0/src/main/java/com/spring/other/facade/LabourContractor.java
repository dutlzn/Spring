package com.spring.other.facade;

import com.spring.other.facade.subclasses.BrickLayer;
import com.spring.other.facade.subclasses.BrickWorker;
import com.spring.other.facade.subclasses.Mason;

public class LabourContractor {
    private Mason worker1 = new Mason();
    private BrickWorker worker2 = new BrickWorker();
    private BrickLayer worker3 = new BrickLayer();

    public void buildHouse() {
        worker1.mix();
        worker2.carry();
        worker3.neat();
    }
}
