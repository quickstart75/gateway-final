package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.enhanced.TableGenerator;

public class PrimaryKeyGenerator extends TableGenerator {

   
    public synchronized Serializable generate(SessionImplementor session, Object obj){
    //public Serializable generate(final SharedSessionContractImplementor session, final Object obj){
        return (Long) super.generate(session, obj) + 1;
    }
}