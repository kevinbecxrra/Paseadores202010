/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Clase adaptador que formatea las fechas en formato serialiable utilizando la
 * convención: yyyy-MM-dd hh:mm a. Ej: 2018-02-12 10:15 PM
 * @author Daniel Mateo Guatibonza Solano
 */
public class TimeStampAdapter extends XmlAdapter<String, Date>{
    
    private static final Logger LOGGER = Logger.getLogger(TimeStampAdapter.class.getName());
    
    /**
     * Thread safe {@link DateFormat}.
     */
    private static final ThreadLocal<DateFormat> DATE_FORMAT_TL = new ThreadLocal<DateFormat>() {

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        }
    };

    @Override
    public Date unmarshal(String v) throws Exception {
        LOGGER.log(Level.INFO, "input date "+v);
        return DATE_FORMAT_TL.get().parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        if (v == null) {
            return null;
        }
        return DATE_FORMAT_TL.get().format(v);
    }
}
