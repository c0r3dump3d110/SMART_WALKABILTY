package com.fstm.coredumped.smartwalkabilty.core.routing.model.bo;

import java.util.HashSet;
import java.util.Set;

public abstract class Subject
{
    Set<Observer> observers=new HashSet<Observer>();
    public void Attach(Observer observer)
    {
        observers.add(observer);
    }
    public void Detach(Observer observer)
    {
        observers.remove(observer);
    }
    public void NotifyAll(){
        observers.forEach(Observer::update);
    }
}
